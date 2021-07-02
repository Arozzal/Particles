package Blocks;
import java.awt.Color;

import Game.Game;
import Game.Grid;

public class BlockFire extends Block{
	public BlockFire(Color color, long lastUpdated, long lifeTime, int x, int y){
		super(color, lastUpdated, lifeTime, x, y, 0.01f);
	}
	
	
	@Override
	protected Color generateNewColor() {
		return new Color(255, Game.getRandomInt(58, 120), 0);
	}	
	

	@Override
	public BlockId getId() {
		return BlockId.Fire;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
	
	@Override
	public boolean isFlammable() {
		return false;
	}

	@Override
	protected long generateNewLifetime() {
		return Game.getRandomInt(600, 800);
	}
	
	@Override
	public void update(int x, int y) {
		if (Game.roll(100)){
			return;
		}

		if(Game.roll(10)){
			if(grid.get(x, y + 1).getId() == BlockId.None)
				grid.generateNewBlock(BlockId.Smoke, x, y + 1);
			return;
		}

		int dir = Game.getRandomInt(0, 5);

		switch (dir){
			case 0:
				if (grid.get(x, y + 1).isSolid() == false && !grid.get(x, y + 1).isId(BlockId.Fire)){
					grid.move(x, y, x, y + 1);
					return;
				}
				break;
			
			case 1:
				if (grid.get(x + 1, y).isSolid() == false && !grid.get(x + 1, y).isId(BlockId.Fire)){
					grid.move(x, y, x + 1, y);
					return;
				}
				break;
			case 2:
				if (grid.get(x - 1, y).isSolid() == false && !grid.get(x - 1, y).isId(BlockId.Fire)){
					grid.move(x, y, x - 1, y);
					return;
				}
				break;
			default:
				if (grid.get(x, y - 1).isSolid() == false && !grid.get(x, y - 1).isId(BlockId.Fire)){
					grid.move(x, y, x, y - 1);
					return;
				}
				break;
		}

		burn(x, y, 1,  0, grid);
		burn(x, y, 0,  1, grid);
		burn(x, y, -1, 0, grid);
		burn(x, y, 0, -1, grid);
	}
	
	private void burn(int x, int y, int xoffset, int yoffset, Grid grid)
	{
		if (Game.roll(12))
		{
			if (grid.get(x + xoffset, y + yoffset).isFlammable())
			{
				grid.generateNewBlock(BlockId.Fire, x + xoffset, y + yoffset);
			}
		}
	}
	
	@Override
	public boolean isLiquid() {
		return false;
	}
}
