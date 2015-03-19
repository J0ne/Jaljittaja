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

    public Solmu(int x, int y, boolean OnEste) {
        this.x = x;
        this.y = y;
        this.OnEste = OnEste;
    }
     public Solmu(int x, int y, boolean OnEste, boolean onAlkupiste) {
        this.x = x;
        this.y = y;
        this.OnEste = OnEste;
        this.OnAlkupiste = onAlkupiste;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public int x;
    public int y;

    public int getKustannus() {
        return Kustannus;
    }

    public void setKustannus(int Kustannus) {
        this.Kustannus = Kustannus;
    }

    public boolean isOnEste() {
        return OnEste;
    }

    public void setOnEste(boolean OnEste) {
        this.OnEste = OnEste;
    }
    
    
    public int Kustannus;
    
    public boolean OnEste;
    
    public Solmu Edeltaja;

    public boolean isOnAlkupiste() {
        return OnAlkupiste;
    }
    
    public boolean OnAlkupiste;

    public Solmu getEdeltaja() {
        return Edeltaja;
    }

    public void setEdeltaja(Solmu Edeltaja) {
        this.Edeltaja = Edeltaja;
    }

    public int getG_arvo() {
        return G_arvo;
    }

    public void setG_arvo(int G_arvo) {
        this.G_arvo = G_arvo;
    }

    public int getF_arvo() {
        return F_arvo;
    }

    public void setF_arvo(int F_arvo) {
        this.F_arvo = F_arvo;
    }
    
    public int G_arvo;
    public int F_arvo;
}
