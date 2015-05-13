
public class GUIVendedor extends javax.swing.JFrame {

    // Variables declaration - do not modify                     
    private javax.swing.JButton buttonCrear;
    private javax.swing.JButton buttonSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelError;
    private javax.swing.JLabel labelLibro;
    private javax.swing.JLabel labelNuevaSubasta;
    private javax.swing.JLabel labelPrecioSalida;
    private javax.swing.JLabel labelSubastas;
    private javax.swing.JSpinner spinnerIncremento;
    private javax.swing.JSpinner spinnerPrecioSalida;
    private javax.swing.JTextField txtLibro;
    // End of variables declaration  

    private Vendedor vendedor;

    public GUIVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
        initComponents();

        this.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent){
                finalizar();
            }
        });
    }

    public void finalizar(){
        this.vendedor.takeDown();
    }

    private void buttonCrearActionPerformed(java.awt.event.ActionEvent evt) {                                            
        nuevaSubasta();
    } 

    private void txtLibroActionPerformed(java.awt.event.ActionEvent evt) {                                         
        nuevaSubasta();
    }                                                    

    private void buttonSalirActionPerformed(java.awt.event.ActionEvent evt) {                                            
        finalizar();
    }     

    public void nuevaSubasta(){
        String libro;
        Integer precio;
        Integer incremento;

        labelError.setText("");

        libro = txtLibro.getText();
        precio = Integer.parseInt(spinnerPrecioSalida.getValue().toString());
        incremento = Integer.parseInt(spinnerIncremento.getValue().toString());

        if(libro != null && libro.length() > 0){
            if(vendedor.subastas != null && vendedor.subastas.size() > 0){
                for(int i = 0; i < vendedor.subastas.size(); i++) {
                    if(vendedor.subastas.get(i).getTituloLibro().equals(libro)){
                        labelError.setText("ERROR: ya hay una subasta de ese libro");
                        return;
                    }   
                }
            }
            vendedor.nuevaSubasta(libro, precio, incremento);
        }else{
            labelError.setText("ERROR: introducir un titulo válido!");
        }

    }                      

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        labelSubastas = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        labelNuevaSubasta = new javax.swing.JLabel();
        labelLibro = new javax.swing.JLabel();
        txtLibro = new javax.swing.JTextField();
        labelPrecioSalida = new javax.swing.JLabel();
        spinnerPrecioSalida = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        spinnerIncremento = new javax.swing.JSpinner();
        buttonCrear = new javax.swing.JButton();
        buttonSalir = new javax.swing.JButton();
        labelError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelSubastas.setText("Subastas en curso:");

        jScrollPane1.setViewportView(jList1);

        labelNuevaSubasta.setText("Nueva subasta:");

        labelLibro.setText("Libro:");

        txtLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLibroActionPerformed(evt);
            }
        });

        labelPrecioSalida.setText("Precio salida:");

        spinnerPrecioSalida.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel1.setText("Incremento:");

        spinnerIncremento.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        buttonCrear.setText("Crear");
        buttonCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCrearActionPerformed(evt);
            }
        });

        buttonSalir.setText("Salir");
        buttonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSalirActionPerformed(evt);
            }
        });

        labelError.setFont(new java.awt.Font("Lucida Grande", 2, 13)); // NOI18N
        labelError.setForeground(new java.awt.Color(255, 0, 0));
        labelError.setText("- ERROR -");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelSubastas)
                            .addComponent(labelNuevaSubasta))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelLibro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtLibro))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(18, 18, 18)
                                                .addComponent(spinnerIncremento, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(labelPrecioSalida)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(spinnerPrecioSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18)
                                        .addComponent(buttonCrear))
                                    .addComponent(labelError))
                                .addGap(0, 110, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(buttonSalir)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(labelSubastas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelNuevaSubasta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLibro)
                    .addComponent(txtLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrecioSalida)
                    .addComponent(spinnerPrecioSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(spinnerIncremento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCrear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelError)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(buttonSalir)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

                 
}
