package Blocks;
import java.awt.Color;


public class BlockOutOfLevel extends Block{
	
	
	
	public BlockOutOfLevel(long lastUpdated, long lifeTime, int x, int y){
		super(Color.black, lastUpdated, lifeTime, x, y, 100000000);
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
	public void update(int x, int y) {
		
	}
	
	@Override
	public boolean isLiquid() {
		return false;
	}
}
