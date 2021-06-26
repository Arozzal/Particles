package Blocks;

import java.awt.Color;

import Game.Game;
import Game.Grid;
import Textures.TextureLoader;

public class BlockSteam extends Block{

	public BlockSteam(Color color, long lastUpdated, long lifeTime, int x, int y) {
		super(color, lastUpdated, lifeTime, x, y, -20f);
	}

	@Override
	protected long generateNewLifetime() {
		return Game.getRandomInt(1200, 3000);
	}
	
	@Override
	protected Color generateNewColor() {
		return new Color(Game.getRandomInt(230, 255), Game.getRandomInt(230, 255), Game.getRandomInt(230, 255));
	}
	
	@Override
	public BlockId getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isFlammable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLiquid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(int x, int y, Grid grid) {
		
		if(getLifeTime() < 10) {
			grid.generateNewBlock(BlockId.Water, x, y);
			return;
		}
			
		if (grid.get(x, y - 1).isSolid() == false)
		{
			grid.move(x, y, x, y - 1);
			return;
		}

		if (grid.get(x + 1, y - 1).isSolid() == false)
		{
			grid.move(x, y, x + 1, y - 1);
			return;
		}

		if (grid.get(x - 1, y - 1).isSolid() == false)
		{
			grid.move(x, y, x - 1, y - 1);
			return;
		}
		
		if(grid.get(x, y-1).isLiquid()) {
    		checkForDensitySwap(x, y - 1, x, y,  grid);
    	}
    	
    	if(grid.get(x + 1, y - 1).isLiquid()) {
    		checkForDensitySwap(x + 1, y - 1, x, y, grid);
    	}
    	
    	if(grid.get(x - 1, y - 1).isLiquid()) {
    		checkForDensitySwap(x - 1, y - 1, x, y,  grid);
    	}
		
	}

}
