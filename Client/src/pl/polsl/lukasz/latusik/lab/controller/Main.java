package pl.polsl.lukasz.latusik.lab.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 Main class extending Application. Required for JavaFx App.
 @author Łukasz Latusik
 @version 1.0
 */
public class Main extends Application {
    
   private Controller guiController;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/view.fxml"));
        Parent root = loader.load();
        
        primaryStage.setTitle("Shop Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);

        this.guiController = loader.getController();
        guiController.setIpField(ipNumberProperty());
        guiController.setPortFieldField(portNumberProperty());
        
        
        primaryStage.show();
        
       System.out.println(Thread.activeCount());
        guiController.buttonClearEventHandler();
        guiController.buttonConnectEventHandler();
        guiController.adminInputEnterPressedEventHandler();
        
    }
    
    @Override
    public void stop() throws Exception {
        super.stop();
        guiController.close();
        System.exit(0);
    }
    
    /**
     The entry point of application.
     
     @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println("Main:" + Thread.activeCount());
        launch(args);
    }
    /**
        Gets property object
     */
    private Properties getPropertiesObject() throws IOException{
        Properties properties = new Properties();
        properties.load(new FileReader("config.properties"));
        return properties;
    }
    
    /**
     Takes port number from property object.
     @return port number
     */
    private  String portNumberProperty(){
        try {
           return getPropertiesObject().getProperty("port");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     Takes IP number from property object.
     @return IP number
     */
    private  String ipNumberProperty(){
        try {
            return getPropertiesObject().getProperty("ip");
        } catch (IOException  e) {
            e.printStackTrace();
            return "";
        }
    }
}
