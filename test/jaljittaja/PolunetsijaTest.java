/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittaja;

import java.util.ArrayList;
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
        Polunetsija instance = new Polunetsija(verkko);
        
        ArrayList<Solmu> expResult = new ArrayList<>();
        expResult.add(verkko.Solmut[4][4]);
        expResult.add(verkko.Solmut[3][3]);
        expResult.add(verkko.Solmut[2][2]);
        expResult.add(verkko.Solmut[1][1]);
        expResult.add(alkupiste);
        
        ArrayList<Solmu> result = instance.EtsiLyhinPolku(alkupiste, maali, false);
        assertNotNull(result);
        assertEquals(expResult.size(), result.size());
        VertaileSolmut(expResult, result);
    }
    
    private void VertaileSolmut(ArrayList<Solmu> listaA, ArrayList<Solmu> listaB){
        for(int i = 0; i < listaA.size(); i++){
            assertEquals(listaA.get(i), listaB.get(i));
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
}
