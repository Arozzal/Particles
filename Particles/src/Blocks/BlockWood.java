package Blocks;
import java.awt.Color;

import Game.Game;
import Game.Grid;
import Textures.TextureLoader;

public class BlockWood extends Block{
	public BlockWood(Color color, long lastUpdated, long lifeTime, int x, int y){
		super(color, lastUpdated, lifeTime, x, y, 15.0f);
		grid = Game.game.grid;
	}
	
	
	@Override
	protected Color generateNewColor() {
		//return new Color(Game.getRandomInt(70, 100), 51, 0);
		return TextureLoader.loader.getPixel(BlockId.Wood, x, y, 0);
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
		return true;
	}

	@Override
	public void update(int x, int y) {
		
	}
	
	@Override
	public boolean isLiquid() {
		return false;
	}
}
