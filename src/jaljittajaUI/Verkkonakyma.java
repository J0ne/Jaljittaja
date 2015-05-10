/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittajaUI;

import Tilastointi.SuorituksenInfo;
import jaljittaja.Jaljittaja;
import jaljittaja.tietorakenteet.Lista;
import jaljittaja.verkko.Solmu;
import jaljittaja.verkko.Verkko;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicBorders;

/**
 *
 * @author jouni
 */
public class Verkkonakyma extends JFrame {

    public int getPITUUS() {
        return PITUUS;
    }

    public void setPITUUS(int PITUUS) {
        this.PITUUS = PITUUS;
    }

    public int getKORKEUS() {
        return KORKEUS;
    }

    public void setKORKEUS(int KORKEUS) {
        this.KORKEUS = KORKEUS;
    }

    private int PITUUS = 40;
    private int KORKEUS = 30;
    JTextArea txtArea; 
    JLabel lblOhjeet;
    JPanel pnlInfo;
    JPanel jpanel;
    JPanel jpanel2;
    JButton start;
    JButton btnHiirimoodi;
    JButton btnAjaMassana;
    JButton btnAlusta;
    JLabel lblHiirimoodi;
    JLabel lblMassaAjonInfo;
    JLabel lblKirjoitaCsv;
    JCheckBox cbLiikkuvaMaali, cbKirjoitaTiedostoon;
    JLabel lblLiikkuvaMaali;
    Solmu maali = null;
    JTextArea txtVerkonKoko;
    JTextArea txtAlkupiste;
    Lista<Solmu> maaliLista;
    private int hiirimoodi = 0;
    JComboBox<Integer> verkonKoko = new JComboBox<>();
    

    
    JComboBox<Integer> nopeus = new JComboBox<>();
    public int getHiirimoodi() {
        return hiirimoodi;
    }

    public void setHiirimoodi() {
        if (this.hiirimoodi > 1) {
            this.hiirimoodi = 0;
        } else {
            hiirimoodi++;
        }
    }

    final static BasicStroke stroke = new BasicStroke(1);

    /**
     *
     * @param verkko
     */
    public Verkkonakyma(final Verkko verkko) {
        super("Jäljittaja");
        this.verkko = verkko;
        setSize(1200, 900);
        setVisible(true);
        pnlInfo = new JPanel();
        pnlInfo.setAlignmentX(CENTER_ALIGNMENT);
        pnlInfo.setAlignmentY(TOP_ALIGNMENT);
        jpanel = new JPanel();
        jpanel.setAlignmentX(TOP_ALIGNMENT);
        jpanel.setAlignmentY(CENTER_ALIGNMENT);
        jpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        verkonKoko.addItem(5);
        verkonKoko.addItem(10);
        verkonKoko.addItem(20);
        verkonKoko.addItem(50);
        verkonKoko.addItem(100);
        
        nopeus.addItem(0);
        nopeus.addItem(3);
        nopeus.addItem(7);
        nopeus.addItem(10);
//        lblOhjeet = new JLabel("<html>Voit ajaa ohjelmaa joko a) käyttäen käyttöliittymää tai" + 
//                                    "b) suorittaa sitä suuremmalla aineistolla taustalla, ns. massa-ajona." +
//                "<br> Käyttöliittymä havainnollistaa algoritmin toimintaa ja voit testata sitä vaihtelemalla " + 
//                "lähtöpisteen paikkaa tai asettamalla esteitä, ts. muokata kenttää. todo...</html>");
//                " <br> Käyttöliittymältä käyttäessä voit asettaa maalisolmun liikkuvaksi. Voit myös asettaa esteitä matriisiin, jolloin" + 
//                " "
//                        +"</html>");
//        pnlInfo.add(lblOhjeet);
        txtArea = new JTextArea(4, 50);
        
        lblMassaAjonInfo = new JLabel();
        
        JScrollPane scroller = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        btnHiirimoodi = new JButton("Aseta");
        lblHiirimoodi = new JLabel();
        cbLiikkuvaMaali = new JCheckBox();
        lblKirjoitaCsv = new JLabel("Kirjoita tulos csv-tiedostoon");
        cbKirjoitaTiedostoon = new JCheckBox();
        lblLiikkuvaMaali = new JLabel("Liikkuva maali:");
        VaihdaHiirimoodia();
//        MenuBar menu = new MenuBar();
//        Menu info = new Menu("jee");
//        menu.add(new Menu("Info"));
//        this.setMenuBar(menu);
        jpanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Koordinaatit xy = annaSijaintiMatriisissa(e.getLocationOnScreen().getLocation());
                if (xy == null) {
                    return;
                }
                KasitteleKlikkaus(xy.getX(), xy.getY());
            }

            private void KasitteleKlikkaus(int x, int y) {
                Graphics2D g = (Graphics2D) getGraphics();
                Lista<Solmu> kasiteltavaL = new Lista<Solmu>();
                Solmu kasiteltava = getVerkko().Solmut[x][y];
                kasiteltavaL.Lisaa(kasiteltava);
                switch (hiirimoodi) {
                    case 0:

                        PiirraSolmut(kasiteltavaL, g, 3);
                        getVerkko().AsetaAlkupiste(x, y);
                        break;
                    case 2:
                        PiirraSolmut(kasiteltavaL, g, 10);
                        getVerkko().AsetaEste(x, y);
                        break;
                    default:
                        break;
                }

            }

        });
        start = new JButton("Aloita");

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graphics2D g = (Graphics2D) getGraphics();
                paint(g);
                boolean liikkuvaMaali = cbLiikkuvaMaali.isSelected();
                int viiveMs = (int)nopeus.getSelectedItem();
                Jaljittaja.Kaynnista(getVerkko(), liikkuvaMaali, viiveMs);

            }
        });
        
        //btnAlusta = new JButton("Päivitä");
        verkonKoko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtArea.setText("");
                int verkonSivu = (int)verkonKoko.getSelectedItem();
                if(verkonSivu < 50){
                    setKORKEUS(30);
                    setPITUUS(40);
                }
                else
                {
                    setKORKEUS(7);
                    setPITUUS(10);
                }
                Jaljittaja.Alusta(verkonSivu);
                Graphics2D g = (Graphics2D) getGraphics();  
                paint(g);
            }
        });
        
        btnHiirimoodi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                VaihdaHiirimoodia();
            }
        });
        btnAjaMassana = new JButton("Suorita massa-ajona taustalla");
        btnAjaMassana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean kirjoitaCsv = cbKirjoitaTiedostoon.isSelected();
                boolean liikkuvaMaali = cbLiikkuvaMaali.isSelected();
                Jaljittaja.KaynnistaMassaAjona(kirjoitaCsv,liikkuvaMaali);
            }
        });
jpanel.add(pnlInfo);
        jpanel.add(lblLiikkuvaMaali);
        jpanel.add(cbLiikkuvaMaali);
        jpanel.add(btnHiirimoodi);
        jpanel.add(lblHiirimoodi);
        
        jpanel.add(new JLabel("Verkon sivu:"));
        jpanel.add(verkonKoko);

        
        //jpanel.add(btnAlusta);
        jpanel.add(new JLabel("Näytön nopeus (ms): "));
        jpanel.add(nopeus);
        start.setBorder(new BasicBorders.ButtonBorder(Color.yellow, Color.GREEN, Color.lightGray, Color.blue));
                start.setBorderPainted(true);
        jpanel.add(start);
        jpanel2 = new JPanel();
        jpanel2.setLocation(150, 800);
        //jpanel2.setLayout(new FlowLayout(FlowLayout.LEFT));

        //jpanel2.set
        jpanel2.add(btnAjaMassana);
        jpanel2.add(new JLabel("Massa-ajon info"));
        jpanel2.add(scroller);
        
        getContentPane().add(jpanel2, BorderLayout.SOUTH);
        txtArea.setEditable(false);
        jpanel2.add(txtArea);
        jpanel2.add(cbKirjoitaTiedostoon);
        jpanel2.add(lblKirjoitaCsv);
        
        //jpanel.add(jpanel2);
        this.add(jpanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }

    public void setMassaAjonInfoteksti(String info) {
        String temptxt = txtArea.getText();
        temptxt += "\n" + info;
        //lblMassaAjonInfo.setText(temptxt);
        txtArea.setText(temptxt);
    }

    private void VaihdaHiirimoodia() {
        this.setHiirimoodi();
        switch (this.getHiirimoodi()) {
            case 0:
                btnHiirimoodi.setText("Aseta alkupiste");
                break;
            case 1:
                //lblHiirimoodi.setText("[ei valittu]");
                break;
            case 2:
                btnHiirimoodi.setText("Aseta este");
                break;

        }
    }

    private void setText(String text) {
        this.txtArea.setText(text);
    }

    class Koordinaatit {

        public Koordinaatit(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        int x;
        int y;
    }

    private Koordinaatit annaSijaintiMatriisissa(Point point) {
        int sivu = this.getVerkko().Solmut.length;
        int x = (((int) point.getX() - 100) / getPITUUS());
        int y = (((int) point.getY() - 100) / getKORKEUS()); // 160 ... 170 Mac vs. Ubuntu,Win 
        if (point.getX() < 100 || point.getY() < 100) {
            return null;
        }

        return new Koordinaatit(x, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(stroke);
        int i = 0;
        int vari = 0;
        for (Solmu[] rivi : this.getVerkko().Solmut) {
            int j = 0;
            for (Solmu solmu : rivi) {

                String nimi = solmu.getX() + "," + solmu.getY();
                if (solmu.OnEste) {
                    PiirraSolmu(g2d, i, j, nimi, 10);
                } else {
                    PiirraSolmu(g2d, i, j, nimi, 0);
                }
                if (solmu.OnAlkupiste) {
                    PiirraSolmu(g2d, i, j, "" + solmu.getG_arvo(), 3);
                }
                if (solmu.Maali) {
                    PiirraSolmu(g2d, i, j, "" + solmu.getG_arvo(), 4);
                }
                j += KORKEUS;
            }
            i += PITUUS;
        }
    }

    public void PiirraSolmut(Lista<Solmu> solmutLista, Graphics g, int tila) {
        Graphics2D g2d = (Graphics2D) g;
        int i = 0;
        int tempI = 0;

        for (Solmu[] rivi : this.getVerkko().Solmut) {
            int j = 0;
            int tempJ = 0;
            for (Solmu solmu : rivi) {

                String nimi = solmu.getX() + "," + solmu.getY();
                for (Solmu s : solmutLista) {
                    //System.out.println("X: " +s.getX() +", Y: " + s.getY() + " -> i: " + tempI + ", j: " + tempJ);
                    if (s.getX() == tempI && s.getY() == tempJ && !solmu.OnEste) {
                        if (!s.isMaali()) {
                            if (!s.OnEste || !s.OnAlkupiste) {
                                if (tila != 0) {
                                    // naapurisolmuille kustannus
                                    nimi = "" + s.getG_arvo();
                                }
                                PiirraSolmu(g2d, i, j, nimi, tila);
                            }

                        } else {
                            PiirraSolmu(g2d, i, j, nimi, 4);
                        }

                    }
                }
                j += KORKEUS;
                tempJ++;
            }
            i += PITUUS;
            tempI++;
        }

    }
    /**
     *
     */
    public Verkko verkko;

    /**
     *
     * @return
     */
    public Verkko getVerkko() {
        return verkko;
    }

    /**
     *
     * @param verkko
     */
    public void setVerkko(Verkko verkko) {
        this.verkko = verkko;
    }

    private void PiirraSolmu(Graphics2D g2d, int x, int y, String id, int state) {
        int x1 = 100 + x;
        int y1 = 80 + y;
        Color color;
        switch (state) {
            case 1:
                color = Color.BLUE;
                break;
            case 2:
                color = Color.MAGENTA;
                break;
            case 3:
                color = Color.GREEN;
                break;
            case 4:
                color = Color.RED;
                break;
            case 5:
                color = Color.ORANGE;
                break;
            case 10:
                color = Color.BLACK;
                break;
            default:
                color = Color.WHITE;
                break;
        }
        if (state > -1) {

            g2d.setPaint(color);
            g2d.fill(new Rectangle2D.Double(x1, y1, PITUUS-1, KORKEUS-1));
            g2d.drawRect(x1, y1, PITUUS, KORKEUS);
        }
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x1, y1, PITUUS, KORKEUS);
        if (state == 1) {
            g2d.setColor(Color.WHITE);
        }
        
        String lbl = "";
        if(verkko.Solmut.length < 50){
            lbl = id;
        }
        g2d.drawString(lbl, x1 + 5, y1 + 25);
    }
}
