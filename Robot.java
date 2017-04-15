package application;

import java.awt.Point;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;

/*This class models the movement of a robot*/
public class Robot {
	private Arc robot;	//The "robot" is a semi circle
	private double distance;	
	private Timeline action;
	private double xDisplacment;		 //These values	
	private double yDisplacment;		 //are used to 
	private double startAngleDisplacment;//build the animation
	private boolean success;
	
	public Robot(double centerX, double centerY, double radius, double startAngle, double distance)
	{
		//IMPORTANT NOTE start angle should only be 0, 90, 180, or 270, determines direction 
		//the robot will face initially
		//radius is the radius of the robot (semi circle), adjuct according to maze size
		//distance robot will travel on each move command
		robot = new Arc();
		robot.setCenterX(centerX);
		robot.setCenterY(centerY);
		robot.setRadiusX(radius);
		robot.setRadiusY(radius);
		robot.setStartAngle(startAngle);
		robot.setLength(180);
		robot.setType(ArcType.ROUND);
		robot.setFill(Color.SKYBLUE);
		this.distance = distance;	
		
	}
	public Arc getRobot() 
	{
		return robot;
	};
	
	
	
	private boolean forward() 
	{	
		KeyValue kv = null;
		KeyFrame kf = null;
		int newX = (int)(robot.getCenterX() + xDisplacment);
		int newY = (int)(robot.getCenterY() + yDisplacment);
		
		if((robot.getStartAngle() + startAngleDisplacment) < 0)	{	//if angle is negative right/left facing is reverse
			if(((robot.getStartAngle() + startAngleDisplacment)%360) == 0) {	//robot is facing top of screen
				kv = new KeyValue(robot.centerYProperty(), robot.getCenterY() - distance + yDisplacment);			
				newY = (int)(robot.getCenterY() - distance + yDisplacment);
				yDisplacment += -distance;
			}
			else if((((robot.getStartAngle() + startAngleDisplacment)%360)%270) == 0) {	//robot is facing left side of screen								//robot is facing right side of screen
				kv = new KeyValue(robot.centerXProperty(), robot.getCenterX() - distance + xDisplacment);
				newX = (int)(robot.getCenterX() - distance + xDisplacment);
				xDisplacment += -distance;				
			}
			else if((((robot.getStartAngle() + startAngleDisplacment)%360)%180) == 0) {	//robot is facing bottom of screen
				kv = new KeyValue(robot.centerYProperty(), robot.getCenterY() + distance + yDisplacment);
				newY = (int)(robot.getCenterY() + distance + yDisplacment);
				yDisplacment += distance;
			}
			else if((((robot.getStartAngle() + startAngleDisplacment)%360)%90) == 0){//robot is facing right side of screen
				kv = new KeyValue(robot.centerXProperty(), robot.getCenterX() + distance + xDisplacment);
				newX = (int)(robot.getCenterX() + distance + xDisplacment);
				xDisplacment += distance;
			}
		}
		else {
		if(((robot.getStartAngle() + startAngleDisplacment)%360) == 0) {	//robot is facing top of screen
			kv = new KeyValue(robot.centerYProperty(), robot.getCenterY() - distance + yDisplacment);
			newY = (int)(robot.getCenterY() - distance + yDisplacment);
			yDisplacment += -distance;
		}
		else if((((robot.getStartAngle() + startAngleDisplacment)%360)%270) == 0) {										//robot is facing right side of screen
			kv = new KeyValue(robot.centerXProperty(), robot.getCenterX() + distance + xDisplacment);
			newX = (int)(robot.getCenterX() + distance + xDisplacment);
			xDisplacment += distance;		
		}
		else if((((robot.getStartAngle() + startAngleDisplacment)%360)%180) == 0) {	//robot is facing bottom of screen
			kv = new KeyValue(robot.centerYProperty(), robot.getCenterY() + distance + yDisplacment);
			newY = (int)(robot.getCenterY() + distance + yDisplacment);
			yDisplacment += distance;
		}
		else if((((robot.getStartAngle() + startAngleDisplacment)%360)%90) == 0){//robot is facing left side of screen
			kv = new KeyValue(robot.centerXProperty(), robot.getCenterX() - distance + xDisplacment);
			newX = (int)(robot.getCenterX() - distance + xDisplacment);
			xDisplacment += -distance;
		}
		}
		if(Model.isValidPoint(new Point(newX, newY))) { //there is no wall in the way
		kf = new KeyFrame(Duration.millis(1000), kv);
		action.getKeyFrames().add(kf);
		return true;	
		}
		else {	//there is a wall in the way
			System.out.println("hit");
			return false;
		}
		
	}
	
	
	
	
	
	private void turnRight() 
	{	//90 degree turn
		KeyValue kv = new KeyValue(robot.startAngleProperty(), robot.getStartAngle() - 90 + startAngleDisplacment);
		KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
		startAngleDisplacment += -90;
		action.getKeyFrames().add(kf);
	}
	
	private void turnLeft() 
	{	//90 degree turn
		KeyValue kv = new KeyValue(robot.startAngleProperty(), robot.getStartAngle() + 90 + startAngleDisplacment);
		KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
		startAngleDisplacment += 90;
		action.getKeyFrames().add(kf);
	}
	/*the robot can execute a sequence of commands,
	  given as a string array
	  ex: bot.execute(new String[]{"forward","turn left", "forward", "turn right"})*/
	public boolean execute(ObservableList<String> commands) 
	{	
		xDisplacment = 0;
		yDisplacment = 0;
		startAngleDisplacment = 0;
		success = true;
		SequentialTransition path = new SequentialTransition();
		for(String i : commands) 
		{
			action = new Timeline();
			switch (i) {
			case "Robot.MoveForward();":
				success = forward();
				break;
			case "Robot.TurnLeft();":
				turnLeft();
				break;
			case "Robot.TurnRight();":
				turnRight();
				break;
			}
			if(!success) break;	//if there is a wall in the way
			path.getChildren().add(action);
		}
		path.setOnFinished(e -> { //.play() is an asynchronous call, this is needed to display success/failure after animation finishes
			if(new Point((int)robot.getCenterX(),	//robot is on the goal at end of animation
					(int)robot.getCenterY()).equals(new Point(525, 375))) {	
				//IMPORTANT NOTE: need way to get goal location from view
			View.showWinDialog();
			}
			else if(success && !(new Point((int)robot.getCenterX(),	//robot did not run into any walls but is NOT on the goal
					(int)robot.getCenterY()).equals(new Point(525, 375)))) {
				View.showIncompleteDialog();
			}
			else if(!success) {	//robot ran into a wall
				View.showHitWallDialog();
			}		
		});
		
		path.play();
		return success;
	}
	
	public void setLocation(double centerX, double centerY) 
	{
		robot.setCenterX(centerX);
		robot.setCenterY(centerY);	
	}
	public Point getLocation() {
		return new Point((int)robot.getCenterX(), (int)robot.getCenterY());
	}
	
}
