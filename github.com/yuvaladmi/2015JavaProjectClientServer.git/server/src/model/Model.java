package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;

public interface Model {
    /**
	 * This method create a new Maze3d in a thread. All the mazes saved in an
	 * HashMap.
	 */
    public void generateMaze(String[] arr);
    /**
	 * This method gets a Maze name and sends the presenter this maze.
	 * @return Maze3d
	 */
    public Maze3d sendGame(String str);
    
    /**
	 * This method gets a name of a maze and solving algorithm and solves it in
	 * a Thread. All the solutions saved in an HashMap.
	 */
    public void solve(String[] arr);
    /**
	 * This method gets a name of a maze and sends the Controller its solution
	 */
    public Solution<Position> bringSolution(String name);
  
    /**
     * This method save the hashMaps in a Zip file
     */
    public void saveZip();
    /**
     * This method loads the hashMaps from an existing zip file
     */
    public void loadZip();
    /**
	 * This method closes all the open threads.
	 */
    public void close();
   
}
