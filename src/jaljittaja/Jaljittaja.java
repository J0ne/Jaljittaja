
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
     * Pääohjelma
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Solmu alkupiste = new Solmu(1, 6, false, true);
        Solmu maali = new Solmu(16,13, false);
        maali.setMaali(true);
        Verkko matriisi = new Verkko(20, alkupiste, maali);
        
        Polunetsija etsija = new Polunetsija(matriisi);
        boolean liikkuvaMaali = false;
        etsija.EtsiLyhinPolku(alkupiste, maali, liikkuvaMaali);
        
        //Verkkonakyma vn = new Verkkonakyma(matriisi);
        
    }
    
}
