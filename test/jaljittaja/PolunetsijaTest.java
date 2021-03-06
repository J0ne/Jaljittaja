/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittaja;

import jaljittaja.tietorakenteet.Lista;
import jaljittaja.verkko.Verkko;
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
public class PolunetsijaTest {
    
    public PolunetsijaTest() {
    }
    Solmu alkupiste;
    Solmu maali;
    Polunetsija polunEtsija;
    Verkko verkko;
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        alkupiste = new Solmu(0, 0, false,true);
        maali = new Solmu(4, 4, false);
        maali.setMaali(true);
        verkko = new Verkko(5, alkupiste, maali);
        polunEtsija = new Polunetsija(verkko, false);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of EtsiLyhinPolku method, of class Polunetsija.
     */
    @Test
    public void testEtsiLyhinPolku() {
        System.out.println("EtsiLyhinPolku");
        Verkko verkko = new Verkko(5, alkupiste, maali);
        Polunetsija instance = new Polunetsija(verkko, false);
        
        Lista<Solmu> expResult = new Lista<Solmu>();
        expResult.Lisaa(verkko.Solmut[4][4]);
        expResult.Lisaa(verkko.Solmut[3][3]);
        expResult.Lisaa(verkko.Solmut[2][2]);
        expResult.Lisaa(verkko.Solmut[1][1]);
        expResult.Lisaa(alkupiste);
        
        Lista<Solmu> result = instance.EtsiLyhinPolku(alkupiste, maali, false);
        assertNotNull(result);
        assertEquals(expResult.AlkioidenMaara(), result.AlkioidenMaara());
        VertaileSolmut(expResult, result);
    }
    
    private void VertaileSolmut(Lista<Solmu> listaA, Lista<Solmu> listaB){
        for(int i = 0; i < listaA.AlkioidenMaara(); i++){
            assertEquals(listaA.AnnaAlkio(i), listaB.AnnaAlkio(i));
        }
    }
    /* 
 5 x 5 
 O--O--O--O--O--O
 | A|  |  |  |  | 
 O--O--O--O--O--O
 |  |  |  |  |  |
 O--O--O--O--O--O
 |  |  |  |  |  |
 O--O--0--O--O--O
 |  |  |  |  | B|
 O--O--O--O--O--O

 */

    /**
     * Test of laskeHeuristinenArvio method, of class Polunetsija.
     */
    @Test
    public void testLaskeHeuristinenArvio() {
        System.out.println("laskeHeuristinenArvio");
        Solmu alku = new Solmu(0, 0, false);
        
        Solmu maali = new Solmu(0,1, false);
        int expResult = 1;
        int result = polunEtsija.laskeHeuristinenArvio(alku, maali);
        assertEquals(expResult, result);
        
        // uusi maali
        maali = new Solmu(1,1, false);
        expResult = 2;
        result = polunEtsija.laskeHeuristinenArvio(alku, maali);
        assertEquals(expResult, result);
        
         // uusi maali
        maali = new Solmu(0,4, false);
        expResult = 4;
        result = polunEtsija.laskeHeuristinenArvio(alku, maali);
        assertEquals(expResult, result);
                maali = new Solmu(0,4, false);
                         // uusi maali
        maali = new Solmu(3,4, false);
        expResult = 7;
        result = polunEtsija.laskeHeuristinenArvio(alku, maali);
        assertEquals(expResult, result);
    }

    /**
     * Test of AsetaMaali method, of class Polunetsija.
     */
    @Test
    public void testAsetaMaali() {
        System.out.println("AsetaMaali");
        int uusiX = 1;
        int uusiY = 2;
        // testi-luokan alustuksessa anettu maali
        Solmu vanhaMaali = maali;
        
        Polunetsija instance = new Polunetsija(verkko, false);
        
        Solmu expResult = verkko.Solmut[uusiX][uusiY];
        Solmu result = instance.AsetaMaali(uusiX, uusiY, vanhaMaali);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of annaEtaisyys method, of class Polunetsija.
     */
    @Test
    public void testAnnaEtaisyys() {
        System.out.println("annaEtaisyys");
        Solmu nykyinen = new Solmu(4, 0, false);
        Solmu naapuri = new Solmu(0, 0, false);
        Polunetsija instance = new Polunetsija(verkko);
        int expResult = 4;
        int result = instance.annaEtaisyys(nykyinen, naapuri);
        assertEquals(expResult, result);

    }
}
