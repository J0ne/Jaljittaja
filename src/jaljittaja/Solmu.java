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
     *
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
     *
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
     *
     */
    public int x;

    /**
     *
     */
    public int y;

    /**
     *
     * @return
     */
    public int getKustannus() {
        return Kustannus;
    }

    /**
     *
     * @param Kustannus
     */
    public void setKustannus(int Kustannus) {
        this.Kustannus = Kustannus;
    }

    /**
     *
     * @return
     */
    public boolean isOnEste() {
        return OnEste;
    }

    /**
     *
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
    public int Kustannus;
    
    /**
     *
     */
    public boolean OnEste;
    
    /**
     *
     */
    public Solmu Edeltaja;

    /**
     *
     * @return
     */
    public boolean isMaali() {
        return Maali;
    }

    /**
     *
     * @param Maali
     */
    public void setMaali(boolean Maali) {
        if(Maali && OnEste){this.OnEste = false;}
        this.Maali = Maali;
    }
    
    /**
     *
     */
    public boolean Maali;

    /**
     *
     * @return
     */
    public boolean isOnAlkupiste() {
        return OnAlkupiste;
    }

    /**
     *
     * @param OnAlkupiste
     */
    public void setOnAlkupiste(boolean OnAlkupiste) {
        if(OnAlkupiste){
            OnEste = false;
        }
        this.OnAlkupiste = OnAlkupiste;
    }
    
    /**
     *
     */
    public boolean OnAlkupiste;

    /**
     *
     * @return
     */
    public Solmu getEdeltaja() {
        return Edeltaja;
    }

    /**
     *
     * @param Edeltaja
     */
    public void setEdeltaja(Solmu Edeltaja) {
        this.Edeltaja = Edeltaja;
    }

    /**
     *
     * @return
     */
    public int getG_arvo() {
        return G_arvo;
    }

    /**
     *
     * @param G_arvo
     */
    public void setG_arvo(int G_arvo) {
        this.G_arvo = G_arvo;
    }

    /**
     *
     * @return
     */
    public int getF_arvo() {
        return F_arvo;
    }

    /**
     *
     * @param F_arvo
     */
    public void setF_arvo(int F_arvo) {
        System.out.println("Asetetaan F: " + F_arvo);
        this.F_arvo = F_arvo;
    }
    
    /**
     *
     */
    public int G_arvo;

    /**
     *
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
