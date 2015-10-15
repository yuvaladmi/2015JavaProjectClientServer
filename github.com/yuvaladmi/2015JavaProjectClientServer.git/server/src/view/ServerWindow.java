package view;

import javax.sql.rowset.serial.SerialException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import controller.Controller;

public class ServerWindow extends BasicWindow {
	protected Controller controller;
	protected Button connected;
	protected Button disconnected;
	protected Text text;
	protected Image connectedImage;
	protected Image disconnectedImage;

	public ServerWindow(String title, int width, int height, Controller controller) {
		super(title, width, height);
		this.controller = controller;
		connected = new Button(shell, SWT.PUSH);
		disconnected = new Button(shell, SWT.PUSH);
		connectedImage = new Image(display, "resources/connected.jpg");
		disconnectedImage = new Image(display, "resources/disconnected.jpg");
	}

	@Override
	public void displayPopUp(String str) {
	}

	@Override
	public void initWidgets() {
		shell.setLayout(new GridLayout(2, false));
		connected.setLayoutData(new GridData(SWT.TOP, SWT.NONE, false, false, 2, 2));
		connected.setImage(connectedImage);
		connected.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				controller.start();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		disconnected.setLayoutData(new GridData(SWT.TOP, SWT.NONE, false, false, 2, 2));
		disconnected.setImage(disconnectedImage);
		disconnected.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				controller.stop();
				shell.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		text = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		

	}
	
}
