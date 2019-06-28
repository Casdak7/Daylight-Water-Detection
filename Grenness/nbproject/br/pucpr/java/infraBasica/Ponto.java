/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pucpr.java.infraBasica;

/**
 *
 * @author Usuario
 */
public class Ponto {
           
    public int R;
    public int G;
    public int B;
    
    private int x;
    private int y;
    
    public int Cluster = 0;
    
    public double Distancia = 0;

    public Ponto(int R, int G, int B) {
        this.R = R;
        this.G = G;
        this.B = B;
    }
    
    public Ponto(int R, int G, int B,int x, int y) {
        this.R = R;
        this.G = G;
        this.B = B;
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
    
}
