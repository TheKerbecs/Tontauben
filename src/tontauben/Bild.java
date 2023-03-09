package tontauben;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bild {

    private static BufferedImage bildGrid;
    public static final int TILE_SIZE = 64;
    public static final int TILE_ROW = 5, TILE_COLUM = 6;
    private static int all;
    private static final BufferedImage[] bim = new BufferedImage[TILE_COLUM*TILE_ROW];

    public static BufferedImage ladeBild() {
        BufferedImage bild = null;
        try {
            bild = ImageIO.read(new File("bilder/NEU.png"));
        } catch (IOException e) {
        }
        return bild;
    }

    public static BufferedImage gibBild(int xGrid, int yGrid) {
        if (bildGrid == null) {
            bildGrid = ladeBild();
        }
        return bildGrid.getSubimage(xGrid * TILE_SIZE, yGrid * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    public static BufferedImage[] gibBildArray() {
        for (int i = 0; i < TILE_COLUM; i++) {
            for (int j = 0; j < TILE_ROW; j++, all++) {
                bim[all] = Bild.gibBild(j, i);
            }
        }
        all = 0;
        return bim;
    }
}
