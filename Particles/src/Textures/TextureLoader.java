package Textures;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Blocks.BlockId;

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
	
	
	public void initTexture(String path, BlockId id) {
		BufferedImage img = loadImage(path);
		
		
		TextureContainer container = new TextureContainer();
		container.width = img.getWidth();
		container.height = img.getHeight();
		container.data = new BufferedImage[1];
		container.data[0] = img;
		
		containers[id.ordinal()] = container;
	}
	
	public void initAnimation(String path, BlockId id, int amount) {
		TextureContainer container = new TextureContainer();
		
		container.data = new BufferedImage[amount];
		
		for(int i = 0; i < amount; i++) {
			BufferedImage img = loadImage(path + i);
			container.data[i] = img;
		}
		
		container.width = container.data[0].getWidth();
		container.height = container.data[0].getHeight();
		
		
		containers[id.ordinal()] = container;
	}
	
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
		initTexture("wood", BlockId.Wood);
		initTexture("sand", BlockId.Sand);
		initAnimation("water", BlockId.Water, 9);
	}
	
	
	
}
