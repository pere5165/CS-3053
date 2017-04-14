
package application;

import java.awt.Point;
//import java.util.Collections;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
//import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class View extends Application {
	
	static LinkedList<Point> validPoints = new LinkedList<Point>();	//changed to static to call accessor in Robot
	ListView<String> list = new ListView<String>();
	ObservableList<String> items =FXCollections.observableArrayList ();
	ObservableList<String> actions = FXCollections.observableArrayList(
			"MoveForward();", "TurnLeft();", "TurnRight();");
	Robot bot = new Robot(75,475,20,0,50);
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,800,900);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			Pane maze = new Pane();
			
			Rectangle r = new Rectangle(25,25,760,500);
			r.setFill(Color.BLUE);
			
			Rectangle r1 = new Rectangle(50,450,50,50);
			r1.setFill(Color.GREEN);
			Point x1 = new Point(75,475);	
			validPoints.add(x1);
			
			Rectangle r2 = new Rectangle(50,400,50,50);
			Point x2 = new Point(75,425);
			validPoints.add(x2);
			
			Rectangle r3 = new Rectangle(50,350,50,50);
			Point x3 = new Point(75,375);
			validPoints.add(x3);
			
			Rectangle r4 = new Rectangle(50,300,50,50);
			Point x4 = new Point(75,325);
			validPoints.add(x4);
			
			Rectangle r5 = new Rectangle(50,250,50,50);
			Point x5 = new Point(75,275);
			validPoints.add(x5);
			
			Rectangle r6 = new Rectangle(100,250,50,50);
			Point x6 = new Point(125,275);
			validPoints.add(x6);
			
			Rectangle r7 = new Rectangle(150,250,50,50);
			Point x7 = new Point(175,275);
			validPoints.add(x7);
			
			Rectangle r8 = new Rectangle(200,250,50,50);
			Point x8 = new Point(225,275);
			validPoints.add(x8);
			
			Rectangle r9 = new Rectangle(200,200,50,50);
			Point x9= new Point(225,225);
			validPoints.add(x9);
			
			Rectangle r10 = new Rectangle(200,150,50,50);
			Point x10 = new Point(225,175);
			validPoints.add(x10);
			
			Rectangle r11 = new Rectangle(250,150,50,50);
			Point x11 = new Point(275,175);
			validPoints.add(x11);
			
			Rectangle r12 = new Rectangle(300,150,50,50);
			Point x12 = new Point(325,175);
			validPoints.add(x12);
			
			Rectangle r13 = new Rectangle(300,200,50,50);
			Point x13 = new Point(325,225);
			validPoints.add(x13);
			
			Rectangle r14 = new Rectangle(300,250,50,50);
			Point x14 = new Point(325,275);
			validPoints.add(x14);
			
			Rectangle r15 = new Rectangle(300,300,50,50);
			Point x15 = new Point(325,325);
			validPoints.add(x15);
			
			Rectangle r16 = new Rectangle(300,350,50,50);
			Point x16 = new Point(325,375);
			validPoints.add(x16);
			
			Rectangle r17 = new Rectangle(350,350,50,50);
			Point x17 = new Point(375,375);
			validPoints.add(x17);
			
			Rectangle r18 = new Rectangle(400,350,50,50);
			Point x18 = new Point(425,375);
			validPoints.add(x18);
			
			Rectangle r19 = new Rectangle(450,350,50,50);
			Point x19 = new Point(475,375);
			validPoints.add(x19);
			
			Rectangle r20 = new Rectangle(500,350,50,50);
			Point x20 = new Point(525,375);
			validPoints.add(x20);
			r20.setFill(Color.RED);
		
			maze.getChildren().addAll(r, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20);
			root.setCenter(maze);
			
			//This is the pane that will hold the action list
			BorderPane listing = new BorderPane();
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
			
			doAction.setOnMouseClicked((e) ->
			{
				String choice = comboBox.getSelectionModel().getSelectedItem();
				//FXCollections.reverse(items);
				
				if (choice.equalsIgnoreCase("MoveForward();"))
				{
					items.add("Robot.MoveForward();");
				}
				else if (choice.equalsIgnoreCase("TurnLeft();"))
				{
					items.add("Robot.TurnLeft();");
				}
				else if(choice.equalsIgnoreCase("TurnRight();"))
				{
					items.add("Robot.TurnRight();");
				}
				
				//FXCollections.reverse(items);
				list.setItems(items);
			}
			
			);
			//button to run list of actions
			Button go = new Button("Go!");
			go.setOnMouseClicked(e -> {
				bot.execute(items);	
			});
			
			BorderPane form = new BorderPane();
			form.setTop(direct);
			HBox jiggs = new HBox();
			jiggs.getChildren().addAll(robot, comboBox,doAction);
			form.setCenter(jiggs);
			form.setBottom(go);
			inputPane.getChildren().add(form);
			
			BorderPane userInput = new BorderPane();
			userInput.setLeft(inputPane);
			userInput.setRight(listing);
			
			Stage inputStage = new Stage();
			inputStage.setScene(new Scene(userInput));

			//code for displaying robot here
			bot = new Robot(75,475,20,0,50);
			root.getChildren().add(bot.getRobot());
			
			
			primaryStage.show();
			inputStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public LinkedList<Point> getValidPointList() {
		return validPoints;
		
	}
	
	public static boolean isValidPoint(Point point) {
		if(validPoints.contains(point)) return true;
		else return false;	
	}
	
	public static void showWinDialog() {	//display when robot arrives at the goal
		Dialog<String> winDialog = new Dialog<>();
		winDialog.setContentText("Level Complete!");
		winDialog.getDialogPane().getButtonTypes().add(new ButtonType("Continue", ButtonData.CANCEL_CLOSE));
		winDialog.show();
	}
	
	public static void showIncompleteDialog() {	//display when robot is not at the goal when animation is finished
		Dialog<String> winDialog = new Dialog<>();
		winDialog.setContentText("Almost There!");
		winDialog.getDialogPane().getButtonTypes().add(new ButtonType("Try Again", ButtonData.CANCEL_CLOSE));
		winDialog.show();
	}
	public static void showHitWallDialog() {	//display when robot hits a wall
		Dialog<String> winDialog = new Dialog<>();
		winDialog.setContentText("There is something in the way!");
		winDialog.getDialogPane().getButtonTypes().add(new ButtonType("Try Again", ButtonData.CANCEL_CLOSE));
		winDialog.show();
	}
		
}
