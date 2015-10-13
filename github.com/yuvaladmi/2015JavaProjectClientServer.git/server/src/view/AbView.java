package view;

import java.util.Observable;

public abstract class AbView extends Observable implements View {

	public abstract void start();

	public abstract void stop();

	public abstract void display(String s);

}
