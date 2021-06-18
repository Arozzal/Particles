import java.awt.Color;

public abstract class Block {
	protected Color color = Color.black;
	protected long lastUpdated = 0;
	protected long lifeTime = 0;
	
	Block(){
		
	}
	
	Block(Color color, long lastUpdated, long lifeTime){
		if(color == null) {
			this.color = generateNewColor();
		}else {
			this.color = color;
		}
		
		if(lifeTime < 0)
			this.lifeTime = generateNewLifetime();
		else
			this.lifeTime = lifeTime;
		
		this.lastUpdated = lastUpdated;
	}
	
	protected Color generateNewColor() {
		return Color.black;
	}
	
	protected long generateNewLifetime() {
		return 1000000;
	}
	
	public abstract BlockId getId();
	public abstract boolean isSolid();
	public abstract boolean isFlammable();
	public abstract void update(int x, int y, Grid grid);
}