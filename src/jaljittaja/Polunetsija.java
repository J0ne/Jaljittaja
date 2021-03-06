/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittaja;

import jaljittaja.tietorakenteet.Prioriteettijono;
import jaljittaja.tietorakenteet.Lista;
import jaljittaja.verkko.Verkko;
import jaljittaja.verkko.Solmu;
import Tilastointi.SuorituksenInfo;
import jaljittajaUI.Verkkonakyma;
import java.awt.Graphics;
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
     * Konstruktori joka saa parametrinä verkon
     *
     * @param verkko
     */
    public Polunetsija(Verkko verkko) {
        this.verkko = verkko;
        if (nakyma == null) {
            nakyma = new Verkkonakyma(verkko);
        }else{
            nakyma.setVerkko(verkko);
        }
        piirretaanUI = true;
        g = nakyma.getGraphics();
    }

    // Konstruktori jolla polunetsintä voidaan suorittaa ilman UI-simulointia
    /**
     * Konstruktori jolla ohjelma voidaan käynnistää ilman
     * käyttöliittymäsimulointia
     *
     * @param verkko
     * @param ilmanUI
     */
    public Polunetsija(Verkko verkko, boolean ilmanUI) {
        this.verkko = verkko;
        this.piirretaanUI = false;
        this.suoritusinfo = new SuorituksenInfo();
        this.suoritusinfo.setVerkonKoko(verkko.Solmut.length ^ 2);
    }

    int viive;
    
    public int getViive() {
        return viive;
    }

    public void setViive(int viive) {
        this.viive = viive;
    }
    SuorituksenInfo suoritusinfo;

    /**
     * Palauttaa Tietorivi-olion, eli tiedot algoritmin suorituksesta
     *
     * @return
     */
    public SuorituksenInfo getTietorivi() {
        return suoritusinfo;
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
    int kasitellytSolmut = 0;
    int maalinStepit = 0;

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
            
            // otetaan listasta solmu, jolla on pienin F-arvo
            Solmu kasittelyssaOleva = avoinLista.AnnaSolmu();
            suljettuLista.Lisaa(kasittelyssaOleva);

            // piirretään verkkoa 
            if (piirretaanUI) {
                Lista<Solmu> nykyinenPiirrettava = new Lista<Solmu>();
                nykyinenPiirrettava.Lisaa(kasittelyssaOleva);
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
                tmp = liikkunutMaali;
            } else {
                liikkunutMaali = tmp;
            }

            // jos "kasittelyssaOleva" == maali, polku on löytynyt
            if (kasittelyssaOleva.isMaali()) {
                kuljettuReitti = tulostaPolku(kasittelyssaOleva);
                if (piirretaanUI) {
                    nakyma.PiirraSolmut(kuljettuReitti, g, 1);
                } else {
                    suoritusinfo.setLapikaydytSolmut(kasitellytSolmut);
                    suoritusinfo.setMaalinStepit(maalinStepit);
                }

                return kuljettuReitti;
            }
            Lista<Solmu> naapuriSolmut = annaSolmunNaapurit(kasittelyssaOleva);
            if (piirretaanUI) {
                nakyma.PiirraSolmut(naapuriSolmut, g, 2);
            }
            // käydään kaikki kasittelyssa olevan solmun naapurit
            for (Solmu naapuri : naapuriSolmut) {
                // jos jo kasitelty -> jatketaan
                if (suljettuLista.OnkoAlkioListassa(naapuri)) {
                    continue;
                }

                int kokeiltava_G_arvo = kasittelyssaOleva.getG_arvo() + annaEtaisyys(kasittelyssaOleva, naapuri);
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

                    naapuri.setG_arvo(kokeiltava_G_arvo);
                    naapuri.setF_arvo(kokeiltava_G_arvo + laskeHeuristinenArvio(naapuri, liikkunutMaali));
                    naapuri.setEdeltaja(kasittelyssaOleva);
                    avoinLista.LisaaListaan(naapuri);
                }
                // pieni delay, jotta helpompi seurata UI:sta
                if (piirretaanUI) {
                    try {
                        Thread.sleep(getViive());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Polunetsija.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
        if (!piirretaanUI) {
            suoritusinfo.setLapikaydytSolmut(kasitellytSolmut);
            suoritusinfo.setMaalinStepit(maalinStepit);
        }

        return null;
    }
    int liikeLaskuri;
    private Solmu LiikutaMaalia(Solmu vanhaMaali) {
        liikeLaskuri++;
        // hidastus
//                if(liikeLaskuri % 3 != 0){
//                return vanhaMaali;
//                }
                
        int max = this.verkko.Solmut.length - 1;
        Random rand = new Random();
        int x = 1 - rand.nextInt(3);
        int y = 1 - rand.nextInt(3);
        int uusiX = vanhaMaali.getX() + x;
        int uusiY = vanhaMaali.getY() + y;
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

        boolean onEste = this.verkko.Solmut[uusiX][uusiY].isOnEste() == true;
        Solmu uusiMaali;
        if (!onEste) {
            uusiMaali = AsetaMaali(uusiX, uusiY, vanhaMaali);
        } else {
            uusiMaali = vanhaMaali;
        }
        maalinStepit++;

        liikeLaskuri++;
         
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
        int summa = etaisyysX + etaisyysY;
        return summa;
    }

    private Lista<Solmu> tulostaPolku(Solmu maali) {
        Lista<Solmu> polku = new Lista<>();
        Solmu nykyinen = maali;
        while (nykyinen != null) {
            polku.Lisaa(nykyinen);
            Solmu edeltaja = nykyinen.getEdeltaja();
            nykyinen = edeltaja;
        }
        return polku;
    }

    private Lista<Solmu> annaSolmunNaapurit(Solmu solmu) {
        Lista<Solmu> naapurit = new Lista<>();
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
