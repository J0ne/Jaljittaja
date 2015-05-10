/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tietorakenteet;

import jaljittaja.tietorakenteet.Prioriteettijono;
import jaljittaja.verkko.Solmu;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jouni
 */
public class PrioriteettijonoTest {
    
    public PrioriteettijonoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of LisaaListaan method, of class Prioriteettijono.
     */
    @Test
    public void testLisaaListaan() {
        System.out.println("LisaaListaan");
        Solmu lisattava = new Solmu(0, 1, false);
        Prioriteettijono instance = new Prioriteettijono();
        instance.LisaaListaan(lisattava);
        assertEquals(1, instance.ListanKoko());
    }

    /**
     * Test of AnnaSolmu method, of class Prioriteettijono.
     */
    @Test
    public void testAnnaSolmu() {
        System.out.println("AnnaSolmu");
        // pienin prioriteetti
        Solmu solmuF10 = new Solmu(0, 1, false);
        solmuF10.setF_arvo(10);
        
        Solmu solmuF11 = new Solmu(1, 1, false);
        solmuF11.setF_arvo(11);
        
        Prioriteettijono prioriteettijono = new Prioriteettijono();
        prioriteettijono.LisaaListaan(solmuF11);
        prioriteettijono.LisaaListaan(solmuF10);
        Solmu result = prioriteettijono.AnnaSolmu();
        assertEquals(solmuF10, result);
    }

    /**
     * Test of PoistaSolmu method, of class Prioriteettijono.
     */
    @Test
    public void testPoistaSolmu() {
        System.out.println("PoistaSolmu");
        Solmu lisattava = new Solmu(0, 1, false);
        Prioriteettijono instance = new Prioriteettijono();
        instance.LisaaListaan(lisattava);
        assertEquals(1, instance.ListanKoko());
        assertTrue(instance.OnkoJonossa(lisattava));
        
        instance.PoistaSolmu(lisattava);
        
        assertEquals(0, instance.ListanKoko());
        assertFalse(instance.OnkoJonossa(lisattava));

    }

    /**
     * Test of ListanKoko method, of class Prioriteettijono.
     */
    @Test
    public void testListanKoko() {
        System.out.println("ListanKoko");
        Solmu solmuF10 = new Solmu(0, 1, false);
        solmuF10.setF_arvo(10);
        Solmu solmuF11 = new Solmu(1, 1, false);
        solmuF11.setF_arvo(11);
        
        Prioriteettijono prioriteettijono = new Prioriteettijono();
        int expResult = 0;
        int result = prioriteettijono.ListanKoko();
        assertEquals(expResult, result);
        
        prioriteettijono.LisaaListaan(solmuF11);
        result = prioriteettijono.ListanKoko();
        assertEquals(1, result);
        prioriteettijono.LisaaListaan(solmuF10);
        result = prioriteettijono.ListanKoko();
        assertEquals(2, result);
        
        Solmu solmu = prioriteettijono.AnnaSolmu();
        result = prioriteettijono.ListanKoko();
        assertEquals(1, result);
    }

    /**
     * Test of OnkoJonossa method, of class Prioriteettijono.
     */
    @Test
    public void testOnkoJonossa() {
        System.out.println("AnnaSolmunIndeksi");
        
        Solmu lisattava = new Solmu(0, 1, false);
        Prioriteettijono instance = new Prioriteettijono();
        instance.LisaaListaan(lisattava);
        assertTrue(instance.OnkoJonossa(lisattava));
        
        instance.PoistaSolmu(lisattava);
        
        assertEquals(0, instance.ListanKoko());
        assertFalse(instance.OnkoJonossa(lisattava));
    }
    
}
