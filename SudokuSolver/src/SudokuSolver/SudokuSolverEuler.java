package SudokuSolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class SudokuSolverEuler {

    /**
     * @param args
     * @throws IOException
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
	BufferedReader buf = new BufferedReader(
		new FileReader("src/sudoku.txt"));
	int sum = 0;
	List<SudokuSolver> solvers = new ArrayList<>(50);;
	for (int j = 0; j < 50; j++) {
	    String sudokuString = "";
	    buf.readLine();
	    for (int i = 0; i < 9; i++) {
		sudokuString += buf.readLine();
	    }
	    solvers.add(new SudokuSolver(new Sudoku(sudokuString)));
	}
	buf.close();
	
	System.out.println("read done");
	long startTime = System.nanoTime();
	ForkJoinPool mainPool = new ForkJoinPool();
	List<Future<Sudoku>> answers = mainPool.invokeAll(solvers);
	for (Iterator<Future<Sudoku>> iterator = answers.iterator(); iterator.hasNext();) {
	    Sudoku s = iterator.next().get();
	    sum += (s.getValue(0,0) * 100 + s.getValue(1, 0) * 10 + s.getValue(2, 0));
	}
	/*
	for(SudokuSolver solver : solvers) {
	    Sudoku s = solver.call();
	    sum += (s.getValue(0,0) * 100 + s.getValue(1, 0) * 10 + s.getValue(2, 0));
	}
	*/
	long endTime = System.nanoTime();
	System.out.println(endTime - startTime);
	
	
	System.out.println(sum);
    }

}
