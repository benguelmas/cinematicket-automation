package gui;

import java.awt.CardLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class AnaMenu extends javax.swing.JFrame {

    public AnaMenu() {
        initComponents();
        
        CardLayout cardLayout = new CardLayout();
        panelAnaMenu.setLayout(cardLayout);
        
        panelAnaMenu.add(panelFilmSec,"filmSecmePanel");
        panelAnaMenu.add(panelDashboard,"dashboardPanel");
        
      // İlk boş ekran
    JPanel panelBos = new JPanel();
    panelBos.add(new JLabel("Ana Menü'ye Hoşgeldiniz!"));
    panelAnaMenu.add(panelBos, "bos");
    
    // İlk açılışta boş panel göster
     cardLayout.show(panelAnaMenu, "bos");
     
     // Başlangıçta il  listelesini doldur
      
        ilListele(cmbiLsec);
        ilListele(jComboBox1);

        btnFilmSec.addActionListener(e -> {
        cardLayout.show(panelAnaMenu, "filmSecmePanel");
});


        btnDashboard.addActionListener(e -> {
        cardLayout.show(panelAnaMenu, "dashboardPanel");
        dashboardSayilariYukle();  // sayıları yükle
});

      
      cmbiLsec.addActionListener(e -> salonListele(getSelectedId(cmbiLsec), cmbSalonSec));
      cmbSalonSec.addActionListener(e -> {
      int salonId = getSelectedId(cmbSalonSec);
      seansListele(salonId, cmbSeansSec);
      filmListele(salonId, cmbFilmSec);
});
        jComboBox1.addActionListener(e -> salonListele(getSelectedId(jComboBox1), jComboBox2));
        jComboBox2.addActionListener(e -> filmListele(getSelectedId(jComboBox2), jComboBox3));
         
        jComboBox3.addActionListener(e -> {
    int filmId = getSelectedId(jComboBox3);
    if (filmId != -1) {
        bosKoltukSayisiniGoster(filmId);
    }
});
     
}
    private int getSelectedId(JComboBox<String> combo) {
    String secim = (String) combo.getSelectedItem();
    if (secim == null || !secim.contains(" - ")) return -1;
    return Integer.parseInt(secim.split(" - ")[0]);
}
// LİSTELEME
  private void ilListele(JComboBox<String> hedefComboBox) {
        hedefComboBox.removeAllItems();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT id, il_adi FROM iller");
            while (rs.next()) {
                hedefComboBox.addItem(rs.getInt("id") + " - " + rs.getString("il_adi"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "İl yüklenemedi: " + e.getMessage());
        }
    }
    
    private void salonListele(int ilId, JComboBox<String> hedefComboBox) {
        hedefComboBox.removeAllItems();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
            PreparedStatement ps = conn.prepareStatement("SELECT id, salon_adi FROM salonlar WHERE il_id = ?");
            ps.setInt(1, ilId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hedefComboBox.addItem(rs.getInt("id") + " - " + rs.getString("salon_adi"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Salon yüklenemedi: " + e.getMessage());
        }
    }
        private void filmListele(int salonId, JComboBox<String> hedefComboBox) {
        hedefComboBox.removeAllItems();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT DISTINCT f.id, f.film_adi FROM filmler f JOIN seanslar s ON f.id = s.film_id WHERE s.salon_id = ?");
            ps.setInt(1, salonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hedefComboBox.addItem(rs.getInt("id") + " - " + rs.getString("film_adi"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Film yüklenemedi: " + e.getMessage());
        }
    }
            private void seansListele(int salonId, JComboBox<String> hedefComboBox) {
        hedefComboBox.removeAllItems();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
            PreparedStatement ps = conn.prepareStatement("SELECT id, saat FROM seanslar WHERE salon_id = ?");
            ps.setInt(1, salonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hedefComboBox.addItem(rs.getInt("id") + " - " + rs.getString("saat"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Seans yüklenemedi: " + e.getMessage());
        }
    }
    
    private void bosKoltukSayisiniGoster(int salonId) {
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "SELECT COUNT(*) AS bos_sayisi FROM koltuklar WHERE salon_id = ? AND durum = 0";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, salonId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            bosKoltuk.setText("Bu salonun boş koltuk sayısı: " + rs.getInt("bos_sayisi"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Boş koltuk sayısı yüklenemedi: " + e.getMessage());
    }
}
   
    private void dashboardSayilariYukle() {
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {

        // İl sayısı
        String sqlIl = "SELECT COUNT(*) AS il_sayisi FROM iller";
        Statement stIl = (Statement) conn.createStatement();
        ResultSet rsIl = stIl.executeQuery(sqlIl);
        if (rsIl.next()) {
            jLabel8.setText("Toplam İl Sayısı: " + rsIl.getInt("il_sayisi"));
        }

        // Salon sayısı
        String sqlSalon = "SELECT COUNT(*) AS salon_sayisi FROM salonlar";
        Statement stSalon = (Statement) conn.createStatement();
        ResultSet rsSalon = stSalon.executeQuery(sqlSalon);
        if (rsSalon.next()) {
            jLabel9.setText("Toplam Salon Sayısı: " + rsSalon.getInt("salon_sayisi"));
        }

      // Son 10 gün filmleri
        DefaultTableModel model = (DefaultTableModel) tableFilmler.getModel();
        model.setRowCount(0);
        Statement st3 = conn.createStatement();
        ResultSet rs3 = st3.executeQuery("SELECT id, film_adi, tur FROM filmler WHERE eklenme_tarihi >= date('now', '-10 days')");
        while (rs3.next()) {
            model.addRow(new Object[] {
                rs3.getInt("id"),
                rs3.getString("film_adi"),
                rs3.getString("tur")
            });
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Dashboard yükleme hatası: " + e.getMessage());
    }
}
    private void biletAlmaEkraninaGec(int salonId, int filmId, int seansId) {
    BiletAlma biletAlmaFrame = new BiletAlma(salonId, filmId, seansId);
    biletAlmaFrame.setVisible(true);
    this.dispose();  // Ana menüyü kapatıyoruz
}
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelAnaMenu = new javax.swing.JPanel();
        panelDashboard = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableFilmler = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        bosKoltuk = new javax.swing.JLabel();
        panelFilmSec = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbiLsec = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbFilmSec = new javax.swing.JComboBox<>();
        cmbSalonSec = new javax.swing.JComboBox<>();
        cmbSeansSec = new javax.swing.JComboBox<>();
        btnDevamEt = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        panelSolMenu = new javax.swing.JPanel();
        btnFilmSec = new javax.swing.JButton();
        btnDashboard = new javax.swing.JButton();
        btnCikis = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel6.setBackground(new java.awt.Color(51, 51, 255));
        jLabel6.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 153, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Dashboard");
        jLabel6.setToolTipText("");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Toplam İl Sayısı:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("Toplam Salon Sayısı:");

        tableFilmler.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Film Adı", "Tür"
            }
        ));
        jScrollPane1.setViewportView(tableFilmler);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel10.setText("SON 10 GÜNDE EKLENEN FİLMLER");

        jLabel11.setText(" İl Seç");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setText("Salon Seç");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel13.setText("Film Seç");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        bosKoltuk.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        javax.swing.GroupLayout panelDashboardLayout = new javax.swing.GroupLayout(panelDashboard);
        panelDashboard.setLayout(panelDashboardLayout);
        panelDashboardLayout.setHorizontalGroup(
            panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDashboardLayout.createSequentialGroup()
                .addGroup(panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDashboardLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDashboardLayout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(bosKoltuk, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 573, Short.MAX_VALUE))
            .addGroup(panelDashboardLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDashboardLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelDashboardLayout.createSequentialGroup()
                        .addGroup(panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panelDashboardLayout.createSequentialGroup()
                                .addGroup(panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox1, 0, 140, Short.MAX_VALUE)
                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelDashboardLayout.createSequentialGroup()
                                    .addGap(54, 54, 54)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelDashboardLayout.setVerticalGroup(
            panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDashboardLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(panelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(bosKoltuk)
                .addGap(53, 53, 53))
        );

        panelFilmSec.setLayout(null);

        jLabel2.setBackground(new java.awt.Color(255, 204, 204));
        jLabel2.setText("İl:");
        panelFilmSec.add(jLabel2);
        jLabel2.setBounds(130, 180, 79, 16);

        cmbiLsec.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelFilmSec.add(cmbiLsec);
        cmbiLsec.setBounds(340, 180, 114, 22);

        jLabel3.setText("Film:");
        panelFilmSec.add(jLabel3);
        jLabel3.setBounds(130, 220, 37, 16);

        jLabel4.setText("Salon:");
        panelFilmSec.add(jLabel4);
        jLabel4.setBounds(130, 260, 37, 16);

        jLabel5.setText("Seans:");
        panelFilmSec.add(jLabel5);
        jLabel5.setBounds(130, 310, 57, 16);

        cmbFilmSec.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelFilmSec.add(cmbFilmSec);
        cmbFilmSec.setBounds(340, 220, 114, 22);

        cmbSalonSec.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelFilmSec.add(cmbSalonSec);
        cmbSalonSec.setBounds(340, 260, 114, 22);

        cmbSeansSec.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelFilmSec.add(cmbSeansSec);
        cmbSeansSec.setBounds(340, 310, 114, 22);

        btnDevamEt.setText("Devam Et");
        btnDevamEt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDevamEtActionPerformed(evt);
            }
        });
        panelFilmSec.add(btnDevamEt);
        btnDevamEt.setBounds(200, 380, 173, 41);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Bilet almak için lütfen gerekli bilgileri seçiniz.");
        panelFilmSec.add(jLabel7);
        jLabel7.setBounds(130, 110, 329, 29);

        javax.swing.GroupLayout panelAnaMenuLayout = new javax.swing.GroupLayout(panelAnaMenu);
        panelAnaMenu.setLayout(panelAnaMenuLayout);
        panelAnaMenuLayout.setHorizontalGroup(
            panelAnaMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAnaMenuLayout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addComponent(panelFilmSec, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(154, 154, 154)
                .addComponent(panelDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1941, 1941, 1941))
        );
        panelAnaMenuLayout.setVerticalGroup(
            panelAnaMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAnaMenuLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(panelAnaMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelFilmSec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(764, Short.MAX_VALUE))
        );

        btnFilmSec.setText("Film Seç");
        btnFilmSec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilmSecActionPerformed(evt);
            }
        });

        btnDashboard.setText("Dashboard");
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });

        btnCikis.setText("Çıkış");
        btnCikis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCikisActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setText("Ana Menü");

        javax.swing.GroupLayout panelSolMenuLayout = new javax.swing.GroupLayout(panelSolMenu);
        panelSolMenu.setLayout(panelSolMenuLayout);
        panelSolMenuLayout.setHorizontalGroup(
            panelSolMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSolMenuLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(panelSolMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(btnDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(btnFilmSec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCikis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        panelSolMenuLayout.setVerticalGroup(
            panelSolMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSolMenuLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1)
                .addGap(59, 59, 59)
                .addComponent(btnFilmSec, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnCikis, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(251, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(panelSolMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelAnaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelSolMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(panelAnaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFilmSecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilmSecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFilmSecActionPerformed

    private void btnCikisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCikisActionPerformed
          this.dispose();  // Ana menüyü kapatıyoruz
          new LoginEkrani().setVisible(true);
    }//GEN-LAST:event_btnCikisActionPerformed

    private void btnDevamEtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDevamEtActionPerformed
  try {
            int salonId = Integer.parseInt(cmbSalonSec.getSelectedItem().toString().split(" - ")[0]);
            int filmId = Integer.parseInt(cmbFilmSec.getSelectedItem().toString().split(" - ")[0]);
            int seansId = Integer.parseInt(cmbSeansSec.getSelectedItem().toString().split(" - ")[0]);

            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
               String sql = "SELECT COUNT(*) AS bos_sayisi FROM koltuklar WHERE salon_id = ? AND durum = 0";
               PreparedStatement ps = conn.prepareStatement(sql);
               ps.setInt(1, salonId);
               ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int bosKoltuk = rs.getInt("bos_sayisi");
                    JOptionPane.showMessageDialog(this, "Boş Koltuk Sayısı: " + bosKoltuk);

                    if (bosKoltuk > 0) {
                        biletAlmaEkraninaGec(salonId, filmId, seansId);
                    } else {
                        JOptionPane.showMessageDialog(this, "Bu seans dolu, lütfen başka seçim yapın.");
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
  
    }//GEN-LAST:event_btnDevamEtActionPerformed

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseClicked

  
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AnaMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bosKoltuk;
    private javax.swing.JButton btnCikis;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnDevamEt;
    private javax.swing.JButton btnFilmSec;
    private javax.swing.JComboBox<String> cmbFilmSec;
    private javax.swing.JComboBox<String> cmbSalonSec;
    private javax.swing.JComboBox<String> cmbSeansSec;
    private javax.swing.JComboBox<String> cmbiLsec;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelAnaMenu;
    private javax.swing.JPanel panelDashboard;
    private javax.swing.JPanel panelFilmSec;
    private javax.swing.JPanel panelSolMenu;
    private javax.swing.JTable tableFilmler;
    // End of variables declaration//GEN-END:variables
}
