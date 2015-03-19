
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaljittaja;

/**
 * Suuntaamaton verkko (n x n matriisi), joka muodostuu solmuista ja kaarista
 *
 * @author jouni
 */
public class Verkko {

    public Verkko(int sivu) {
        RakennaVerkko(sivu);
    }

    public Solmu[][] Solmut;

    private Verkko RakennaVerkko(int n) {

        int rivit = n;
        int sarakkeet = n;
        Solmut = new Solmu[rivit][sarakkeet];
        for (int i = 0; i < rivit; i++) {
            for (int j = 0; j < sarakkeet; j++) {

                Solmut[i][j] = new Solmu(i, j, false);
                
                //luodaan este
                if (j > 0 && j % 5 == 0 && (i > 0 && i < n-1) || (j > 0 && j < 5 && i== 8)) {
                    Solmut[i][j].OnEste = true;
                }
            }
        }
        return this;
    }

}
/* 
 4 x 4 
 A--O--O--O
 |  |  |  |
 O--O--O--O
 |  |  |  |
 O--O--M--O
 |  |  |  |
 O--O--O--O

 */
