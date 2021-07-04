package Blocks;
import java.awt.Color;

import Game.Game;

public class BlockBlackhole extends Block{
	public BlockBlackhole(Color color, long lastUpdated, long lifeTime, int x, int y){
		super(color, lastUpdated, lifeTime, x, y, 15.0f);
		grid = Game.game.grid;
	}
	
	
	@Override
	protected Color generateNewColor() {
		int col = Game.getRandomInt(0, 25);
		return new Color(col, col, col);
	}	

	@Override
	public BlockId getId() {
		return BlockId.Blackhole;
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
		setColor(generateNewColor());
		
		absorb(-1, 0);
		absorb( 1, 0);
		absorb( 0, 1);
		absorb( 0, -1);
	}
	
	private void absorb(int xoff, int yoff) {
		if(!grid.get(x + xoff, y + yoff).isId(BlockId.Blackhole, BlockId.None, BlockId.OutofLevel)){
			if(Game.roll(4))
				grid.generateNewBlock(BlockId.Fire, x + xoff, y + yoff);
		}
		
		if(!grid.get(x + xoff, y+ yoff).isSolid()) {
			grid.generateNewBlock(BlockId.Fire, x + xoff, y + yoff);
		}
	}
	
	@Override
	public boolean isLiquid() {
		return false;
	}
}
