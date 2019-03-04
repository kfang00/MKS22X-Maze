import java.util.*;
import java.io.*;
public class Maze{


    private char[][]maze;
    private boolean animate;//false by default

    /*Constructor loads a maze text file, and sets animate to false by default.

      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)

      'S' - the location of the start(exactly 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!


      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:

         throw a FileNotFoundException or IllegalStateException

    */

    public Maze(String filename) throws FileNotFoundException{
        //COMPLETE CONSTRUCTOR
        int row = 0;
	int col = 0;
	int e = 0;
	int s = 0;
	int hold = 0;
	String h = "";
	animate = false;
	String st = "";
        File f = new File(filename);
        Scanner in = new Scanner(f); 
	while(in.hasNext()){
		h = in.nextLine();
		row += 1;
		col = h.length();
		st += h;
	}
	maze = new char[row][col];
        in.close();
        for (int a = 0; a < maze.length; a++) {
		for (int b = 0; b < maze[0].length; b++) {
			maze[a][b] = st.charAt(hold); //placing file into maze
			if (maze[a][b] == 'E') { //counting Es
				e++;
			}
			if (maze[a][b] == 'S') { //counting Ss
				s++;
			}
			hold++;
		}
	}
	if ((e != 1) || (s != 1)) {
		throw new IllegalStateException();
	}
    }


    private void wait(int millis){
         try {
             Thread.sleep(millis);
         }
         catch (InterruptedException e) {
         }
     }


    public void setAnimate(boolean b){

        animate = b;

    }


    public void clearTerminal(){

        //erase terminal, go to top left of screen.

        System.out.println("\033[2J\033[1;1H");

    }






   /*Return the string that represents the maze.

     It should look like the text file with some characters replaced.

    */
    public String toString(){
	String str = "";
	for (int a = 0; a < maze.length; a++) {
		for (int b = 0; b < maze[0].length; b++) {
			str += "" + maze[a][b];
		}
		str += "\n";
	}
        return str;
    }



    /*Wrapper Solve Function returns the helper function

      Note the helper function has the same name, but different parameters.
      Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.

    */
    public int solve(){
	int r = 0;
	int c = 0;
            //find the location of the S.
	    //erase the S
	for (int a = 0; a < maze.length; a++) {
		for (int b = 0; b < maze[0].length; b++) {
			if (maze[a][b] == 'S') {
				maze[a][b] = ' ';
				r = a;
				c = b;
			}
		}
	}
            //and start solving at the location of the s.
            //return solve(???,???);
	return solve(r, c, 0);
    }

    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.

      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.


      Postcondition:

        The S is replaced with '@' but the 'E' is not.

        All visited spots that were not part of the solution are changed to '.'

        All visited spots that are part of the solution are changed to '@'
    */
    private int solve(int row, int col, int count){ //you can add more parameters since this is private

        //automatic animation! You are welcome.
        if(animate){

            clearTerminal();
            System.out.println(this);

            wait(20);
        }

        //COMPLETE SOLVE
	if (maze[row][col] == 'E') {
		return count; //reached the end
	}
	if ((maze[row][col] == '@') || (maze[row][col] == '#') || (maze[row][col] == '.')) { //places you cannot move
		return -1; 
	}
	maze[row][col] = '@'; //if it's an open space then place @
	int[][] move = {{0, -1}, {0, 1}, {1, 0}, {-1, 0}}; //looping through four possible moves
	for (int a = 0; a < move.length; a++) {
		int g = solve(row + move[a][1], col + move[a][0], count + 1);
		if (g != -1) {
			return g; //if it does not ever return -1 before reaching the end then it will return count
		}
	}
	maze[row][col] = '.'; //if it loops through all the moves without returning the count then that means that all four directions do not work and thus place a .
        return -1; //so it compiles <- when all four directions do not work as well
    }

}
