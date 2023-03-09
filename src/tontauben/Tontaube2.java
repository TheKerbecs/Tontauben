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
public class Tontaube2 extends Tontaube {

    private double tmpPos;

    public Tontaube2(int groesse) {
        this.dieGroesse = groesse;
        tmpPos =0-dieGroesse;
        wuerfleGeschwindigkeit();
        setzeAnzahlTontauben(gibAnzahlTontauben() + 1);
        sichtbar = true;
    }

    @Override
    public void berechnePosition() {
        if (sichtbar) {
            achsePos = yWelt / 2;
            tmpPos += geschwindigkeit;
            xPos = (int) tmpPos;
            int kurve = (int) (achsePos + 100 * Math.sin(0.007 * xPos) + 40 * Math.cos(0.05 * xPos));
            yPos = kurve;
            if (xPos >= xWelt) {
                tmpPos = 0 - dieGroesse;
            }
        }
    }

    @Override
    public void zeichne(Graphics g) {
        if (sichtbar) {
            g.drawImage(Bild.gibBild(4, 4), xPos, yPos, dieGroesse, dieGroesse, null);
            
            if (Oberflaeche.gibDebug()) {
                g.setColor(Color.red);
                for (int i = -dieGroesse; i < xWelt; i++) {
                    int kurve = (int) (achsePos + 100 * Math.sin(0.007 * i) + 40 * Math.cos(0.05 * i));
                    g.fillOval(i + dieGroesse / 2, kurve + dieGroesse / 2, 4, 4);
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
