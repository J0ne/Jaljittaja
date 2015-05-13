/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tilastointi;

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
     *
     * @param alkupiste
     * @param maali
     * @param verkonKoko
     * @param kierrokset
     * @param liikkuvaMaali
     * @return info
     */
    public String Aja(Solmu alkupiste, Solmu maali, int verkonKoko, int kierrokset, boolean liikkuvaMaali) {

        maali.setMaali(true);
        int[] onnistumisTilastoTmp = new int[kierrokset];
        double[] kestojenTilastointi = new double[kierrokset];
        
        for (int i = 0; i < kierrokset; i++) {
            Verkko verkko = new Verkko(verkonKoko, alkupiste, maali);
            Polunetsija etsija = new Polunetsija(verkko, false);
            int polkuLoytyi = 0;
            Date startTime = new Date();

            Lista lyhinPolku = etsija.EtsiLyhinPolku(alkupiste, maali, liikkuvaMaali);

            if (lyhinPolku != null) {
//                System.out.println("Löytyi " + lyhinPolku.toString());
                polkuLoytyi = 100;
                onnistumisTilastoTmp[i] = polkuLoytyi;
            }

            Date endTime = new Date();
            double suorituksenKesto = endTime.getTime() - startTime.getTime();
            kestojenTilastointi[i] = suorituksenKesto;
            SuorituksenInfo tietorivi = etsija.getTietorivi();
            tietorivi.setVerkonKoko(verkonKoko);
            tietorivi.setSuorituksenKesto(suorituksenKesto);
            tietorivi.setPolkuLoytyi(polkuLoytyi);
            suoritustenData.Lisaa(tietorivi);
        }
        int onnistumisprosentti = laskeOnnistumisprosentti(onnistumisTilastoTmp);
        double keskimaarainenSuoritusaika = laskeKeskiasrvo(kestojenTilastointi);
        int suoritukset = suoritustenData.AlkioidenMaara();

        return "Solmuja: " + verkonKoko*verkonKoko + ", kierroksia " + kierrokset
                + ", polku löytyi " + onnistumisprosentti + "% suorituksista, aikaa/suoritus n " + keskimaarainenSuoritusaika + " ms";
    }

    private int laskeOnnistumisprosentti(int[] onnistumisTilastoTmp) {
        int kokonaisMaara = onnistumisTilastoTmp.length;
        int onnistumiset = 0;
        for (int p : onnistumisTilastoTmp) {
            if (p== 100) {
                onnistumiset++;
            }
        }
        return onnistumiset  * 100 / kokonaisMaara;
    }

    private double laskeKeskiasrvo(double[] kestojenTilastointi) {
        double kaikki = 0;
        for (double aika : kestojenTilastointi) {
            kaikki+=aika;
        }
        return kaikki / kestojenTilastointi.length;
    }
}
