/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grafik;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author Grzesiek
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable()
            {
                public void run()
                {
                    JFrame frame = new GUI();
                    frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                    frame.setSize(1024, 730);
                    frame.setResizable(false);
                    frame.setTitle("Grafik v1.1");
                    frame.setVisible(true);
                }
            });
    }
}
