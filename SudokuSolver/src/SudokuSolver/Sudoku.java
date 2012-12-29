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
    
    public Sudoku(Sudoku s) {
	for (int i = 0; i < SIZE; i++) {
	    for (int j = 0; j < SIZE; j++) {
		sudoku[i][j] = new HashSet<Integer>(s.getCell(i, j));
	    }
	}
	cellsLeft = s.getCellsLeft();
    }
    public boolean setNumber(int x, int y, int num) {
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
	boolean valid = removeFromColumn(x, num)  && removeFromRow(y, num) && removeFromBox(x, y, num);
	sudoku[x][y].add(num);
	return valid;
    }
    private boolean removeFromRow(int y, int num) {
	boolean valid = true;
	for (int i = 0; i < 9; i++) {
	    if(!remove(i, y, num)) {
		valid = false;
	    }
	}
	return valid;
    }
    private boolean removeFromColumn(int x, int num) {
	boolean valid = true;
	for (int i = 0; i < 9; i++) {
	    if(!remove(x, i, num)) {
		valid = false;
	    }
	}
	return valid;
    }
    
    private boolean removeFromBox(int x, int y, int num) {
	x = (x/BOX_SIZE)*BOX_SIZE;
	y = (y/BOX_SIZE)*BOX_SIZE;
	boolean valid = true;
	for (int i = 0; i < BOX_SIZE; i++) {
	    for (int j = 0; j < BOX_SIZE; j++) {
		if(!remove(x+i,y+j,num)) {
		    valid = false;
		}
	    }
	}
	return valid;
    }
    public int getValue(int x, int y) {
	Set<Integer> c = getCell(x, y);
	if(c.size() == 1)
	    return (int) c.toArray()[0];
	return 0;
    }
    private boolean remove(int x, int y, int num) {
	if (sudoku[x][y].remove(num)) { // Contained the element
	    int size = sudoku[x][y].size();
	    if (size == 1) {
		cellsLeft--;
	    }
	    return size > 0;
	}
	return true;
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
		s += (j%BOX_SIZE == 0 && j > 0 ? "|":"")+getValue(j, i);
	    }
	    if((i+1)%BOX_SIZE == 0) {
		s += "\n---+---+---";
	    }
	    s += "\n";
	}
        return s;
    }
}
