

public class ListPanel extends javax.swing.JPanel {

    private Application app;
    
    public ListPanel(Application app) {
        this.app = app;
        initComponents();
        
    }

   private void txtSearchUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchUserActionPerformed
   
    }//GEN-LAST:event_txtSearchUserActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed

    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        this.app.logout();
    }//GEN-LAST:event_btnLogoutActionPerformed


       @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelNotificacion = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtSearchUser = new javax.swing.JTextField();
        btnAddUser = new javax.swing.JButton();
        labelError = new javax.swing.JLabel();
        labelNombreUsuario = new javax.swing.JLabel();
<<<<<<< HEAD
        jScrollPane1 = new javax.swing.JScrollPane();
        listaConectados = new javax.swing.JList();
=======
>>>>>>> origin/fix1

        setBackground(new java.awt.Color(18, 15, 102));
        setPreferredSize(new java.awt.Dimension(370, 530));

        labelNotificacion.setFont(new java.awt.Font("Futura", 0, 13)); // NOI18N
        labelNotificacion.setForeground(new java.awt.Color(254, 218, 14));
        labelNotificacion.setText("Notificacion");

        btnLogout.setBackground(new java.awt.Color(204, 51, 51));
        btnLogout.setFont(new java.awt.Font("Futura", 0, 13)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Futura", 0, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Buscar usuario:");

        txtSearchUser.setFont(new java.awt.Font("Futura", 0, 13)); // NOI18N
        txtSearchUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchUserActionPerformed(evt);
            }
        });

        btnAddUser.setBackground(new java.awt.Color(68, 204, 85));
        btnAddUser.setForeground(new java.awt.Color(255, 255, 255));
        btnAddUser.setText("+");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        labelError.setFont(new java.awt.Font("Futura", 2, 13)); // NOI18N
        labelError.setForeground(new java.awt.Color(204, 51, 51));
        labelError.setText("ERROR");
<<<<<<< HEAD

        labelNombreUsuario.setFont(new java.awt.Font("Futura", 0, 18)); // NOI18N
        labelNombreUsuario.setForeground(new java.awt.Color(255, 255, 255));
        labelNombreUsuario.setText("NOMBRE_USUARIO");

        listaConectados.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listaConectados);
=======

        labelNombreUsuario.setFont(new java.awt.Font("Futura", 0, 18)); // NOI18N
        labelNombreUsuario.setForeground(new java.awt.Color(255, 255, 255));
        labelNombreUsuario.setText("NOMBRE_USUARIO");
>>>>>>> origin/fix1

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(233, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
<<<<<<< HEAD
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(labelNombreUsuario)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(labelError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelNotificacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
=======
                    .addComponent(labelNombreUsuario)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelAlert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(13, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
>>>>>>> origin/fix1
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
<<<<<<< HEAD
                .addContainerGap(13, Short.MAX_VALUE)
=======
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
>>>>>>> origin/fix1
                .addComponent(labelNombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelError)
<<<<<<< HEAD
=======
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelAlert)
>>>>>>> origin/fix1
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelNotificacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout)
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel jLabel1;
<<<<<<< HEAD
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelError;
    private javax.swing.JLabel labelNombreUsuario;
    private javax.swing.JLabel labelNotificacion;
    private javax.swing.JList listaConectados;
=======
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelAlert;
    private javax.swing.JLabel labelError;
    private javax.swing.JLabel labelNombreUsuario;
>>>>>>> origin/fix1
    private javax.swing.JTextField txtSearchUser;
    // End of variables declaration//GEN-END:variables
}
