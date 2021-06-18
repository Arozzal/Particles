import java.awt.Color;

public class BlockSand extends Block{

	
	
	
	BlockSand(Color color, long lastUpdated, long lifeTime){
		super(color, lastUpdated, lifeTime);
	}
	
	@Override
	protected Color generateNewColor() {
		return new Color(229, 228, (int) (Math.random() * (255 - 1)) + 1);
	}
	
	@Override
	public BlockId getId() {
		return BlockId.Sand;
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
		if(Game.getRandomInt(0, 100) == 1) {
    		return;
    	}
    	
    	if(grid.get(x, y+1).isSolid() == false) {
    		grid.move(x, y, x, y + 1);
    		return;
    	}
    	
    	if(grid.get(x - 1, y+1).isSolid() == false) {
    		grid.move(x, y, x - 1, y + 1);
    		return;
    	}
    	
    	if(grid.get(x + 1, y+1).isSolid() == false) {
    		grid.move(x, y, x + 1, y + 1);
    		return;
    	}
    	
    	if(Game.getRandomInt(0, 15) != 1) {
    		return;
    	}
    	
    	
    	if(grid.get(x, y+1).getId() == BlockId.Water) {
    		grid.swap(x, y, x, y + 1);
    		return;
    	}
    	
    	if(grid.get(x - 1, y+1).getId() == BlockId.Water) {
    		grid.swap(x, y, x - 1, y + 1);
    		return;
    	}
    	
    	if(grid.get(x + 1, y+1).getId() == BlockId.Water) {
    		grid.swap(x, y, x + 1, y + 1);
    		return;
    	}
	}
	
}
