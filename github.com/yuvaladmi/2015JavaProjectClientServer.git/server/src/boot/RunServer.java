package boot;

import controller.Controller;
import model.Maze3dClientHandler;
import model.Maze3dModel;
import model.Model;
import view.MyView;
import view.View;

public class RunServer {

	public static void main(String[] args) {
		Controller controller = new Controller();
		Model model = new Maze3dModel(controller);
		Maze3dClientHandler mazeClientHandle = new Maze3dClientHandler(model);
		View view = new MyView(controller);
		controller.setMaze(mazeClientHandle);
		controller.setM(model);
		controller.setV(view);
		view.start();
		
	}
}
