package SudokuSolver;
import java.util.concurrent.Callable;


@SuppressWarnings("serial")
public class SudokuSolver implements Callable<Sudoku> {
    private Sudoku sudoku;
    private int x;
    private int y;
    /**
     * 
     * @param sudoku
     * @param x x coordinate marking the cell to fill
     * @param y y coordinate marking the cell to fill
     */
    SudokuSolver(Sudoku sudoku, int x, int y) {
	this.sudoku = sudoku;
	this.x = x;
	this.y = y;
    }
    public SudokuSolver(Sudoku s) {
	this(s,0,0);
    }
    public Sudoku call() {
	Sudoku s = null;
	for(Integer num :sudoku.getCell(x, y)) {
	    s = new Sudoku(sudoku);
	    if(s.setNumber(x, y, num)) {
		int newX = x;
		int newY = y;
		if(++newX == Sudoku.SIZE) {
		    newX = 0;
		    newY++;
		}
		if(newY != Sudoku.SIZE) {
		    SudokuSolver solver = new SudokuSolver(s, newX, newY);
		    s = solver.call();
		    if(s != null) {
			// This is the solution.
			return s;
		    }
		}
		
	    } else {
		s = null;
	    }
	}
	return s;
    }
    
    public static void main(String[] args) {
	Sudoku s = new Sudoku(
		"530070000" + 
		"600195000" + 
		"098000060" +
		"800060003" + 
		"400803001" + 
		"700020006" + 
		"060000280" + 
		"000419005" + 
		"000080079");
	s = new SudokuSolver(s).call();
	System.out.println(s.toString());
    }
}
