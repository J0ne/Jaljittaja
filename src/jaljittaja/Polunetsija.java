/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittaja;

import jaljittajaUI.Verkkonakyma;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Luokka toteuttaa polunetsintäalgoritmin, sekä kaikki sen tarvitsemat
 * tietorakenteet ja apumetodit
 *
 * @author jouni
 */
public class Polunetsija {

    /**
     * Konstruktori joka saa parametriä verkon
     *
     * @param verkko
     */
    public Polunetsija(Verkko verkko) {
        this.verkko = verkko;
        if (nakyma == null) {
            nakyma = new Verkkonakyma(verkko);
        }
        piirretaanUI = true;
        g = nakyma.getGraphics();
    }

    // todo: refaktorointi, nyt vaan ylikirjoitetaan signature, vaikka piirretaan <- aina false tässä
    public Polunetsija(Verkko verkko, boolean ilmanUI) {
        this.verkko = verkko;
        this.piirretaanUI = false;
    }
    /**
     * Verkko
     */
    public Verkko verkko;

    /**
     * Verkkonäkymä. Tämän avulla voidaan havainnollistaa algortimin suorituksen
     * etenemistä
     */
    public static Verkkonakyma nakyma;
    Graphics g;
    boolean piirretaanUI;

    /**
     * Metodi joka etsii lyhyimmän polun pisteestä A pisteeseen B.
     *
     * @param alkupiste
     * @param maali
     * @param liikkuvaMaali
     * @return
     */
    // todo: refaktoroitava
    public Lista<Solmu> EtsiLyhinPolku(Solmu alkupiste, Solmu maali, boolean liikkuvaMaali) {

        Prioriteettijono avoinLista = new Prioriteettijono(); // open set 
        Lista<Solmu> suljettuLista = new Lista<Solmu>(); // closed set

        Lista<Solmu> kuljettuReitti = new Lista<Solmu>();

        int G_arvo = 0;
        int F_arvo = G_arvo + laskeHeuristinenArvio(alkupiste, maali);
        alkupiste.setF_arvo(F_arvo);
        alkupiste.setEdeltaja(null);
        avoinLista.LisaaListaan(alkupiste);
        Solmu tmp = maali;
        while (avoinLista.ListanKoko() != 0) {
            Solmu nykyinen = avoinLista.AnnaSolmu();
            suljettuLista.Lisaa(nykyinen);

            // piirretään verkkoa 
            if (piirretaanUI) {
                Lista<Solmu> nykyinenPiirrettava = new Lista<Solmu>();
                nykyinenPiirrettava.Lisaa(nykyinen);
                nakyma.PiirraSolmut(nykyinenPiirrettava, g, 5);
            }

            Solmu liikkunutMaali;
            // maali liikkuu vain ehdollisena
            if (liikkuvaMaali) {
                liikkunutMaali = LiikutaMaalia(tmp);
                tmp.setMaali(false);
                if (piirretaanUI) {
                    Lista<Solmu> maaliPiirrettava = new Lista<Solmu>();
                    maaliPiirrettava.Lisaa(liikkunutMaali);
                    maaliPiirrettava.Lisaa(tmp);
                    nakyma.PiirraSolmut(maaliPiirrettava, g, 0);
                }

                System.out.println(liikkunutMaali.toString() + " X: " + liikkunutMaali.getX() + " Y: " + liikkunutMaali.getY());
                tmp = liikkunutMaali;
            } else {
                liikkunutMaali = tmp;
            }

            if (nykyinen.isMaali()) {
                System.out.println("Tulostetaan polku:");
                kuljettuReitti = tulostaPolku(nykyinen);
                if (piirretaanUI) {
                    nakyma.PiirraSolmut(kuljettuReitti, g, 1);
                }

                return kuljettuReitti;
            }
            Lista<Solmu> naapuriSolmut = annaSolmunNaapurit(nykyinen);
            if (piirretaanUI) {
                nakyma.PiirraSolmut(naapuriSolmut, g, 2);
            }

            for (Solmu naapuri : naapuriSolmut) {
                if (suljettuLista.OnkoAlkioListassa(naapuri)) {
                    continue;
                }

                int kokeiltava_G_arvo = nykyinen.getG_arvo() + annaEtaisyys(nykyinen, naapuri);
                System.out.println(" Alustava G: " + kokeiltava_G_arvo);
                //if neighbor in OPEN and cost less than g(neighbor):
                if (avoinLista.OnkoJonossa(naapuri) & kokeiltava_G_arvo < naapuri.getG_arvo()) {
                    //remove neighbor from OPEN, because new path is better
                    avoinLista.PoistaSolmu(naapuri);
                }
                //if neighbor in CLOSED and cost less than g(neighbor): **
                if (suljettuLista.OnkoAlkioListassa(naapuri) && kokeiltava_G_arvo < naapuri.getG_arvo()) {
                    //remove neighbor from CLOSED
                    suljettuLista.PoistaListasta(naapuri);
                }
                //if neighbor not in OPEN and neighbor not in CLOSED:
                if (!avoinLista.OnkoJonossa(naapuri) && !suljettuLista.OnkoAlkioListassa(naapuri)) {
//                    set g(neighbor) to cost
//      add neighbor to OPEN
//      set priority queue rank to g(neighbor) + h(neighbor)
//      set neighbor's parent to current
                    naapuri.setG_arvo(kokeiltava_G_arvo);
                    naapuri.setF_arvo(kokeiltava_G_arvo + laskeHeuristinenArvio(naapuri, liikkunutMaali));
                    naapuri.setEdeltaja(nykyinen);
                    avoinLista.LisaaListaan(naapuri);
                }
                // pieni delay, jotta helpompi seurata UI:sta
                if (piirretaanUI) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Polunetsija.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
        return null;
    }

    private Solmu LiikutaMaalia(Solmu vanhaMaali) {
        int max = this.verkko.Solmut.length - 1;
        Random rand = new Random();
        int x = 1 - rand.nextInt(3);
        int y = 1 - rand.nextInt(3);
        int uusiX = vanhaMaali.getX() + x;
        int uusiY = vanhaMaali.getY() + y; // + y;
        if (uusiX > max) {
            uusiX = max;
        }
        if (uusiY > max) {
            uusiY = max;
        }
        if (uusiX < 0) {
            uusiX = 0;
        }
        if (uusiY < 0) {
            uusiY = 0;
        }
        System.out.println("Random " + x + " -- " + y);
        Solmu uusiMaali = AsetaMaali(uusiX, uusiY, vanhaMaali);
        return uusiMaali;
    }

    /**
     * Apumetodi liikkuvan maalin toteutukseen. Palauttaa uuden maalin.
     *
     * @param uusiX
     * @param uusiY
     * @param vanhaMaali
     * @return
     */
    public Solmu AsetaMaali(int uusiX, int uusiY, Solmu vanhaMaali) {
        Solmu uusiMaali = verkko.Solmut[uusiX][uusiY];
        uusiMaali.setMaali(true);
        vanhaMaali.setMaali(false);
        verkko.AsetaMaalinSijainti(uusiX, uusiY);
        return uusiMaali;
    }

    /**
     * Laskee annettujen solmujen etäisyyteen perustuvan heuristisen arvion.
     *
     * @param alku
     * @param maali
     * @return
     */
    protected int laskeHeuristinenArvio(Solmu alku, Solmu maali) {
        int etaisyysX = Math.abs(maali.getX() - alku.getX());
        int etaisyysY = Math.abs(maali.getY() - alku.getY());
        if (etaisyysX < 0) {
            etaisyysX *= -1;
        }
        if (etaisyysY < 0) {
            etaisyysY *= -1;
        }
        int summa = etaisyysX + etaisyysY;
        System.out.println("F : etaisyysX: " + etaisyysX + " etaisyysY: " + etaisyysY + " -> " + summa);
        return summa;
    }

    private Lista<Solmu> tulostaPolku(Solmu maali) {
        Lista<Solmu> polku = new Lista<Solmu>();
        System.out.println("Maali: " + maali.getX() + "," + maali.getY());
        Solmu nykyinen = maali;
        while (nykyinen != null) {
            System.out.println(" -> " + nykyinen.toString());
            polku.Lisaa(nykyinen);
            Solmu edeltaja = nykyinen.getEdeltaja();
            nykyinen = edeltaja;
        }
        return polku;
    }

    private Lista<Solmu> annaSolmunNaapurit(Solmu solmu) {
        Lista<Solmu> naapurit = new Lista<Solmu>();
        int solmuX = solmu.getX();
        int solmuY = solmu.getY();
        int minX = 0;
        int minY = 0;

        int maxX = this.verkko.Solmut.length - 1;
        int maxY = maxX;
        if (minX < solmuX - 1) {
            minX = solmuX - 1;
        }
        if (minY < solmuY - 1) {
            minY = solmuY - 1;
        }
        if (maxX > solmuX + 1) {
            maxX = solmuX + 1;
        }
        if (maxY > solmuY + 1) {
            maxY = solmuY + 1;
        }
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                if (i == solmuX && j == solmuY) {
                    continue; // ei lisätä solmua naapuriensa joukkoon
                }
                Solmu naapurisolmu = this.verkko.Solmut[i][j];
                if (naapurisolmu.OnAlkupiste) {
                    continue;
                }
                if (!naapurisolmu.OnEste) {
                    naapurit.Lisaa(naapurisolmu);
                }

            }
        }
        return naapurit;
    }

    /**
     * Laskee solmujen välisen etäisyyden
     *
     * @param nykyinen
     * @param naapuri
     * @return
     */
    protected int annaEtaisyys(Solmu nykyinen, Solmu naapuri) {
        int etaisyysX = nykyinen.getX() - naapuri.getX();
        int etaisyysY = nykyinen.getY() - naapuri.getY();
        if (etaisyysX < 0) {
            etaisyysX *= -1;
        }
        if (etaisyysY < 0) {
            etaisyysY *= -1;
        }
        return etaisyysX + etaisyysY;
    }

}
