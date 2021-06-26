package Blocks;
import java.awt.Color;

import Game.*;
import Textures.TextureLoader;
import Textures.TextureLoader.TextureContainer;

public class BlockEmpty extends Block{
	public BlockEmpty(long lastUpdated, int x, int y){
		super(TextureLoader.loader.getPixel(BlockId.None, x, y, 0), lastUpdated, 1000000, x, y, 0);
	}
	
	@Override
	public BlockId getId() {
		return BlockId.None;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public void update(int x, int y, Grid grid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFlammable() {
		return false;
	}
	
	@Override
	public boolean isLiquid() {
		return false;
	}
}
