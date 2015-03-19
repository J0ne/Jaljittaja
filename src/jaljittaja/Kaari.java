/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jaljittaja;

/**
 * Kahden solmun väli. Verkko on suuntaamaton, joten molemmat solmut ovat saman arvoisia
 * @author jouni
 */
public class Kaari {

    /**
     *
     * @return
     */
    public Solmu getSolmuA() {
        return SolmuA;
    }

    /**
     *
     * @param SolmuA
     */
    public void setSolmuA(Solmu SolmuA) {
        this.SolmuA = SolmuA;
    }

    /**
     *
     * @return
     */
    public Solmu getSolmuB() {
        return SolmuB;
    }

    /**
     *
     * @param SolmuB
     */
    public void setSolmuB(Solmu SolmuB) {
        this.SolmuB = SolmuB;
    }

    /**
     *
     * @return
     */
    public int getPaino() {
        return Paino;
    }

    /**
     *
     * @param Paino
     */
    public void setPaino(int Paino) {
        this.Paino = Paino;
    }

    /**
     * Toinen solmuista.
     */
    public Solmu SolmuA;

    /**
     * Toinen solmuista.
     */
    public Solmu SolmuB;
    /**
     Kaaren paino. Kustannus
     */
    public int Paino;

    /**
     *
     * @param SolmuA
     * @param SolmuB
     * @param Paino
     */
    public Kaari(Solmu SolmuA, Solmu SolmuB, int Paino) {
        this.SolmuA = SolmuA;
        this.SolmuB = SolmuB;
        this.Paino = Paino;
    }
   
}
