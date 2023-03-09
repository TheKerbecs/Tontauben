package tontauben;


import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author radle
 */
public class Musik {
    public static Clip SpieleMusik(String datei) {
        try {
            File musicPath = new File(datei);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                return clip;
            } else {
                System.out.println("nichts da");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
