package Blocks;
import java.awt.Color;

import Game.*;

public class BlockEmpty extends Block{
	public BlockEmpty(long lastUpdated, int x, int y){
		super(Color.black, lastUpdated, 1000000, x, y);
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
