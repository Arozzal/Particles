import java.awt.Color;

public class BlockFire extends Block{
	BlockFire(Color color, long lastUpdated, long lifeTime){
		super(color, lastUpdated, lifeTime);
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
		return Game.getRandomInt(200, 400);
	}
	
	@Override
	public void update(int x, int y, Grid grid) {
		if (Game.getRandomInt(0, 100) == 1)
		{
			return;
		}

		if(Game.getRandomInt(0, 10) == 0)
		{
			if(grid.get(x, y + 1).getId() == BlockId.None)
			grid.setBlock(BlockId.Smoke, x, y + 1);
			return;
		}

		int dir = Game.getRandomInt(0, 5);

		switch (dir)
		{
			case 0:
				if (grid.get(x, y + 1).isSolid() == false && grid.get(x, y + 1).getId() != BlockId.Fire)
				{
					grid.move(x, y, x, y + 1);
					return;
				}
				break;
			
			case 1:
				if (grid.get(x + 1, y).isSolid() == false && grid.get(x + 1, y).getId() != BlockId.Fire)
				{
					grid.move(x, y, x + 1, y);
					return;
				}
				break;
			case 2:
				if (grid.get(x - 1, y).isSolid() == false && grid.get(x - 1, y).getId() != BlockId.Fire)
				{
					grid.move(x, y, x - 1, y);
					return;
				}
				break;
			default:
				if (grid.get(x, y - 1).isSolid() == false && grid.get(x, y - 1).getId() != BlockId.Fire)
				{
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
		if (Game.getRandomInt(0, 20) == 0)
		{
			if (grid.get(x + xoffset, y + yoffset).isFlammable())
			{
				grid.setBlock(BlockId.Fire, x + xoffset, y + yoffset);
			}
		}
	}

	
	
}
