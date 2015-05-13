
import jade.core.Agent;

import javax.swing.text.DefaultCaret;

public class GUISubasta extends javax.swing.JFrame {

    // Variables declaration - do not modify                     
    private javax.swing.JTextArea areaMensajes;
    private javax.swing.JButton buttonCerrar;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration  

    private Subasta subasta;
    private Agent agente;
    private boolean fin = false;

    public GUISubasta(Agent agente, Subasta subasta) {
        this.agente = agente;
        initComponents();
        this.setTitle(agente.getLocalName()+": Subasta por '"+subasta.getTituloLibro()+"'");

        DefaultCaret caret = (DefaultCaret) areaMensajes.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        this.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent){
                cerrar();
            }
        });
    }

    private void buttonCerrarActionPerformed(java.awt.event.ActionEvent evt) {                                             
        cerrar();
    }

    private void cerrar(){
        if(fin){
            dispose();
        }else{
            setVisible(false);
        }
    }

    public void addMensaje(String mensaje){
        this.areaMensajes.append(mensaje+"\n");
    }

    public void finalizar(){
        this.fin = true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        areaMensajes = new javax.swing.JTextArea();
        buttonCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        areaMensajes.setColumns(20);
        areaMensajes.setRows(5);
        jScrollPane1.setViewportView(areaMensajes);

        buttonCerrar.setText("Cerrar");
        buttonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonCerrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCerrar))
        );

        pack();
    }// </editor-fold>                        

                 
}
