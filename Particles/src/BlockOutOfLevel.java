import java.awt.Color;

public class BlockOutOfLevel extends Block{
	
	
	
	BlockOutOfLevel(long lastUpdated, long lifeTime){
		super(Color.white, lastUpdated, lifeTime);
	}
	
	@Override
	public BlockId getId() {
		return BlockId.OutofLevel;
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
		
	}
}
