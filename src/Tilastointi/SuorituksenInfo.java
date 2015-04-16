/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tilastointi;

/**
 * Yhden polunetsintä suorituksen info. Tämä vastaa yhtä riviä tulostettavassa  csv-tiedostossa
 * tai UI:ssa
 * @author jouni
 */
public class SuorituksenInfo {
    int verkonKoko;
    boolean polkuLoytyi;
    double suorituksenKesto;

    /**
     * Suorituksen kesto tiedetään vasta suorituksen päätyttyä, joten sen asettamiseen oma metodinsa
     * @param suorituksenKesto
     */
    public void setSuorituksenKesto(double suorituksenKesto) {
        this.suorituksenKesto = suorituksenKesto;
    }
    int lapikaydytSolmut;
    int maalinStepit;
    // todo: myöhemmin täydentyy tarvittavat tilastoitavat tiedot

    @Override
    public String toString() {
        return "SuorituksenInfo{" + "verkonKoko=" + verkonKoko + ", polkuLoytyi=" + polkuLoytyi + ", suorituksenKesto=" + suorituksenKesto + ", lapikaydytSolmut=" + lapikaydytSolmut + ", maalinStepit=" + maalinStepit + '}';
    }
    
}
