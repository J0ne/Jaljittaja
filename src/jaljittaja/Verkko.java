
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

    /**
     * Konstruktori
     * @param sivu
     * @param alkupiste
     * @param maali
     */
    public Verkko(int sivu, Solmu alkupiste, Solmu maali) {
        RakennaVerkko(sivu, alkupiste, maali);
        this.maali = maali;
    }
    
    /**
     * Matriisi, jossa kaikki verkon solmut
     */
    public Solmu[][] Solmut;

    /**
     * Verkon päätepiste, piste B, eli "maali"
     */
    public Solmu maali;
    private Verkko RakennaVerkko(int n, Solmu alkupiste, Solmu maali) {

        int x = n;
        int y = n;
        Solmut = new Solmu[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {

                Solmut[i][j] = new Solmu(i, j, false);
                //alkupiste
                if(alkupiste.getX() == i && alkupiste.getY() == j){
                Solmut[i][j].OnAlkupiste = true;
                }
                if(maali.getX() == i && maali.getY() == j){
                Solmut[i][j].setMaali(true);
                }
//                //luodaan este
//                if ((j >= 2 && j % 7 == 0 && i %3 == 0 )&& (i > 0 && i < n-3) ||
//                        (j > 2 && j < n-3 && i== 8)
//                        || (j >= 5 && j < n-1 && i== 2)) {
//                    Solmut[i][j].OnEste = true;
//                }
            }
        }
        return this;
    }
    
    /**
     * Asettaa maalin, eli polunetsinnän päätepisteen.
     * @param x
     * @param y
     */
    public void AsetaMaalinSijainti(int x, int y){
        
        if(!this.Solmut[x][y].isOnEste()){
            this.Solmut[this.maali.getX()][this.maali.getY()].setMaali(false);
            this.maali = this.Solmut[x][y];
            this.Solmut[x][y].setMaali(true);
        }
        
        else
            return;
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
