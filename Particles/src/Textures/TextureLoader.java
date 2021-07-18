package Textures;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Blocks.BlockId;

/**
 * Diese Klasse lädt Png Bilder
 * Die Blöcke könenn dann ihre Id und Position angeben, um somit die Farbe der Textur an dieser Stelle zu bekommen.
 * 
 *
 */
public class TextureLoader {

	public class TextureContainer{
		public int width = 0;
		public int height = 0;
		public BufferedImage[] data;
	}
	
	private TextureContainer[] containers;
	public static final TextureLoader loader = new TextureLoader();
	
	public BufferedImage loadImage(String path) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(getClass().getResourceAsStream(path + ".png"));
		} catch (IOException e) {}
		
		return img;
	}
	
	/**
	 * Lädt eine einzelne Textur
	 * @param path
	 * @param id
	 */
	public void initTexture(String path, BlockId id) {
		BufferedImage img = loadImage(path);
		
		
		TextureContainer container = new TextureContainer();
		container.width = img.getWidth();
		container.height = img.getHeight();
		container.data = new BufferedImage[1];
		container.data[0] = img;
		
		containers[id.ordinal()] = container;
	}
	
	/**
	 * Lädt mehrere Bilder als Animation.
	 * Die Bilder müssen am Ende mit Zahlen durchnummeriert sein.
	 * 
	 * @param path
	 * @param id
	 * @param amount
	 */
	public void initAnimation(String path, BlockId id, int amountOfFrames) {
		TextureContainer container = new TextureContainer();
		
		container.data = new BufferedImage[amountOfFrames];
		
		for(int i = 0; i < amountOfFrames; i++) {
			BufferedImage img = loadImage(path + i);
			container.data[i] = img;
		}
		
		container.width = container.data[0].getWidth();
		container.height = container.data[0].getHeight();
		
		
		containers[id.ordinal()] = container;
	}
	
	/**
	 * Gibt die Farbe des Pixels im Bild an der angegeben Stelle zurück.
	 * Die x, y angaben werden modulisiert, damit sie niemals auserhalb des Bildes sein können.
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param pos, Angabe bei einer Animation, welches Frame gewählt werden soll. Bei einer normalen Textur immer 0 eingeben.
	 * @return Color
	 */
	public Color getPixel(BlockId id, int x, int y, int pos) {
		BufferedImage img = containers[id.ordinal()].data[pos];
		
		if(x < 0) x = -x;
		if(y < 0) y = -y;
		
		x %= img.getWidth();
		y %= img.getHeight();
		
		return new Color(img.getRGB(x, y));
	}
	
	
	public TextureLoader(){
		containers = new TextureContainer[BlockId.OutofLevel.ordinal()];
		initTexture("back", BlockId.None);
		initTexture("wood", BlockId.Wood);
		initTexture("sand", BlockId.Sand);
		initTexture("metal", BlockId.Steel);
		initAnimation("water", BlockId.Water, 3);
		initAnimation("lava", BlockId.Lava, 16);
	}
	
	
	
}
