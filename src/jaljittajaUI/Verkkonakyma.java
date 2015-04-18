/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittajaUI;

import jaljittaja.Jaljittaja;
import jaljittaja.Lista;
import jaljittaja.Solmu;
import jaljittaja.Verkko;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 *
 * @author jouni
 */
public class Verkkonakyma extends JFrame {

    private final int PITUUS = 45;
    private final int KORKEUS = 35;
    JTextArea txtArea;
    JPanel jpanel;
    JButton start;
    JButton btnHiirimoodi;
    JLabel lblHiirimoodi;
    Solmu maali = null;
    JTextArea txtVerkonKoko;
    JTextArea txtAlkupiste;
    Lista<Solmu> maaliLista;
    private int hiirimoodi = 0;

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

    final static BasicStroke stroke = new BasicStroke(2);

    /**
     *
     * @param verkko
     */
    public Verkkonakyma(final Verkko verkko) {
        super("Jäljittaja");
        this.verkko = verkko;
        setSize(1200, 900);
        setVisible(true);

        jpanel = new JPanel();
        jpanel.setAlignmentX(TOP_ALIGNMENT);
        jpanel.setAlignmentY(TOP_ALIGNMENT);
        jpanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        jpanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        txtArea = new JTextArea(1, 8);
        btnHiirimoodi = new JButton("Aseta");
        lblHiirimoodi = new JLabel();
        VaihdaHiirimoodia();
        jpanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Koordinaatit xy = annaSijaintiMatriisissa(e.getLocationOnScreen().getLocation());
                if (xy == null) {
                    return;
                }
                KasitteleKlikkaus(xy.getX(), xy.getY());
//                setText(xy.getX() + ", " + xy.getY());
//                maali = new Solmu(xy.getX(), xy.getY(), false, false);
//                maali.setMaali(true);
//                Graphics2D g = (Graphics2D) getGraphics();
//                maaliLista = new Lista<Solmu>();
//                maaliLista.Lisaa(maali);
//                PiirraSolmut(maaliLista, g, 1);
            }

            private void KasitteleKlikkaus(int x, int y) {
                Graphics2D g = (Graphics2D) getGraphics();
                Lista<Solmu> kasiteltavaL = new Lista<Solmu>();
                Solmu kasiteltava = verkko.Solmut[x][y];
                kasiteltavaL.Lisaa(kasiteltava);
                switch(hiirimoodi){
                    case 2:
                        PiirraSolmut(kasiteltavaL, g, 10);
                        verkko.AsetaEste(x, y);
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
//                Jaljittaja.KaynnistaMassaAjona();
                 Jaljittaja.Kaynnista();
//                if(maaliLista.AlkioidenMaara() > 0){
//                    Jaljittaja.Kaynnista(maaliLista.AnnaAlkio(0));
//                }
//                else{
//                    Jaljittaja.Kaynnista();
//                }
            }
        });
        btnHiirimoodi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                VaihdaHiirimoodia();
            }
        });
        jpanel.add(start);
        jpanel.add(txtArea);

        jpanel.add(btnHiirimoodi);
        jpanel.add(lblHiirimoodi);

        this.add(jpanel);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }

    private void VaihdaHiirimoodia() {
        this.setHiirimoodi();
        switch (this.getHiirimoodi()) {
            case 0:
                lblHiirimoodi.setText("alkupiste");
                break;
            case 1:
                lblHiirimoodi.setText("maali");
                break;
            case 2:
                lblHiirimoodi.setText("este");
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

        int sivu = this.verkko.Solmut.length;
        int x = (((int) point.getX() - 150) / PITUUS);
        int y = (((int) point.getY() - 170) / KORKEUS);
        if (point.getX() < 150 || point.getY() < 170) {
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
        for (Solmu[] rivi : this.verkko.Solmut) {
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

        //ArrayList<Solmu> solmutArrayList = solmutLista.AnnaListaArrayListina();
        Graphics2D g2d = (Graphics2D) g;
        int i = 0;
        int tempI = 0;

        for (Solmu[] rivi : this.verkko.Solmut) {
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
                            //PiirraSolmu(g2d, i, j, nimi, 0);
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
        int x1 = 150 + x;
        int y1 = 150 + y;
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
                color = Color.YELLOW;
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
            g2d.fill(new Rectangle2D.Double(x1, y1, PITUUS - 3, KORKEUS - 3));
            g2d.drawRect(x1, y1, PITUUS, KORKEUS);
        }
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x1, y1, PITUUS, KORKEUS);
        if (state == 1) {
            g2d.setColor(Color.WHITE);
        }
        g2d.drawString(id, x1 + 5, y1 + 25);
    }
}
