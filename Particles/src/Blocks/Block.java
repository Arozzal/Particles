package Blocks;
import java.awt.Color;
import Game.*;

public abstract class Block {
	private Color color = Color.black;
	private long lastUpdated = 0;
	private long lifeTime = 0;
	private float density = 0;
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
	
	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}
	
	
	Block(Color color, long lastUpdated, long lifeTime, int x, int y, float density){
		this.x = x;
		this.y = y;
		setDensity(density);
		
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
	
	protected void checkForDensitySwap(int x1, int y1, int x2, int y2, Grid grid) {
		float densityDiff = grid.get(x1, y1).getDensity() - grid.get(x2, y2).getDensity();
		if(densityDiff > 0) {
			
			int rollVal = 500 / (int)densityDiff;
			if(Game.roll(rollVal)) {
				grid.swap(x1, y1, x2, y2);
			}
		}
	}
	
	/**
	 * 
	 * @return The BlockId of this Block
	 */
	public abstract BlockId getId();
	public abstract boolean isSolid();
	public abstract boolean isFlammable();
	public abstract boolean isLiquid();
	
	public abstract void update(int x, int y, Grid grid);

	
}