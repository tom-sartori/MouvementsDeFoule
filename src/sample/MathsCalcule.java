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
        // double u = coordC.getX() - coordA.getX();
        // double v = coordC.getY() - coordA.getY();

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
                for (int k = 0; k < o.getListePointsPhysique().size() - 1; k++) {
                    if (estSuperpose(coordA, coordB, o.getDiagonales().get(w), o.getDiagonales().get(w + 1)) && (!estCoupe(coordA, coordB, o.getListePointsPhysique().get(k), o.getListePointsPhysique().get(k + 1)))) {
                        if (o.getListePointsPhysique().get(k).getX() == o.getDiagonales().get(w).getX() && o.getListePointsPhysique().get(k).getY() == o.getDiagonales().get(w).getY()) {

                            if (k - 1 >= 0 && k + 1 < o.getListePointsPhysique().size()) {
                                listTableau.add(o.getListePointsPhysique().get(k - 1));
                                listTableau.add(o.getListePointsPhysique().get(k));
                                listTableau.add(o.getListePointsPhysique().get(k));
                                listTableau.add(o.getListePointsPhysique().get(k + 1));
                            } else {

                                if (k - 1 < 0) {
                                    listTableau.add(o.getListePointsPhysique().get(k));
                                    listTableau.add(o.getListePointsPhysique().get(k + 1));
                                    listTableau.add(o.getListePointsPhysique().get(o.getListePointsPhysique().size() - 1));
                                    listTableau.add(o.getListePointsPhysique().get(k));
                                } else {

                                    if (k + 1 >= o.getListePointsPhysique().size()) {
                                        listTableau.add(o.getListePointsPhysique().get(k - 1));
                                        listTableau.add(o.getListePointsPhysique().get(k));
                                        listTableau.add(o.getListePointsPhysique().get(k));
                                        listTableau.add(o.getListePointsPhysique().get(0));
                                    }
                                }
                            }
                        } else {

                            if (o.getListePointsPhysique().get(k).getX() == o.getDiagonales().get(w + 1).getX() && o.getListePointsPhysique().get(k).getY() == o.getDiagonales().get(w + 1).getY()) {

                                if (k - 1 >= 0 && k + 1 < o.getListePointsPhysique().size()) {
                                    listTableau.add(o.getListePointsPhysique().get(k - 1));
                                    listTableau.add(o.getListePointsPhysique().get(k));
                                    listTableau.add(o.getListePointsPhysique().get(k));
                                    listTableau.add(o.getListePointsPhysique().get(k + 1));
                                } else {

                                    if (k - 1 < 0) {
                                        listTableau.add(o.getListePointsPhysique().get(k));
                                        listTableau.add(o.getListePointsPhysique().get(k + 1));
                                        listTableau.add(o.getListePointsPhysique().get(o.getListePointsPhysique().size() - 1));
                                        listTableau.add(o.getListePointsPhysique().get(k));

                                    } else {

                                        if (k + 1 >= o.getListePointsPhysique().size()) {
                                            listTableau.add(o.getListePointsPhysique().get(k - 1));
                                            listTableau.add(o.getListePointsPhysique().get(k));
                                            listTableau.add(o.getListePointsPhysique().get(k));
                                            listTableau.add(o.getListePointsPhysique().get(0));
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
        for (int i = 0; i < o.getListePointsPhysique().size(); i++) {
            if (i != o.getListePointsPhysique().size() - 1) {
                    if (estCoupe(coordA, coordB, o.getListePointsPhysique().get(i), o.getListePointsPhysique().get(i + 1))) {
                        listTableau.add(o.getListePointsPhysique().get(i));
                        listTableau.add(o.getListePointsPhysique().get(i + 1));
                    }

            } else {
                    if (estCoupe(coordA, coordB, o.getListePointsPhysique().get(i), o.getListePointsPhysique().get(0))) {
                        listTableau.add(o.getListePointsPhysique().get(i));
                        listTableau.add(o.getListePointsPhysique().get(0));
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

    public static Point getVecteurDirecteur(Point A, Point B) {
        return new Point(B.getX() - A.getX(), B.getY() - A.getY());
    }

    public static Point normaliseVecteur(Point u, double taille) {
        double x = u.getX();
        double y = u.getY();
        double argument = Math.sqrt((x * x) + (y * y));

        double xPrime = (taille / argument) * x;
        double yPrime = (taille / argument) * y;
        return new Point(xPrime, yPrime);
    }

    // Retourne la droite d'equation ax+b passant par A et B.
    // Retourne un point avec x correspondant à a et y correspondant à b.
    public static Point getDroite(Point A, Point B) {
        double a = (B.getY() - A.getY()) / (B.getX() - A.getX());
        double b = A.getY() - (a * A.getX());
        return new Point(a, b);
    }

    // Retourne le point d'intersection des deux droites en paramètre
    // Pre-requis ; pour chaque droite, le Point correspond à : x = a et y = b
    public static Point getPointIntersection (Point d1, Point d2) {
        double a1 = d1.getX();
        double b1 = d1.getY();
        double a2 = d2.getX();
        double b2 = d2.getY();

        if (a1 == a2) {
            return null;
        }

        double x = (b2 - b1) / (a1 - a2);
        double y = (a1 * x) + b1;

        return new Point(x, y);
    }

    // Retourne le point d'arrivé, en partant du Point A et en ajoutant le vecteur u.
    public static Point getPointPrime (Point A, Point vecU) {
        return new Point(A.getX() + vecU.getX(), A.getY() + vecU.getY());
    }

    // Retourne l'équation de la droite (ax+b).
    // Retourne un Point avec x correspondant à a et y correspondant à b.
    public static Point getDroiteParallele (Point A, Point B, double espacement) {
        Point vecteurDir = MathsCalcule.getVecteurDirecteur(A, B);
        Point vecteurOrthogonal = new Point(- vecteurDir.getY(), vecteurDir.getX());
        vecteurOrthogonal = MathsCalcule.normaliseVecteur(vecteurOrthogonal, espacement);
        Point Aprime = MathsCalcule.getPointPrime(A, vecteurOrthogonal);
        Point Bprime = MathsCalcule.getPointPrime(B, vecteurOrthogonal);

        Point d1 = MathsCalcule.getDroite(Aprime, Bprime);
        return d1;
    }
}