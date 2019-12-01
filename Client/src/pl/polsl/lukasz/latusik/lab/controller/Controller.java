package pl.polsl.lukasz.latusik.lab.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pl.polsl.lukasz.latusik.lab.server.InputManager;
import pl.polsl.lukasz.latusik.lab.server.ServerConnector;
import pl.polsl.lukasz.latusik.lab.server.ServerResponseThread;

import java.io.IOException;

/**
 Controller for view.fxml GUI.
 @author Łukasz Latusik
 @version 1.0
 */
public class Controller {
	
	private ServerConnector serverConnector;
	private ServerResponseThread serverResponseThread;
	
	@FXML
	private Button clearButton;
	@FXML
	private Button connectButton;
	@FXML
	private TextField adminInput;
	@FXML
	private TextField ipField;
	@FXML
	private TextField portField;
	@FXML
	private TextArea textField;
	
	/**
	 Button clear event handler.
	 */
	public void buttonClearEventHandler(){
		clearButton.setOnAction(actionEvent -> clearTextField());
	}
	
	/**
	 Button connect event handler.
	 */
	public void buttonConnectEventHandler(){
		connectButton.setOnAction(actionEvent -> buttonConnectResponse());
	}
	
	/**
	 Admin input enter pressed event handler.
	 */
	public void  adminInputEnterPressedEventHandler(){
		adminInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if(keyEvent.getCode().equals(KeyCode.ENTER)){
					adminInputEnterPressedResponse();
				}
			}
		});
	}
	
	/**
	 Response to pressed enter.
	 */
	private void adminInputEnterPressedResponse(){
		String adminInput = getAdminInput();
		if(adminInput!=null && adminInput.isBlank()){
			clearTextField();
			clearAdminInput();
			showLine("Wrong input.");
		}
		else{
			showLine("Admin:"  + adminInput );
			clearAdminInput();
			try {this.serverConnector.sendMessage(adminInput);}
			catch (NullPointerException e){
				showLine("Server unreachable.");
			}
		}
	}
	
	/**
	 Response to connect button.
	 */
	private void buttonConnectResponse(){
		clearTextField();

		if(this.serverResponseThread!= null) {
			this.serverResponseThread.end();
			serverResponseThread = null;
		}

		if(this.serverConnector != null){
			try {
				this.serverConnector.close();
				serverConnector = null;
			} catch (Exception e) {
				showLine(e.toString());
			}
		}
		
		try
		{
			String ip = InputManager.checkIP(getIP());
			int port = InputManager.parsePort(getPort());
			this.serverConnector=new ServerConnector(port,ip);
			ServerResponseThread serverResponseThread = new ServerResponseThread(serverConnector,this::showLine);
			serverResponseThread.start();
			System.out.println("Serwer response:"+ Thread.activeCount());
		} catch (IOException e) {
			showLine("Connection refused.");
			this.serverConnector = null;
			this.serverResponseThread = null;
		}
		catch (Exception e){
			showLine("Exception occurred:");
			showLine(e.toString());
			this.serverConnector=null;
			this.serverResponseThread=null;
		}
	}
	
	/**
	 Set ip field.
	 
	 @param value the value
	 */
	public void setIpField(String value){
		ipField.setText(value);
	}
	
	/**
	 Set port field field.
	 
	 @param value the value
	 */
	public void setPortFieldField(String value){
		portField.setText(value);
	}
	
	/**
	 Clears admin input field.
	 */
	private void clearAdminInput(){
		this.adminInput.clear();
	}
	
	/**
	 Close.
	 */
	public void close(){
		try {
			if(serverConnector!=null) {
				serverConnector.close();
			}
			if(serverResponseThread!=null) {
				serverConnector.close();
			}
		} catch (Exception e) {
			clearTextField();
			showLine(e.toString());
		}
	}
	
	/**
	 Show line.
	 
	 @param line the line
	 */
	public synchronized void showLine(String line){
		String currentText= textField.getText();
		textField.setText(currentText + "\n" + line);
	}
	
	/**
	 Clear text field.
	 */
	public void clearTextField(){
		textField.clear();
	}
	
	/**
	 Get ip string.
	 
	 @return the string
	 */
	public String getIP(){
		return ipField.getText();
	}
	
	/**
	 Get port string.
	 
	 @return the string
	 */
	public String getPort(){
		return portField.getText();
	}
	
	/**
	 Gets admin input.
	 
	 @return the admin input
	 */
	public String getAdminInput()
	{
		return  adminInput.getText();
	}
	
	
	
}
