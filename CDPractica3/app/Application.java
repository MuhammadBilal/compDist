
import java.awt.Color;
import java.io.*;
import java.rmi.*;
import java.util.HashMap;
import java.util.ArrayList;

public class Application extends javax.swing.JFrame {

    private static String hostName = "localhost";
    private static String portNum = "3456";

    public LoginPanel login;
    public ListPanel list;

    private ServerInterface h;
    private ClientInterface callbackObj;
    private PeerInterface peerObj;

    public Color blueBackground = new Color(18, 15, 102);
    private HashMap<String, PeerInterface> friends;
    private HashMap<String, ChatFrame> chatsOn;
    public String user;
    
    public Application() {
        initComponents();
        this.setLocationRelativeTo(null); // center of the screen
        this.getContentPane().setBackground(blueBackground);
        
        reset();

        this.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if(callbackObj!=null) { logout(); }
            }
        });
        
    }

    public void reset(){
        login = new LoginPanel(this);
        this.setContentPane(login);
        this.invalidate();
        this.validate();

        this.friends = new HashMap<>();
        this.chatsOn = new HashMap<>();
    }

    public void startClient(String user, String pass){
      
       try{
            int RMIPort;
            this.user = user;
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader bf = new BufferedReader(is);
            
            RMIPort = Integer.parseInt(portNum);
            String registryURL = "rmi://localhost:"+portNum+"/callback";
            
            h=(ServerInterface)Naming.lookup(registryURL);
            System.out.println("Lookup completed");
                    
            callbackObj = new ClientImp(this, user, pass);
            peerObj = new ClientImp(this, user);

        }catch(Exception e){
            this.login.setError("ERROR: no se pudo conectar con el servidor");
        }


      try{
         h.register(callbackObj, peerObj);
         System.out.println("Registered for callback");
         
      }catch(Exception e){
         System.out.println("ERROR: register for callback exception: "+e.getMessage());
      }

    }

    public void loged(){
        this.setContentPane(list = new ListPanel(this, this.user));
        this.invalidate();
        this.validate();   
    }

    public void alreadyLogged() {
        try{
            this.login.setError("ERROR: ya se ha iniciado sesion en otra aplicacion");
        }catch(Exception e){
            System.out.println("Error alreadyLogged: "+e);
        }
    }
    
    public void logout(){

        try{
            System.out.println("Unregistered for callback");
            h.unregister(callbackObj);
        }catch(Exception e){
            System.out.println("ERROR: unregister for callback exception: "+e.getMessage());
        }

        for (ChatFrame c : chatsOn.values()) {
            c.dispose();
        }

        list = null;
        this.friends = null;
        callbackObj = null;
        peerObj = null;
        h= null;
        chatsOn = null;
        reset();
    }
    
    public void errorLogin(){
        this.login.setError("El nombre de usuario y/o la contraseña son incorrectos");
    }

    public void addUser(String username){
        try{
            h.searchUser(callbackObj, username);
            System.out.println("USUARIO A BUSCAR: "+username);
        }catch(Exception e){
            System.out.println("Exception searchUser: "+e.getMessage());
        }
       
    }

    public void setNotification(String notification){
        System.out.println("NOTIFICACION: "+notification);
        this.list.setNotification(notification);
    }

    public void setError(String error){
        System.out.println("ERROR: "+error);
        this.list.setError(error);
    }

    public void acceptedRequest(String username) {
        try {
            h.acceptedRequest(callbackObj, username);
        } catch (Exception e) {
            System.out.println("Exeption at acceptedRequest: "+ e.getMessage());
        }
    }

    public void rejectedRequest(String username) {
        try {
            h.rejectedRequest(callbackObj, username);
        } catch (Exception e) {
            System.out.println("Exeption at rejectedRequest: "+ e.getMessage());
        }
    }

    public void updateFriendList(ArrayList<PeerInterface> friendlist) throws RemoteException {
        
        int size = friendlist.size();
        if (size < 0) { 
            return; // no conected friends
        }

        String[] names = new String[size];
        String name = "";

        // Updates friend list in the app
        if (this.friends == null) this.friends = new HashMap<String, PeerInterface>();
        for (int i = 0; i < size; i++) {
            PeerInterface peer = (PeerInterface) friendlist.get(i);
            name = peer.getUser();
            this.friends.put(name, peer);
            names[i] = name;
        }

        list.updateConectedUsers(names);
        
    }

    public void updateFriends(){
        int i=0, size = this.friends.size();
        if(size < 0){ return; }

        String[] names = new String[size];

        for(String name : friends.keySet()){
            names[i] = name;
            i++;
        }

        list.updateConectedUsers(names);
    }

    public void connectedUser(PeerInterface friend) throws RemoteException {
        String name = friend.getUser();
        this.friends.put(name, friend);
        setNotification(name+" se ha conectado.");

        if(!chatsOn.isEmpty() && chatsOn.containsKey(name)){
            ChatFrame chat = chatsOn.get(name);
            chat.reconnectedUser();
        }

        updateFriends();
    }

    public void disconnectedUser(PeerInterface friend) throws RemoteException {
        String name = friend.getUser();
        this.friends.remove(name);
        setNotification(name+" se ha desconectado.");

        if(!chatsOn.isEmpty() && chatsOn.containsKey(name)){
            ChatFrame chat = chatsOn.get(name);
            chat.disconnectedUser();
        }

        updateFriends();
    }

    public void receiveRequestNotification(String clientFrom) throws RemoteException {
        RequestFrame r = new RequestFrame(this, clientFrom);
        r.setVisible(true);
    }

    public void startChat(String friend) {
        PeerInterface friendInt;
        ChatFrame chat;

        if(chatsOn.isEmpty() || !chatsOn.containsKey(friend)){
            friendInt = (PeerInterface) friends.get(friend);

            chat = new ChatFrame(this, friendInt);
            chat.setVisible(true);

            chatsOn.put(friend, chat);
       }else{
            chat = chatsOn.get(friend);

            if(chat.isChatClosed()){
                chat.openChat();
            }
        }
    }

    public void sendMessage(PeerInterface friend, String message){
        
        try{
            friend.sendMessage(peerObj, message);
            receiveMessage(friend, message);
        }catch(Exception e){
            System.out.println("Exception sendMessage: "+e.getMessage());
        }
    }

    public void receiveMessage(PeerInterface friend, String msg) throws RemoteException { 

        String friendName = friend.getUser();
        ChatFrame chat;

        if(chatsOn.isEmpty() || !chatsOn.containsKey(friendName)){
            chat = new ChatFrame(this, friend);
            chat.setVisible(true);

            chatsOn.put(friendName, chat);
        }else{
            chat = (ChatFrame) chatsOn.get(friendName);
            if(chat.isChatClosed()){
                chat.openChat();
            }
        }
        chat.appendMessage(msg);
    }

    public boolean noFriendsOnline(){
        return friends.isEmpty();
    }

    public boolean isConnected(String username){
        return friends.containsKey(username);
    }
    
    public void deleteChat(String username){
        friends.remove(username);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MeteøChat");
        setBackground(new java.awt.Color(18, 15, 102));
        setPreferredSize(new java.awt.Dimension(370, 530));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 535, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Application().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
