/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittaja.tietorakenteet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Geneerinen tietorakenne
 *
 * @author jouni
 * @param <E>
 */
public class Lista<E> implements Iterable<E> {

    private int koko = 0;
    private static final int KAPASITEETTI = 10;
    private Object[] alkiot;

    /**
     * Konstruktori
     */
    public Lista() {
        this.alkiot = new Object[KAPASITEETTI];
    }

    /**
     * Lisää alkion listaan
     *
     * @param olio
     */
    public void Lisaa(E olio) {
        if (koko == alkiot.length) {
            kasvataListanKokoa();
        }
        alkiot[koko++] = olio;
    }

    private void kasvataListanKokoa() {
        int tuplattuKoko = alkiot.length * 2;

        // todo: saako käyttää, vai pitääkö tehdä itse?
        alkiot = Arrays.copyOf(alkiot, tuplattuKoko);
    }

    /**
     * Palauttaa alkion listasta indeksin mukaan
     *
     * @param indeksi
     * @return
     */
    public E AnnaAlkio(int indeksi) {
        if (indeksi < 0 || indeksi > alkiot.length) {
            throw new IndexOutOfBoundsException("Listassa on " + alkiot.length + " oliota. Haetun indeksi " + indeksi);
        }
        return (E) alkiot[indeksi];
    }

    /**
     * Poistaa parametrina annetun alkion listasta.
     *
     * @param poistettava
     */
    public void PoistaListasta(E poistettava) {
        int poistettavanIndeksi = -1;
        // haetaan poistettavan indeksi
        for (int i = 0; i < alkiot.length; i++) {

            if (alkiot[i] != null && alkiot[i].equals(poistettava)) {
                poistettavanIndeksi = i;
            }
        }
        if (poistettavanIndeksi == -1) {
            return;
        }
        // siirretään poistettavan indekin jälkeisiä "vasemmalle" listassa
        for (int j = poistettavanIndeksi + 1; j <= alkiot.length; j++) {
            if (j < alkiot.length) {
                alkiot[j - 1] = alkiot[j];
            }
        }
        koko--;

        // todo: piennä listaa tarvittaessa
    }

    /**
     * Palauttaa listan alkioiden määrän. Huom. eri kuin listan kapasiteetti.
     *
     * @return koko
     */
    public int AlkioidenMaara() {
        return koko;
    }

    /**
     * Palauttaa tiedon, onko parametrna annettu alkio listassa
     *
     * @param alkio
     * @return
     */
    public boolean OnkoAlkioListassa(E alkio) {
        for (Object alkiot1 : alkiot) {

            if (alkiot1 != null && alkiot1.equals(alkio)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Palauttaa listan alkiot ArrayList-tietorakenteessa. Apumetodi testatusta
     * varten
     *
     * @return
     */
    public ArrayList<E> AnnaListaArrayListina() {
        ArrayList<E> palautettava = new ArrayList<>();
        for (Object alkiot1 : alkiot) {
            if (alkiot1 != null) {
                palautettava.add((E) alkiot1);
            }
        }
        return palautettava;

    }

    @Override
    public String toString() {
        String alkiotMjono = "";
        for (Object alkiot1 : alkiot) {
            if (alkiot1 != null) {
                alkiotMjono += "\r\n " + alkiot1;
            }
        }
        return "Lista{" + "koko=" + koko + ", alkiot:" + alkiotMjono + '}';
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {

            private int indeksi = 0;

            @Override
            public boolean hasNext() {
                return indeksi < alkiot.length && alkiot[indeksi] != null;
            }

            @Override
            public E next() {
                return (E) alkiot[indeksi++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}
