/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grafik;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author Grzegorz
 */
public class GUI extends JFrame{
    private JPanel panelGrafikTworzenie;
    private JMenuBar menu;
    private JMenu plik;
    private JMenu pomoc;
    private JMenuItem drukuj;
    private JMenuItem drukujObecnosc;
    private JMenuItem zakoncz;
    private JMenuItem kadra;
    private JMenuItem oProgramie;
    private JLabel naglowekGrafik;
    private JComboBox miesiaceGrafik;
    private Grafik grafikK;
    private ImageIcon img;
    private OknoKadra oknoKadra;
    private Drukownik drukownik;
    private String[] miesiaceLista= {"Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"};
    
    public GUI() {
        this.setContentPane(getGrafikTworzenie());
        this.setJMenuBar(getMenu());
     }
    
    private JMenuBar getMenu(){
        menu = new JMenuBar();
        plik = new JMenu("Plik");
        drukuj = new JMenuItem("Drukuj");
        drukuj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int dzien, noc;
                boolean test = true;
                for(int i = 0; i < grafikK.getTablica().length; i++){
                    dzien=0;
                    noc=0;
                    for(int j = 0; j < grafikK.getTablica()[0].length; j++){
                        if(grafikK.getTablica()[i][j]==GrafikStatus.DZIEN)
                            dzien ++;
                        if(grafikK.getTablica()[i][j]==GrafikStatus.NOC)
                            noc++;
                    }
                    if(dzien!=2||noc!=2){
                        JOptionPane.showMessageDialog(null, "W grafiku znaleziono błąd w rozkładzie zmian! Popraw go!", "Bląd", JOptionPane.INFORMATION_MESSAGE);
                        test = false;
                        break;
                    }
                }
                if(test==true){
                    try {
                        File img1 = new File("./grafik/grafik.png");
                        final BufferedImage image = ImageIO.read(img1);
                        Graphics g = image.getGraphics();
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("Times New Roman", Font.BOLD, 90));
                        g.drawString(grafikK.getTytul(), 1370, 155);
                    
                        for(int i = 0; i <= grafikK.getTablica().length; i++){
                            if(i == 0)
                                g.setColor(Color.BLACK);
                            else if(i%2!=0)
                                g.setColor(new Color(192, 192, 192));
                            else
                                g.setColor(new Color(128, 128, 128));
                            g.fillRect(185 , 205 + 65 * i, 280, 57);
                        }
                        for(int i = 0; i <= grafikK.getTablica().length; i++)
                            for(int j = 0; j < grafikK.getTablica()[0].length; j++){
                                if(i == 0)
                                    g.setColor(Color.BLACK);
                                else if(i%2!=0)
                                    g.setColor(new Color(192, 192, 192));
                                else
                                    g.setColor(new Color(128, 128, 128));                                  
                                g.fillRect(472 + 356 * j, 205 + 65 * i, 348, 57); 
                            }                            
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("Times New Roman", Font.BOLD, 50));
                        g.drawString("DZIEŃ", 260, 250);
                        try {
                            FileReader fr = new FileReader("./kadra/kadra.txt");
                            BufferedReader br = new BufferedReader(fr);
                            String nazwisko;
                            int s;
                            int k=1;
                            while((nazwisko = br.readLine()) != null)
                            {
                                s = nazwisko.length();
                                System.out.println(s);
                                g.drawString(nazwisko, 122 + 356 * k + (356-26*s)/2, 250);
                                k++;
                            }
                            fr.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Grafik.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                        g.setFont(new Font("Times New Roman", Font.BOLD, 50));
                        for(int i = 0; i< grafikK.getTablica().length; i++){
                            if(grafikK.getKolumnaDni()[i].toString().endsWith("So") || grafikK.getKolumnaDni()[i].toString().endsWith("N"))
                                g.setColor(Color.RED);
                            else
                                g.setColor(Color.BLACK);
                            g.drawString(grafikK.getKolumnaDni()[i], 260, 315 + 65 * i);
                        }
                        g.setColor(Color.BLACK);  
                        g.setFont(new Font("Times New Roman", Font.PLAIN, 50));
                        for(int i = 0; i < grafikK.getTablica().length; i++)
                            for(int j = 0; j < grafikK.getTablica()[0].length; j++){
                                if(grafikK.getTablica()[i][j]==GrafikStatus.DZIEN)
                                    g.drawString(grafikK.getTablica()[i][j].toString(), 570 + j * 356, 315 + i * 65);
                                else if(grafikK.getTablica()[i][j]==GrafikStatus.NOC)
                                    g.drawString(grafikK.getTablica()[i][j].toString(), 590 + j * 356, 315 + i * 65);     
                            }
                            g.dispose();
                        File img2 = new File("./grafik/grafik.png");
                        final BufferedImage image2 = ImageIO.read(img2);
                        Graphics g1 = image2.getGraphics();
                        g1.setColor(Color.BLACK);
                        g1.fillRect(182 , 202, 3136, grafikK.getTablica().length*65+65);
                        g1.setFont(new Font("Times New Roman", Font.BOLD, 90));
                        g1.drawString(grafikK.getTytul(), 1370, 155);
                    
                        for(int i = 0; i <= grafikK.getTablica().length; i++){
                            g1.setColor(Color.WHITE);
                            g1.fillRect(185 , 205 + 65 * i, 280, 57);
                        }
                        for(int i = 0; i <= grafikK.getTablica().length; i++)
                            for(int j = 0; j < grafikK.getTablica()[0].length; j++){
                                g1.setColor(Color.WHITE);                               
                                g1.fillRect(472 + 356 * j, 205 + 65 * i, 348, 57);
                            }                            
                        g1.setColor(Color.BLACK);
                        g1.setFont(new Font("Times New Roman", Font.BOLD, 50));
                        g1.drawString("DZIEŃ", 260, 250);
                        try {
                            FileReader fr = new FileReader("./kadra/kadra.txt");
                            BufferedReader br = new BufferedReader(fr);
                            String nazwisko;
                            int s;
                            int k=1;
                            while((nazwisko = br.readLine()) != null){
                                s = nazwisko.length();
                                g1.drawString(nazwisko, 122 + 356 * k + (356-26*s)/2, 250);
                                k++;
                            }
                            fr.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Grafik.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        g1.setFont(new Font("Times New Roman", Font.BOLD, 50));
                        for(int i = 0; i< grafikK.getTablica().length; i++)
                            g1.drawString(grafikK.getKolumnaDni()[i], 260, 315 + 65 * i);
                           
                        g1.setFont(new Font("Times New Roman", Font.PLAIN, 50));
                        for(int i = 0; i < grafikK.getTablica().length; i++)
                            for(int j = 0; j < grafikK.getTablica()[0].length; j++){
                                if(grafikK.getTablica()[i][j]==GrafikStatus.DZIEN)
                                    g1.drawString(grafikK.getTablica()[i][j].toString(), 570 + j * 356, 315 + i * 65);
                                else if(grafikK.getTablica()[i][j]==GrafikStatus.NOC)
                                    g1.drawString(grafikK.getTablica()[i][j].toString(), 590 + j * 356, 315 + i * 65);               
                            }
                        g1.dispose();
                           
                        BufferedImage image1;
                        String userHomeFolder = System.getProperty("user.home");
                        image1=rotate90ToRight(image2);
                        ImageIO.write(image1, "png", new File("./grafik/grafik1.png"));
                        try{
                            ImageIO.write(image, "png", new File(userHomeFolder+"/Pulpit/Grafik.png"));
                        }catch(Exception s){
                            ImageIO.write(image, "png", new File(userHomeFolder+"/Desktop/Grafik.png"));
                        }
                            
                    } catch (IOException ex) {}                                        
                    img = new javax.swing.ImageIcon("./grafik/grafik1.png");
                    drukownik = new Drukownik(img);
                    drukownik.start();
                }
            } 
	});
        drukujObecnosc = new JMenuItem("Drukuj grafik obecności");
        drukujObecnosc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                            File img2 = new File("./grafik/grafik.png");
                            final BufferedImage image2 = ImageIO.read(img2);
                            Graphics g1 = image2.getGraphics();
                            g1.setColor(Color.BLACK);
                            g1.fillRect(182 , 202, 3136, grafikK.getTablica().length*65+195);
                            g1.setFont(new Font("Times New Roman", Font.BOLD, 90));
                            g1.drawString(grafikK.getTytul(), 1370, 155);
                    
                            for(int i = 0; i <= grafikK.getTablica().length+2; i++){
                                g1.setColor(Color.WHITE);
                                g1.fillRect(185 , 205 + 65 * i, 280, 57);
                            }
                            for(int i = 0; i <= grafikK.getTablica().length+2; i++)
                                for(int j = 0; j < grafikK.getTablica()[0].length; j++){
                                    g1.setColor(Color.WHITE);                               
                                    g1.fillRect(472 + 356 * j, 205 + 65 * i, 348, 57);
                           
                                }                            
                            g1.setColor(Color.BLACK);
                            g1.setFont(new Font("Times New Roman", Font.BOLD, 50));
                            g1.drawString("DZIEŃ", 260, 250);
                            try {
                                FileReader fr = new FileReader("./kadra/kadra.txt");
                                BufferedReader br = new BufferedReader(fr);
                                String nazwisko;
                                int s;
                                int k=1;
                                while((nazwisko = br.readLine()) != null)
                                {
                                    s = nazwisko.length();
                                    g1.drawString(nazwisko, 122 + 356 * k + (356-26*s)/2, 250);
                                    k++;
                                }
                            fr.close();
                   
                            } catch (IOException ex) {
                                Logger.getLogger(Grafik.class.getName()).log(Level.SEVERE, null, ex);
                            }
                       
                            g1.setFont(new Font("Times New Roman", Font.BOLD, 50));
                            for(int i = 0; i< grafikK.getTablica().length; i++)
                                g1.drawString(grafikK.getKolumnaDni()[i], 260, 315 + 65 * i);
                            g1.drawString("Suma", 260, 250 + 65 * (grafikK.getTablica().length+1));
                            g1.drawString("Urlop", 260, 250 + 65 * (grafikK.getTablica().length+2));
                            g1.dispose();
                           
                            BufferedImage image1;
                            image1=rotate90ToRight(image2);
                            ImageIO.write(image1, "png", new File("./grafik/grafikObecnosc.png"));
                            
                        } catch (IOException ex) {
                        }                                        
                        img = new javax.swing.ImageIcon("./grafik/grafikObecnosc.png");
                        drukownik = new Drukownik(img);
                        drukownik.start();
            }
	});
        kadra = new JMenuItem("Kadra");
        kadra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                oknoKadra = new OknoKadra(grafikK);                
            }
	});
        zakoncz = new JMenuItem("Zakończ", 'Z');
        zakoncz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 System.exit(0);               
            }
	});
        zakoncz.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
        plik.add(drukuj);
        plik.add(drukujObecnosc);
        plik.add(kadra);
        plik.addSeparator();
        plik.add(zakoncz);
        menu.add(plik);
        pomoc = new JMenu("Pomoc");
        oProgramie = new JMenuItem("O programie", 'O');
        oProgramie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "<html><center><h1><i>Grafik</i></h1><hr>Autor: Grzegorz Orczykowski<BR/>e-mail: <a href=\"mailto:grzesiek2010@gmail.com\">grzesiek2010@gmail.com</a><BR/>wersja: 1.1<BR/>data wydania: 01.09.2013<BR/>data wydania aktualnej wersji: 12.12.2013</center></html>", "O programie", JOptionPane.INFORMATION_MESSAGE);
            }
	});
        oProgramie.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        pomoc.add(oProgramie);
        menu.add(pomoc);
        return menu;
    }
    public BufferedImage rotate90ToRight( BufferedImage inputImage ){
	int width = inputImage.getWidth();
	int height = inputImage.getHeight();
	BufferedImage returnImage = new BufferedImage( height, width , inputImage.getType()  );

	for( int x = 0; x < width; x++ ) {
		for( int y = 0; y < height; y++ ) {
                       
			returnImage.setRGB((height-y-1),x,inputImage.getRGB( x,y));
		}
	}
	return returnImage;
}
    private JPanel getGrafikTworzenie(){
        panelGrafikTworzenie = new JPanel();
        panelGrafikTworzenie.setSize(993, 900);
        panelGrafikTworzenie.setLayout(null);
        naglowekGrafik = new JLabel("Grafik");
        naglowekGrafik.setSize(300, 30);
        naglowekGrafik.setLocation(367, 5);
        naglowekGrafik.setFont(new Font("Times New Roman", Font.BOLD, 27));
        panelGrafikTworzenie.add(naglowekGrafik);
        miesiaceGrafik = new JComboBox(miesiaceLista);
        Calendar cal = Calendar.getInstance();
        if(cal.get(Calendar.MONTH)<11)
            miesiaceGrafik.setSelectedIndex(cal.get(Calendar.MONTH)+1);
        else
            miesiaceGrafik.setSelectedIndex(0);
        miesiaceGrafik.setLocation(472, 5);
        miesiaceGrafik.setSize(180, 30);
        miesiaceGrafik.setFont(new Font("Times New Roman", Font.BOLD, 27));
        
        panelGrafikTworzenie.add(miesiaceGrafik);
        grafikK = new Grafik(this, miesiaceGrafik.getSelectedIndex());
        grafikK.setLocation(32, 45);
        panelGrafikTworzenie.add(grafikK);
        miesiaceGrafik.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                grafikK.odswiez(miesiaceGrafik.getSelectedIndex());
            }
	});  
        return panelGrafikTworzenie;
    }
}
