/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittaja;

import java.util.ArrayList;

/**
 * Priotiteettijono, jonka prioriteetti perustuu solmun F-arvoon
 * @author jouni
 */
public class Prioriteettijono {

    /**
     *
     * @return
     */
    public ArrayList<Solmu> getLista() {
        if (lista != null) {
            return lista;
        } else {
            lista = new ArrayList<>();
            return lista;
        }
    }

    int pieninKustannus = 0;

    /**
     *
     * @param lisattava
     */
    public void LisaaListaan(Solmu lisattava) {
        System.out.println("Lisätty: x:" + lisattava.getX() + ", j:" + lisattava.getY() + " G: "
                + lisattava.getG_arvo());
        lista = this.getLista();
        lista.add(lisattava);
    }

    /**
     *
     * @return
     */
    public Solmu AnnaSolmu() {
        int pieninF_arvo = 1000; // tarpeeksi korkea, joka päivittyy listaa läpi käydessä
        int palautettavanIndeksi = -1;
        for (int i = 0; i < lista.size(); i++) {
            Solmu kandidaatti = lista.get(i);
            if (kandidaatti.getF_arvo() < pieninF_arvo) {
                pieninF_arvo = kandidaatti.getF_arvo();
                palautettavanIndeksi = i;
            }

        }
        Solmu palautettava = lista.get(palautettavanIndeksi);
        lista.remove(palautettavanIndeksi);
        return palautettava;
    }
    ArrayList<Solmu> lista;

    /**
     *
     * @param solmu
     */
    public void PoistaSolmu(Solmu solmu) {
        lista.remove(solmu);
    }

    /**
     *
     * @return
     */
    public int ListanKoko() {
        return lista.size();
    }

    /**
     *
     * @param solmu
     * @return
     */
    public int AnnaSolmunIndeksi(Solmu solmu) {
        return lista.indexOf(solmu);
    }
}
