package application;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class View 
			implements Listener{
	


	ListView<String> list = new ListView<String>();
	ObservableList<String> items =FXCollections.observableArrayList ();
	ObservableList<String> actions = FXCollections.observableArrayList(
			"MoveForward();", "TurnLeft();", "TurnRight();", "while(notAtGoal()){", "}endwhile",
			"if(pathLeft()){", "if(pathRight()){", "if(pathForward()){", "}endif");
	Robot bot = new Robot(75,475,20,0,50);
	
	
	private Model model;
	private static Controller usedController;
	private static Button New;
	private static int count;
	private static Label myLabel;
	private static StringProperty value = new SimpleStringProperty("Level 1");
	
	public View(Model model, Controller c, Stage primaryStage) {
		model.addListener( this );
		
		this.model = model;
		View.usedController=c;
		
		init(primaryStage );
	}
	
	private void init(Stage primaryStage )
	{
		
			count = 1;
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,800,700);
			primaryStage.setTitle("Robo Maze");
			
			//Menu bar
			MenuBar menu = new MenuBar();
			menu.prefWidthProperty().bind(primaryStage.widthProperty());
			
			myLabel = new Label("Level " + count);
			myLabel.textProperty().bind(value);
		    
		    Menu file = new Menu("File");
		    MenuItem exit = new Menu("Exit");
		    
		    Menu game = new Menu("Game");
		    MenuItem reload = new MenuItem("Reload New");
		    MenuItem clear = new MenuItem("Clear List");
		    
		    file.getItems().addAll(exit);
		    game.getItems().addAll(reload, clear);
		    
		    menu.getMenus().addAll(file, game);
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			Pane maze = new Pane();
			
			//generate random Maze
			generateMaze(maze);
			root.setTop(maze);
			
			//This is the pane that will hold the action list
			BorderPane listing = new BorderPane();
			listing.setPadding(new Insets(10, 20, 10, 20));
			list.setItems(items);
			listing.setCenter(list);
			Text title = new Text("Action List");
			listing.setTop(title);
			
			//This is the pane that will prompt user to input using a combobox
			Pane inputPane = new Pane();
			Label direct = new Label("Input actions from here!");
			Label robot = new Label("Robot.");
			final ComboBox<String> comboBox = new ComboBox<String>(actions);
			Button doAction = new Button("Add Action");
			//set both combobox and and doaction button on action
			usedController.setChoiceString(comboBox.getSelectionModel().getSelectedItem());
			comboBox.setOnAction(usedController);
			doAction.setOnMouseClicked(usedController);
			
			Tooltip x = new Tooltip("Select Command for Robot to Perform");
			comboBox.setTooltip(x);
			
			
			
			//button to run list of actions
			Button go = new Button("Go");
			go.setOnMouseClicked(e -> {
				bot.execute(items);	
			});
			
			//Button created to go to next level when chosen
			New = new Button("Next Level");
			New.setOnMouseClicked(e -> {
				generateMaze(maze);
				New.setDisable(true);
			});
			New.setDisable(true);
			
			//Action listener for the reload option on menuBar
			reload.setOnAction(new EventHandler<ActionEvent>() {
		        public void handle(ActionEvent t) {
		        	generateMaze(maze);
		        }
		   });
			
			
			
			//Action listener for the exit option on menuBar
			exit.setOnAction(new EventHandler<ActionEvent>() {
		        public void handle(ActionEvent t) {
		        	System.exit(0);
		        }
		   });
			
			//Action listener for the clear option on menuBar
			clear.setOnAction(new EventHandler<ActionEvent>() {
		        public void handle(ActionEvent t) {
		        	usedController.clearItemsList();
		        }
		   });
			
			//Clear List Button
			Button listClearBtn = new Button("Clear Actions");
			listClearBtn.setOnMouseClicked(e -> {
				usedController.clearItemsList();	
			});
			
			HBox commandButtons = new HBox();		   
		    commandButtons.getChildren().addAll(go, listClearBtn, New, myLabel);
		    commandButtons.setStyle("-fx-padding: 5;" + 
	                "-fx-border-style: solid inside;" + 
	                "-fx-background-color: #f0ffff;" +
		    		"-fx-spacing: 5;" 
	               );  
		
			BorderPane form = new BorderPane();
			form.setPadding(new Insets(10, 20, 10, 20));
			form.setTop(direct);
			HBox jiggs = new HBox();
   
		    jiggs.setStyle("-fx-padding: 1;" + 
	                "-fx-border-style: solid inside;" + 
	                "-fx-background-color: #f8f8ff;" +
		    		"-fx-spacing: 1;");
			
			jiggs.getChildren().addAll(robot, comboBox,doAction);
			form.setCenter(jiggs);
			form.setBottom(commandButtons);
			
			inputPane.getChildren().add(form);
			
			BorderPane userInput = new BorderPane();
			userInput.setPadding(new Insets(10, 20, 10, 20));
			userInput.setLeft(inputPane);
			userInput.setCenter(listing);
			
			BorderPane bottom = new BorderPane();
			bottom.setLeft(form);
			bottom.setCenter(userInput);
			bottom.setTop(inputPane);
			
			maze.getChildren().add(menu);
			
			//code for displaying robot here
			bot = new Robot(75,475,20,0,50);
			root.setCenter(bottom);
			
			root.getChildren().add(bot.getRobot());
		
			primaryStage.show();
			
			
		
	}
	
	public static void showWinDialog(Robot bot) {	//display when robot arrives at the goal
		bot.setLocation(75,475);
		count++;
		value.set("Level " + count);
		Dialog<String> winDialog = new Dialog<>();
		New.setDisable(false);
		winDialog.setContentText("Level Complete!");
		winDialog.getDialogPane().getButtonTypes().add(new ButtonType("Continue", ButtonData.CANCEL_CLOSE));
		winDialog.show();
	}
	
	public static void showIncompleteDialog(Robot bot) {	//display when robot is not at the goal when animation is finished
		
		bot.setLocation(75,475);
		
		Dialog<String> winDialog = new Dialog<>();
		winDialog.setContentText("Almost There!");
		winDialog.getDialogPane().getButtonTypes().add(new ButtonType("Try Again", ButtonData.CANCEL_CLOSE));
		winDialog.show();
	}
	public static void showHitWallDialog(Robot bot) {	//display when robot hits a wall
		bot.setLocation(75,475);
		
		Dialog<String> winDialog = new Dialog<>();
		winDialog.setContentText("Oops You Hit A Wall!");
		winDialog.getDialogPane().getButtonTypes().add(new ButtonType("Try Again", ButtonData.CANCEL_CLOSE));
		winDialog.show();
		
           
	}
	public static void showInfiniteDialog(Robot bot) {
		
		bot.setLocation(75,475);
		
		Dialog<String> winDialog = new Dialog<>();
		winDialog.setContentText("Infinite Loop! That would take too long!");
		winDialog.getDialogPane().getButtonTypes().add(new ButtonType("Try Again", ButtonData.CANCEL_CLOSE));
		winDialog.show();
		
	}
	public void setVisible(Stage primaryStage) {
		// TODO Auto-generated method stub
		
	}
	
	public void generateMaze(Pane maze) {
		
		usedController.clearValidPoints();
		// TODO Auto-generated method stub
		int xCoord = 50;
		int yCoord = 450;
		
		int xPoint = 75;
		int yPoint = 475;
		
		
		//Random Maze Generator
		Rectangle rbase = new Rectangle(0,25,810,550);
		rbase.setFill(Color.BLUE);
		
		maze.getChildren().add(rbase);
		
		for (int count = 0; count < 20; count++){
			
			int randomNum1 = ThreadLocalRandom.current().nextInt(1, 7 + 1);
			
			if (randomNum1 == 3 || randomNum1 == 4 || randomNum1 == 5){
				int randomNum2 = ThreadLocalRandom.current().nextInt(1, 20 + 1);
				
				if (randomNum2 == 2 && xCoord > 50){
					xCoord = xCoord - 50;
					xPoint = xPoint - 50;
				}
				else if(xCoord < 750){
					xCoord = xCoord + 50;
					xPoint = xPoint + 50;
				}
			}
			
			if (randomNum1 == 1 || randomNum1 == 2 ){
				int randomNum2 = ThreadLocalRandom.current().nextInt(1, 20 + 1);
				
				if (randomNum2 == 2 && yCoord < 500 ){
					yCoord = yCoord + 50;
					yPoint = yPoint + 50;
				}
				else if(yCoord > 50){
					yCoord = yCoord - 50;
					yPoint = yPoint - 50;
				}
			}
			System.out.println(xCoord + " "  + yCoord);
			Rectangle r = new Rectangle(xCoord,yCoord,50,50);
			Point x = new Point(xPoint,yPoint);
			usedController.addValidPoint(x);
			maze.getChildren().add(r);
		}
		
		Rectangle r1 = new Rectangle(50,450,50,50);
		r1.setFill(Color.GREEN);
		Point x1 = new Point(75,475);	
		usedController.addValidPoint(x1);
		
		
		Rectangle rEnd = new Rectangle(xCoord+50,yCoord,50,50);
		Point xEnd = new Point(xPoint+50,yPoint);
		usedController.addValidPoint(xEnd);
		rEnd.setFill(Color.RED);
		maze.getChildren().addAll(r1,rEnd);
	}
	@Override
	public void updated() {
		// TODO Auto-generated method stub
		items = model.items;
		list.setItems(items);
		
	}
	
		
}
