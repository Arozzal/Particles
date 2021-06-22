package Blocks;
import java.awt.Color;

import Game.Game;
import Game.Grid;
import Textures.TextureLoader;

public class BlockWater extends Block{
	public BlockWater(Color color, long lastUpdated, long lifeTime, int x, int y){
		super(color, lastUpdated, lifeTime, x, y);
	}
	
	@Override
	protected Color generateNewColor() {
		return TextureLoader.loader.getPixel(BlockId.Water, x, y, 1);
		//return new Cdsdsolor(24,(int) (Math.random() * (220 - 200)) + 200, 226);
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
		if (Game.roll(100)){
			return;
		}
		
		this.x = x;
		this.y = y;
		setColor(generateNewColor());
		
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
