package Game;
import Blocks.*;

public class Grid {
	
	Block[] levelGrid;
	final Block outOfLevelBlock;
	
	int sizex;
	int sizey;
	int blocksize;
	
	
	Grid(int sizex, int sizey, int blocksize){
		outOfLevelBlock = new BlockOutOfLevel(0, 0, 0, 0);
		levelGrid = new Block[sizex * sizey];
		this.sizex = sizex;
		this.sizey = sizey;
		this.blocksize = blocksize;
		for(int y = 0; y < sizey; y++) {
			for(int x = 0; x < sizex; x++) {
				levelGrid[y * sizex + x] = new BlockEmpty(0, x, y);
				//levelGrid[y * sizex + x] = new BlockWood(null, -1, 100000000);
			}
		}
		
	}
	
	public Block get(int x, int y) {
		if(x < 0 || y < 0 || x >= sizex || y >= sizey) {
			return outOfLevelBlock;
		}
		
		return levelGrid[y * sizex + x];
	}
	
	void set(Block block, int x, int y) {
		if(x < 0 || y < 0 || x >= sizex || y >= sizey) {
			return;
		}
		
		levelGrid[y * sizex + x] = block;
	}
	
	public void move(int x1, int y1, int x2, int y2) {	
		set(get(x1, y1), x2, y2);
		setBlock(BlockId.None, x1, y1);
	}
	
	public void swap(int x1, int y1, int x2, int y2) {
		Block bk1 = get(x1, y1);
		Block bk2 = get(x2, y2);
		

		
		set(bk2, x1, y1);
		set(bk1, x2, y2);
	}
	
	public void setBlock(BlockId id, int x, int y) {
		Block bk = get(x, y);
		
		switch(id) {
		case Sand: set(new BlockSand(null, bk.getLastUpdated(), -1, x, y), x, y); break;
		case Water: set(new BlockWater(null, bk.getLastUpdated(), -1, x, y), x, y); break;
		case Wood: set(new BlockWood(null, bk.getLastUpdated(), -1, x, y), x, y); break;
		case Fire: set(new BlockFire(null, bk.getLastUpdated(), -1, x, y), x, y); break;
		case Smoke: set(new BlockSmoke(null, bk.getLastUpdated(), -1, x, y), x, y); break;
		case Corruption: set(new BlockCorruption(null, bk.getLastUpdated(), -1, x, y), x, y); break;
		case None: set(new BlockEmpty(bk.getLastUpdated(), x, y), x, y); break;
		default: System.err.print("Unknown Block Id " + id.toString()); break;
		}
	}
}
