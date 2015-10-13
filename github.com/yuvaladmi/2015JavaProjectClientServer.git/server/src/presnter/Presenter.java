package presnter;

import java.util.Observable;
import java.util.Observer;

import model.MyServer;
import view.View;

public class Presenter implements Observer{

	MyServer m;
	View v;
	public Presenter(MyServer m,View v) {
		this.m=m;
		this.v=v;
		
	}
	@Override
	public void update(Observable o, Object obj) {
	if (o==v){
		
	}
	else{
		if(o==m){
			v.display((String)(obj));
		}
	}
		
	}

}
