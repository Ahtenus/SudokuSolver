package SudokuSolver;

import java.util.HashSet;
import java.util.Set;

public class Sudoku {
    public static final int SIZE = 9;
    public static final int BOX_SIZE = 3;
    @SuppressWarnings("unchecked")
    private Set<Integer>[][] sudoku = new Set[SIZE][SIZE];
    private int cellsLeft;
    
    public Sudoku() {
	cellsLeft = SIZE*SIZE;
	for (int i = 0; i < SIZE; i++) {
	    for (int j = 0; j < SIZE; j++) {
		sudoku[i][j] = new HashSet<Integer>(SIZE * 2);
		for (int k = 1; k < SIZE+1; k++) {
		    sudoku[i][j].add(k);
		}
		
	    }
	}
    }
    public Sudoku(String s) {
	this();
	System.out.println("he");
	for (int i = 0; i < SIZE*SIZE; i++) {
	    	int v = Character.getNumericValue(s.charAt(i));
	    	if(v > 0 && v < 10) {
	    	    int x = i%SIZE;
	    	    int y = i/SIZE;
	    	//System.out.println("x = "+ x +" y = "+y + " v = "+v + " cellsLeft = " + cellsLeft);
	    	    setNumber(x,y, v);
	    	}
	}
    }
    public void setNumber(int x, int y, int num) {
	if(!sudoku[x][y].contains(num))
	    throw new IllegalArgumentException("The number is used somewhere else in the box/row/column.");
	/*
	if(sudoku[x][y].size() == 1 && !sudoku[x][y].contains(num))
	    throw new IllegalArgumentException("The cell already have a different number set");
	    */
	if(sudoku[x][y].size() > 1) {
	    cellsLeft--;
	}
	sudoku[x][y].clear();
	removeFromColumn(x, num);
	removeFromRow(y, num);
	removeFromBox(x, y, num);
	sudoku[x][y].add(num);
    }
    private void removeFromRow(int y, int num) {
	for (int i = 0; i < 9; i++) {
	    remove(i, y, num);
	}
    }
    private void removeFromColumn(int x, int num) {
	for (int i = 0; i < 9; i++) {
	    remove(x, i, num);
	}
    }
    
    private void removeFromBox(int x, int y, int num) {
	x = (x/BOX_SIZE)*BOX_SIZE;
	y = (y/BOX_SIZE)*BOX_SIZE;
	
	for (int i = 0; i < BOX_SIZE; i++) {
	    for (int j = 0; j < BOX_SIZE; j++) {
		remove(x+i,y+j,num);
	    }
	    
	}
    }
    public int getValue(int x, int y) {
	Set<Integer> c = getCell(x, y);
	if(c.size() == 1)
	    return (int) c.toArray()[0];
	return 0;
    }
    private void remove(int x, int y, int num) {
	if (sudoku[x][y].remove(num)) { // Contained the element
	    if (sudoku[x][y].size() == 1) {
		cellsLeft--;
	    }
	}
    }
    public Set<Integer> getCell(int x, int y) {
	return sudoku[x][y];
    }
    /**
     * @return the number of cells with more than one possible value
     */
    public int getCellsLeft() {
	return cellsLeft;
    }
    @Override
    public String toString() {
	String s = "";
        for (int i = 0; i < SIZE; i++) {
	    for (int j = 0; j < SIZE; j++) {
		s += ""+getValue(j, i);
	    }
	    s += "\n";
	}
        return s;
    }
}