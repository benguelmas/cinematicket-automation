package gui;

import javax.swing.*;
import java.sql.*;

public class LoginEkrani extends javax.swing.JFrame {

    public LoginEkrani() {
        initComponents();
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtKullanici = new javax.swing.JLabel();
        txtSifre = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        KullaniciGiris = new javax.swing.JTextField();
        SifreGiris = new javax.swing.JPasswordField();
        btnSign = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtKullanici.setText("Kullanıcı Adı:");

        txtSifre.setText("Şifre:");

        btnLogin.setText("Giriş Yap");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        SifreGiris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SifreGirisActionPerformed(evt);
            }
        });

        btnSign.setText("Kayıt Ol");
        btnSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Sinema Bilet Otomasyonuna Hoş Geldiniz!");

        jLabel2.setText("Daha önceden kayıt olduysanız lütfen giriş yapınız.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSign, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtKullanici, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSifre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(KullaniciGiris)
                            .addComponent(SifreGiris, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))))
                .addGap(172, 172, 172))
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKullanici)
                    .addComponent(KullaniciGiris, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSifre)
                    .addComponent(SifreGiris, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(btnLogin)
                .addGap(18, 18, 18)
                .addComponent(btnSign)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SifreGirisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SifreGirisActionPerformed

    }//GEN-LAST:event_SifreGirisActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
    
    String kullaniciAdi = KullaniciGiris.getText(); // Kullanıcı adı ve şifreyi arayüzden alıyoruz
    String sifre = new String(SifreGiris.getPassword());

    // Veritabanı bağlantısı ve sorgu nesneleri tanımlanıyor
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        String url = "jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db";
        // Veritabanına bağlantı kur
        conn = DriverManager.getConnection(url);

        String sql = "SELECT * FROM kullanicilar WHERE kullanici_adi = ? AND sifre = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, kullaniciAdi);// 1. parametre: kullanıcı adı
        pstmt.setString(2, sifre);// 2. parametre: şifre
        // Sorguyu çalıştırıyoruz
        rs = pstmt.executeQuery();
        // Eğer kullanıcı bulunduysa
        if (rs.next()) {
            String rol = rs.getString("rol");
            JOptionPane.showMessageDialog(this, "Giriş başarılı! Rol: " + rol);

            if (rol.equals("admin")) {
                new AdminPanel().setVisible(true);//adminse admin panelini açar
            } else {
                new AnaMenu().setVisible(true);//kullanici ise ana menüyü açar
            }

            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Hatalı kullanıcı adı veya şifre!");
        }

    } catch (SQLException e) {
          e.printStackTrace(); // hata konsola yazılır
        JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            System.out.println("Kapatma hatası: " + ex.getMessage());
        }
    }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnSignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignActionPerformed
         new KayitEkranii().setVisible(true);
         this.dispose(); // login ekranını kapatmak istersen
    }//GEN-LAST:event_btnSignActionPerformed

   
    public static void main(String args[]) {
          java.awt.EventQueue.invokeLater(() -> {
            new LoginEkrani().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField KullaniciGiris;
    private javax.swing.JPasswordField SifreGiris;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSign;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel txtKullanici;
    private javax.swing.JLabel txtSifre;
    // End of variables declaration//GEN-END:variables
}
