package gui;

import java.awt.CardLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class AdminPanel extends javax.swing.JFrame {

    public AdminPanel() {
        initComponents();
    
    CardLayout cardLayout = new CardLayout();
    panelIcerik.setLayout(cardLayout);
   
    //İlk boş ekran
    JPanel panelBos = new JPanel();
    panelIcerik.add(panelBos, "bos");

    // Tasarımda eklenen paneli doğrudan kullan
    panelIcerik.add(paneliL, "il");
    panelIcerik.add(panelFilm, "film");
    panelIcerik.add(panelSeans, "seans");
    panelIcerik.add(panelSalon, "salon");   
    panelIcerik.add(panelKullanici, "kullanici");
     
    
    // İlk açılışta boş panel göster
     cardLayout.show(panelIcerik, "bos");
    
     // Butonlara tıklayınca panel değiştir
    btnILYonet.addActionListener(e -> {
        cardLayout.show(panelIcerik, "il");
        ilListele();
    });

    btnFilmYonet.addActionListener(e -> {
        cardLayout.show(panelIcerik, "film");
        filmListele();
    });
     btnSalonYonet.addActionListener(e -> {
        cardLayout.show(panelIcerik, "salon");
          illeriYukle();
          salonListele();
    });

    btnSeansYonet.addActionListener(e -> {
        cardLayout.show(panelIcerik, "seans");
        salonlariYukle();
        filmleriYukle();
        seansListele();  
    });

    btnKullaniciYonet.addActionListener(e -> {
        cardLayout.show(panelIcerik, "kullanici");
        kullaniciListele();
    });
}
    // İl listesini veritabanından yükleyen metod
    private void ilListele() {
        
        DefaultListModel<String> ilModel = new DefaultListModel<>();
        lst_iller.setModel(ilModel);
        
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT il_adi FROM iller");
            while (rs.next()) {
                ilModel.addElement(rs.getString("il_adi"));// Her ili modele ekle
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Listeleme hatası: " + e.getMessage());
        }
    }
    // Filmleri veritabanından yükleyen metod
    private void filmListele () {
    
        DefaultListModel<String> filmModel = new DefaultListModel<>();
        lstFilmler.setModel(filmModel);
        
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id, film_adi, tur FROM filmler");
        while (rs.next()) {
            String entry = rs.getInt("id") + " - " + rs.getString("film_adi") + " (" + rs.getString("tur") + ")";
            filmModel.addElement(entry); //Her filmi ID ve türle birlikte modele ekle
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Film listeleme hatası: " + e.getMessage());
    }
   }
    // Seansları (film + salon + saat) listeleyen metod
    private void seansListele() {
    
        DefaultListModel<String> seansModel = new DefaultListModel<>();
        lstSeanslar.setModel(seansModel);
   
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "SELECT s.id, s.saat, f.film_adi, sa.salon_adi FROM seanslar s " +
                     "JOIN filmler f ON s.film_id = f.id " +
                     "JOIN salonlar sa ON s.salon_id = sa.id";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            String entry = rs.getInt("id") + " - " + rs.getString("saat") +
                           " | " + rs.getString("film_adi") +
                           " @ " + rs.getString("salon_adi");
            seansModel.addElement(entry);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Seans listeleme hatası: " + e.getMessage());
    }
}
    // Salonları comboBox'a yükleyen metod
    private void salonlariYukle() {
    
        cmbSalonSec.removeAllItems();
   
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id, salon_adi FROM salonlar");
        while (rs.next()) {
            cmbSalonSec.addItem(rs.getInt("id") + " - " + rs.getString("salon_adi"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Salon yükleme hatası: " + e.getMessage());
    }
}
// Filmleri comboBox'a yükleyen metod
   private void filmleriYukle() {
    
       cmbFilmSec.removeAllItems();
       
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id, film_adi FROM filmler");
        while (rs.next()) {
            cmbFilmSec.addItem(rs.getInt("id") + " - " + rs.getString("film_adi"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Film yükleme hatası: " + e.getMessage());
    }
}
   // Salonları listeleyen metod
    private void salonListele() {
   
    DefaultListModel<String> salonModel = new DefaultListModel<>();
    lstSalonlar.setModel(salonModel);
    
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
       String sql = "SELECT s.id, s.salon_adi, i.il_adi FROM salonlar s JOIN iller i ON s.il_id = i.id";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
       while (rs.next()) {
        String entry = rs.getInt("id") + " - " + rs.getString("salon_adi") + " (" + rs.getString("il_adi") + ")";
        salonModel.addElement(entry);
}
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Salon listeleme hatası: " + e.getMessage());
    }
}
// İlleri comboBox'a yükleyen metod
    private void illeriYukle() {
    
        cmbSaloniLSec.removeAllItems();
        
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "SELECT id, il_adi FROM iller";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            cmbSaloniLSec.addItem(rs.getInt("id") + " - " + rs.getString("il_adi"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "İller yüklenemedi: " + e.getMessage());
    }
}
// Kullanıcıları listeleyen metod
    private void kullaniciListele() {
   
    DefaultListModel<String> kullaniciModel = new DefaultListModel<>();
    lstKullanicilar.setModel(kullaniciModel);
    
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "SELECT id, kullanici_adi, rol FROM kullanicilar";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            String entry = rs.getInt("id") + " - " + rs.getString("kullanici_adi") + " (" + rs.getString("rol") + ")";
            kullaniciModel.addElement(entry);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Kullanıcı listeleme hatası: " + e.getMessage());
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSolMenu = new javax.swing.JPanel();
        btnILYonet = new javax.swing.JButton();
        btnSalonYonet = new javax.swing.JButton();
        btnFilmYonet = new javax.swing.JButton();
        btnSeansYonet = new javax.swing.JButton();
        btnKullaniciYonet = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cikisButonu = new javax.swing.JButton();
        panelIcerik = new javax.swing.JPanel();
        panelFilm = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFilmAdi = new javax.swing.JTextField();
        txtFilmTur = new javax.swing.JTextField();
        btnFilmEkle = new javax.swing.JButton();
        btnFilmSil = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstFilmler = new javax.swing.JList<>();
        jLabel10 = new javax.swing.JLabel();
        panelSeans = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtSeansSaat = new javax.swing.JTextField();
        cmbFilmSec = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cmbSalonSec = new javax.swing.JComboBox<>();
        btnSeansEkle = new javax.swing.JButton();
        btnSeansSil = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstSeanslar = new javax.swing.JList<>();
        jLabel9 = new javax.swing.JLabel();
        panelKullanici = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtKullaniciAdi = new javax.swing.JTextField();
        txtKullaniciSifre = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btnKullaniciEkle = new javax.swing.JButton();
        btnKullaniciSil = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        lstKullanicilar = new javax.swing.JList<>();
        cmbKullaniciRol = new javax.swing.JComboBox<>();
        panelSalon = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtSalonAdi = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cmbSaloniLSec = new javax.swing.JComboBox<>();
        btnSalonEkle = new javax.swing.JButton();
        btnSalonSil = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        lstSalonlar = new javax.swing.JList<>();
        jLabel13 = new javax.swing.JLabel();
        paneliL = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtIlAdi = new javax.swing.JTextField();
        btniLekle = new javax.swing.JButton();
        btniLsil = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lst_iller = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnILYonet.setText("İl Yönetimi");
        btnILYonet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnILYonetActionPerformed(evt);
            }
        });

        btnSalonYonet.setText("Salon Yönetimi");

        btnFilmYonet.setText("Film Yönetimi");
        btnFilmYonet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilmYonetActionPerformed(evt);
            }
        });

        btnSeansYonet.setText("Seans Yönetimi");
        btnSeansYonet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeansYonetActionPerformed(evt);
            }
        });

        btnKullaniciYonet.setText("Kullanıcı Yönetimi");

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setText("Admin Paneli");

        cikisButonu.setText("Çıkış Yap");
        cikisButonu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cikisButonuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSolMenuLayout = new javax.swing.GroupLayout(panelSolMenu);
        panelSolMenu.setLayout(panelSolMenuLayout);
        panelSolMenuLayout.setHorizontalGroup(
            panelSolMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSolMenuLayout.createSequentialGroup()
                .addGroup(panelSolMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSolMenuLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(panelSolMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnKullaniciYonet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSeansYonet)
                            .addComponent(btnFilmYonet)
                            .addComponent(btnSalonYonet)
                            .addComponent(btnILYonet)
                            .addComponent(cikisButonu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelSolMenuLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(103, Short.MAX_VALUE))
        );
        panelSolMenuLayout.setVerticalGroup(
            panelSolMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSolMenuLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(58, 58, 58)
                .addComponent(btnILYonet)
                .addGap(28, 28, 28)
                .addComponent(btnSalonYonet)
                .addGap(38, 38, 38)
                .addComponent(btnFilmYonet)
                .addGap(32, 32, 32)
                .addComponent(btnSeansYonet)
                .addGap(32, 32, 32)
                .addComponent(btnKullaniciYonet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(cikisButonu)
                .addGap(78, 78, 78))
        );

        panelFilm.setLayout(null);

        jLabel3.setText("Film Adı:");
        panelFilm.add(jLabel3);
        jLabel3.setBounds(124, 104, 65, 16);

        jLabel4.setText("Türü:");
        panelFilm.add(jLabel4);
        jLabel4.setBounds(125, 141, 64, 22);

        txtFilmAdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFilmAdiActionPerformed(evt);
            }
        });
        panelFilm.add(txtFilmAdi);
        txtFilmAdi.setBounds(222, 101, 71, 22);
        panelFilm.add(txtFilmTur);
        txtFilmTur.setBounds(222, 141, 71, 22);

        btnFilmEkle.setText("Ekle");
        btnFilmEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilmEkleActionPerformed(evt);
            }
        });
        panelFilm.add(btnFilmEkle);
        btnFilmEkle.setBounds(117, 207, 72, 23);

        btnFilmSil.setText("Sil");
        btnFilmSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilmSilActionPerformed(evt);
            }
        });
        panelFilm.add(btnFilmSil);
        btnFilmSil.setBounds(221, 207, 72, 23);

        lstFilmler.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lstFilmler);

        panelFilm.add(jScrollPane2);
        jScrollPane2.setBounds(97, 275, 223, 137);

        jLabel10.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel10.setText("Film Yönetimi");
        panelFilm.add(jLabel10);
        jLabel10.setBounds(139, 46, 133, 37);

        panelSeans.setLayout(null);

        jLabel6.setText("Seans Saati:");
        panelSeans.add(jLabel6);
        jLabel6.setBounds(119, 95, 83, 24);

        jLabel7.setText("Film Seçimi:");
        panelSeans.add(jLabel7);
        jLabel7.setBounds(119, 174, 70, 16);
        panelSeans.add(txtSeansSaat);
        txtSeansSaat.setBounds(229, 96, 86, 22);

        cmbFilmSec.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelSeans.add(cmbFilmSec);
        cmbFilmSec.setBounds(229, 171, 86, 22);

        jLabel8.setText("Salon Seçimi:");
        panelSeans.add(jLabel8);
        jLabel8.setBounds(119, 134, 80, 16);

        cmbSalonSec.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelSeans.add(cmbSalonSec);
        cmbSalonSec.setBounds(229, 131, 86, 22);

        btnSeansEkle.setText("Ekle");
        btnSeansEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeansEkleActionPerformed(evt);
            }
        });
        panelSeans.add(btnSeansEkle);
        btnSeansEkle.setBounds(117, 223, 82, 23);

        btnSeansSil.setText("Sil");
        btnSeansSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeansSilActionPerformed(evt);
            }
        });
        panelSeans.add(btnSeansSil);
        btnSeansSil.setBounds(229, 223, 86, 23);

        lstSeanslar.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(lstSeanslar);

        panelSeans.add(jScrollPane3);
        jScrollPane3.setBounds(81, 280, 268, 146);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel9.setText("Seans Yönetimi");
        panelSeans.add(jLabel9);
        jLabel9.setBounds(153, 27, 171, 39);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel14.setText("Kullanıcı Yönetimi");

        jLabel15.setText("Kullanıcı Adı:");

        jLabel16.setText("Şifre:");

        jLabel17.setText("Rol:");

        btnKullaniciEkle.setText("Ekle");
        btnKullaniciEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKullaniciEkleActionPerformed(evt);
            }
        });

        btnKullaniciSil.setText("Sil");
        btnKullaniciSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKullaniciSilActionPerformed(evt);
            }
        });

        lstKullanicilar.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(lstKullanicilar);

        cmbKullaniciRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "kullanici" }));

        javax.swing.GroupLayout panelKullaniciLayout = new javax.swing.GroupLayout(panelKullanici);
        panelKullanici.setLayout(panelKullaniciLayout);
        panelKullaniciLayout.setHorizontalGroup(
            panelKullaniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKullaniciLayout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addGroup(panelKullaniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelKullaniciLayout.createSequentialGroup()
                        .addGroup(panelKullaniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelKullaniciLayout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(95, 95, 95))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelKullaniciLayout.createSequentialGroup()
                                .addComponent(btnKullaniciEkle)
                                .addGap(58, 58, 58)))
                        .addGroup(panelKullaniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnKullaniciSil)
                            .addComponent(cmbKullaniciRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelKullaniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelKullaniciLayout.createSequentialGroup()
                            .addGroup(panelKullaniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(58, 58, 58)
                            .addGroup(panelKullaniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtKullaniciSifre, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtKullaniciAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 178, Short.MAX_VALUE))
        );
        panelKullaniciLayout.setVerticalGroup(
            panelKullaniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKullaniciLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel14)
                .addGap(39, 39, 39)
                .addGroup(panelKullaniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKullaniciAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(panelKullaniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKullaniciSifre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(26, 26, 26)
                .addGroup(panelKullaniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cmbKullaniciRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(panelKullaniciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKullaniciSil)
                    .addComponent(btnKullaniciEkle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        panelSalon.setLayout(null);

        jLabel11.setText("Salon Adı:");
        panelSalon.add(jLabel11);
        jLabel11.setBounds(92, 89, 70, 16);
        panelSalon.add(txtSalonAdi);
        txtSalonAdi.setBounds(180, 86, 106, 22);

        jLabel12.setText("İl Seçimi:");
        panelSalon.add(jLabel12);
        jLabel12.setBounds(92, 120, 70, 16);

        cmbSaloniLSec.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelSalon.add(cmbSaloniLSec);
        cmbSaloniLSec.setBounds(180, 117, 106, 22);

        btnSalonEkle.setText("Ekle");
        btnSalonEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalonEkleActionPerformed(evt);
            }
        });
        panelSalon.add(btnSalonEkle);
        btnSalonEkle.setBounds(103, 166, 72, 23);

        btnSalonSil.setText("Sil");
        btnSalonSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalonSilActionPerformed(evt);
            }
        });
        panelSalon.add(btnSalonSil);
        btnSalonSil.setBounds(214, 166, 72, 23);

        lstSalonlar.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(lstSalonlar);

        panelSalon.add(jScrollPane4);
        jScrollPane4.setBounds(84, 218, 218, 155);

        jLabel13.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel13.setText("Salon Yönetimi");
        panelSalon.add(jLabel13);
        jLabel13.setBounds(119, 28, 149, 22);

        jLabel2.setText("İl Adı:");

        btniLekle.setText("Ekle");
        btniLekle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btniLekleActionPerformed(evt);
            }
        });

        btniLsil.setText("Sil");
        btniLsil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btniLsilActionPerformed(evt);
            }
        });

        lst_iller.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lst_iller);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("İl Yönetimi");

        javax.swing.GroupLayout paneliLLayout = new javax.swing.GroupLayout(paneliL);
        paneliL.setLayout(paneliLLayout);
        paneliLLayout.setHorizontalGroup(
            paneliLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneliLLayout.createSequentialGroup()
                .addGroup(paneliLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneliLLayout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addGroup(paneliLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(paneliLLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(paneliLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(paneliLLayout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtIlAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(paneliLLayout.createSequentialGroup()
                                        .addComponent(btniLekle, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(btniLsil, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(paneliLLayout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        paneliLLayout.setVerticalGroup(
            paneliLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneliLLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(paneliLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIlAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(38, 38, 38)
                .addGroup(paneliLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btniLekle)
                    .addComponent(btniLsil))
                .addGap(53, 53, 53)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelIcerikLayout = new javax.swing.GroupLayout(panelIcerik);
        panelIcerik.setLayout(panelIcerikLayout);
        panelIcerikLayout.setHorizontalGroup(
            panelIcerikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIcerikLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(paneliL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(311, 311, 311)
                .addComponent(panelSeans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(220, 220, 220)
                .addComponent(panelFilm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(248, 248, 248)
                .addComponent(panelSalon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(558, 558, 558)
                .addComponent(panelKullanici, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(374, Short.MAX_VALUE))
        );
        panelIcerikLayout.setVerticalGroup(
            panelIcerikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIcerikLayout.createSequentialGroup()
                .addGroup(panelIcerikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelIcerikLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(panelIcerikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelSeans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelKullanici, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelFilm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelIcerikLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(panelSalon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelIcerikLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(paneliL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(panelSolMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2084, Short.MAX_VALUE)
                .addComponent(panelIcerik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(305, 305, 305))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(panelSolMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(panelIcerik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(349, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btniLekleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btniLekleActionPerformed

    String ilAdi = txtIlAdi.getText().trim();
    if (ilAdi.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Lütfen bir il adı girin.");
        return;
    }

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "INSERT INTO iller (il_adi) VALUES (?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, ilAdi);// Sorguya il adını yerleştir
        ps.executeUpdate();// Sorguyu çalıştır
        
        JOptionPane.showMessageDialog(this, "İl eklendi.");
        txtIlAdi.setText(""); // Alanı temizle
        ilListele();// Listeyi günceller
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Ekleme hatası: " + e.getMessage());
    }

    }//GEN-LAST:event_btniLekleActionPerformed

    private void btniLsilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btniLsilActionPerformed
    
    String seciliIl = lst_iller.getSelectedValue();
    if (seciliIl == null) {
        JOptionPane.showMessageDialog(this, "Lütfen listeden bir il seçin.");
        return;
    }

    int onay = JOptionPane.showConfirmDialog(this, seciliIl + " ilini silmek istediğinize emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
    if (onay != JOptionPane.YES_OPTION) return;

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "DELETE FROM iller WHERE il_adi = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, seciliIl);// İl adı parametresini sorguya yerleştiriyoruz
        ps.executeUpdate(); // Sorguyu çalıştırıyoruz (veriyi siliyor)
        JOptionPane.showMessageDialog(this, "İl silindi.");
        ilListele();//Listeyi günceller
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Silme hatası: " + e.getMessage());
    }
    }//GEN-LAST:event_btniLsilActionPerformed

    private void btnFilmEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilmEkleActionPerformed
    String filmAdi = txtFilmAdi.getText().trim();
    String filmTur = txtFilmTur.getText().trim();

    if (filmAdi.isEmpty() || filmTur.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Film adı ve türü girin.");
        return;
    }

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "INSERT INTO filmler (film_adi, tur) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, filmAdi);
        ps.setString(2, filmTur);
        ps.executeUpdate();
        
        JOptionPane.showMessageDialog(this, "Film eklendi.");
       
        txtFilmAdi.setText("");
        txtFilmTur.setText("");
        filmListele();
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Film ekleme hatası: " + ex.getMessage());
    }
        
    }//GEN-LAST:event_btnFilmEkleActionPerformed

    private void btnFilmSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilmSilActionPerformed
        String secili = lstFilmler.getSelectedValue();
    if (secili == null) {
        JOptionPane.showMessageDialog(this, "Listeden bir film seçin.");
        return;
    }

    int filmId = Integer.parseInt(secili.split(" - ")[0]);

    int onay = JOptionPane.showConfirmDialog(this, "Silmek istiyor musunuz?", "Onay", JOptionPane.YES_NO_OPTION);
    if (onay != JOptionPane.YES_OPTION) return;

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "DELETE FROM filmler WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, filmId);
        ps.executeUpdate();
        
        JOptionPane.showMessageDialog(this, "Film silindi.");
        filmListele();
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Film silme hatası: " + ex.getMessage());
    }
    }//GEN-LAST:event_btnFilmSilActionPerformed

    private void btnFilmYonetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilmYonetActionPerformed
      
    }//GEN-LAST:event_btnFilmYonetActionPerformed

    private void btnILYonetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnILYonetActionPerformed
        
    }//GEN-LAST:event_btnILYonetActionPerformed

    private void btnSeansYonetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeansYonetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSeansYonetActionPerformed

    private void btnSeansEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeansEkleActionPerformed
            String saat = txtSeansSaat.getText().trim();
    if (saat.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Seans saati girin.");
        return;
    }

    if (cmbSalonSec.getSelectedItem() == null || cmbFilmSec.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this, "Salon ve film seçin.");
        return;
    }

    int salonId = Integer.parseInt(cmbSalonSec.getSelectedItem().toString().split(" - ")[0]);
    int filmId = Integer.parseInt(cmbFilmSec.getSelectedItem().toString().split(" - ")[0]);

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "INSERT INTO seanslar (saat, salon_id, film_id) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, saat);
        ps.setInt(2, salonId);
        ps.setInt(3, filmId);
        ps.executeUpdate();
        
        JOptionPane.showMessageDialog(this, "Seans eklendi.");
        txtSeansSaat.setText("");
        seansListele();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Seans ekleme hatası: " + e.getMessage());
    }
    }//GEN-LAST:event_btnSeansEkleActionPerformed

    private void btnSeansSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeansSilActionPerformed
         String secili = lstSeanslar.getSelectedValue();
    if (secili == null) {
        JOptionPane.showMessageDialog(this, "Listeden bir seans seçin.");
        return;
    }

    int seansId = Integer.parseInt(secili.split(" - ")[0]);

    int onay = JOptionPane.showConfirmDialog(this, "Silmek istiyor musunuz?", "Onay", JOptionPane.YES_NO_OPTION);
    if (onay != JOptionPane.YES_OPTION) return;

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "DELETE FROM seanslar WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, seansId);
        ps.executeUpdate();
        
        JOptionPane.showMessageDialog(this, "Seans silindi.");
        seansListele();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Seans silme hatası: " + e.getMessage());
    }
    }//GEN-LAST:event_btnSeansSilActionPerformed

    private void btnSalonEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalonEkleActionPerformed
       String salonAdi = txtSalonAdi.getText().trim();
    if (salonAdi.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Salon adı girin.");
        return;
    }

    if (cmbSaloniLSec.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this, "Bir il seçin.");
        return;
    }

    int ilId = Integer.parseInt(cmbSaloniLSec.getSelectedItem().toString().split(" - ")[0]);

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "INSERT INTO salonlar (salon_adi, il_id) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, salonAdi);
        ps.setInt(2, ilId);
        ps.executeUpdate();
        
        JOptionPane.showMessageDialog(this, "Salon eklendi.");
        txtSalonAdi.setText("");
        salonListele();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Salon ekleme hatası: " + e.getMessage());
    }
    }//GEN-LAST:event_btnSalonEkleActionPerformed

    private void btnSalonSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalonSilActionPerformed
       String secili = lstSalonlar.getSelectedValue();
    if (secili == null) {
        JOptionPane.showMessageDialog(this, "Listeden bir salon seçin.");
        return;
    }

    int salonId = Integer.parseInt(secili.split(" - ")[0]);

    int onay = JOptionPane.showConfirmDialog(this, "Silmek istiyor musunuz?", "Onay", JOptionPane.YES_NO_OPTION);
    if (onay != JOptionPane.YES_OPTION) return;

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "DELETE FROM salonlar WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, salonId);
        ps.executeUpdate();
        
        JOptionPane.showMessageDialog(this, "Salon silindi.");
        salonListele();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Salon silme hatası: " + e.getMessage());
    }
    }//GEN-LAST:event_btnSalonSilActionPerformed

    private void btnKullaniciEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKullaniciEkleActionPerformed
         String kullaniciAdi = txtKullaniciAdi.getText().trim();
    String sifre = txtKullaniciSifre.getText().trim();
    String rol = (String) cmbKullaniciRol.getSelectedItem();

    if (kullaniciAdi.isEmpty() || sifre.isEmpty() || rol == null) {
        JOptionPane.showMessageDialog(this, "Tüm bilgileri doldurun.");
        return;
    }

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "INSERT INTO kullanicilar (kullanici_adi, sifre, rol) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, kullaniciAdi);
        ps.setString(2, sifre);
        ps.setString(3, rol);
        ps.executeUpdate();
        
        JOptionPane.showMessageDialog(this, "Kullanıcı eklendi.");
        
        txtKullaniciAdi.setText("");
        txtKullaniciSifre.setText("");
        kullaniciListele();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Kullanıcı ekleme hatası: " + e.getMessage());
    }
    }//GEN-LAST:event_btnKullaniciEkleActionPerformed

    private void btnKullaniciSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKullaniciSilActionPerformed
         String secili = lstKullanicilar.getSelectedValue();
    if (secili == null) {
        JOptionPane.showMessageDialog(this, "Listeden bir kullanıcı seçin.");
        return;
    }

    int kullaniciId = Integer.parseInt(secili.split(" - ")[0]);

    int onay = JOptionPane.showConfirmDialog(this, "Silmek istiyor musunuz?", "Onay", JOptionPane.YES_NO_OPTION);
    if (onay != JOptionPane.YES_OPTION) return;

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db")) {
        String sql = "DELETE FROM kullanicilar WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, kullaniciId);
        ps.executeUpdate();
        
        JOptionPane.showMessageDialog(this, "Kullanıcı silindi.");
        kullaniciListele();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Kullanıcı silme hatası: " + e.getMessage());
    }
    }//GEN-LAST:event_btnKullaniciSilActionPerformed

    private void txtFilmAdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFilmAdiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFilmAdiActionPerformed

    private void cikisButonuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cikisButonuActionPerformed
          // Şu anki ekranı kapat
          this.dispose();
          new LoginEkrani().setVisible(true);
    }//GEN-LAST:event_cikisButonuActionPerformed


    public static void main(String args[]) {
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilmEkle;
    private javax.swing.JButton btnFilmSil;
    private javax.swing.JButton btnFilmYonet;
    private javax.swing.JButton btnILYonet;
    private javax.swing.JButton btnKullaniciEkle;
    private javax.swing.JButton btnKullaniciSil;
    private javax.swing.JButton btnKullaniciYonet;
    private javax.swing.JButton btnSalonEkle;
    private javax.swing.JButton btnSalonSil;
    private javax.swing.JButton btnSalonYonet;
    private javax.swing.JButton btnSeansEkle;
    private javax.swing.JButton btnSeansSil;
    private javax.swing.JButton btnSeansYonet;
    private javax.swing.JButton btniLekle;
    private javax.swing.JButton btniLsil;
    private javax.swing.JButton cikisButonu;
    private javax.swing.JComboBox<String> cmbFilmSec;
    private javax.swing.JComboBox<String> cmbKullaniciRol;
    private javax.swing.JComboBox<String> cmbSalonSec;
    private javax.swing.JComboBox<String> cmbSaloniLSec;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JList<String> lstFilmler;
    private javax.swing.JList<String> lstKullanicilar;
    private javax.swing.JList<String> lstSalonlar;
    private javax.swing.JList<String> lstSeanslar;
    private javax.swing.JList<String> lst_iller;
    private javax.swing.JPanel panelFilm;
    private javax.swing.JPanel panelIcerik;
    private javax.swing.JPanel panelKullanici;
    private javax.swing.JPanel panelSalon;
    private javax.swing.JPanel panelSeans;
    private javax.swing.JPanel panelSolMenu;
    private javax.swing.JPanel paneliL;
    private javax.swing.JTextField txtFilmAdi;
    private javax.swing.JTextField txtFilmTur;
    private javax.swing.JTextField txtIlAdi;
    private javax.swing.JTextField txtKullaniciAdi;
    private javax.swing.JTextField txtKullaniciSifre;
    private javax.swing.JTextField txtSalonAdi;
    private javax.swing.JTextField txtSeansSaat;
    // End of variables declaration//GEN-END:variables
}
