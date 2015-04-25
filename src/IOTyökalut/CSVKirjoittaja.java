/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOTyökalut;

import Tilastointi.SuorituksenInfo;
import jaljittaja.tietorakenteet.Lista;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Tämä luokka kirjoittaa Massasuorittaja-ajon tulokset csv-tiedostoon.
 *
 * @author jouni
 */
public class CSVKirjoittaja {

    private static final String EROTINMERKKI = ",";
    private static final String RIVINVAIHTO = "\n";
    private static final String TIEDOSTON_OTSIKKO = "solmujen määrä, polku löytyi, kesto (ms), käsitellyt solmut, maalin askeleet";

    public static void kirjoitaCSVTiedosto(String tiedostonNimi, Lista<SuorituksenInfo> data) {
        FileWriter tiedostokirjoittaja = null;

        try {
            tiedostokirjoittaja = new FileWriter(tiedostonNimi);

            // otsikkorivi
            tiedostokirjoittaja.append(TIEDOSTON_OTSIKKO);
            tiedostokirjoittaja.append(RIVINVAIHTO);

            // loopataan kaikki data
            for (SuorituksenInfo suorituksenInfo : data) {
                tiedostokirjoittaja.append(String.valueOf(suorituksenInfo.getVerkonKoko()));
                tiedostokirjoittaja.append(EROTINMERKKI);
                tiedostokirjoittaja.append(String.valueOf(suorituksenInfo.isPolkuLoytyi()));
                tiedostokirjoittaja.append(EROTINMERKKI);
                tiedostokirjoittaja.append(String.valueOf(suorituksenInfo.getSuorituksenKesto()));
                tiedostokirjoittaja.append(EROTINMERKKI);
                tiedostokirjoittaja.append(String.valueOf(suorituksenInfo.getMaalinStepit()));
                tiedostokirjoittaja.append(RIVINVAIHTO);

            }

            System.out.println("CSV:n luonti onnistui!");
        } catch (Exception e) {
            System.out.println("CSV:n luonti epäonnistui. Virhe: " + e.getMessage());
        } finally {
            try {
                tiedostokirjoittaja.flush();
                tiedostokirjoittaja.close();
            } catch (IOException e) {
                System.out.println("Virhe  Tiedostokirjoittajan lopetusrutiineissa (flush / close)");

            }

        }

    }

}
