/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittaja.verkko;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jouni
 */
public class VerkkoTest {
    
    public VerkkoTest() {
    }

    /**
     * Test of AsetaMaalinSijainti method, of class Verkko.
     */
    @Test
    public void testAsetaMaalinSijainti() {
        System.out.println("AsetaMaalinSijainti");
        int x = 0;
        int y = 0;
        Solmu alkupiste = new Solmu(0, 0, false);
        Verkko instance = new Verkko(4, alkupiste, alkupiste);
        instance.AsetaMaalinSijainti(1, 1);
        // TODO review the generated test code and remove the default call to fail.
        Solmu maali = instance.maali;
        assertTrue(maali.isMaali());
    }

    /**
     * Test of AsetaEste method, of class Verkko.
     */
    @Test
    public void testAsetaEste() {
        System.out.println("AsetaEste");
        int x = 2;
        int y = 2;
        Solmu alkupiste = new Solmu(0, 0, false);
        Verkko instance = new Verkko(4, alkupiste, alkupiste);
        instance.AsetaEste(x, y);
        // TODO review the generated test code and remove the default call to fail.
        Solmu este = instance.Solmut[x][y];
        assertTrue(este.isOnEste());
    }

    /**
     * Test of AsetaAlkupiste method, of class Verkko.
     */
    @Test
    public void testAsetaAlkupiste() {
        System.out.println("AsetaAlkupiste");
        int x = 2;
        int y = 2;
        Solmu alkupiste = new Solmu(0, 0, false);
        Verkko instance = new Verkko(4, alkupiste, alkupiste);
        instance.AsetaAlkupiste(x, y);
        // TODO review the generated test code and remove the default call to fail.
        Solmu uusiAlkupiste = instance.Solmut[x][y];
        assertTrue(uusiAlkupiste.OnAlkupiste);
        // testataan, ettei vanha ole este
        assertFalse(alkupiste.isOnAlkupiste());
    }
    
}
