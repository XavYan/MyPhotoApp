/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.hbarquin.myphotoapp;

import java.awt.Point;

/**
 *
 * @author xavie
 */
public class SubImageFormWindow extends javax.swing.JDialog {

    /**
     * Creates new form SubImageFormWindow
     */
    public SubImageFormWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Select Region Of Interest");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exampleImage = new javax.swing.JPanel();
        image = new javax.swing.JLabel();
        subImageForm = new javax.swing.JPanel();
        p1Value = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        inputXP1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        inputYP1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        p2Value = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        inputXP2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        inputYP2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        buttonsForm = new javax.swing.JPanel();
        acceptButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridLayout(3, 1));

        image.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\GDrive\\Projects\\MyPhotoApp\\assets\\img\\rectangle.png")); // NOI18N

        javax.swing.GroupLayout exampleImageLayout = new javax.swing.GroupLayout(exampleImage);
        exampleImage.setLayout(exampleImageLayout);
        exampleImageLayout.setHorizontalGroup(
            exampleImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exampleImageLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(image)
                .addContainerGap(115, Short.MAX_VALUE))
        );
        exampleImageLayout.setVerticalGroup(
            exampleImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exampleImageLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(exampleImage);

        subImageForm.setLayout(new java.awt.GridLayout(2, 1));

        p1Value.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Valores de P1");
        p1Value.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

        inputXP1.setText("0");
        inputXP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputXP1ActionPerformed(evt);
            }
        });
        p1Value.add(inputXP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 50, -1));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("X");
        p1Value.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 20, 20));

        inputYP1.setText("0");
        inputYP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputYP1ActionPerformed(evt);
            }
        });
        p1Value.add(inputYP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 50, -1));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Y");
        p1Value.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 20, 20));

        subImageForm.add(p1Value);

        p2Value.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Valores de P2");
        p2Value.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

        inputXP2.setText("0");
        inputXP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputXP2ActionPerformed(evt);
            }
        });
        p2Value.add(inputXP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 50, -1));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("X");
        p2Value.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 20, 20));

        inputYP2.setText("0");
        inputYP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputYP2ActionPerformed(evt);
            }
        });
        p2Value.add(inputYP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 50, -1));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Y");
        p2Value.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 20, 20));

        subImageForm.add(p2Value);

        getContentPane().add(subImageForm);

        acceptButton.setText("Aceptar");
        acceptButton.setActionCommand("");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancelar");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonsFormLayout = new javax.swing.GroupLayout(buttonsForm);
        buttonsForm.setLayout(buttonsFormLayout);
        buttonsFormLayout.setHorizontalGroup(
            buttonsFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsFormLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(acceptButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
        );
        buttonsFormLayout.setVerticalGroup(
            buttonsFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsFormLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(buttonsFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acceptButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(75, Short.MAX_VALUE))
        );

        getContentPane().add(buttonsForm);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButtonActionPerformed
        MainWindow parent = (MainWindow) this.getParent();
        
        Point p1 = new Point(Integer.parseInt(inputXP1.getText()), Integer.parseInt(inputYP1.getText()));
        Point p2 = new Point(Integer.parseInt(inputXP2.getText()), Integer.parseInt(inputYP2.getText()));
        parent.setSubImage(p1, p2);
        
        this.dispose();
    }//GEN-LAST:event_acceptButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void inputXP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputXP1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputXP1ActionPerformed

    private void inputYP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputYP1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputYP1ActionPerformed

    private void inputXP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputXP2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputXP2ActionPerformed

    private void inputYP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputYP2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputYP2ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(SubImageFormWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SubImageFormWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SubImageFormWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SubImageFormWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SubImageFormWindow dialog = new SubImageFormWindow(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JPanel buttonsForm;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel exampleImage;
    private javax.swing.JLabel image;
    private javax.swing.JTextField inputXP1;
    private javax.swing.JTextField inputXP2;
    private javax.swing.JTextField inputYP1;
    private javax.swing.JTextField inputYP2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel p1Value;
    private javax.swing.JPanel p2Value;
    private javax.swing.JPanel subImageForm;
    // End of variables declaration//GEN-END:variables
}
