package boot;

import controller.Controller;
import model.Maze3dClientHandler;
import model.Maze3dModel;
import model.Model;
import view.MyView;
import view.ServerWindow;
import view.View;


public class RunServer {

	public static void main(String[] args) {
		Controller controller = new Controller();
		ServerWindow sw=new ServerWindow("Server", 320, 670, controller);
		Model model = new Maze3dModel(controller);
		View view = new MyView(controller);
		Maze3dClientHandler mazeClientHandle = new Maze3dClientHandler(view);
		controller.setM(model);
		controller.setV(view);
		controller.setMaze(mazeClientHandle);
		controller.setSw(sw);
		sw.run();
		
	}
}
