/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tontauben;

import java.awt.Graphics;

/**
 *
 * @author radle
 */
public class Tontaube1 extends Tontaube {

    private Vector2D geschwindigkeit = new Vector2D(10, 3);
    private Vector2D maus = new Vector2D();
    private final double G = 15;
    private final double masse = 100, speed = -1;
    private final int geschwindigkeitsLimit = 16;
    private final int minA = 200, maxA = 7000;

    public Tontaube1(int groesse) {
        this.dieGroesse = groesse;
        xPos = 0 - dieGroesse;
        yPos = 30;
        setzeAnzahlTontauben(gibAnzahlTontauben() + 1);
        sichtbar = true;
    }

    @Override
    public void berechnePosition() {
        if (sichtbar) {
            maus = new Vector2D(Oberflaeche.gibxFK(), Oberflaeche.gibyFK());
            maus.subtract(xPos, yPos);
            double abstandSq = Math.min(Math.max(maus.getLengthSq(), minA), maxA);
            double staerke = G * (masse) / abstandSq;
            maus.setMag(staerke);
            geschwindigkeit.subtract(maus);
            geschwindigkeit.limit(geschwindigkeitsLimit);
            yPos += (int) geschwindigkeit.y;
            xPos += (int) geschwindigkeit.x;

            if (xPos >= xWelt - dieGroesse) {
                xPos = xWelt - dieGroesse;
                geschwindigkeit.x *= speed;
            } else if (xPos <= 0) {
                xPos = 0;
                geschwindigkeit.x *= speed;
            }
            if (yPos >= yWelt - dieGroesse) {
                yPos = yWelt - dieGroesse;
                geschwindigkeit.y *= speed;
            } else if (yPos <= 0) {
                yPos = 0;
                geschwindigkeit.y *= speed;
            }
        }
    }

    @Override
    public void zeichne(Graphics g) {
        if (sichtbar) {
            g.drawImage(Bild.gibBild(3, 4), xPos, yPos, dieGroesse, dieGroesse, null);
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
