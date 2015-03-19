/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittaja;

import jaljittajaUI.Verkkonakyma;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jouni
 */
public class Polunetsija { 

    public Polunetsija(Verkko verkko) {
        this.verkko = verkko;
    }

    public Verkko verkko;
    public Verkkonakyma nakyma;
    Graphics g;

    public ArrayList<Solmu> EtsiLyhinPolku(Solmu alkupiste, Solmu maali) {
        nakyma = new Verkkonakyma(verkko);
        g = nakyma.getGraphics();

        Prioriteettijono avoinLista = new Prioriteettijono(); // open set 
        ArrayList<Solmu> suljettuLista = new ArrayList<>(); // closed set

        ArrayList<Solmu> kuljettuReitti = new ArrayList<>();

        int G_arvo = 0;
        int F_arvo = G_arvo + laskeHeuristinenArvio(alkupiste, maali);
        alkupiste.setKustannus(F_arvo);
        alkupiste.setF_arvo(F_arvo);
        alkupiste.setEdeltaja(null);
        avoinLista.LisaaListaan(alkupiste);
        while (avoinLista.ListanKoko() != 0) {
            Solmu nykyinen = avoinLista.AnnaSolmu();

            if (nykyinen.getX() == maali.getX() && nykyinen.getY() == maali.getY()) {
                System.out.println("Tulostetaan polku:");
                return tulostaPolku(kuljettuReitti, nykyinen);
                
                
                
            }
            avoinLista.PoistaSolmu(nykyinen);
            suljettuLista.add(nykyinen);

            ArrayList<Solmu> naapuriSolmut = annaSolmunNaapurit(nykyinen);
            nakyma.PiirraSolmut(naapuriSolmut, g, 2);
            for (Solmu naapuri : naapuriSolmut) {
                if (suljettuLista.contains(naapuri)) {
                    continue;
                }

                int alustava_G_arvo = nykyinen.getG_arvo()+ annaEtaisyys(nykyinen, naapuri);
                /*
                 if neighbor not in openset or tentative_g_score < g_score[neighbor] 
                 came_from[neighbor] := current
                 g_score[neighbor] := tentative_g_score
                 f_score[neighbor] := g_score[neighbor] + heuristic_cost_estimate(neighbor, goal)
                 if neighbor not in openset
                 add neighbor to openset
                 */
                if (avoinLista.AnnaSolmunIndeksi(naapuri) == -1 || alustava_G_arvo < naapuri.getG_arvo()) {
                    kuljettuReitti.remove(naapuri);
                    kuljettuReitti.add(nykyinen);
                    //naapuri.setEdeltaja(nykyinen);
                    naapuri.setG_arvo(alustava_G_arvo);
                    naapuri.setF_arvo(alustava_G_arvo + laskeHeuristinenArvio(naapuri, maali));
                    if (avoinLista.AnnaSolmunIndeksi(naapuri) == -1) {
                        naapuri.setEdeltaja(nykyinen);
                        avoinLista.LisaaListaan(naapuri);
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Polunetsija.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            
        }
        return null;
    }

    private int laskeHeuristinenArvio(Solmu alku, Solmu maali) {
        int etaisyysX = Math.abs(maali.getX() - alku.getX());
        int etaisyysY = Math.abs(maali.getY() - alku.getY());
                if (etaisyysX < 0) {
            etaisyysX *= -1;
        }
        if (etaisyysY < 0) {
            etaisyysY *= -1;
        }
        return etaisyysX + etaisyysY;
    }

    private ArrayList<Solmu> tulostaPolku(ArrayList<Solmu> lista, Solmu maali) {
        ArrayList<Solmu> polku = new ArrayList<>();
        System.out.println("Maali: " + maali.getX() + "," + maali.getY());
        for (Solmu lista1 : lista) {
            System.out.println("-->" + lista1.getX() + "," + lista1.getY());
        }
//        polku.add(nykyinen);
//        while (nykyinen != null) {
//            Solmu solmu = nykyinen.getEdeltaja();
//            if (solmu != null) {
//                polku.add(solmu);
//                System.out.println("-->" + solmu.getX() + "," + solmu.getY());
//            }
//            nykyinen = solmu;
//        }
        
        return polku;
    }

    private ArrayList<Solmu> annaSolmunNaapurit(Solmu solmu) {
        ArrayList<Solmu> naapurit = new ArrayList<>();
        int solmuX = solmu.getX();
        int solmuY = solmu.getY();
        int minX = 0;
        int minY = 0;

        int maxX = this.verkko.Solmut.length-1;
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
                if(naapurisolmu.OnAlkupiste){continue;}
                if (!naapurisolmu.OnEste) {
                    naapurisolmu.setEdeltaja(solmu);
                    naapurit.add(naapurisolmu);
                }

            }
        }
        return naapurit;
    }

    private int annaEtaisyys(Solmu nykyinen, Solmu naapuri) {
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

    class Prioriteettijono {
        
        public ArrayList<Solmu> getLista() {
            if (lista != null) {
                return lista;
            } else {
                lista = new ArrayList<>();
                return lista;
            }
        }

        int pieninKustannus = 0;

        public void LisaaListaan(Solmu lisattava) {
            lista = this.getLista();
            lista.add(lisattava);
        }

        public Solmu AnnaSolmu() {
            int pieninKustannus = 1000;
            int palautettavanIndeksi = -1;
            for (int i = 0; i < lista.size(); i++) {
                Solmu kandidaatti = lista.get(i);
                if (kandidaatti.getF_arvo()< pieninKustannus) {
                    pieninKustannus = kandidaatti.getF_arvo();
                    palautettavanIndeksi = i;
                }

            }
            Solmu palautettava = lista.get(palautettavanIndeksi);
            return palautettava;
        }
        ArrayList<Solmu> lista;

        public void PoistaSolmu(Solmu solmu) {
            lista.remove(solmu);
        }

        public int ListanKoko() {
            return lista.size();
        }

        public int AnnaSolmunIndeksi(Solmu solmu) {
            return lista.indexOf(solmu);
        }
    }

}
