/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tontauben;

import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author radle
 */
public abstract class Tontaube {
    private Random r = new Random();
    private static int dieAnzahlTontauben;
    protected int dieGroesse, xPos, yPos, xWelt, yWelt, achsePos = 60;
    protected double geschwindigkeit, maxGeschwindigkeit = 2, minGeschwindigkeit = 0.1;
    protected boolean sichtbar = false;
    protected Partikel[] partikel = new Partikel[7];
    public static int gibAnzahlTontauben(){
        return dieAnzahlTontauben;
    }
    public static void setzeAnzahlTontauben(int anzahl){
        dieAnzahlTontauben = anzahl;
    }
    public int gibX(){
        return xPos;
    }
    
    public int gibY(){
        return yPos;
    }
    public int gibGroesse(){
        return dieGroesse;
    }
    public void setzeSichtbar(boolean pWert){
        sichtbar = pWert;
    }
    public boolean gibSichtbar(){
        return sichtbar;
    }
    public void setzexWelt(int xWelt){
        this.xWelt = xWelt;
    }
    public void setzeyWelt(int yWelt){
        this.yWelt = yWelt;
    }
    public abstract void getroffen();
    public void wuerfleGeschwindigkeit(){
        geschwindigkeit = r.nextDouble() * maxGeschwindigkeit + minGeschwindigkeit;
    }
    public abstract void berechnePosition();
    public abstract void zeichne(Graphics g);
}

