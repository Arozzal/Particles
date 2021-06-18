import java.awt.Color;

public class BlockWater extends Block{
	BlockWater(Color color, long lastUpdated, long lifeTime){
		super(color, lastUpdated, lifeTime);
	}
	
	@Override
	protected Color generateNewColor() {
		return new Color(24,(int) (Math.random() * (220 - 200)) + 200, 226);
	}
	
	@Override
	public BlockId getId() {
		return BlockId.Water;
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
    public void update(int x, int y, Grid grid){
		if (Game.getRandomInt(0, 100) == 1){
			return;
		}

		if (grid.get(x, y + 1).isSolid() == false){
			grid.move(x, y, x, y + 1);
			return;
		}

		if (grid.get(x + 1, y + 1).isSolid() == false){
			grid.move(x, y, x + 1, y + 1);
			return;
		}

		if (grid.get(x - 1, y + 1).isSolid() == false){
			grid.move(x, y, x - 1, y + 1);
			return;
		}


	
		if (waterMovement(x, y, 1, grid)){
			return;
		}
		
		if (waterMovement(x, y, -1, grid)){
			return;
		}
		

	}

	boolean waterMovement(int x, int y, int xoffset, Grid grid){
		if (grid.get(x + xoffset, y).isSolid()){
			return false;
		}

		for (int i = 0; i < 80; i++){
			if (grid.get(x, y + 1).isSolid() == false){
				grid.move(x, y, x, y + 1);
				y++;
			}
			else if (grid.get(x + xoffset, y).isSolid() == false){
				grid.move(x, y, x + xoffset, y);
				x += xoffset;
			}
			else{
				return true;
			}
		}
		return true;
	
	}
	
}
