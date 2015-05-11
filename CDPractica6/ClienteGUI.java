
import javax.swing.text.DefaultCaret;


public class ClienteGUI extends javax.swing.JFrame {

    // Variables declaration - do not modify                     
    private javax.swing.JTextArea areaMensajes;
    private javax.swing.JButton buttonSuscribir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelServidor;
    private javax.swing.JLabel labelTiempoSuscripcion;
    private javax.swing.JSpinner spinnerTiempoSuscripcion;
    private javax.swing.JTextField txtServidor;
    // End of variables declaration  

    private Cliente cliente;

    public ClienteGUI(Cliente cliente) {
        this.cliente = cliente;
        initComponents();
        
        DefaultCaret caret = (DefaultCaret) areaMensajes.getCaret();  // automatic scroll down
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);    

        this.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent){
                finalizar();
            }
        });
    }

    private void buttonSuscribirActionPerformed(java.awt.event.ActionEvent evt) {                                                
        Integer tiempo;
        String servidor;

        tiempo = Integer.parseInt(spinnerTiempoSuscripcion.getValue().toString());
        servidor = (String) txtServidor.getText();

        tiempo*=2;  // 2hz -> 0,5 segundos -> 2 mensajes de suscripcion por segundo
        if(this.cliente.puedeSuscribir){                            // Comprueba que no hay una suscripcion en curso
            if(servidor!= null && servidor.length() > 0)
                this.cliente.nuevaSuscripcion(tiempo, servidor);
        }else {
            addMensaje("ERROR: ya hay una suscripcion en curso\nDebe finalizar antes de iniciar otra");
        }
    }                                               

    public void addMensaje(String mensaje){
        this.areaMensajes.append(mensaje+"\n");
    }

    public void finalizar(){ this.cliente.finalizar(); }


   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        areaMensajes = new javax.swing.JTextArea();
        labelTiempoSuscripcion = new javax.swing.JLabel();
        spinnerTiempoSuscripcion = new javax.swing.JSpinner();
        buttonSuscribir = new javax.swing.JButton();
        labelServidor = new javax.swing.JLabel();
        txtServidor = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cliente");

        areaMensajes.setColumns(20);
        areaMensajes.setRows(5);
        jScrollPane1.setViewportView(areaMensajes);

        labelTiempoSuscripcion.setText("Tiempo suscripcion:");

        spinnerTiempoSuscripcion.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        buttonSuscribir.setText("Suscribir");
        buttonSuscribir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSuscribirActionPerformed(evt);
            }
        });

        labelServidor.setText("Servidor:");

        txtServidor.setText("Servidor");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelServidor)
                                .addGap(35, 35, 35)
                                .addComponent(txtServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelTiempoSuscripcion)
                                .addGap(18, 18, 18)
                                .addComponent(spinnerTiempoSuscripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonSuscribir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelTiempoSuscripcion)
                            .addComponent(spinnerTiempoSuscripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelServidor)
                            .addComponent(txtServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(28, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonSuscribir)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>                        
             

           
}
