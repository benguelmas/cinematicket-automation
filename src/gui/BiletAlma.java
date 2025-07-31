package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

import java.sql.ResultSet;

public class BiletAlma extends javax.swing.JFrame {

    private final int salonId;
    private final int filmId;
    private final int seansId;
    
    public BiletAlma(int salonId, int filmId, int seansId) {
        initComponents();
        this.salonId = salonId;
        this.filmId = filmId;
        this.seansId = seansId;
        
        bosKoltuklariListele(this.salonId);
         
        lblFilm.setText("Film ID: " + filmId);
        lblSalon.setText("Salon ID: " + salonId);
        lblSeans.setText("Seans ID: " + seansId);
        
    }
 // Veritabanından boş koltukları listeleyen metod
 private void bosKoltuklariListele(int salonId) {
    
     StringBuilder bosKoltuklar = new StringBuilder();
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "SELECT koltuk_no FROM koltuklar WHERE salon_id = ? AND durum = 0";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, salonId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) { // Her boş koltuğu stringe ekle
            String koltukNo = rs.getString("koltuk_no");
            bosKoltuklar.append(koltukNo).append("  ");

        }
        // Boş koltukları JTextArea'ya yazdır
        txtBosKoltuklar.setText(bosKoltuklar.toString());  // burası JTextArea'ya yazdırır

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Boş koltukları yüklerken hata: " + ex.getMessage());
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFilm = new javax.swing.JLabel();
        lblSalon = new javax.swing.JLabel();
        lblSeans = new javax.swing.JLabel();
        btnBiletAl = new javax.swing.JButton();
        txtAd = new javax.swing.JTextField();
        txtSoyad = new javax.swing.JTextField();
        txtKoltukNo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtBosKoltuklar = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnGeri = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblFilm.setText("jLabel1");

        lblSalon.setText("jLabel2");

        lblSeans.setText("jLabel3");

        btnBiletAl.setText("Bilet Al");
        btnBiletAl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBiletAlActionPerformed(evt);
            }
        });

        txtSoyad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoyadActionPerformed(evt);
            }
        });

        jLabel1.setText("Kullanıcı Adını Giriniz:");

        jLabel2.setText("Kullanıcı Soyadını Giriniz:");

        jLabel3.setText("Koltuk Numarasını Giriniz:");

        jLabel4.setText("Lütfen Boş Olan Koltuklardan Seçiniz");

        txtBosKoltuklar.setColumns(20);
        txtBosKoltuklar.setRows(5);
        jScrollPane2.setViewportView(txtBosKoltuklar);

        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\HP\\Desktop\\f9ab65bf-1216-4a12-a7dc-d8cfcc54d21d.png")); // NOI18N
        jLabel5.setText("jLabel5");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel6.setText("Bilet Alma Paneli");

        btnGeri.setText("Geri");
        btnGeri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGeriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtKoltukNo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAd, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoyad, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(84, 84, 84)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(0, 587, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFilm, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(433, 433, 433)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblSalon, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(76, 76, 76)
                            .addComponent(lblSeans, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnGeri)
                            .addGap(293, 293, 293)
                            .addComponent(btnBiletAl, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel6)
                .addGap(123, 123, 123)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtSoyad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKoltukNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFilm, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSeans)
                    .addComponent(lblSalon, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(173, 173, 173)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBiletAl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGeri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(396, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBiletAlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBiletAlActionPerformed
    
    String ad = txtAd.getText().trim();
    String soyad = txtSoyad.getText().trim();
    String koltukNoStr = txtKoltukNo.getText().trim().toUpperCase();  // A1, A2 gibi (küçük harfi de yakalasın)

    if (ad.isEmpty() || soyad.isEmpty() || koltukNoStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ad, soyad ve koltuk numarası girilmelidir!");
        return;
    }

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {

        // Önce koltuk boş mu kontrol et
        String checkSql = "SELECT durum FROM koltuklar WHERE salon_id = ? AND koltuk_no = ?";
        PreparedStatement checkPs = conn.prepareStatement(checkSql);
        checkPs.setInt(1, salonId);
        checkPs.setString(2, koltukNoStr);
        ResultSet rs = checkPs.executeQuery();

        if (rs.next() && rs.getInt("durum") == 1) {
            JOptionPane.showMessageDialog(this, "Bu koltuk zaten dolu, lütfen başka bir koltuk seçin!");
            return;
        }

        // Bilet kaydet
        String sql = "INSERT INTO biletler (ad, soyad, salon_id, film_id, seans_id, koltuk_no) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, ad);
        ps.setString(2, soyad);
        ps.setInt(3, salonId);
        ps.setInt(4, filmId);
        ps.setInt(5, seansId);
        ps.setString(6, koltukNoStr);  // METİN olarak ekle
        ps.executeUpdate();

        // Koltuk durumunu güncelle
        String updateSql = "UPDATE koltuklar SET durum = 1 WHERE salon_id = ? AND koltuk_no = ?";
        PreparedStatement ps2 = conn.prepareStatement(updateSql);
        ps2.setInt(1, salonId);
        ps2.setString(2, koltukNoStr);
        ps2.executeUpdate();

       // Bilet bilgilerini göster
        String biletBilgisi = "🎫 Bilet Bilgisi 🎫\n\n"
        + "Ad: " + ad + "\n"
        + "Soyad: " + soyad + "\n"
        + "Film ID: " + filmId + "\n"
        + "Salon ID: " + salonId + "\n"
        + "Seans ID: " + seansId + "\n"
        + "Koltuk No: " + koltukNoStr + "\n\n"
        + "İyi seyirler!";

JOptionPane.showMessageDialog(this, biletBilgisi);

        // Ana menüye dön
        new AnaMenu().setVisible(true);
        this.dispose();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
    }
    }//GEN-LAST:event_btnBiletAlActionPerformed

    private void txtSoyadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoyadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoyadActionPerformed

    private void btnGeriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGeriActionPerformed
        // Ana menüye dön
        new AnaMenu().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnGeriActionPerformed

   
    public static void main(String args[]) {
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BiletAlma(1,1,1).setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBiletAl;
    private javax.swing.JButton btnGeri;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblFilm;
    private javax.swing.JLabel lblSalon;
    private javax.swing.JLabel lblSeans;
    private javax.swing.JTextField txtAd;
    private javax.swing.JTextArea txtBosKoltuklar;
    private javax.swing.JTextField txtKoltukNo;
    private javax.swing.JTextField txtSoyad;
    // End of variables declaration//GEN-END:variables
}