
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittaja;

import Tilastointi.Massasuorittaja;
import jaljittajaUI.Verkkonakyma;
import java.awt.Graphics;
import java.util.ArrayList;
import sun.awt.X11.XConstants;

/**
 * Jaljittaja-luokka, josta ohjelma käynnistetään halutulla tavalla
 * @author jouni
 */
public class Jaljittaja {

    /**
     * Pääohjelma
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        alkupiste = new Solmu(0, 8, false, true);
        maali = new Solmu(16, 13, false);
        maali.setMaali(true);

        matriisi = new Verkko(20, alkupiste, maali);
        etsija = new Polunetsija(matriisi);
        //Verkkonakyma vn = new Verkkonakyma(matriisi);
    }
    private static Solmu alkupiste;
    private static Solmu maali;
    private static Polunetsija etsija;
    private static Verkko matriisi;

    /**
     * Polunetsinnän käynnistävä metodi valmiilla, kovakoodatuilla syötteillä
     */
    public static void Kaynnista() {
        boolean liikkuvaMaali = false;

        etsija.EtsiLyhinPolku(alkupiste, maali, liikkuvaMaali);
    }

    /**
     * Polunetsinnän käynnistävä metodi, jonka avulla maali voidaan antaa parametrina
     * @param uusiMaali
     */
    public static void Kaynnista(Solmu uusiMaali) {
        boolean liikkuvaMaali = false;
        uusiMaali.setMaali(true);
        etsija = new Polunetsija(matriisi);
        etsija.EtsiLyhinPolku(alkupiste, uusiMaali, liikkuvaMaali);
    }
        /**
     * Polunetsinnän käynnistävä metodi, jonka avulla maali voidaan antaa parametrina
     * @param uusiMaali
     */
    public static void Kaynnista(Verkko verkko) {
        boolean liikkuvaMaali = false;
        matriisi = verkko;
        etsija = new Polunetsija(matriisi);
        etsija.EtsiLyhinPolku(matriisi.alkupiste, matriisi.maali, liikkuvaMaali);
    }
    /**
     * Polunetsinnän käynnistäminen massa-ajona
     */
    public static void KaynnistaMassaAjona(){
        Massasuorittaja suorittaja = new Massasuorittaja();
        suorittaja.Aja();
    }
}
