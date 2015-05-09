


public class GUIVendedor extends javax.swing.JFrame {

    private Vendedor vendedor;
    
    public GUIVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        labelTitulo = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        labelPrecioSalida = new javax.swing.JLabel();
        spinnerPrecioSalida = new javax.swing.JSpinner();
        labelIncremento = new javax.swing.JLabel();
        spinnerIncremento = new javax.swing.JSpinner();
        buttonAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelTitulo.setText("Titulo libro:");

        txtTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloActionPerformed(evt);
            }
        });

        labelPrecioSalida.setText("Precio salida:");

        labelIncremento.setText("Incremento");

        buttonAceptar.setText("Aceptar");
        buttonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(labelIncremento)
                            .addGap(18, 18, 18)
                            .addComponent(spinnerIncremento))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(labelPrecioSalida)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spinnerPrecioSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonAceptar)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTitulo)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrecioSalida)
                    .addComponent(spinnerPrecioSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIncremento)
                    .addComponent(spinnerIncremento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonAceptar)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void txtTituloActionPerformed(java.awt.event.ActionEvent evt) {                                          

    }                                         

    private void buttonAceptarActionPerformed(java.awt.event.ActionEvent evt) {                                              
        String titulo;
        int precioSalida;
        int incremento;

        if((titulo = txtTitulo.getText())!=null)
            this.vendedor.titulo = titulo;

        precioSalida = Integer.parseInt(spinnerPrecioSalida.getValue().toString());
        incremento = Integer.parseInt(spinnerIncremento.getValue().toString());

        this.vendedor.setPrecioSalida(precioSalida);
        this.vendedor.setIncremento(incremento);
        this.vendedor.continuar = true;
    }                                             

    // Variables declaration - do not modify                     
    private javax.swing.JButton buttonAceptar;
    private javax.swing.JLabel labelIncremento;
    private javax.swing.JLabel labelPrecioSalida;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JSpinner spinnerIncremento;
    private javax.swing.JSpinner spinnerPrecioSalida;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration                   
}
