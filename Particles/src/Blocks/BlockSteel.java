package Blocks;
import java.awt.Color;

import Game.Game;
import Textures.TextureLoader;

public class BlockSteel extends Block{
	public BlockSteel(Color color, long lastUpdated, long lifeTime, int x, int y){
		super(color, lastUpdated, lifeTime, x, y, 15.0f);
		grid = Game.game.grid;
	}
	
	
	@Override
	protected Color generateNewColor() {
		return TextureLoader.loader.getPixel(BlockId.Steel, x, y, 0);
	}	

	@Override
	public BlockId getId() {
		return BlockId.Wood;
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
	@Override
	public boolean isFlammable() {
		return false;
	}

	@Override
	public void update(int x, int y) {
		
	}
	
	@Override
	public boolean isLiquid() {
		return false;
	}
}
