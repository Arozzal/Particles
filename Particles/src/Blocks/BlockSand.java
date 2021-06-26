package Blocks;
import java.awt.Color;

import Game.Game;
import Game.Grid;
import Textures.TextureLoader;

public class BlockSand extends Block{

	
	
	
	public BlockSand(Color color, long lastUpdated, long lifeTime, int x, int y){
		super(color, lastUpdated, lifeTime, x, y, 21.0f);
	}
	
	@Override
	protected Color generateNewColor() {
		//return new Color(229, 228, (int) (Math.random() * (255 - 1)) + 1);
		return TextureLoader.loader.getPixel(BlockId.Sand, x, y, 0);
	}
	
	@Override
	public BlockId getId() {
		return BlockId.Sand;
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
	public void update(int x, int y, Grid grid) {
		if(Game.roll(100)) {
    		return;
    	}
    	
    	if(grid.get(x, y+1).isSolid() == false) {
    		grid.move(x, y, x, y + 1);
    		return;
    	}
    	
    	if(grid.get(x - 1, y+1).isSolid() == false) {
    		grid.move(x, y, x - 1, y + 1);
    		return;
    	}
    	
    	if(grid.get(x + 1, y+1).isSolid() == false) {
    		grid.move(x, y, x + 1, y + 1);
    		return;
    	}
    	
    	
    	if(grid.get(x, y+1).isLiquid()) {
    		checkForDensitySwap(x, y, x, y + 1, grid);
    	}
    	
    	if(grid.get(x + 1, y+1).isLiquid()) {
    		checkForDensitySwap(x, y, x + 1, y + 1, grid);
    	}
    	
    	if(grid.get(x - 1, y+1).isLiquid()) {
    		checkForDensitySwap(x, y, x - 1, y + 1, grid);
    	}

	}
	
	@Override
	public boolean isLiquid() {
		return false;
	}

}
