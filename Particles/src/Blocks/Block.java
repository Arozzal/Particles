package Blocks;
import java.awt.Color;
import Game.*;

public abstract class Block {
	protected Color color = Color.black;
	protected long lastUpdated = 0;
	protected long lifeTime = 0;
	protected int x;
	protected int y;
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}

	Block(){
		
	}
	
	Block(Color color, long lastUpdated, long lifeTime, int x, int y){
		this.x = x;
		this.y = y;
		
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
	
	public boolean isId(BlockId id) {
		if(getId() == id)
			return true;
		return false;
	}
	
	public abstract BlockId getId();
	public abstract boolean isSolid();
	public abstract boolean isFlammable();
	public abstract void update(int x, int y, Grid grid);
}