package Blocks;

import java.awt.Color;

import Game.Game;
import Game.Grid;
import Textures.TextureLoader;

public class BlockLava extends Block{

	
	
	public BlockLava(Color color, long lastUpdated, long lifeTime, int x, int y){
		super(color, lastUpdated, lifeTime, x, y, 20.0f);
		grid = Game.game.grid;
	}
	
	@Override
	protected Color generateNewColor() {
		return TextureLoader.loader.getPixel(BlockId.Lava, x, y, (int)(Game.getMillis() / 100 % 15));
	}
	
	@Override
	public BlockId getId() {
		return BlockId.Lava;
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
    public void update(int x, int y){
		
		
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
		
		if (!Game.roll(20)){
			return;
		}

		
		if (waterMovement(x, y, 1, grid)){
			return;
		}
		
		if (waterMovement(x, y, -1, grid)){
			return;
		}
		
		evaporateWater(x, y, 0, -1, grid);
		evaporateWater(x, y, 1, 0, grid);
		evaporateWater(x, y, -1, 0, grid);
		evaporateWater(x, y, 0, 1, grid);
		
		burn(x, y, 0, -1, grid);
		burn(x, y, 1, 0, grid);
		burn(x, y, -1, 0, grid);
		burn(x, y, 0, 1, grid);
		
		if(!Game.roll(250)) {
			return;
		}
		
		setColor(generateNewColor());

	}
	
	private void burn(int x, int y, int xoffset, int yoffset, Grid grid){
		if (Game.roll(12)){
			if (grid.get(x + xoffset, y + yoffset).isFlammable()){
				grid.generateNewBlock(BlockId.Fire, x + xoffset, y + yoffset);
			}
		}
	}
	
	void evaporateWater(int x, int y, int offx, int offy, Grid grid) {
		if(grid.get(x + offx, y + offy).isId(BlockId.Water)) {
			grid.generateNewBlock(BlockId.Steam, x + offx, y + offy);
			if(Game.roll(5)) {
				grid.generateNewBlock(BlockId.Wood, x, y);
			}
			
		}
	}

	boolean waterMovement(int x, int y, int xoffset, Grid grid){
		if (grid.get(x + xoffset, y).isSolid()){
			return false;
		}

		for (int i = 0; i < 1; i++){
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
	
	@Override
	public boolean isLiquid() {
		return true;
	}

}
