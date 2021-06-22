package Blocks;
import java.awt.Color;
import Game.*;

public class BlockCorruption extends Block{
	
	int doingChance;
	
	public BlockCorruption(Color color, long lastUpdated, long lifeTime, int x, int y){
		super(color, lastUpdated, lifeTime, x, y);
		
		doingChance = Game.getRandomInt(5, 30);
	}
	
	
	@Override
	protected Color generateNewColor() {
		return new Color(133, 33, Game.getRandomInt(128, 255));
	}	
	

	@Override
	public BlockId getId() {
		return BlockId.Corruption;
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
		return 100;
	}


	@Override
	public void update(int x, int y, Grid grid) {
		
		if(grid.get(x, y + 1).isSolid() == false) {
			if(Game.roll(200)) {
				grid.move(x, y, x, y + 1);
			}
		}
		
		if(!Game.roll(doingChance)) {
			return;
		}
		
		for(int xx = -1; xx < 2; xx++) {
			for(int yy = -1; yy < 2; yy++) {
				
				Block block = grid.get(x + xx, y + yy);
				int neighbourCount = getNeighbourCount(x + xx, y + yy, grid);
				
				
				if(block.getId() == BlockId.Corruption) {
					if(neighbourCount > 3 || neighbourCount < 2) {
						block.setLifeTime(0);
						if(xx == 0 && yy == 0)
							break;
					}
				}	
				
				if(neighbourCount == 3) {
					//Block overwriteBlock = grid.get(x + xx, y + yy);
					
					grid.setBlock(BlockId.Corruption, x + xx, y + yy);
					//grid.get(x + xx, y + yy).setLifeTime(lifeTime);
				}		
			}	
		}
	}
	
	public int getNeighbourCount(int x, int y, Grid grid) {
		int neighbourCount = 0;
		for(int xx = -1; xx < 2; xx++) {
			for(int yy = -1; yy < 2; yy++) {
				if(xx == 0 && y == 0)
					continue;
				
				if(grid.get(x + xx, y + yy).getId() == BlockId.Corruption) {
					neighbourCount++;
				}
			}
		}
		
		return neighbourCount;
	}
}
