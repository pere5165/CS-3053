package application;
	


import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application{
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//construct Model
			Model model = new Model();
			
			//construct Controller
			Controller c = new Controller( model );
			
			//construct View
			View v = new View( model, c, primaryStage );
					
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
