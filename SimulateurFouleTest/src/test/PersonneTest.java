package test;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sample.Obstacle;
import sample.ObstacleRectangle;
import sample.Personne;

import static org.junit.jupiter.api.Assertions.*;

class PersonneTest {
    @Test
    public void test_Obstacle_Sur_Chemin(){
       Personne p = new Personne(20,20);
       double[] sortie={60,40};
       double[] coordC={20,40};
       double[] coordD={30,20};
       assertTrue(p.toucheObstacle(sortie,coordC,coordD));
    }
    @Test
    public void test_Chemin_Sans_Obstacle(){
        Personne p = new Personne(20,20);
        double[] sortie={60,40};
        double[] coordC={10,40};
        double[] coordD={20,30};
        assertFalse(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Personne_Longe_Mur(){
        Personne p = new Personne(20,20);
        double[] sortie={60,40};
        double[] coordC={28,24};
        double[] coordD={10,30};
        assertFalse(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Chemin_Colinéaire_Pas_Superpose_Xdep_Plus_Petit_Que_XCoinA(){
        Personne p = new Personne(20,20);
        double[] sortie={60,20};
        double[] coordC={80,20};
        double[] coordD={100,20};
        assertFalse(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Chemin_Colinéaire_Pas_Superpose_Xdep_Plus_Grand_Que_CoinA(){
        Personne p = new Personne(80,20);
        double[] sortie={100,20};
        double[] coordC={60,20};
        double[] coordD={20,20};
        assertFalse(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Chemin_Colinéaire_Superpose_Sur_X(){
        Personne p = new Personne(20,20);
        double[] sortie={100,20};
        double[] coordC={60,20};
        double[] coordD={80,20};
        assertTrue(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Chemin_Colinéaire_Pas_Superpose_ydep_Plus_Petit_Que_YCoinA(){
        Personne p = new Personne(20,20);
        double[] sortie={20,60};
        double[] coordC={20,80};
        double[] coordD={20,100};
        assertFalse(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Chemin_Colinéaire_Pas_Superpose_Ydep_Plus_Grand_Que_YCoinA(){
        Personne p = new Personne(20,80);
        double[] sortie={20,100};
        double[] coordC={20,60};
        double[] coordD={20,20};
        assertFalse(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Chemin_Colinéaire_Superpose_Sur_Y(){
        Personne p = new Personne(20,20);
        double[] sortie={20,100};
        double[] coordC={20,60};
        double[] coordD={20,80};
        assertTrue(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Coord_Obstacle(){
        Personne p =new Personne(20,20);
        double[] sortie = {100,60};
        Obstacle o = new ObstacleRectangle(40,40,40,20);
        double[][] solution = {{40,40},{80,40},{80,40},{80,60}};
        for(int i=0;i<solution.length;i++){
            for (int j=0;j<solution[0].length;j++){
                assertEquals(solution[i][j],p.coordCointouche(sortie,o)[i][j]);
            }
        }
    }
    @Test
    public void test_Coord_Obstacle_Avec_Coin_De_Obstacle_Sur_Chemin(){
        Personne p =new Personne(20,20);
        double[] sortie = {100,60};
        Obstacle o = new ObstacleRectangle(40,40,20,20);
        for(int i=0;i<4;i++){
            for (int j=0;j<2;j++){
                assertEquals(0,p.coordCointouche(sortie,o)[i][j]);
            }
        }
    }

    @Test
    public void test_Coord_Obstacle_Avec_Obstacle_Surperpose_Sur_Chemin(){
        Personne p =new Personne(20,20);
        double[] sortie = {100,20};
        Obstacle o = new ObstacleRectangle(40,20,20,20);
        double[][] solution = {{40,20},{60,20}};
        for(int i=0;i< solution.length;i++){
            for (int j=0;j<solution[0].length;j++){
                assertEquals(solution[i][j],p.coordCointouche(sortie,o)[i][j]);
            }
        }
    }

    @Test
    public void test_Coord_Obstacle_Avec_Chemin_Travers_Obstacle_Par_Diagonale_0_2(){
        Personne p =new Personne(20,20);
        double[] sortie = {100,100};
        Obstacle o = new ObstacleRectangle(40,40,20,20);
        double[][] solution = {{40,40},{60,60}};

        for(int i=0;i<solution.length;i++){
            for (int j=0;j<solution[0].length;j++){
                assertEquals(solution[i][j],p.coordCointouche(sortie,o)[i][j]);
            }
        }
    }

   /* @Test
    public void test_Coord_Obstacle_Avec_Chemin_Travers_Obstacle_Par_Diagonale_1_3(){
        Personne p =new Personne(0,80);
        double[] sortie = {120,0};
        Obstacle o = new ObstacleRectangle(20,100,80,80);
        double[][] solution = {{20,100},{100,20}};

        for(int i=0;i<solution.length;i++){
            for (int j=0;j<solution[0].length;j++){
                assertEquals(solution[i][j],p.coordCointouche(sortie,o)[i][j]);
            }
        }
    }*/
}