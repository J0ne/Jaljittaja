/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tilastointi;

import IOTyökalut.CSVKirjoittaja;
import jaljittaja.tietorakenteet.Lista;
import jaljittaja.Polunetsija;
import jaljittaja.verkko.Solmu;
import jaljittaja.verkko.Verkko;
import java.util.Date;

/**
 * Tämä luokka suorittaa useita polunetsintöjä annetuin parametrein ja kokoaa
 * suoritusten tiedot
 *
 * @author jouni
 */
public class Massasuorittaja {

    Lista<SuorituksenInfo> suoritustenData = new Lista<>();

    public Lista<SuorituksenInfo> getSuoritustenData() {
        return suoritustenData;
    }

    public void setSuoritustenData(Lista<SuorituksenInfo> suoritustenData) {
        this.suoritustenData = suoritustenData;
    }
    /**
     * Ajetaan useampi etsintä silmukassa.
     * @param alkupiste
     * @param maali
     * @param verkonKoko
     * @param kierrokset
     * @param liikkuvaMaali
     * @return info
     */
    public String Aja(Solmu alkupiste, Solmu maali, int verkonKoko, int kierrokset, boolean liikkuvaMaali) {
        
        maali.setMaali(true);
        for (int i = 0; i < kierrokset; i++) {
            Verkko verkko = new Verkko(verkonKoko, alkupiste, maali);
            Polunetsija etsija = new Polunetsija(verkko, false);
            boolean polkuLoytyi = false;
            Date startTime = new Date();
            
            Lista lyhinPolku = etsija.EtsiLyhinPolku(alkupiste, maali, liikkuvaMaali);
            
            if (lyhinPolku != null) {
//                System.out.println("Löytyi " + lyhinPolku.toString());
                polkuLoytyi = true;
            }
            Date endTime = new Date();
            double suorituksenKesto = endTime.getTime() - startTime.getTime();
            SuorituksenInfo tietorivi = etsija.getTietorivi();
            tietorivi.suorituksenKesto = suorituksenKesto;
            tietorivi.setPolkuLoytyi(polkuLoytyi);
            suoritustenData.Lisaa(tietorivi);
          }
//        for (SuorituksenInfo suoritustenData1 : suoritustenData) {
//            System.out.println(suoritustenData1.toString());
//            
//        }
        int suoritukset = suoritustenData.AlkioidenMaara();
        
        return "Verkon sivu: " + verkonKoko +", kierroksia " + kierrokset + ", suorituksia (kumul.)" + suoritukset + 
                ", polku löytyi (%)" + " TODO:"; 
    }
}
