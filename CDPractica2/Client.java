import java.awt.Color;
import java.io.*;
import java.rmi.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultCaret.*;

public class Client extends javax.swing.JFrame {

   private static String hostName = "localhost";
   private static String portNum = "3456";
   private Integer time = 0;
   private ServerInterface h;
   private ClientInterface callbackObj;
   private boolean flag = false;

    public Client() {
        initComponents();

        DefaultCaret caret = (DefaultCaret) TextArea.getCaret();  // automatic scroll down
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);         

        this.getContentPane().setBackground(new Color(102, 153, 255));
        startClient();
    } 

   public void startClient(){

    try {
          int RMIPort;         
          InputStreamReader is = new InputStreamReader(System.in);
          BufferedReader br = new BufferedReader(is);
         
          RMIPort = Integer.parseInt(portNum); 
          String registryURL = "rmi://localhost:" + portNum + "/callback";  
          
          // find the remote object and cast it to an 
          //   interface object
          h =(ServerInterface)Naming.lookup(registryURL);
          System.out.println("Lookup completed " );

          //System.out.println("Server said " + h.sayHello());
          callbackObj = new ClientImpl(this);
          
        } 
        catch (Exception e) {
          System.out.println("Exception in Client: " + e);
          TextArea.append("\nERROR: no se pudo conectar con el servidor:\n"+e+"\n\n");
        }

   }
    
    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });   
        
    }


    private void BtnSusActionPerformed(java.awt.event.ActionEvent evt) {                                       
        //Accion del boton suscribir
        if(SpinTime.getValue()!=null && (Integer)SpinTime.getValue() >0){
            System.out.println("Tiempo suscripcion"+SpinTime.getValue());
            time = (Integer)SpinTime.getValue();
            try{
               h.registerForCallback(callbackObj, time);
               System.out.println("Registered for callback.");
               flag = true;
            }catch(Exception e){
               System.out.println("ERROR: register for callback exception: "+e.getMessage());
            }
            TextArea.append("\n - Suscripción iniciada -\n - Tiempo: "+time+" segundos -\n\n");
        }else{
           TextArea.append("\nERROR: imposible realizar suscripción si\n tiempo es inferior o igual a 0\n\n");
        }
    }                                      

    private void BtnRenActionPerformed(java.awt.event.ActionEvent evt) {                                       
    //Accion del boton renovar
      if(flag){
         if(SpinTime.getValue()!=null && (Integer)SpinTime.getValue() >=0){
               System.out.println("Tiempo suscripcion: "+SpinTime.getValue());
               time = (Integer)SpinTime.getValue();
               try{
                  h.unregisterForCallback(callbackObj);
                  h.registerForCallback(callbackObj, time);
               }catch(Exception e){
                  System.out.println("ERROR: register for callback exception: "+e.getMessage());
               }
         }
         TextArea.append("\n - Suscripcion renovada -\n - por "+time+" segundos -\n\n");
       }else{
         TextArea.append("\nERROR: Todavia no se ha realizado una suscripción.\nImposible renovar.\n\n");
       }
    }                                      

    private void BtnCanActionPerformed(java.awt.event.ActionEvent evt) {                                       
    //Accion del boton cancelar
      if(flag){
          try{
            System.out.println("Unregister for callback");
            h.unregisterForCallback(callbackObj);
          }catch(Exception e){
               System.out.println("ERROR: unregister for callback exception: "+e.getMessage());
          } 
          //TextArea.append("\n - Suscripción cancelada -\n\n");
      }else{
         TextArea.append("\nError: Todavia no se ha realizado una suscripción.\nImposible cancelar.\n\n");
      }
    }    

    public void appendText(String txt){
         TextArea.append(txt+"\n");
    }

    //--------------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TextArea = new javax.swing.JTextArea();
        BtnSus = new javax.swing.JButton();
        BtnRen = new javax.swing.JButton();
        BtnCan = new javax.swing.JButton();
        SpinTime = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cliente");

        TextArea.setColumns(20);
        TextArea.setRows(5);
        jScrollPane1.setViewportView(TextArea);

        BtnSus.setText("Suscribir");
        BtnSus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSusActionPerformed(evt);
            }
        });

        BtnRen.setText("Renovar");
        BtnRen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRenActionPerformed(evt);
            }
        });

        BtnCan.setText("Cancelar");
        BtnCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCanActionPerformed(evt);
            }
        });

        jLabel1.setText("TIempo de suscripción:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(SpinTime, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BtnSus, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(BtnRen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnCan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BtnSus)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnRen)
                    .addComponent(SpinTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnCan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

                                  

    // Variables declaration - do not modify                     
    private javax.swing.JButton BtnCan;
    private javax.swing.JButton BtnRen;
    private javax.swing.JButton BtnSus;
    private javax.swing.JSpinner SpinTime;
    private javax.swing.JTextArea TextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration                   
}
