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
        Solmu maali = new Solmu(45, 45, false);
        maali.setMaali(true);

        
        for (int i = 0; i < 50; i++) {
            Verkko verkko = new Verkko(50, alkupiste, maali);
            Polunetsija etsija = new Polunetsija(verkko, false);
            boolean polkuLoytyi = false;
            Date startTime = new Date();
            
            Lista lyhinPolku = etsija.EtsiLyhinPolku(alkupiste, maali, true);
            
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
        for (SuorituksenInfo suoritustenData1 : suoritustenData) {
            System.out.println(suoritustenData1.toString());
            
        }
        int suoritukset = suoritustenData.AlkioidenMaara();
    }
}
