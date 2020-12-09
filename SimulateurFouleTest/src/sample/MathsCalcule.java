package sample;

import java.util.ArrayList;
import java.util.List;

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
        double k1 = valeurDeK(coordA,coordB,coordC,coordD).get(0);
        double k2 = valeurDeK(coordA,coordB,coordC,coordD).get(1);
        return (0 < k1  && k1 < 0.999999) && (0 < k2 && k2 < 0.999999);
    }

    public static List<Double> valeurDeK(Point coordA, Point coordB, Point coordC, Point coordD){
        List<Double> listK =new ArrayList<>();
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
        listK.add(k1);
        listK.add(k2);
        return listK;
    }

    public static double distance(Point A, Point B) {
        return Math.sqrt(Math.pow(A.getX() - B.getX(), 2) + Math.pow(A.getY() - B.getY(), 2));
    }

    // Retourne vrais si AB est colinéaire à CD et qu'ils se chevauchent sur plus d'un point.
    public static boolean estSuperpose(Point coordA, Point coordB, Point coordC, Point coordD) {
        if (MathsCalcule.determinant(coordA, coordB, coordC, coordD) == 0) {
            if (coordA.getX()<=coordC.getX() && coordB.getX()<coordC.getX() && coordA.getX()<coordD.getX() && coordB.getX()<coordD.getX()){
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
            if (w + 1 < o.getDiagonales().size()) {
                for (int k = 0; k < o.getListePoints().size() - 1;k++) {
                    if (estSuperpose(coordA, coordB, o.getDiagonales().get(w), o.getDiagonales().get(w + 1)) && (!estCoupe(coordA, coordB, o.getListePoints().get(k), o.getListePoints().get(k + 1)))) {
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

    // Renvoie le point du milieu du segment [AB].
    public static Point getMilieu(Point A, Point B) {
        Point milieu = new Point();
        milieu.setX((A.getX() + B.getX()) / 2);
        milieu.setY((A.getY() + B.getY()) / 2);
        return milieu;
    }

}