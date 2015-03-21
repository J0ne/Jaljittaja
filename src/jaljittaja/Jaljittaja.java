
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jaljittaja;

import jaljittajaUI.Verkkonakyma;
import java.awt.Graphics;
import java.util.ArrayList;
import sun.awt.X11.XConstants;

/**
 *
 * @author jouni
 */
public class Jaljittaja {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Solmu alkupiste = new Solmu(3, 6, false, true);
        Solmu maali = new Solmu(14, 12, false);
        
        Verkko matriisi = new Verkko(15, alkupiste, maali);
        
        Polunetsija etsija = new Polunetsija(matriisi);        
        etsija.EtsiLyhinPolku(alkupiste, maali);
        
        //Verkkonakyma vn = new Verkkonakyma(matriisi);
        
    }
    
}
