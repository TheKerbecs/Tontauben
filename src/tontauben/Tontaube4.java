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
public class Tontaube4 extends Tontaube {

    private Vector2D geschwindigkeit = new Vector2D(10, 3);
    private Vector2D maus = new Vector2D();
    private final int geschwindigkeitsLimit = 8, partikelAnzahl = 2;

    public Tontaube4(int groesse) {
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
            maus.multiply(0.004f);
            geschwindigkeit.add(maus);
            geschwindigkeit.limit(geschwindigkeitsLimit);
            yPos += (int) geschwindigkeit.y;
            xPos += (int) geschwindigkeit.x;
        }
    }

    @Override
    public void zeichne(Graphics g) {
        if (sichtbar) {
            g.drawImage(Bild.gibBild(2, 5), xPos, yPos, dieGroesse, dieGroesse, null);
        } else {
            for (int i = 0; i < partikelAnzahl; i++) {
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
        for (int i = 0; i < partikelAnzahl; i++) {
            partikel[i] = new Partikel(new Vector2D(xPos, yPos), i, dieGroesse, this);
        }
    }
}
