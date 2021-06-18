import java.awt.Color;

public class BlockSmoke extends Block{

	public BlockSmoke(Color color, long lastUpdated, long lifeTime){
		super(color, lastUpdated, lifeTime);
	}
	
	@Override
	protected Color generateNewColor() {
		float col = (float)Game.getRandomInt(50, 100) / 255.0f;

		return new Color(col, col, col);
	}
	
	@Override
	public BlockId getId() {
		return BlockId.Smoke;
	}
	
	@Override
	protected long generateNewLifetime() {
		return Game.getRandomInt(400, 800);
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
	public void update(int x, int y, Grid grid) {
		if (Game.getRandomInt(0, 100) == 1)
		{
			return;
		}

		if (Game.getRandomInt(0, 5) != 1)
		{
			return;
		}

		if (grid.get(x, y - 1).isSolid() == false && grid.get(x, y - 1).getId() != BlockId.Smoke)
		{
			grid.move(x, y, x, y - 1);
			return;
		}

		if (grid.get(x + 1, y - 1).isSolid() == false && grid.get(x + 1, y - 1).getId() != BlockId.Smoke)
		{
			grid.move(x, y, x + 1, y - 1);
			return;
		}

		if (grid.get(x - 1, y - 1).isSolid() == false && grid.get(x - 1, y - 1).getId() != BlockId.Smoke)
		{
			grid.move(x, y, x - 1, y - 1);
			return;
		}
	}

}