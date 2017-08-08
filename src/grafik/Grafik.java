/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grafik;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Grzesiek
 */
public class Grafik extends JPanel{
    private  Calendar calendar = new GregorianCalendar();
    private int rok;
    private Calendar toDay = Calendar.getInstance();
    private int dlugosc;
    private GrafikStatus[][] tablica;
    private String[] weekdayNames = new DateFormatSymbols().getShortWeekdays();
    private String[] kolumnaDni;
    private int dayNumber;
    private GUI gui;
    private String tytul;
    private String[] miesiaceLista= {"Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"};
    
    public Grafik(GUI gui, int miesiac) {   
        this.gui = gui;
        rok = toDay.get(Calendar.YEAR);
        if(miesiac < toDay.get(Calendar.MONTH))
            rok++;
        calendar.set(rok,miesiac,1);
        dlugosc = (calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1) * 17 + (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 2) * 2;
        tablica = new GrafikStatus[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)][8];
        for(int i = 0; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
            for(int j = 0; j < 8; j++)
                tablica[i][j] = GrafikStatus.WOLNE;     
        tytul = "Grafik " + miesiaceLista[miesiac];
        Dimension rozmiar = new Dimension(956, dlugosc);
	setSize(rozmiar);
	setMinimumSize(rozmiar);
	setMaximumSize(rozmiar);
	setPreferredSize(rozmiar);
        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e){
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  
            }
        }); 
        addMouseListener(new MouseAdapter() { 
            int x, y, z, w;
            public void mousePressed(MouseEvent e){
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Point p = e.getPoint();
                    if (p.x % 106 != 0 && p.x % 106 != 1 && p.y % 19 != 0 && p.y % 19 != 1) {
                        x = (p.x - 1) / 106 - 1;
                        y = (p.y - 1) / 19 - 1;            
                        if(x>=0 && y>=0){
                            if(tablica[y][x] == GrafikStatus.WOLNE)
                                tablica[y][x] = GrafikStatus.DZIEN;
                            else if(tablica[y][x] == GrafikStatus.DZIEN)
                                tablica[y][x] = GrafikStatus.NOC;
                            else if(tablica[y][x] == GrafikStatus.NOC || tablica[y][x] == GrafikStatus.IKS)
                                tablica[y][x] = GrafikStatus.WOLNE;
                            repaint();
                        }
                    }
                }
                else if (e.getButton() == MouseEvent.BUTTON3) {
                    Point p = e.getPoint();
                    if (p.x % 106 != 0 && p.x % 106 != 1 && p.y % 19 != 0 && p.y % 19 != 1) {
                        x = (p.x - 1) / 106 - 1;
                        y = (p.y - 1) / 19 - 1;            
                        if(x>=0 && y>=0){
                            if(tablica[y][x] == GrafikStatus.IKS)
                                tablica[y][x] = GrafikStatus.WOLNE;
                            else
                                tablica[y][x] = GrafikStatus.IKS;
                            repaint();
                        }
                    }
                }                 
            }    
	});    
    }
    
    public void odswiez(int miesiac){
        rok = toDay.get(Calendar.YEAR);
        if(miesiac < toDay.get(Calendar.MONTH))
            rok++;
        calendar.set(rok,miesiac,1);
        dayNumber = calendar.get(Calendar.DAY_OF_WEEK);
        dlugosc = (calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+1) * 17 + (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 2) * 2;
        tablica = new GrafikStatus[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)][8];
        tytul = "Grafik " + miesiaceLista[miesiac];
        Dimension rozmiar = new Dimension(956, dlugosc);
	setSize(rozmiar);
	setMinimumSize(rozmiar);
	setMaximumSize(rozmiar);
	setPreferredSize(rozmiar);
        for(int i = 0; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
            for(int j = 0; j < 8; j++)
                tablica[i][j] = GrafikStatus.WOLNE;        
        repaint();
    }
        
    @Override
    public void paint(Graphics g) {  
        dayNumber = calendar.get(Calendar.DAY_OF_WEEK);
	Image img = createImage(getSize().width, getSize().height);
	Graphics2D g2 = (Graphics2D) img.getGraphics();
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2.setColor(Color.WHITE);
	g2.fillRect(0, 0, 956, dlugosc);
        g2.setFont(new Font("Times New Roman", Font.BOLD, 15));
	for (int i = 0; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
            for (int j = 0; j < 9; j++) {
                    if(i == 0)
                        g2.setColor(Color.BLACK);
                    else if(i%2==0)
                        g2.setColor(new Color(128, 128, 128));
                    else
                        g2.setColor(new Color(192, 192, 192));
                    g2.fillRect(2+ 106 * j, 2 + 19 * i, 104, 17);
                }
        kolumnaDni = new String[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)];
        for (int i = 0; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
            for (int j = 0; j < 9; j++) {
                    if(i == 0){
                        g2.setColor(Color.WHITE);
                        if(i == 0 && j ==0)
                            g2.drawString("DZIEŃ", 30, 16);  
                    }
                    if(j == 0 && i > 0){
                        if(dayNumber == 1 || dayNumber == 7)
                            g2.setColor(Color.RED);
                        else
                            g2.setColor(Color.BLACK);
                        kolumnaDni[i-1] = (i + ".  " + weekdayNames[dayNumber]).toString();
                        g2.drawString(i + ".  " + weekdayNames[dayNumber], 32, 16 + 19 *i);   
                        dayNumber++;
                        if(dayNumber == 8)
                            dayNumber=1;
                    }
                    if(i>=1 && j>=1){
                        g2.setColor(Color.BLACK);
                        if(tablica[i-1][j-1] == GrafikStatus.DZIEN)
                            g2.drawString("DZIEŃ", 31 + 106 * j, 16 + 19 * i);
                        else if(tablica[i-1][j-1] == GrafikStatus.NOC)
                            g2.drawString("NOC", 36 + 106 * j, 16 + 19 * i);
                        else if(tablica[i-1][j-1] == GrafikStatus.IKS)
                            g2.drawString("X", 48 + 106 * j, 16 + 19 * i);
                    }
                }
                try {
                    FileReader fr = new FileReader("./kadra/kadra.txt");
                    BufferedReader br = new BufferedReader(fr);
                    String nazwisko;
                    int s;
                    int k=1;
                    g2.setColor(Color.WHITE);
                    while((nazwisko = br.readLine()) != null)
			{
                            s = nazwisko.length();
                            g2.drawString(nazwisko, (106 - 8*s)/2 + 106 * k + 3, 16);
                            k++;
			}
                    fr.close();
                   
                } catch (IOException ex) {
                    Logger.getLogger(Grafik.class.getName()).log(Level.SEVERE, null, ex);
                }
		g.drawImage(img, 0, 0, this); 
	}
      
        public GrafikStatus[][] getTablica(){
            return tablica;
        }
        
        public String[] getKolumnaDni(){
            return kolumnaDni;
        }
        
        public void odswiez(){
            repaint();
        }
        
        public String getTytul(){
            return tytul;
        }
        
	@Override
	public void update(Graphics g) {
            paint(g);
	}    
}
