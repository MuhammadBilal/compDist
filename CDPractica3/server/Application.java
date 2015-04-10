
import java.awt.Color;
import java.io.*;
import java.rmi.*;
import java.util.HashMap;
import java.util.ArrayList;

public class Application extends javax.swing.JFrame {

    public LoginPanel login;
    public ListPanel list;
    private static String hostName = "localhost";
    private static String portNum = "3456";
    private String user;
    private Integer time = 0;
    private ServerInterface h;
    private ClientInterface callbackObj;
    private PeerInterface peerObj;
    private boolean flag = false;
    private Color blueBackground = new Color(18, 15, 102);
    private HashMap<String, PeerInterface> friends;
    
    public Application() {
        initComponents();
        this.getContentPane().setBackground(blueBackground);
        
        login = new LoginPanel(this);
        this.setContentPane(login);
        this.invalidate();
        this.validate();

        this.friends = new HashMap<>();
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
                
        ChatFrame c = new ChatFrame();
        //c.setAlwaysOnTop(true);
        c.setVisible(true);
    }
    
    public void logout(){

        try{
            System.out.println("Unregistered for callback");
            h.unregister(callbackObj);
        }catch(Exception e){
            System.out.println("ERROR: unregister for callback exception: "+e.getMessage());
        }

        login = new LoginPanel(this);
        this.setContentPane(login);
        this.invalidate();
        this.validate();
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

    public void updateFriendList(ArrayList<PeerInterface> friendlist) throws RemoteException {
        
        int size = friendlist.size();
        if (size < 0) { // antes estaba a 1
            return; // no conected friends
        }

        String[] names = new String[size];
        String name = "";

        //System.out.println("** Debug: " + friendlist.size() + " amigos conectados.");
         
        // Updates friend list in the app
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
        //list.connectedUser(name);
        setNotification(name+" se ha conectado.");
        updateFriends();
    }

    public void disconnectedUser(PeerInterface friend) throws RemoteException {
        String name = friend.getUser();
        this.friends.remove(name);
        //list.disconnectedUser(name);
        setNotification(name+" se ha desconectado.");
        updateFriends();
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
