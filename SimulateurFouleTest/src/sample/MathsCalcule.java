package sample;

import java.util.ArrayList;

public class MathsCalcule {

    public static double determinant(Point coordA, Point coordB, Point coordC, Point coordD) {
        double a = coordB.getX() - coordA.getX();
        double b = coordB.getY() - coordA.getY();
        double c = coordC.getX() - coordD.getX();
        double d = coordC.getY() - coordD.getY();
        double u = coordC.getX() - coordA.getX();
        double v = coordC.getY() - coordA.getY();

        double determinant = (a * d) - (b * c);
        return determinant;
    }

    public static boolean estCoupe(Point coordA, Point coordB, Point coordC, Point coordD) {
        double a = coordB.getX() - coordA.getX();
        double b = coordB.getY() - coordA.getY();
        double c = coordC.getX() - coordD.getX();
        double d = coordC.getY() - coordD.getY();
        double u = coordC.getX() - coordA.getX();
        double v = coordC.getY() - coordA.getY();
        double determinant = determinant(coordA, coordB, coordC, coordD);
        double mat1 = (1 / determinant) * d;
        double mat2 = (1 / determinant) * (-b);
        double mat3 = (1 / determinant) * (-c);
        double mat4 = (1 / determinant) * a;
        double k1 = (mat1 * u) + (mat3 * v);
        double k2 = (mat2 * u) + (mat4 * v);
        //System.out.println("La valeur de K est "+k1 +"\n"+ "La valeur de k' est " +k2);
        if ((0 < k1 && k1 < 1) && (0 < k2 && k2 < 1)) {
            //System.out.println("Les segments se touchent. ");
            return true;
        } else {
            //System.out.println("Segments se touchent pas. ");
            return false;
        }
    }



    public static double distance(Point A, Point B) {
        return Math.sqrt(Math.pow(A.getX() - B.getX(), 2) + Math.pow(A.getY() - B.getY(), 2));
    }

    public static boolean estSuperpose(Point coordA, Point coordB, Point coordC, Point coordD) {
        if (MathsCalcule.determinant(coordA, coordB, coordC, coordD) == 0) {
            if (coordB.getX() == coordC.getX()) {
                //System.out.println("Sur meme axe x. ");
                if (((coordA.getY() < coordC.getY() && coordB.getY() < coordC.getY()) && (coordA.getY() < coordD.getY() && coordB.getY() < coordD.getY())) || ((coordA.getY() > coordC.getY() && coordB.getY() > coordC.getY()) && (coordA.getY() > coordD.getY() && coordB.getY() > coordD.getY()))) {
                    //System.out.println("pas superposés. ");
                    return false;
                } else {
                    return true;
                }
            } else {
                //System.out.println("sur meme axe y. ");
                if (((coordA.getX() < coordC.getX() && coordB.getX() < coordC.getX()) && (coordA.getX() < coordD.getX() && coordB.getX() < coordD.getX())) || ((coordA.getX() > coordC.getX() && coordB.getX() > coordC.getX()) && (coordA.getX() > coordD.getX() && coordB.getX() > coordD.getX()))) {
                    //System.out.println("pas superposés. ");
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }



    public static ArrayList<Point> coordSegments(Point coordA,Point coordB, Obstacle o){
        ArrayList<Point> listTableau = new ArrayList<>();
        int j = 0;
        int w =0;
        for(int i=0;i<o.getDiagonales().size()/2;i++) {
            w=i+j;
            if (w < o.getDiagonales().size() && w + 1 < o.getDiagonales().size()) {

                if (estSuperpose(coordA, coordB, o.getDiagonales().get(w), o.getDiagonales().get(w + 1))) {

                    for(int k=0; k<o.getCoins().size();k++){
                        if(o.getCoins().get(k).getX()==o.getDiagonales().get(w).getX() && o.getCoins().get(k).getY()==o.getDiagonales().get(w).getY()){

                            if(k-1>=0 && k+1<o.getCoins().size()){
                                listTableau.add(o.getCoins().get(k-1));
                                listTableau.add(o.getCoins().get(k));
                                listTableau.add(o.getCoins().get(k));
                                listTableau.add(o.getCoins().get(k+1));
                            } else{

                                if (k-1<0){
                                    listTableau.add(o.getCoins().get(k));
                                    listTableau.add(o.getCoins().get(k+1));
                                    listTableau.add(o.getCoins().get(o.getCoins().size()-1));
                                    listTableau.add(o.getCoins().get(k));
                                } else {

                                    if(k+1>=o.getCoins().size()){
                                        listTableau.add(o.getCoins().get(k-1));
                                        listTableau.add(o.getCoins().get(k));
                                        listTableau.add(o.getCoins().get(k));
                                        listTableau.add(o.getCoins().get(0));
                                    }
                                }
                            }
                        } else {

                            if(o.getCoins().get(k).getX()==o.getDiagonales().get(w+1).getX() && o.getCoins().get(k).getY()==o.getDiagonales().get(w+1).getY()){

                                if(k-1>=0 && k+1<o.getCoins().size()){
                                    listTableau.add(o.getCoins().get(k-1));
                                    listTableau.add(o.getCoins().get(k));
                                    listTableau.add(o.getCoins().get(k));
                                    listTableau.add(o.getCoins().get(k+1));
                                } else{

                                    if (k-1<0){
                                        listTableau.add(o.getCoins().get(k));
                                        listTableau.add(o.getCoins().get(k+1));
                                        listTableau.add(o.getCoins().get(o.getCoins().size()-1));
                                        listTableau.add(o.getCoins().get(k));

                                    } else {

                                        if(k+1>=o.getCoins().size()){
                                            listTableau.add(o.getCoins().get(k-1));
                                            listTableau.add(o.getCoins().get(k));
                                            listTableau.add(o.getCoins().get(k));
                                            listTableau.add(o.getCoins().get(0));
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
            j++;
        }
        if(!listTableau.isEmpty()){
            return listTableau;
        }
        for (int i =0; i<o.getCoins().size();i++) {
            if (i != o.getCoins().size() - 1) {

                if (estCoupe(coordA, coordB, o.getCoins().get(i), o.getCoins().get(i + 1))) {
                    listTableau.add(o.getCoins().get(i));
                    listTableau.add(o.getCoins().get(i + 1));
                }

            } else {
                if (estCoupe(coordA,coordB, o.getCoins().get(i), o.getCoins().get(0))) {

                    if (estSuperpose(coordA,coordB, o.getCoins().get(i), o.getCoins().get(0))) {

                        if (o.getCoins().size() > 4) {

                            if (estCoupe(coordA,coordB, o.getCoins().get(0), o.getCoins().get(i - 1))) {
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

