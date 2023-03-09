/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tontauben;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author radle
 */
public class Tontaube3 extends Tontaube {

    private double tmpPos;

    public Tontaube3(int groesse) {
        this.dieGroesse = groesse;
        tmpPos = 0 - dieGroesse;
        wuerfleGeschwindigkeit();
        setzeAnzahlTontauben(gibAnzahlTontauben() + 1);
        sichtbar = true;
    }

    @Override
    public void berechnePosition() {
        if (sichtbar) {
            achsePos = yWelt / 3;
            tmpPos += geschwindigkeit;
            xPos = (int) tmpPos;
            int sinusKurve = (int) (achsePos + (map(achsePos, 10, 400, 4, 70) * Math.sin(map(achsePos, 10, 400, 0.1, 0.05) * xPos)
                    * map(achsePos, 10, 400, 1, 5) * Math.cos(0.01 * xPos)));
            yPos = sinusKurve;
            if (xPos >= xWelt) {
                tmpPos = 0 - dieGroesse;
            }
        }
    }

    private double map(double x, double inMin, double inMax, double outMin, double outMax) { //Funktion aus Arduino
        return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }

    @Override
    public void zeichne(Graphics g) {
        if (sichtbar) {
            g.drawImage(Bild.gibBild(0, 5), xPos, yPos, dieGroesse, dieGroesse, null);
            
            if (Oberflaeche.gibDebug()) {
                g.setColor(Color.BLACK);
                for (int i = -dieGroesse; i < xWelt; i++) {
                    int sinusKurve = (int) (achsePos + (map(achsePos, 10, 400, 4, 70) * Math.sin(map(achsePos, 10, 400, 0.1, 0.05) * i)
                            * map(achsePos, 10, 400, 1, 5) * Math.cos(0.01 * i)));
                    g.fillOval(i + dieGroesse / 2, sinusKurve + dieGroesse / 2, 4, 4);
                }
                g.drawLine(0, achsePos, xWelt, achsePos);
            }
        } else {
            for (int i = 0; i < partikel.length; i++) {
                if (partikel[i] != null) {
                    if (partikel[i].gibSichtbar()) {
                        partikel[i].zeichne(g);
                    } else if (partikel[i] != null) {
                        partikel[i] = null;
                    }
                }
            }
        }
    }

    @Override
    public void getroffen() {
        for (int i = 0; i < partikel.length; i++) {
            partikel[i] = new Partikel(new Vector2D(xPos, yPos), i, dieGroesse, this);

        }
    }
}
