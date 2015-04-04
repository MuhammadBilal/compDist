
package cliente;

import java.awt.Color;
import java.io.*;
import java.rmi.*;

public class Application extends javax.swing.JFrame {

    public LoginPanel login;
    public ListPanel list;
    private static String hostName = "localhost";
    private static String portNum = "3456";
    private Integer time = 0;
    private ServerInterface h;
    private ClientInterface callbackObj;
    private boolean flag = false;
    
    public Application() {
        initComponents();
        this.getContentPane().setBackground(new Color(18, 15, 102));
        
        login = new LoginPanel(this);
        this.setContentPane(login);
        this.invalidate();
        this.validate();
    }

    public void startClient(String user, String pass){
        try{
            int RMIPort;
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader bf = new BufferedReader(is);
            
            RMIPort = Integer.parseInt(portNum);
            String registryURL = "rmi://localhost:"+portNum+"/callback";
            
            h=(ServerInterface)Naming.lookup(registryURL);
            System.out.println("Lookup completed");
                    
            callbackObj = new ClientImp(this, user, pass);
        }catch(Exception e){
            
        }
    }

    public void loged(){
        this.setContentPane(new ListPanel(this));
        this.invalidate();
        this.validate();   
                
        ChatFrame c = new ChatFrame();
        c.setAlwaysOnTop(true);
        c.setVisible(true);
    }
    
    public void logout(){
        this.setContentPane(new LoginPanel(this));
        this.invalidate();
        this.validate();
    }
    
    public void errorLogin(){
        this.login.setError("El nombre de usuario y/o la contraseña son incorrectos");
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
