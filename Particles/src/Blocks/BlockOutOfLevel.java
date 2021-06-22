package Blocks;
import java.awt.Color;

import Game.*;

public class BlockOutOfLevel extends Block{
	
	
	
	public BlockOutOfLevel(long lastUpdated, long lifeTime, int x, int y){
		super(Color.white, lastUpdated, lifeTime, x, y);
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
