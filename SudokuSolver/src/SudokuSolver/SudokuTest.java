package SudokuSolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

public class SudokuTest {

    @Test
    public void testSudoku() {
	Sudoku s = new Sudoku();
	Set<Integer> c = s.getCell(0, 0);
	assertTrue(c.contains(1));
	assertTrue(c.contains(2));
	assertTrue(c.contains(3));
	assertTrue(c.contains(4));
	assertTrue(c.contains(5));
	assertTrue(c.contains(6));
	assertTrue(c.contains(7));
	assertTrue(c.contains(8));
	assertTrue(c.contains(9));
	assertTrue(!c.contains(10));
	assertTrue(!c.contains(0));
	assertTrue(s.getCell(8, 8).contains(3));
	assertTrue(!s.getCell(8, 8).contains(10));
    }

    @Test
    public void testSudokuString() {
	Sudoku s = new Sudoku(
		"534678912" + 
		"672195348" + 
		"198342567" +
		"859761423" + 
		"426853791" + 
		"713924856" + 
		"961537284" + 
		"287419635" + 
		"345286179");
	for (int i = 0; i < Sudoku.SIZE; i++) {
	    for (int j = 0; j < Sudoku.SIZE; j++) {
		assertEquals(1, s.getCell(i, j).size());
	    }
	}
	assertEquals(0, s.getCellsLeft());
    }
    
    @Test
    public void testSudokuString2() {
	Sudoku s = new Sudoku(
		"534678912" + 
		"672195348" + 
		"198342567" +
		"859761423" + 
		"426853791" + 
		"713924856" + 
		"961537284" + 
		"287419635" + 
		"000000000");
	for (int i = 0; i < Sudoku.SIZE; i++) {
	    for (int j = 0; j < Sudoku.SIZE; j++) {
		assertEquals(1, s.getCell(i, j).size());
	    }
	}
	assertEquals(0, s.getCellsLeft());
    }
    @Test
    public void testGetValue() {
	Sudoku s = new Sudoku(
		"534678912" + 
		"672195348" + 
		"198342567" +
		"859761423" + 
		"426853791" + 
		"713924856" + 
		"961537284" + 
		"287419635" + 
		"000000000");
	// 345286179
	assertEquals(3, s.getValue(0, 8));
	assertEquals(9, s.getValue(8, 8));
    }
    @Test
    public void testSetRemove() {
	Sudoku s = new Sudoku();
	s.setNumber(0, 0, 5);
	assertTrue("box test", !s.getCell(2, 2).contains(5));
	assertTrue("column test", !s.getCell(0, 5).contains(5));
	assertTrue("row test", !s.getCell(5, 0).contains(5));
	assertTrue(s.getCell(1, 3).contains(5));
	assertTrue(s.getCell(3, 1).contains(5));
    }
    
    @Test
    public void testSetNumber() {
	Sudoku s = new Sudoku();
	s.setNumber(0, 0, 5);
	assertEquals(1,s.getCell(0, 0).size());
    }
    /*
    @Test(expected=IllegalArgumentException.class)
    public void testSetAlreadySet() {
	Sudoku s = new Sudoku();
	s.setNumber(0, 0, 5);
	s.setNumber(0, 0, 5);
    }
    */
    @Test
    public void testCellsLeft() {
	Sudoku s = new Sudoku();
	assertEquals(9*9, s.getCellsLeft());
	for (int j = 0; j < 8; j++) {
	    s.setNumber(0, j, j+1);
	}
	assertEquals(9*8, s.getCellsLeft());
	
    }
    @Test(expected=IllegalArgumentException.class)
    public void testSetInvalid() {
	Sudoku s = new Sudoku();
	s.setNumber(4, 4, 5);
	s.setNumber(3, 3, 5);
    }
}
