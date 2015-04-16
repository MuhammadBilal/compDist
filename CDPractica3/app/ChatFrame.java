

import java.awt.Color;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultCaret.*;

public class ChatFrame extends javax.swing.JFrame {

    private Application app;
    private PeerInterface peer;
    private boolean chatClosed;
    private boolean connected;
    private String username;

    public ChatFrame(Application app, PeerInterface peer) {
        initComponents();

        this.setLocationRelativeTo(null); // center of the screen
        this.getContentPane().setBackground(new Color(18, 15, 102));
        this.txtMsg.requestFocus();

        this.app = app;
        this.peer = peer;
        this.chatClosed = false;
        this.connected = true;

        DefaultCaret caret = (DefaultCaret) areaChat.getCaret();  // automatic scroll down
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);      
        areaChat.setEditable(false);

        try{
            username = peer.getUser();
            labelUsername.setText(username);
        }catch(Exception e){}
        
        this.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if(app.isConnected(username)){ closeChat(); }
                else{ deleteChat();}
            }
        });
    }

    public void appendMessage(String msg){
        areaChat.append(msg+"\n");
    }

    public void disconnectedUser(){
        areaChat.append(" - - - "+username+" se ha desconectado - - -\n");
        this.connected = false;
    }

    public void reconnectedUser(){
        areaChat.append(" - - - "+username+" se ha reconectado - - -\n");
        this.connected = true;
    }

    private void txtMsgActionPerformed(java.awt.event.ActionEvent evt){
        sendMessage();
    }

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        sendMessage();
    }//GEN-LAST:event_btnSendActionPerformed

    private void sendMessage() {
        String message = txtMsg.getText();
        if(connected){
            if(message != null && !message.equals("")){
                message = app.user + ": " + message;
                app.sendMessage(peer, message);
            }
            this.txtMsg.setText("");
         }
    }
   
    public void closeChat(){
        setChatClosed(true);
        setVisible(false);
    }

    public void openChat(){
        setChatClosed(false);
        setVisible(true);
    }

    public void setChatClosed(boolean b){ this.chatClosed = b; }

    public boolean isChatClosed(){ return this.chatClosed; }

    public void deleteChat(){
        dispose();
        app.deleteChat(username);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        areaChat = new javax.swing.JTextArea();
        btnSend = new javax.swing.JButton();
        txtMsg = new javax.swing.JTextField();
        labelUsername = new javax.swing.JLabel();
        labelMeteorIcon = new javax.swing.JLabel();

        setTitle("Ventana chat");
        setBackground(new java.awt.Color(18, 15, 102));

        areaChat.setBackground(new java.awt.Color(0, 0, 0));
        areaChat.setColumns(20);
        areaChat.setFont(new java.awt.Font("Futura", 0, 13)); // NOI18N
        areaChat.setForeground(new java.awt.Color(255, 255, 255));
        areaChat.setRows(5);
        jScrollPane1.setViewportView(areaChat);

        btnSend.setFont(new java.awt.Font("Futura", 0, 13)); // NOI18N
        btnSend.setText("Enviar");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        txtMsg.setBackground(new java.awt.Color(0, 0, 0));
        txtMsg.setFont(new java.awt.Font("Futura", 0, 13)); // NOI18N
        txtMsg.setForeground(new java.awt.Color(255, 255, 255));

        txtMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMsgActionPerformed(evt);
            }
        });

        labelUsername.setFont(new java.awt.Font("Futura", 0, 18)); // NOI18N
        labelUsername.setForeground(new java.awt.Color(255, 255, 255));
        //labelUsername.setText("NOMBRE_AMIGO");

        labelMeteorIcon.setIcon(new javax.swing.ImageIcon("media/meteor.png")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtMsg)
                        .addGap(18, 18, 18)
                        .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(labelMeteorIcon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelUsername)
                        .addGap(0, 293, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelMeteorIcon)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(labelUsername)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSend)
                    .addComponent(txtMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaChat;
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JLabel labelMeteorIcon;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtMsg;
    // End of variables declaration//GEN-END:variables
}
