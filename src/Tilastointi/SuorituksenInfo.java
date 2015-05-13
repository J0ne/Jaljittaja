/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tilastointi;

/**
 * Yhden polunetsintä suorituksen info. Tämä vastaa yhtä riviä tulostettavassa
 * csv-tiedostossa tai UI:ssa
 *
 * @author jouni
 */
public class SuorituksenInfo {

    public int getVerkonKoko() {
        return verkonKoko;
    }

    public void setVerkonKoko(int verkonKoko) {

        this.verkonKoko = verkonKoko * verkonKoko;
    }

    public int getPolkuLoytyi() {
        return polkuLoytyi;
    }

    public void setPolkuLoytyi(int polkuLoytyi) {
        this.polkuLoytyi = polkuLoytyi;
    }

    public int getLapikaydytSolmut() {
        return lapikaydytSolmut;
    }

    public void setLapikaydytSolmut(int lapikaydytSolmut) {
        this.lapikaydytSolmut = lapikaydytSolmut;
    }

    public int getMaalinStepit() {
        return maalinStepit;
    }

    public void setMaalinStepit(int maalinStepit) {
        this.maalinStepit = maalinStepit;
    }
    int verkonKoko;
    int polkuLoytyi;
    double suorituksenKesto;

    public double getSuorituksenKesto() {
        return suorituksenKesto;
    }

    /**
     * Suorituksen kesto tiedetään vasta suorituksen päätyttyä, joten sen
     * asettamiseen oma metodinsa
     *
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
        return "SuorituksenInfo{" + "verkonKoko=" + getVerkonKoko() + ", polkuLoytyi=" + polkuLoytyi + ", suorituksenKesto=" + suorituksenKesto + ", lapikaydytSolmut=" + lapikaydytSolmut + ", maalinStepit=" + maalinStepit + '}';
    }

}
