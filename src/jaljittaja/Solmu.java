/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jaljittaja;

/**
 * Verkon solmu
 * @author jouni
 */
public class Solmu {

    /**
     * Konstruktori
     * @param x
     * @param y
     * @param OnEste
     */
    public Solmu(int x, int y, boolean OnEste) {
        this.x = x;
        this.y = y;
        this.OnEste = OnEste;
    }

    /**
     * Konstruktori
     * @param x
     * @param y
     * @param OnEste
     * @param onAlkupiste
     */
    public Solmu(int x, int y, boolean OnEste, boolean onAlkupiste) {
        this.x = x;
        this.y = y;
        this.OnEste = OnEste;
        this.OnAlkupiste = onAlkupiste;
    }
    
    /**
     * 
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * X-koordinaatti
     */
    public int x;

    /**
     * Y-koordinaatti
     */
    public int y;


    /**
     * Metodi jolla tutkitaan, onko kyseinen solmun instanssi este
     * @return
     */
    public boolean isOnEste() {
        return OnEste;
    }

    /**
     * Metodi, jolla solmu voidaan asettaa esteen rooliin.
     * @param val
     */
    public void setOnEste(boolean val) {
        if(this.OnAlkupiste){
            val = false;
        }
        this.OnEste = val;
    }
    
    /**
     * 
     */
    public boolean OnEste;
    
    /**
     * Solmua edeltävä solmu löydetyssä polussa
     */
    public Solmu Edeltaja;

    /**
     * Palauttaa tiedon siitä, onko kyseessä maali, eli piste B.
     * Polunetsinnän kohde
     * @return
     */
    public boolean isMaali() {
        return Maali;
    }

    /**
     * Solmun asetus maaliksi.
     * @param Maali
     */
    public void setMaali(boolean Maali) {
        if(Maali && OnEste){this.OnEste = false;}
        this.Maali = Maali;
    }
    
    /**
     * Solmun erikoistapaus: maali, eli piste B.
     */
    public boolean Maali;

    /**
     * Polunetsinnän lähtöpiste, piste A
     * @return
     */
    public boolean isOnAlkupiste() {
        return OnAlkupiste;
    }

    /**
     * Alkupisteen asetus. Alkupisteellä ei voi olla edeltäjää.
     * @param OnAlkupiste
     */
    public void setOnAlkupiste(boolean OnAlkupiste) {
        if(OnAlkupiste){
            OnEste = false;
        }
        this.OnAlkupiste = OnAlkupiste;
    }
    
    /**
     * Palauttaa tiedon, onko Solmu alkupiste, eli piste A
     */
    public boolean OnAlkupiste;

    /**
     * Palauttaa solmun edeltäjäsolmun
     * @return
     */
    public Solmu getEdeltaja() {
        return Edeltaja;
    }

    /**
     * Asettaa solmun edeltäjän
     * @param Edeltaja
     */
    public void setEdeltaja(Solmu Edeltaja) {
        this.Edeltaja = Edeltaja;
    }

    /**
     * Palauttaa solmulle lasketun G-arvon.
     * @return
     */
    public int getG_arvo() {
        return G_arvo;
    }

    /**
     * Asettaa solmun G-arvon
     * @param G_arvo
     */
    public void setG_arvo(int G_arvo) {
        this.G_arvo = G_arvo;
    }

    /**
     * Palauttaa solmun F-arvon.
     * @return
     */
    public int getF_arvo() {
        return F_arvo;
    }

    /**
     * Asettaa F-arvon
     * @param F_arvo
     */
    public void setF_arvo(int F_arvo) {
        System.out.println("Asetetaan F: " + F_arvo);
        this.F_arvo = F_arvo;
    }
    
    /**
     * G-arvo
     */
    public int G_arvo;

    /**
     * F-arvo
     */
    public int F_arvo;

    @Override
    public String toString() {
        if(this.isMaali()){
            return "MAALI!";
        }
        if(this.isOnAlkupiste()){
            return "ALKUPISTE";
        }
        return "Solmu{" + "x=" + this.getX() + ", y=" + getY() + " G:" + this.getG_arvo() +
                " F:" + this.getF_arvo() + '}';
    }
    
    
}
