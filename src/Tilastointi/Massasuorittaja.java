/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tilastointi;

import jaljittaja.Lista;
import jaljittaja.Polunetsija;
import jaljittaja.Solmu;
import jaljittaja.Verkko;
import java.util.Date;

/**
 * Tämä luokka suorittaa useita polunetsintöjä annetuin parametrein ja kokoaa
 * suoritusten tiedot
 *
 * @author jouni
 */
public class Massasuorittaja {

    Lista<SuorituksenInfo> suoritustenData = new Lista<>();
    /**
     * Ajetaan useampi etsintä silmukassa.
     */
    public void Aja() {
        // todo: nämä parametreiksi
        Solmu alkupiste = new Solmu(1, 1, false, true);
        Solmu maali = new Solmu(4, 4, false);
        maali.setMaali(true);
        Verkko verkko = new Verkko(10, alkupiste, maali);
        Polunetsija etsija = new Polunetsija(verkko, false);
        for (int i = 0; i < 5; i++) {
            Date startTime = new Date();
            Lista lyhinPolku = etsija.EtsiLyhinPolku(alkupiste, maali, false);
            
            if (lyhinPolku.AlkioidenMaara() > 0) {
                System.out.println("Löytyi " + lyhinPolku.toString());
            }
            Date endTime = new Date();
            double suorituksenKesto = endTime.getTime() - startTime.getTime();
            etsija.getTietorivi().suorituksenKesto = suorituksenKesto;
            suoritustenData.Lisaa(etsija.getTietorivi());
        }
        for (SuorituksenInfo suoritustenData1 : suoritustenData) {
            suoritustenData1.toString();
            
        }
        int suoritukset = suoritustenData.AlkioidenMaara();
    }
}
