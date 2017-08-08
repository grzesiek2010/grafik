/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grafik;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.ImageIcon;

/**
 *
 * @author Grzesiek
 */
public class Drukownik extends Thread{
    
    private ImageIcon img;
    
    public Drukownik(ImageIcon img){
        this.img = img;
    }
    
    public void run(){
        final PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(new Printable()
        {
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex != 0) {
                    return NO_SUCH_PAGE;
                }
                graphics.drawImage(img.getImage(), 0, 30, 600, 766, null);
                return PAGE_EXISTS;
            }
        });
        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (Exception prt) {
            }
        }        
    }
}
