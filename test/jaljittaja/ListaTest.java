/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittaja;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author jouni
 */
public class ListaTest {
    
    public ListaTest() {
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
     * Test of Lisaa method, of class Lista.
     */
    @Test
    public void testLisaa() {
        System.out.println("Lisaa");
        Lista instance = new Lista();
        instance.Lisaa(1);
        instance.Lisaa(2);
        assertEquals(2, instance.AlkioidenMaara());
        instance.Lisaa(3);
        instance.Lisaa(4);
        
        assertEquals(4, instance.AlkioidenMaara());
        assertTrue(instance.OnkoAlkioListassa(1));
        assertTrue(instance.OnkoAlkioListassa(2));
        assertTrue(instance.OnkoAlkioListassa(3));
        assertTrue(instance.OnkoAlkioListassa(4));
        System.out.print(instance.toString());
    }

    /**
     * Test of AnnaAlkio method, of class Lista.
     */
    @Test
    public void testAnnaAlkio() {
        System.out.println("AnnaAlkio");
        int indeksi = 1;
        Lista instance = new Lista();
        instance.Lisaa(1);
        instance.Lisaa(2);
        Object result = instance.AnnaAlkio(indeksi);
        assertEquals(2, result);
        
        Solmu solmu = new Solmu(2, 3, false);
        instance.Lisaa(solmu);
        
        result = instance.AnnaAlkio(2);
        assertEquals(solmu, result);
    }

    /**
     * Test of PoistaListasta method, of class Lista.
     */
    @Test
    public void testPoistaListasta() {
        System.out.println("PoistaListasta");
        Lista instance = new Lista();
        instance.Lisaa(1);
        instance.Lisaa(2);
        instance.Lisaa(3);
        instance.Lisaa(4);
        
        assertEquals(4, instance.AlkioidenMaara());
        System.out.println(instance.toString());
        instance.PoistaListasta(2);
        assertEquals(3, instance.AlkioidenMaara());
        assertFalse("Ei poistunut", instance.OnkoAlkioListassa(2));
        
        System.out.println(instance.toString());
    }

    /**
     * Test of AlkioidenMaara method, of class Lista.
     */
    @Test
    public void testAlkioidenMaara() {
        System.out.println("AlkioidenMaara");
        Lista instance = new Lista();
        int result = instance.AlkioidenMaara();
        assertEquals(0, result);
        
        instance.Lisaa("eka");
        instance.Lisaa("toka");
        instance.Lisaa(1);
        
        int expResult = 3;
        result = instance.AlkioidenMaara();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of OnkoAlkioListassa method, of class Lista.
     */
    @Test
    public void testOnkoAlkioListassa() {
        System.out.println("OnkoAlkioListassa");
        Solmu solmu = new Solmu(3, 2, false);
        Solmu solmu2 = new Solmu(3, 2, false);
        Lista instance = new Lista();
        instance.Lisaa(solmu);
        boolean expResult = true;
        boolean result = instance.OnkoAlkioListassa(solmu);
        assertEquals(expResult, result);
        // negatiivinen assert - solmu2:a ei lisätty
        result = instance.OnkoAlkioListassa(solmu2);
        assertEquals(false, result);

    }

    /**
     * Test of toString method, of class Lista.
     */
    @Test @Ignore
    public void testToString() {
        System.out.println("toString");
        Lista instance = new Lista();
        
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
