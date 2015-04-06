

public class LoginPanel extends javax.swing.JPanel {

    private Application app;
    
    public LoginPanel(Application app) {
        this.app = app;
        initComponents();
        txtUser.setText("");
        txtPass.setText("");
        labelError.setText("");
    }
    
    public void setError(String msg){
        this.labelError.setText(msg);
    }

    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserActionPerformed
        labelError.setText("");
    }//GEN-LAST:event_txtUserActionPerformed

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        labelError.setText("");
    }//GEN-LAST:event_txtPassActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String pass = new String(txtPass.getPassword());
        String user = txtUser.getText();
        if(user==null || user.equals("") || pass.equals("")){
            labelError.setText("Los campos usuario y password deben estar cubiertos");
        }else{


        }
    }//GEN-LAST:event_btnLoginActionPerformed




    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtUsername = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        labelUser = new javax.swing.JLabel();
        labelPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        labelError = new javax.swing.JLabel();

        txtUsername.setFont(new java.awt.Font("Futura", 0, 13)); // NOI18N

        jButton1.setText("jButton1");

        setBackground(new java.awt.Color(18, 15, 102));
        setMaximumSize(new java.awt.Dimension(370, 530));
        setPreferredSize(new java.awt.Dimension(370, 530));

        jLabel1.setIcon(new javax.swing.ImageIcon("media/logo.png")); // NOI18N

        txtUser.setFont(new java.awt.Font("Futura", 0, 13)); // NOI18N
        txtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });

        labelUser.setFont(new java.awt.Font("Futura", 0, 13)); // NOI18N
        labelUser.setForeground(new java.awt.Color(255, 255, 255));
        labelUser.setText("Nombre de usuario:");

        labelPass.setFont(new java.awt.Font("Futura", 0, 13)); // NOI18N
        labelPass.setForeground(new java.awt.Color(255, 255, 255));
        labelPass.setText("Password:");

        txtPass.setFont(new java.awt.Font("Futura", 0, 13)); // NOI18N
        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });

        btnLogin.setBackground(new java.awt.Color(68, 204, 85));
        btnLogin.setFont(new java.awt.Font("Futura", 0, 13)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        labelError.setFont(new java.awt.Font("Futura", 2, 13)); // NOI18N
        labelError.setForeground(new java.awt.Color(204, 51, 51));
        labelError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelError.setText("ERROR: - - - ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(labelUser)
                                    .addComponent(txtUser)
                                    .addComponent(labelPass)
                                    .addComponent(txtPass, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(122, 122, 122)
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addComponent(labelError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel1)
                .addGap(49, 49, 49)
                .addComponent(labelUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelPass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLogin)
                .addGap(35, 35, 35)
                .addComponent(labelError)
                .addContainerGap(95, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelError;
    private javax.swing.JLabel labelPass;
    private javax.swing.JLabel labelUser;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
