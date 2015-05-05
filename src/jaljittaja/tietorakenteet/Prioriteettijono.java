/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittaja.tietorakenteet;

import jaljittaja.verkko.Solmu;

/**
 * Priotiteettijono, jonka prioriteetti perustuu solmun F-arvoon
 *
 * @author jouni
 */
public class Prioriteettijono {

    /**
     *
     * @return
     */
    private Lista<Solmu> getLista() {
        if (lista != null) {
            return lista;
        } else {
            lista = new Lista<>();
            return lista;
        }
    }

    int pieninKustannus = 0;

    /**
     * Lisää alkion listaan
     *
     * @param lisattava
     */
    public void LisaaListaan(Solmu lisattava) {
//        System.out.println("Lisätty: x:" + lisattava.getX() + ", j:" + lisattava.getY() + " G: "
//                + lisattava.getG_arvo());
        lista = this.getLista();
        lista.Lisaa(lisattava);
    }

    /**
     * Antaa listan pienimmän F-arvon omaavan solmun ja poistaa sen listasta
     *
     * @return
     */
    public Solmu AnnaSolmu() {
        int pieninF_arvo = 1000; // tarpeeksi korkea, joka päivittyy listaa läpi käydessä
        int palautettavanIndeksi = -1;
        for (int i = 0; i < lista.AlkioidenMaara(); i++) {
            Solmu kandidaatti = lista.AnnaAlkio(i);
            if (kandidaatti.getF_arvo() < pieninF_arvo) {
                pieninF_arvo = kandidaatti.getF_arvo();
                palautettavanIndeksi = i;
            }

        }
        Solmu palautettava = lista.AnnaAlkio(palautettavanIndeksi);
        lista.PoistaListasta(palautettava);
        return palautettava;
    }

    private Lista<Solmu> lista;

    /**
     * Poistaa parametrina annetun solmun listasta
     *
     * @param solmu
     */
    public void PoistaSolmu(Solmu solmu) {
        lista.PoistaListasta(solmu);
    }

    /**
     * Palauttaa lista koon
     *
     * @return
     */
    public int ListanKoko() {
        return getLista().AlkioidenMaara();
    }

    /**
     * Palauttaa listan indeksin, jossa annettu solmu on. Jos solmua ei ole
     * listassa, palauttaa -1
     *
     * @param solmu
     * @return
     */
    public boolean OnkoJonossa(Solmu solmu) {
        return lista.OnkoAlkioListassa(solmu);
    }
}
