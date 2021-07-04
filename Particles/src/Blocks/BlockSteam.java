package Blocks;

import java.awt.Color;

import Game.Game;

public class BlockSteam extends Block{

	public BlockSteam(Color color, long lastUpdated, long lifeTime, int x, int y) {
		super(color, lastUpdated, lifeTime, x, y, -20f);
		grid = Game.game.grid;
	}

	@Override
	protected long generateNewLifetime() {
		return Game.getRandomInt(1200, 10000);
	}
	
	@Override
	protected Color generateNewColor() {
		return new Color(Game.getRandomInt(230, 255), Game.getRandomInt(230, 255), Game.getRandomInt(230, 255));
	}
	
	@Override
	public BlockId getId() {
		return BlockId.Steam;
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
	public boolean isLiquid() {
		return false;
	}

	@Override
	public void update(int x, int y) {
		
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
