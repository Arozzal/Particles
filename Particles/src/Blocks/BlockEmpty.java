package Blocks;

import Game.*;
import Textures.TextureLoader;

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
	public void update(int x, int y) {
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
