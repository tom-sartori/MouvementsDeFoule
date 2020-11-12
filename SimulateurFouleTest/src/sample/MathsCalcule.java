package sample;

import java.util.ArrayList;

public class MathsCalcule {

    // A commenter
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

    // A commenter
    // Renvoie vrais si les segements AB et CD coupent en un point intérieur strictement à AB et CD.
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
        //System.out.println("Les segments se touchent. ");
        return (0 < k1 && k1 < 1) && (0 < k2 && k2 < 1);
    }

    public static double distance(Point A, Point B) {
        return Math.sqrt(Math.pow(A.getX() - B.getX(), 2) + Math.pow(A.getY() - B.getY(), 2));
    }

    // Retourne vrais si AB est colinéaire à CD et qu'ils se chevauchent sur plus d'un point.
    public static boolean estSuperpose(Point coordA, Point coordB, Point coordC, Point coordD) {
        if (MathsCalcule.determinant(coordA, coordB, coordC, coordD) == 0) {
            if (((coordA.getY() < coordC.getY() && coordB.getY() < coordC.getY()) && (coordA.getY() < coordD.getY() && coordB.getY() < coordD.getY())) || ((coordA.getY() > coordC.getY() && coordB.getY() > coordC.getY()) && (coordA.getY() > coordD.getY() && coordB.getY() > coordD.getY()))) {
                return false;
            } else if (((coordA.getX() < coordC.getX() && coordB.getX() < coordC.getX()) && (coordA.getX() < coordD.getX() && coordB.getX() < coordD.getX())) || ((coordA.getX() > coordC.getX() && coordB.getX() > coordC.getX()) && (coordA.getX() > coordD.getX() && coordB.getX() > coordD.getX()))) {
                return false;
            } else if (coordA.getX()<=coordC.getX() && coordB.getX()<coordC.getX() && coordA.getX()<coordD.getX() && coordB.getX()<coordD.getX()){
                return false;
            }else if (coordA.getX()<=coordD.getX() && coordB.getX()<coordD.getX() &&coordA.getX()<coordC.getX() && coordB.getX()<coordC.getX()){
                return false;
            }else if(coordA.getY()<=coordC.getY() && coordB.getY()<coordC.getY() && coordA.getY()<coordD.getY() && coordB.getY()<coordD.getY()){
                return false;
            } else if(coordA.getY()<=coordD.getY() && coordB.getY()<coordD.getY() &&coordA.getY()<coordC.getY() && coordB.getY()<coordC.getY()){
                return false;
            } else if (coordA.getX()>=coordC.getX() && coordB.getX()>coordC.getX() && coordA.getX()>coordD.getX() && coordB.getX()>coordD.getX()){
                return false;
            }else if (coordA.getX()>=coordD.getX() && coordB.getX()>coordD.getX() &&coordA.getX()>coordC.getX() && coordB.getX()>coordC.getX()){
                return false;
            }else if(coordA.getY()>=coordC.getY() && coordB.getY()>coordC.getY() && coordA.getY()>coordD.getY() && coordB.getY()>coordD.getY()){
                return false;
            } else if(coordA.getY()>=coordD.getY() && coordB.getY()>coordD.getY() &&coordA.getY()>coordC.getY() && coordB.getY()>coordC.getY()){
                return false;
            } else if (coordB.getX()<=coordC.getX() && coordA.getX()<coordC.getX() && coordA.getX()<coordD.getX() && coordB.getX()<coordD.getX()){
                return false;
            }else if (coordB.getX()<=coordD.getX() && coordA.getX()<coordD.getX() &&coordA.getX()<coordC.getX() && coordB.getX()<coordC.getX()){
                return false;
            }else if(coordB.getY()<=coordC.getY() && coordA.getY()<coordC.getY() && coordA.getY()<coordD.getY() && coordB.getY()<coordD.getY()){
                return false;
            } else if(coordB.getY()<=coordD.getY() && coordA.getY()<coordD.getY() &&coordA.getY()<coordC.getY() && coordB.getY()<coordC.getY()){
                return false;
            } else if (coordB.getX()>=coordC.getX() && coordA.getX()>coordC.getX() && coordA.getX()>coordD.getX() && coordB.getX()>coordD.getX()){
                return false;
            }else if (coordB.getX()>=coordD.getX() && coordA.getX()>coordD.getX() &&coordA.getX()>coordC.getX() && coordB.getX()>coordC.getX()){
                return false;
            }else if(coordB.getY()>=coordC.getY() && coordA.getY()>coordC.getY() && coordA.getY()>coordD.getY() && coordB.getY()>coordD.getY()){
                return false;
            } else if(coordB.getY()>=coordD.getY() && coordA.getY()>coordD.getY() &&coordA.getY()>coordC.getY() && coordB.getY()>coordC.getY()){
                return false;
            } else
                return true;
        }
        return false;
    }


    // Retourne les segments d'obstacles qu'intersectent le segment AB.
    // Devrait renvoyer vrais si le mouvement de A à B est valide, ou sinon, faux.
    public static ArrayList<Point> coordSegments(Point coordA, Point coordB, Obstacle o) {
        ArrayList<Point> listTableau = new ArrayList<>();
        int j = 0;
        int w = 0;
        for (int i = 0; i < o.getDiagonales().size() / 2; i++) {
            w = i + j;
            if (w < o.getDiagonales().size() && w + 1 < o.getDiagonales().size()) {

                if (estSuperpose(coordA, coordB, o.getDiagonales().get(w), o.getDiagonales().get(w + 1)) && (!estCoupe(coordA, coordB, o.getListePoints().get(i), o.getListePoints().get(i+1)))) {
                    for (int k = 0; k < o.getListePoints().size(); k++) {
                        if (o.getListePoints().get(k).getX() == o.getDiagonales().get(w).getX() && o.getListePoints().get(k).getY() == o.getDiagonales().get(w).getY()) {

                            if (k - 1 >= 0 && k + 1 < o.getListePoints().size()) {
                                listTableau.add(o.getListePoints().get(k - 1));
                                listTableau.add(o.getListePoints().get(k));
                                listTableau.add(o.getListePoints().get(k));
                                listTableau.add(o.getListePoints().get(k + 1));
                            } else {

                                if (k - 1 < 0) {
                                    listTableau.add(o.getListePoints().get(k));
                                    listTableau.add(o.getListePoints().get(k + 1));
                                    listTableau.add(o.getListePoints().get(o.getListePoints().size() - 1));
                                    listTableau.add(o.getListePoints().get(k));
                                } else {

                                    if (k + 1 >= o.getListePoints().size()) {
                                        listTableau.add(o.getListePoints().get(k - 1));
                                        listTableau.add(o.getListePoints().get(k));
                                        listTableau.add(o.getListePoints().get(k));
                                        listTableau.add(o.getListePoints().get(0));
                                    }
                                }
                            }
                        } else {

                            if (o.getListePoints().get(k).getX() == o.getDiagonales().get(w + 1).getX() && o.getListePoints().get(k).getY() == o.getDiagonales().get(w + 1).getY()) {

                                if (k - 1 >= 0 && k + 1 < o.getListePoints().size()) {
                                    listTableau.add(o.getListePoints().get(k - 1));
                                    listTableau.add(o.getListePoints().get(k));
                                    listTableau.add(o.getListePoints().get(k));
                                    listTableau.add(o.getListePoints().get(k + 1));
                                } else {

                                    if (k - 1 < 0) {
                                        listTableau.add(o.getListePoints().get(k));
                                        listTableau.add(o.getListePoints().get(k + 1));
                                        listTableau.add(o.getListePoints().get(o.getListePoints().size() - 1));
                                        listTableau.add(o.getListePoints().get(k));

                                    } else {

                                        if (k + 1 >= o.getListePoints().size()) {
                                            listTableau.add(o.getListePoints().get(k - 1));
                                            listTableau.add(o.getListePoints().get(k));
                                            listTableau.add(o.getListePoints().get(k));
                                            listTableau.add(o.getListePoints().get(0));
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
        if (!listTableau.isEmpty()) {
            return listTableau;
        }
        for (int i = 0; i < o.getListePoints().size(); i++) {
            if (i != o.getListePoints().size() - 1) {

                if (estCoupe(coordA, coordB, o.getListePoints().get(i), o.getListePoints().get(i + 1))) {
                    listTableau.add(o.getListePoints().get(i));
                    listTableau.add(o.getListePoints().get(i + 1));
                }

            } else {
                if (estCoupe(coordA, coordB, o.getListePoints().get(i), o.getListePoints().get(0))) {
                        listTableau.add(o.getListePoints().get(i));
                        listTableau.add(o.getListePoints().get(0));
                }
            }
        }
        return listTableau;
    }

}