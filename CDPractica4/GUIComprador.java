
import java.util.ArrayList;

public class GUIComprador extends javax.swing.JFrame {

    // Variables declaration - do not modify                     
    private javax.swing.JButton buttonNuevaSubasta;
    private javax.swing.JButton buttonSalir;
    private javax.swing.JLabel labelLibro;
    private javax.swing.JLabel labelLimite;
    private javax.swing.JLabel labelNSubastas;
    private javax.swing.JSpinner spinnerLimite;
    private javax.swing.JTextField txtLibro;
    // End of variables declaration 

    private Comprador comprador;

    public GUIComprador(Comprador comprador) {
        this.comprador = comprador;
        initComponents();

        this.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent){
                finalizar();
            }
        });
    }

    private void finalizar(){
        this.comprador.takeDown();
    }

    private void buttonNuevaSubastaActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        nuevaSubasta();
    }                                                  

    private void buttonSalirActionPerformed(java.awt.event.ActionEvent evt) {                                            
        finalizar();
    }                                           

    private void txtLibroActionPerformed(java.awt.event.ActionEvent evt) {                                         
        nuevaSubasta();
    }  

    private void nuevaSubasta(){
        String libro;
        Integer maximo;

        libro = txtLibro.getText();
        maximo = Integer.parseInt(spinnerLimite.getValue().toString());

        if(libro != null && libro.length() > 0){
            if(!this.comprador.subastas.contains(libro)){
                this.comprador.nuevaSubasta(libro, maximo);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        labelNSubastas = new javax.swing.JLabel();
        labelLibro = new javax.swing.JLabel();
        labelLimite = new javax.swing.JLabel();
        txtLibro = new javax.swing.JTextField();
        spinnerLimite = new javax.swing.JSpinner();
        buttonNuevaSubasta = new javax.swing.JButton();
        buttonSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelNSubastas.setText("Subastas en curso: ");

        labelLibro.setText("Libro:");

        labelLimite.setText("Limite precio:");

        txtLibro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLibroActionPerformed(evt);
            }
        });

        spinnerLimite.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        buttonNuevaSubasta.setText("Nueva subasta");
        buttonNuevaSubasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNuevaSubastaActionPerformed(evt);
            }
        });

        buttonSalir.setText("Salir");
        buttonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelLibro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtLibro))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelNSubastas)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(labelLimite)
                                        .addGap(18, 18, 18)
                                        .addComponent(spinnerLimite, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonNuevaSubasta)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(181, 181, 181)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelNSubastas)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLibro)
                    .addComponent(txtLibro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLimite)
                    .addComponent(spinnerLimite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonNuevaSubasta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(buttonSalir)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        
                                
                  
}
