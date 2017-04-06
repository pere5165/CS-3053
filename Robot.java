package application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
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
		this.distance = distance;		
	}
	public Arc getRobot() 
	{
		return robot;
	};
	private void forward() 
	{	
		KeyValue kv;
		KeyFrame kf = null;
		
		if((robot.getStartAngle() + startAngleDisplacment) < 0)	{	//if angle is negative right/left facing is reverse
			if(((robot.getStartAngle() + startAngleDisplacment)%360) == 0) {	//robot is facing top of screen
				kv = new KeyValue(robot.centerYProperty(), robot.getCenterY() - distance + yDisplacment);
				kf = new KeyFrame(Duration.millis(1000), kv);
				yDisplacment += -distance;
			}
			else if((((robot.getStartAngle() + startAngleDisplacment)%360)%270) == 0) {	//robot is facing left side of screen								//robot is facing right side of screen
				kv = new KeyValue(robot.centerXProperty(), robot.getCenterX() - distance + xDisplacment);
				kf = new KeyFrame(Duration.millis(1000), kv);
				xDisplacment += -distance;
				
			}
			else if((((robot.getStartAngle() + startAngleDisplacment)%360)%180) == 0) {	//robot is facing bottom of screen
				kv = new KeyValue(robot.centerYProperty(), robot.getCenterY() + distance + yDisplacment);
				kf = new KeyFrame(Duration.millis(1000), kv);
				yDisplacment += distance;
			}
			else if((((robot.getStartAngle() + startAngleDisplacment)%360)%90) == 0){//robot is facing right side of screen
				kv = new KeyValue(robot.centerXProperty(), robot.getCenterX() + distance + xDisplacment);
				kf = new KeyFrame(Duration.millis(1000), kv);
				xDisplacment += distance;
			}
		}
		else {
		if(((robot.getStartAngle() + startAngleDisplacment)%360) == 0) {	//robot is facing top of screen
			kv = new KeyValue(robot.centerYProperty(), robot.getCenterY() - distance + yDisplacment);
			kf = new KeyFrame(Duration.millis(1000), kv);
			yDisplacment += -distance;
		}
		else if((((robot.getStartAngle() + startAngleDisplacment)%360)%270) == 0) {										//robot is facing right side of screen
			kv = new KeyValue(robot.centerXProperty(), robot.getCenterX() + distance + xDisplacment);
			kf = new KeyFrame(Duration.millis(1000), kv);
			xDisplacment += distance;
			
		}
		else if((((robot.getStartAngle() + startAngleDisplacment)%360)%180) == 0) {	//robot is facing bottom of screen
			kv = new KeyValue(robot.centerYProperty(), robot.getCenterY() + distance + yDisplacment);
			kf = new KeyFrame(Duration.millis(1000), kv);
			yDisplacment += distance;
		}
		else if((((robot.getStartAngle() + startAngleDisplacment)%360)%90) == 0){//robot is facing left side of screen
			kv = new KeyValue(robot.centerXProperty(), robot.getCenterX() - distance + xDisplacment);
			kf = new KeyFrame(Duration.millis(1000), kv);
			xDisplacment += -distance;
		}
		}
		action.getKeyFrames().add(kf);
		
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
	public void execute(String[] commands) 
	{	
		xDisplacment = 0;
		yDisplacment = 0;
		startAngleDisplacment = 0;
		SequentialTransition path = new SequentialTransition();
		for(String i : commands) 
		{
			action = new Timeline();
			nextAction(i);
			path.getChildren().add(action);
		}
		
		path.play();		
	}
	
	private void nextAction(String command) {
		switch (command) {
		case "forward":
			forward();
			break;
		case "turn left":
			turnLeft();
			break;
		case "turn right":
			turnRight();
			break;
		}
	}
}
