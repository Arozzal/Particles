package UnitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Game.*;
import Blocks.*;

class GridTest {
	
	
	@Test
	public void testGridSetAndGetMethods() {
		Grid grid = new Grid(5, 5);
		grid.generateNewBlock(BlockId.Lava, 2, 2);
		Block block = grid.get(2, 2);
		
		assertEquals(BlockId.Lava, block.getId());
	}
	
	@Test
	public void testGenerateNewBlocksMethod() {
		
		Grid grid = new Grid(5, 5);
		
		BlockId[] blockIdValues = BlockId.None.getDeclaringClass().getEnumConstants();
		for(BlockId blockId : blockIdValues) {
			grid.generateNewBlock(blockId, 0, 0);
			if(blockId == BlockId.OutofLevel) {
				assertNotEquals(grid.get(0, 0).getId(), blockId);
			}else {	
				assertEquals(grid.get(0, 0).getId(), blockId);
			}
		}
	}
	
	@Test
	public void testAccessOutOfBounds() {
		Grid grid = new Grid(5, 5);
		
		assertEquals(grid.get(-1, 0).getId(), BlockId.OutofLevel);
		assertEquals(grid.get(0, -1).getId(), BlockId.OutofLevel);
		assertEquals(grid.get(0, 5).getId(), BlockId.OutofLevel);
		assertEquals(grid.get(5, 0).getId(), BlockId.OutofLevel);
	}
	
	@Test
	public void testMoveMethod() {
		Grid grid = new Grid(5, 5);
		
		grid.generateNewBlock(BlockId.Lava, 1, 1);
		grid.generateNewBlock(BlockId.Water, 1, 2);
		
		grid.move(1, 1, 1, 2);
		
		assertEquals(grid.get(1, 1).getId(), BlockId.None);
		assertEquals(grid.get(1, 2).getId(), BlockId.Lava);
	}
	
	@Test
	public void testSwapMethod() {
		Grid grid = new Grid(5, 5);
		grid.generateNewBlock(BlockId.Lava, 1, 1);
		grid.generateNewBlock(BlockId.Fire, 2, 2);
		
		grid.swap(1, 1, 2, 2);
		
		assertEquals(grid.get(1, 1).getId(), BlockId.Fire);
		assertEquals(grid.get(2, 2).getId(), BlockId.Lava);
	}
	
	
}
