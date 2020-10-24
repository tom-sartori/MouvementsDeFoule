package sample;

import java.util.ArrayList;

public class MathsCalcule {

    public static double determinant(Point coordA,Point coordB,Point coordC,Point coordD){
        double a = coordB.getX() - coordA.getX();
        double b = coordB.getY() - coordA.getY();
        double c = coordC.getX() - coordD.getX();
        double d = coordC.getY() - coordD.getY();
        double u = coordC.getX() - coordA.getX();
        double v = coordC.getY() - coordA.getY();

        double determinant = (a * d) - (b * c);
        return determinant;
    }

    public static boolean estCouper(Point coordA, Point coordB,Point coordC,Point coordD){
        double a = coordB.getX() - coordA.getX();
        double b = coordB.getY() - coordA.getY();
        double c = coordC.getX() - coordD.getX();
        double d = coordC.getY() - coordD.getY();
        double u = coordC.getX() - coordA.getX();
        double v = coordC.getY() - coordA.getY();
        double determinant = determinant(coordA,coordB,coordC,coordD);
        double mat1 = (1/determinant) * d;
        double mat2 = (1/determinant) * (-b);
        double mat3 = (1/determinant) * (-c);
        double mat4 = (1/determinant) * a;
        double k1 = (mat1*u) + (mat3*v);
        double k2 = (mat2*u) + (mat4*v);
        System.out.println("La valeur de K est "+k1 +"\n"+ "La valeur de k' est " +k2);
        if ((0 < k1 && k1 < 1) && (0 < k2 && k2 < 1)) {
            System.out.println("Les segments se touchent. ");
            return true;
        }
        else {
            System.out.println("Segments se touchent pas. ");
            return false;
        }
    }


    public static boolean estColEtObs(Point coordA,Point coordB,Point coordC, Point coordD){
        if (coordB.getX() == coordC.getX()) {
            System.out.println("Sur meme axe x. ");
            if ( ((coordA.getY() < coordC.getY() && coordB.getY() < coordC.getY()) && (coordA.getY() < coordD.getY() && coordB.getY() < coordD.getY())) || ((coordA.getY() > coordC.getY() && coordB.getY() > coordC.getY()) && (coordA.getY() > coordD.getY() && coordB.getY() > coordD.getY()))) {
                System.out.println("pas superposés. ");
                return false;
            }
            else {
                System.out.println("Superposés");
                return true;
            }
        }
        else {
            System.out.println("sur meme axe y. ");
            if ( ((coordA.getX() < coordC.getX() && coordB.getX() < coordC.getX()) && (coordA.getX() < coordD.getX() && coordB.getX() < coordD.getX())) || ((coordA.getX() > coordC.getX() && coordB.getX() > coordC.getX()) && (coordA.getX() > coordD.getX() && coordB.getX() > coordD.getX()))) {
                System.out.println("pas superposés. ");
                return false;
            }
            else {
                System.out.println("Superposés");
                return true;
            }
        }
    }

    public static double distance (Point A, Point B) {
        return Math.sqrt( Math.pow(A.getX() - B.getX(), 2) + Math.pow(A.getY() - B.getY(), 2) );
    }

    public static boolean estSuperpose(Point coordA,Point coordB, Point coordC, Point coordD){
        if (MathsCalcule.determinant(coordA,coordB,coordC,coordD)== 0) {
            return MathsCalcule.estColEtObs(coordA,coordB,coordC,coordD);
        }
        return false;
    }
    public static boolean toucheObstacle (Point coordA,Point coordB, Point coordC, Point coordD) {
        if (estSuperpose(coordA,coordB,coordC,coordD)){
            return true;
        }else
            return MathsCalcule.estCouper(coordA,coordB,coordC,coordD);
    }


    public static ArrayList<Point> coordSegments(Point coordA,Point coordB, Obstacle o){
        ArrayList<Point> listTableau = new ArrayList<>();
        for (int i =0; i<o.getCoins().size();i++) {
            if (i != o.getCoins().size() - 1) {
                if (toucheObstacle(coordA,coordB, o.getCoins().get(i), o.getCoins().get(i + 1))) {
                    if (estSuperpose(coordA,coordB, o.getCoins().get(i), o.getCoins().get(i + 1))) {
                        System.out.println("test");
                        if (o.getCoins().size() > 4) {
                            if (i - 1 >= 0) {
                                if (toucheObstacle(coordA,coordB, o.getCoins().get(i + 1), o.getCoins().get(i - 1))) {
                                    listTableau.add(o.getCoins().get(i));
                                    listTableau.add(o.getCoins().get(i + 1));
                                    listTableau.add(o.getCoins().get(i - 1));
                                    listTableau.add(o.getCoins().get(i));
                                }
                            } else {
                                if (toucheObstacle(coordA,coordB, o.getCoins().get(o.getCoins().size()), o.getCoins().get(i + 1))) {
                                    listTableau.add(o.getCoins().get(o.getCoins().size()));
                                    listTableau.add(o.getCoins().get(i));
                                    listTableau.add(o.getCoins().get(i));
                                    listTableau.add(o.getCoins().get(i + 1));
                                }
                            }
                        }
                    }else{
                        System.out.println("test2");
                        listTableau.add(o.getCoins().get(i));
                        listTableau.add(o.getCoins().get(i + 1));
                    }
                }
            } else {
                if (toucheObstacle(coordA,coordB, o.getCoins().get(i), o.getCoins().get(0))) {
                    System.out.println("test3");
                    if (estSuperpose(coordA,coordB, o.getCoins().get(i), o.getCoins().get(0))) {
                        System.out.println("test4");
                        if (o.getCoins().size() > 4) {
                            if (toucheObstacle(coordA,coordB, o.getCoins().get(0), o.getCoins().get(i - 1))) {
                                listTableau.add(o.getCoins().get(i));
                                listTableau.add(o.getCoins().get(0));
                                listTableau.add(o.getCoins().get(i - 1));
                                listTableau.add(o.getCoins().get(i));
                            }
                        }
                    }else {
                        listTableau.add(o.getCoins().get(i));
                        listTableau.add(o.getCoins().get(0));
                    }
                }
            }
        }
        return listTableau;
    }

}


