/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tontauben;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

/**
 *
 * @author radle
 */
public class Partikel {

    Vector2D pos = new Vector2D();
    Vector2D vel = new Vector2D();
    Vector2D gravity = new Vector2D(0,0.5);
    private boolean sichtbar = true;
    private int arrayPos, groesse, xGeschwindigkeit = 10, yGeschwindigkeit = 10;
    private final Point[] BILD_POS = new Point[Bild.TILE_COLUM*Bild.TILE_ROW];
    private final int T2_OFFSET = 7, T3_OFFSET = 14, T4_OFFSET = 21,masse = 10;
    Tontaube t;
    private Random r = new Random();

    public Partikel(Vector2D pos, int arrayPos, int groesse, Tontaube t) {
        this.pos = pos;
        this.vel = new Vector2D(r.nextInt(xGeschwindigkeit) - xGeschwindigkeit/2, r.nextInt(yGeschwindigkeit) - yGeschwindigkeit/2);
        this.arrayPos = arrayPos;
        this.groesse = groesse;
        this.t = t;
        int all = 0;
        /*
            füllt ein Point Array in Größe des Spritesheets dadurch hat jede Position eine bestimmte Stelle im Array
            benutzt zum leichten Identifizieren der Bilder beim Zeichnen.
            arr[0] = 0,0
            arr[1] = 0,1
            ...
            jede Tontaube hat 7 Partikel, hierdurch weiß jeder Partikel an welcher Stelle er ist zusammen mit arrayPos und dem Offset
        */
        for (int y = 0; y < Bild.TILE_COLUM; y++) {
            for (int x = 0; x < Bild.TILE_ROW; x++, all++) {
                BILD_POS[all] = new Point(x, y);                
            }
        }
    }

    private void bewegen() {
        //beim verlassen der Welt unsichtbar machen(zum löschen der Arraystelle  in Tontaube)
        if (pos.x >= t.xWelt || pos.x + Bild.TILE_SIZE <= 0|| pos.y >= t.yWelt) { 
            sichtbar = false;
        } 
        //Vektorrechnung - den einzelnen Partikel nach unten fallen lassen durch Vektoraddition
        //möglichkeit durch Bildgröße eine Masse anzugeben damit es realistischer ist. Allerdings noch nicht umgesetzt
        Vector2D f = Vector2D.div(gravity, masse);    
        vel.add(f);
        pos.add(vel);
    }
    
    public boolean gibSichtbar(){
        return sichtbar;
    }
    
    public void zeichne(Graphics g) {
        bewegen();
        if (t.getClass().equals(Tontaube1.class)) {     // Die Bilder im Spritesheet haben unterschiedliche Positionen, durch den Offset werden diese Klassenbedingt berechnet
            g.drawImage(Bild.gibBild(BILD_POS[arrayPos].x, BILD_POS[arrayPos].y), (int) pos.x, (int) pos.y, groesse, groesse, null);
        } else if (t.getClass().equals(Tontaube2.class)) {
            g.drawImage(Bild.gibBild(BILD_POS[arrayPos + T2_OFFSET].x, BILD_POS[arrayPos + T2_OFFSET].y), (int) pos.x, (int) pos.y,groesse, groesse, null);
        } else if (t.getClass().equals(Tontaube3.class)) {
            g.drawImage(Bild.gibBild(BILD_POS[arrayPos + T3_OFFSET].x, BILD_POS[arrayPos + T3_OFFSET].y), (int) pos.x, (int) pos.y,groesse, groesse, null);
        } else if (t.getClass().equals(Tontaube4.class)) {
            g.drawImage(Bild.gibBild(BILD_POS[arrayPos + T4_OFFSET].x, BILD_POS[arrayPos + T4_OFFSET].y),(int) pos.x, (int) pos.y, groesse, groesse, null);
        }
    }
}
