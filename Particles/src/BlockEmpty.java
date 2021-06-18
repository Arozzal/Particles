import java.awt.Color;

public class BlockEmpty extends Block{
	BlockEmpty(long lastUpdated){
		super(Color.black, lastUpdated, 100000000);
	}
	
	@Override
	public BlockId getId() {
		return BlockId.None;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public void update(int x, int y, Grid grid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFlammable() {
		return false;
	}
}
