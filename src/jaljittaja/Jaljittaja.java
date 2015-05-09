
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittaja;

import IOTyökalut.CSVKirjoittaja;
import jaljittaja.verkko.Verkko;
import jaljittaja.verkko.Solmu;
import Tilastointi.Massasuorittaja;

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
    
    public static void Alusta(){
        alkupiste = new Solmu(0, 8, false, true);
        maali = new Solmu(9, 8, false);
        maali.setMaali(true);

        matriisi = new Verkko(10, alkupiste, maali);
        
        etsija = new Polunetsija(matriisi);
    }
    
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
     * @param verkko
     * @param liikkuvaMaali
     */
    public static void Kaynnista(Verkko verkko, boolean liikkuvaMaali) {
        matriisi = verkko;
        etsija = new Polunetsija(matriisi);
        etsija.EtsiLyhinPolku(matriisi.alkupiste, matriisi.maali, liikkuvaMaali);
    }
    /**
     * Polunetsinnän käynnistäminen massa-ajona.
     * HUOM! Aja-metodin kutsujan vastuulla asettaa järkevät parametrit
     * Ei syötteen tarkistuksia!
     * @param kirjoitaTiedostoon
     */
    public static void KaynnistaMassaAjona(boolean kirjoitaTiedostoon){
        
        // I ajo
        int verkonSivu = 10; // -> 10 * 10 = 100 solmua
        int kierrokset = 10;
        Solmu alkupiste = new Solmu(1, 1, false, true);
        Solmu maali = new Solmu(8, 8, false);
        boolean liikkuvaMaali = true;
        Massasuorittaja suorittaja = new Massasuorittaja();
        String info =  suorittaja.Aja(alkupiste, maali, verkonSivu, kierrokset, liikkuvaMaali);
        
        Polunetsija.nakyma.setMassaAjonInfoteksti(info);
        
        // II ajo
//        verkonSivu = 100;
//        maali = new Solmu(80, 80, false);
//        info =  suorittaja.Aja(alkupiste, maali, verkonSivu, kierrokset, liikkuvaMaali);
//        Polunetsija.nakyma.setMassaAjonInfoteksti(info);
//        
//        
        if(kirjoitaTiedostoon){
          String tiedostonNimi = System.getProperty("user.home") + "/polunetsija.csv";
          CSVKirjoittaja.kirjoitaCSVTiedosto(tiedostonNimi, suorittaja.getSuoritustenData());
          Polunetsija.nakyma.setMassaAjonInfoteksti("Raaka-data tiedostossa: " +tiedostonNimi);
        }

    }
}
