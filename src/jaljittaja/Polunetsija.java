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
/*
    OPEN = priority queue containing START
CLOSED = empty set
while lowest rank in OPEN is not the GOAL:
  current = remove lowest rank item from OPEN
  add current to CLOSED
  for neighbors of current:
    cost = g(current) + movementcost(current, neighbor)
    if neighbor in OPEN and cost less than g(neighbor):
      remove neighbor from OPEN, because new path is better
    if neighbor in CLOSED and cost less than g(neighbor): **
      remove neighbor from CLOSED
    if neighbor not in OPEN and neighbor not in CLOSED:
      set g(neighbor) to cost
      add neighbor to OPEN
      set priority queue rank to g(neighbor) + h(neighbor)
      set neighbor's parent to current

reconstruct reverse path from goal to start
by following parent pointers
    
    */
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
             suljettuLista.add(nykyinen);
            if (nykyinen.isMaali()) {
                System.out.println("Tulostetaan polku:");
                kuljettuReitti = tulostaPolku(nykyinen);
                nakyma.PiirraSolmut(kuljettuReitti, g, 1);
                return kuljettuReitti;
            }
            ArrayList<Solmu> naapuriSolmut = annaSolmunNaapurit(nykyinen);
            nakyma.PiirraSolmut(naapuriSolmut, g, 2);
            for (Solmu naapuri : naapuriSolmut) {
                if (suljettuLista.contains(naapuri)) {
                    continue;
                }

                int kokeiltava_G_arvo = nykyinen.getG_arvo()+ annaEtaisyys(nykyinen, naapuri);
                System.out.println(" Alustava G: " + kokeiltava_G_arvo);
                //if neighbor in OPEN and cost less than g(neighbor):
                if (avoinLista.AnnaSolmunIndeksi(naapuri) != -1 && kokeiltava_G_arvo < naapuri.getG_arvo()) {
                    //remove neighbor from OPEN, because new path is better
                    avoinLista.PoistaSolmu(naapuri);
                }
                //if neighbor in CLOSED and cost less than g(neighbor): **
                if (suljettuLista.contains(naapuri) && kokeiltava_G_arvo < naapuri.getG_arvo()) {
                    //remove neighbor from CLOSED
                    suljettuLista.remove(naapuri);
                }
                //if neighbor not in OPEN and neighbor not in CLOSED:
                if(avoinLista.AnnaSolmunIndeksi(naapuri) == -1 && !suljettuLista.contains(naapuri)){
//                    set g(neighbor) to cost
//      add neighbor to OPEN
//      set priority queue rank to g(neighbor) + h(neighbor)
//      set neighbor's parent to current
                    naapuri.setG_arvo(kokeiltava_G_arvo);
                    naapuri.setF_arvo(kokeiltava_G_arvo + laskeHeuristinenArvio(naapuri, maali));
                    naapuri.setEdeltaja(nykyinen);
                    avoinLista.LisaaListaan(naapuri);
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
        int summa = etaisyysX + etaisyysY;
        System.out.println("F : etaisyysX: "+etaisyysX + " etaisyysY: "+etaisyysY + " -> " + summa);
        return summa;
    }

    private ArrayList<Solmu> tulostaPolku(Solmu maali) {
        ArrayList<Solmu> polku = new ArrayList<>();
        System.out.println("Maali: " + maali.getX() + "," + maali.getY());
        Solmu nykyinen = maali;
        boolean alussa = false;
        while(!nykyinen.OnAlkupiste)
        {
            System.out.println(" -> " +nykyinen.toString());
            polku.add(nykyinen);
            Solmu edeltaja = nykyinen.getEdeltaja();
            nykyinen = edeltaja;
        }
        
        
//        for (Solmu lista1 : lista) {
//            System.out.println("-->" + lista1.getX() + "," + lista1.getY() + " F:" + lista1.getF_arvo() + " G:" + lista1.getG_arvo());
//        }
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
            System.out.println("Lisätty: x:" + lisattava.getX() + ", j:" + lisattava.getY() + " G: " + 
                    lisattava.getG_arvo());
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
            lista.remove(palautettavanIndeksi);
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
