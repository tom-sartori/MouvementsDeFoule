package sample;

public class MathSys {
    private Point coordA;
    private Point coordB;
    private Point coordC;
    private Point coordD;

    public MathSys(Point coordA, Point coordB, Point coordC, Point coordD) {
        this.coordA = coordA;
        this.coordB = coordB;
        this.coordC = coordC;
        this.coordD = coordD;
    }

    public Point getCoordA() {
        return coordA;
    }

    public void setCoordA(Point coordA) {
        this.coordA = coordA;
    }

    public Point getCoordB() {
        return coordB;
    }

    public void setCoordB(Point coordB) {
        this.coordB = coordB;
    }

    public Point getCoordC() {
        return coordC;
    }

    public void setCoordC(Point coordC) {
        this.coordC = coordC;
    }

    public Point getCoordD() {
        return coordD;
    }

    public void setCoordD(Point coordD) {
        this.coordD = coordD;
    }

    public double getA(){
        return coordB.getX() - coordA.getX();
    }
    public double getB(){
        return coordB.getY() - coordA.getY();
    }
    public double getC(){
        return coordC.getX() - coordD.getX();
    }

    public double getD(){
        return coordC.getY() - coordD.getY();
    }

    public double getU(){
        return coordC.getX() - coordA.getX();
    }

    public double getV(){
        return coordC.getY() - coordA.getY();
    }

    public double determinant(){
        double a = getA();
        double b = getB();
        double c = getC();
        double d = getD() ;
        double u = getU();
        double v = getV();

        double determinant = (a * d) - (b * c);
        return determinant;
    }

    public boolean estCouper(){
        double determinant = determinant();
        double mat1 = (1/determinant) * getD();
        double mat2 = (1/determinant) * (-getB());
        double mat3 = (1/determinant) * (-getC());
        double mat4 = (1/determinant) * getA();
        double u = getU();
        double v=getV();
        double k1 = (mat1*u) + (mat3*v);
        double k2 = (mat2*u) + (mat4*v);
        System.out.println("La valeur de K est "+k1 +"\n"+ "La valeur de k' est " +k2);
        if ((0 <= k1 && k1 <= 1) && (0 <= k2 && k2 <= 1)) {
            System.out.println("Les segments se touchent. ");
            return true;
        }
        else {
            System.out.println("Segments se touchent pas. ");
            return false;
        }
    }
    public boolean estColEtObs(){
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
}


