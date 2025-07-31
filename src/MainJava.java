
import gui.LoginEkrani;
import java.io.File;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class MainJava {
     public static void main(String[] args) {
         System.out.println("Veritabanı yolu: " + new java.io.File("sinema.db").getAbsolutePath());
         File dbFile = new File("C:/Users/HP/Documents/NetBeansProjects/SinemaBiletOtomasyonu/sinema.db");
         System.out.println("Gerçekten açılan dosya: " + dbFile.getAbsolutePath());
         System.out.println("Dosya var mı?: " + dbFile.exists());
         
         new LoginEkrani().setVisible(true);

}
}