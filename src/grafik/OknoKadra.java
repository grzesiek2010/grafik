/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grafik;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 *
 * @author Grzesiek
 */
public class OknoKadra extends JFrame{
    private JPanel panel;
    private JButton edytuj;
    private JList lista;
    private String[] listaTab;
    private int i;
    private JTextField poleNazwisko;
    private JLabel tekstNazwisko;
    private String name;
    private Grafik grafikK;
            

    public OknoKadra(Grafik grafikK) {
        this.setSize(350, 400);
        this.setTitle("Kadra");
        this.setVisible(true);
        this.setLocation(300, 155);
        this.setResizable(false);
        this.setContentPane(getPanel());
        this.grafikK = grafikK;
    }
    
    private JPanel getPanel(){
        panel = new JPanel();
        panel.setLayout(null);
        try {
            FileReader fr = new FileReader("./kadra/kadra.txt");
            BufferedReader br = new BufferedReader(fr);
            String nazwisko;
            listaTab = new String[8];
            i = 0;
            while((nazwisko = br.readLine()) != null)
            {
                listaTab[i]=nazwisko;
                i++;
            }
            fr.close();
                   
            } catch (IOException ex) {
                Logger.getLogger(Grafik.class.getName()).log(Level.SEVERE, null, ex);
            }
        lista = new JList(listaTab);
        lista.setLocation(55, 35);
        lista.setSize(240, 190);
        lista.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                boolean adjust = listSelectionEvent.getValueIsAdjusting();
                if (!adjust) {
                    int selections[] = lista.getSelectedIndices();
                    Object selectedValues[] = lista.getSelectedValues();
                    for (int i = 0, n = selections.length; i < n; i++) {
                        name = (String) selectedValues[i];
                        poleNazwisko.setText((String) selectedValues[i]);
                        poleNazwisko.setEnabled(true);
                        edytuj.setEnabled(true);
                    }
                }
            }
    });
        panel.add(lista);
        
        poleNazwisko = new JTextField();
        poleNazwisko.setSize(240, 25);
        poleNazwisko.setLocation(55, 260);
        poleNazwisko.setEnabled(false);
        poleNazwisko.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {
                    listaTab[lista.getSelectedIndex()]=poleNazwisko.getText();
                    grafikK.odswiez();
                    repaint();
                    try {
                        FileWriter fw = new FileWriter("./kadra/kadra.txt");
                        for(int i=0; i<8; i++){
                            fw.write(listaTab[i] + System.getProperty( "line.separator" ));
                        }
                        fw.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Grafik.class.getName()).log(Level.SEVERE, null, ex);
                        }  
                    }          
                }

           public void keyReleased(KeyEvent e) {}
	});      
        panel.add(poleNazwisko);
        
        tekstNazwisko = new JLabel("Nazwisko:");
        tekstNazwisko.setLocation(55, 237);
        tekstNazwisko.setSize(70, 20);
        panel.add(tekstNazwisko);    
        
        edytuj = new JButton("Edytuj");
        edytuj.setToolTipText("Kliknij by edytować pracownika");
        edytuj.setLocation(55, 300);
        edytuj.setSize(70, 25);
        edytuj.setEnabled(false);
        edytuj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listaTab[lista.getSelectedIndex()]=poleNazwisko.getText();
                grafikK.odswiez();
                repaint();
                try {
                    FileWriter fw = new FileWriter("./kadra/kadra.txt");
                    for(int i=0; i<8; i++){
                        fw.write(listaTab[i] + System.getProperty( "line.separator" ));
                       
                    }
                    fw.close();
                   
                    } catch (IOException ex) {
                        Logger.getLogger(Grafik.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
	});
        panel.add(edytuj);
        
        return panel;
    }
}
