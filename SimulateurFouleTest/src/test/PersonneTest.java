package test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sample.Obstacle;
import sample.ObstacleRectangle;
import sample.Personne;
import sample.Point;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonneTest {


    @Test
    public void test_Obstacle_Sur_Chemin(){
        Personne p = new Personne(20,20);
        Point sortie = new Point(60,40);
        Point coordC=new Point(20,40);
        Point coordD=new Point(30,20);
       assertTrue(p.toucheObstacle(sortie,coordC,coordD));
    }
    @Test
    public void test_Chemin_Sans_Obstacle(){
        Personne p = new Personne(20,20);
        Point sortie=new Point(60,40);
        Point coordC=new Point(10,40);
        Point coordD=new Point(20,30);
        assertFalse(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Personne_Longe_Mur(){
        Personne p = new Personne(20,20);
        Point sortie=new Point(60,40);
        Point coordC=new Point(28,24);
        Point coordD= new Point(10,30);
        assertTrue(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Chemin_Colinéaire_Pas_Superpose_Xdep_Plus_Petit_Que_XCoinA(){
        Personne p = new Personne(20,20);
        Point sortie = new Point(60,20);
        Point coordC= new Point(80,20);
        Point coordD= new Point(100,20);
        assertFalse(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Chemin_Colinéaire_Pas_Superpose_Xdep_Plus_Grand_Que_CoinA(){
        Personne p = new Personne(80,20);
        Point sortie= new Point(100,20);
        Point coordC=new Point(60,20);
        Point coordD= new Point(20,20);
        assertFalse(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Chemin_Colinéaire_Superpose_Sur_X(){
        Personne p = new Personne(20,20);
        Point sortie= new Point(100,20);
        Point coordC= new Point(60,20);
        Point coordD= new Point(80,20);
        assertTrue(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Chemin_Colinéaire_Pas_Superpose_ydep_Plus_Petit_Que_YCoinA(){
        Personne p = new Personne(20,20);
        Point sortie=new Point(20,60);
        Point coordC=new Point(20,80);
        Point coordD=new Point(20,100);
        assertFalse(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Chemin_Colinéaire_Pas_Superpose_Ydep_Plus_Grand_Que_YCoinA(){
        Personne p = new Personne(20,80);
        Point sortie=new Point(20,100);
        Point coordC=new Point(20,60);
        Point coordD=new Point(20,20);
        assertFalse(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Chemin_Colinéaire_Superpose_Sur_Y(){
        Personne p = new Personne(20,20);
        Point sortie=new Point(20,100);
        Point coordC=new Point(20,60);
        Point coordD=new Point(20,80);
        assertTrue(p.toucheObstacle(sortie,coordC,coordD));
    }

    @Test
    public void test_Coord_Obstacle(){
        Personne p =new Personne(20,20);
        Point sortie = new Point(100,60);
        Obstacle o = new ObstacleRectangle(40,40,40,20);
        List<Point> listSolution = new ArrayList<>();
        Point a =new Point(40,40);
        Point b=new Point(80,40);
        Point c=new Point(80,60);
        Point d=new Point(40,60);
        listSolution.add(a);
        listSolution.add(b);
        listSolution.add(b);
        listSolution.add(c);
        for(int i=0;i<p.coordCointouche(sortie,o).size();i++){
            assertEquals(listSolution.get(i).getX(),p.coordCointouche(sortie,o).get(i).getX());
            assertEquals(listSolution.get(i).getY(),p.coordCointouche(sortie,o).get(i).getY());
        }
    }

    @Test
    public void test_Coord_Obstacle_Avec_Coin_De_Obstacle_Sur_Chemin(){
        Personne p =new Personne(20,20);
        Point sortie = new Point(100,60);
        Obstacle o = new ObstacleRectangle(40,40,20,20);
        ArrayList<Point> listSolution = new ArrayList<>();
        Point a = new Point(40,40);
        Point b=new Point(60,40);
        Point c=new Point(60,60);
        Point d=new Point(40,60);
        listSolution.add(a);
        listSolution.add(b);
        listSolution.add(b);
        listSolution.add(c);
        for(int i=0;i<p.coordCointouche(sortie,o).size();i++){
            assertEquals(listSolution.get(i).getX(),p.coordCointouche(sortie,o).get(i).getX());
            assertEquals(listSolution.get(i).getY(),p.coordCointouche(sortie,o).get(i).getY());
        }
    }

    @Test
    public void test_Coord_Obstacle_Avec_Obstacle_Surperpose_Sur_Chemin(){
        Personne p =new Personne(20,20);
        Point sortie = new Point(100,20);
        Obstacle o = new ObstacleRectangle(40,20,20,20);
        ArrayList<Point> listSolution = new ArrayList<>();
        Point a =new Point(40,20);
        Point b=new Point(60,20);
        Point c=new Point(60,40);
        Point d=new Point(40,40);
        listSolution.add(a);
        listSolution.add(b);
        listSolution.add(b);
        listSolution.add(c);
        listSolution.add(d);
        listSolution.add(a);
        for (int i=0;i<p.coordCointouche(sortie,o).size();i++) {
            assertEquals(listSolution.get(i).getX(),p.coordCointouche(sortie,o).get(i).getX());
            assertEquals(listSolution.get(i).getY(),p.coordCointouche(sortie,o).get(i).getY());

        }
    }

    @Test
    public void test_Coord_Obstacle_Avec_Chemin_Travers_Obstacle_Par_Diagonale_0_2(){
        Personne p =new Personne(20,20);
        Point sortie = new Point(100,100);
        Obstacle o = new ObstacleRectangle(40,40,20,20);
        ArrayList<Point> listSolution = new ArrayList<>();
        Point a =new Point(40,40);
        Point b=new Point(60,40);
        Point c=new Point(60,60);
        Point d=new Point(40,60);
        listSolution.add(a);
        listSolution.add(b);
        listSolution.add(b);
        listSolution.add(c);
        listSolution.add(c);
        listSolution.add(d);
        listSolution.add(d);
        listSolution.add(a);
        for(int i=0;i<p.coordCointouche(sortie,o).size();i++){
            assertEquals(listSolution.get(i).getX(),p.coordCointouche(sortie,o).get(i).getX());
            assertEquals(listSolution.get(i).getY(),p.coordCointouche(sortie,o).get(i).getY());
        }
    }

   @Test
    public void test_Coord_Obstacle_Avec_Chemin_Travers_Obstacle_Par_Diagonale_1_3(){
        Personne p =new Personne(20,80);
        Point sortie = new Point(80,20);
        Obstacle o = new ObstacleRectangle(40,40,20,20);
       ArrayList<Point> listSolution = new ArrayList<>();
       Point a = new Point(40,40);
       Point b=new Point(60,40);
       Point c=new Point(60,60);
       Point d=new Point(40,60);
       listSolution.add(a);
       listSolution.add(b);
       listSolution.add(b);
       listSolution.add(c);
       listSolution.add(c);
       listSolution.add(d);
       listSolution.add(d);
       listSolution.add(a);

        for(int i=0;i<p.coordCointouche(sortie,o).size();i++){
            assertEquals(listSolution.get(i).getX(),p.coordCointouche(sortie,o).get(i).getX());
            assertEquals(listSolution.get(i).getY(),p.coordCointouche(sortie,o).get(i).getY());
        }
    }
}