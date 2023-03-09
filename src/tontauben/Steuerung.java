/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tontauben;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

/**
 *
 * @author radle
 */
public class Steuerung {

    private float dieAnzahlTreffer = 0, schuesse = 0;
    private boolean spielLauft = true;
    private Timer timr;
    private float tQuote, tQuoteAlt = 0;
    private final int alleTontaubenArten = 3;
    private int delay = 10, tmpCounter = 0;
    private int minGroesse = 30, maxGroesse = 64;
    private Oberflaeche oberflache;
    private Tontaube[] tontaube = new Tontaube[11];
    private Random r = new Random();
    private Clip soundGetroffen;

    public Steuerung(Oberflaeche oberflache) {
        this.oberflache = oberflache;
        tontaube[Tontaube.gibAnzahlTontauben()] = new Tontaube4(minGroesse);
        timr = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verarbeiteTick();
            }
        });
        timr.start();
        highScoreLesen();
    }

    public void feuer() {
        if (spielLauft) {
            schuesse++;
            oberflache.zeigeHighscore(Math.round(tQuoteAlt));
            oberflache.zeigeStatus(oberflache.LAUFT);
            for (int i = 0; i < Tontaube.gibAnzahlTontauben(); i++) { //Alle Tontauben durchlaufen
                if (tontaube[i].gibSichtbar() && pruefeObGetroffen(tontaube[i])) { //Nur sichtbare nach Kollison prüfen
                    soundGetroffen = new Musik().SpieleMusik("musik\\hit.wav");
                    soundGetroffen.start();
                    tontaube[i].getroffen();
                    tontaube[i].setzeSichtbar(false);
                    dieAnzahlTreffer++;
                    oberflache.zeigeStatistik(schuesse, dieAnzahlTreffer);

                    if (dieAnzahlTreffer == tontaube.length - 1) { //Wenn die Treffer gleich wie TontaubenArray Gewonnen
                        oberflache.zeigeStatus(oberflache.GEWONNEN);
                        spielLauft = false;
                        oberflache.neuzeichnen();
                        tQuote = (dieAnzahlTreffer / schuesse) * 100;
                        tontaube[0].getroffen();
                        tontaube[0].setzeSichtbar(false);
                        highScoreSchreiben();
                    }
                    if (tontaube[i].getClass().equals(Tontaube4.class)) {     // Wenn Tontaube4 getroffen Spiel vorbei
                        oberflache.zeigeStatus(oberflache.VERLOREN);
                        spielLauft = false;
                        oberflache.zeigeStatistik(schuesse, dieAnzahlTreffer);
                        for (int j = 0; j < tontaube.length; j++) {     // Alle Tontauben kaputt machen ; nur für Effekt
                            if (tontaube[j] != null) {
                                if (tontaube[j].gibSichtbar()) {
                                    tontaube[j].getroffen();
                                    tontaube[j].setzeSichtbar(false);
                                }
                            }
                        }
                    }
                }
                oberflache.zeigeStatistik(schuesse, dieAnzahlTreffer);
                oberflache.neuzeichnen();
            }

        }
    }

    public void statistikAnzeigen() {
        oberflache.zeigeHighscore(Math.round(tQuoteAlt));
        oberflache.zeigeStatus(oberflache.LAUFT);
        oberflache.zeigeStatistik(schuesse, dieAnzahlTreffer);
    }

    public void verarbeiteTick() {
        if (spielLauft) {
            int minDelay = 100;
            int maxDelay =  500;
            tmpCounter++;
            if (tmpCounter % (r.nextInt(maxDelay - minDelay) + minDelay) == 0 && Tontaube.gibAnzahlTontauben() < tontaube.length) { // im zufälligen Intervall eine neue Tontaube erstellen zwischen min und max
                neueTontaube();
            }
            for (int i = 0; i < Tontaube.gibAnzahlTontauben(); i++) {
                tontaube[i].berechnePosition();
            }
        }
        oberflache.neuzeichnen();

    }

    public void neueTontaube() {
        switch (r.nextInt(alleTontaubenArten)) {
            case 0:
                tontaube[Tontaube.gibAnzahlTontauben()] = new Tontaube1(r.nextInt(maxGroesse - minGroesse) + minGroesse);
                break;
            case 1:
                tontaube[Tontaube.gibAnzahlTontauben()] = new Tontaube2(r.nextInt(maxGroesse - minGroesse) + minGroesse);
                break;
            case 2:
                tontaube[Tontaube.gibAnzahlTontauben()] = new Tontaube3(r.nextInt(maxGroesse - minGroesse) + minGroesse);
                break;
        }
    }

    private boolean pruefeObGetroffen(Tontaube t) {
        Rectangle maus = new Rectangle(oberflache.gibxFK() - Bild.gibBild(0, 1).getWidth() / 2, oberflache.gibyFK() - Bild.gibBild(0, 1).getHeight() / 2,
                Bild.gibBild(0, 1).getWidth(), Bild.gibBild(0, 1).getHeight());
        Rectangle tontaub = new Rectangle(t.gibX(), t.gibY(), t.gibGroesse(), t.gibGroesse());
        return maus.intersects(tontaub);
    }

    public Tontaube[] gibTontauben() {
        return tontaube;
    }

    public void neustarten() {
        dieAnzahlTreffer = 0;
        schuesse = 0;
        Tontaube.setzeAnzahlTontauben((int) dieAnzahlTreffer);
        oberflache.zeigeStatistik(schuesse, dieAnzahlTreffer);
        oberflache.textFeldStatus.setText("");
        oberflache.zeigeStatus(oberflache.LAUFT);
        tontaube[Tontaube.gibAnzahlTontauben()] = new Tontaube4(minGroesse);
        spielLauft = true;
        highScoreLesen();
        oberflache.zeigeHighscore(Math.round(tQuoteAlt));
    }

    public void highScoreLesen() {
        try {
            int maxChars = 99;
            FileReader reader = new FileReader("text/highscore.txt");
            int character = reader.read();
            char[] wert = new char[maxChars];
            int tmp = 0;
            while (character != -1) {
                wert[tmp] = (char) character;
                character = reader.read();
                tmp++;
            }
            tQuoteAlt = Float.parseFloat(new String(wert));
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void highScoreSchreiben() {
        try {
            tQuote = Math.round(dieAnzahlTreffer / schuesse) * 100;
            if (tQuote > tQuoteAlt) {
                PrintWriter pw = new PrintWriter("text/highscore.txt");
                pw.close();
                FileWriter writer = new FileWriter("text/highscore.txt", true);
                writer.write(Integer.toString((int) tQuote));
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
