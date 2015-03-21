/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittajaUI;

import jaljittaja.Solmu;
import jaljittaja.Verkko;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jouni
 */
public class Verkkonakyma extends Frame {

    private final int PITUUS = 45;
    private final int KORKEUS = 35;

    /**
     *
     * @param verkko
     */
    public Verkkonakyma(Verkko verkko) {
        super("Jäljittaja");
        this.verkko = verkko;
        setSize(1200, 900);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }
    final static BasicStroke stroke = new BasicStroke(2);

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

    public void PiirraSolmut(ArrayList<Solmu> solmut, Graphics g, int tila) {
        Graphics2D g2d = (Graphics2D) g;

        int i = 0;
        int tempI = 0;

        for (Solmu[] rivi : this.verkko.Solmut) {
            int j = 0;
            int tempJ = 0;
            for (Solmu solmu : rivi) {

                String nimi = solmu.getX() + "," + solmu.getY();
                for (Solmu s : solmut) {
                    //System.out.println("X: " +s.getX() +", Y: " + s.getY() + " -> i: " + tempI + ", j: " + tempJ);
                    if (s.getX() == tempI && s.getY() == tempJ && !solmu.OnEste) {
                        if (!s.isMaali()) {
                            PiirraSolmu(g2d, i, j, "" + s.getG_arvo(), tila);
                        }
                        else{
                            PiirraSolmu(g2d, i, j, "" + s.getG_arvo(), 4);
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
                color = Color.darkGray;
                break;
        }
        if (state > 0) {
            g2d.setPaint(color);
            g2d.fill(new Rectangle2D.Double(x1 + 5, y1 + 5, PITUUS - 4, KORKEUS - 4));
            g2d.drawRect(x1, y1, PITUUS, KORKEUS);
        }
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x1, y1, PITUUS, KORKEUS);;
        g2d.drawString(id, x1 + 10, y1 + 25);
    }
}
