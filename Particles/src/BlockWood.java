import java.awt.Color;

public class BlockWood extends Block{
	BlockWood(Color color, long lastUpdated, long lifeTime){
		super(color, lastUpdated, lifeTime);
	}
	
	
	@Override
	protected Color generateNewColor() {
		return new Color(Game.getRandomInt(70, 100), 51, 0);
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
	public void update(int x, int y, Grid grid) {
		// TODO Auto-generated method stub
		
	}
}
