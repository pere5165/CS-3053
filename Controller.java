package application;

import java.awt.Point;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

public class Controller 
	implements EventHandler{
	
	Model model;

	public Controller(Model model) {
		// TODO Auto-generated constructor stub
		this.model = model;
	}


	public void addValidPoint(Point input){
		model.validPoints.add(input);
	}



	@Override
	public void handle(Event arg0) {
		// TODO Auto-generated method stub
	
		
		//Distinguish between combobox and button
		if (arg0.getSource().getClass().getSimpleName().contains("Combo")){
			setChoiceString(((ComboBox<?>) arg0.getSource()).getSelectionModel().getSelectedItem().toString());
		}
		else{
			
			if (model.getChoiceString().equalsIgnoreCase("MoveForward();"))
			{
				model.items.add("Robot.MoveForward();");
			}
			else if (model.choice.equalsIgnoreCase("TurnLeft();"))
			{
				model.items.add("Robot.TurnLeft();");
			}
			else if(model.choice.equalsIgnoreCase("TurnRight();"))
			{
				model.items.add("Robot.TurnRight();");
			}
			
			if (model.choice.equalsIgnoreCase("MoveUp();"))
			{
				model.items.add("Robot.MoveUp();");
			}
			else if (model.choice.equalsIgnoreCase("MoveDown();"))
			{
				model.items.add("Robot.MoveDown();");
			}
			else if(model.choice.equalsIgnoreCase("MoveLeft();"))
			{
				model.items.add("Robot.MoveLeft();");
			}
			else if(model.choice.equalsIgnoreCase("MoveRight();"))
			{
				model.items.add("Robot.MoveRight();");
			}
			
	
			model.update();
			
		}
		
	}


	public void setChoiceString(String selectedItem) {
		// TODO Auto-generated method stub
		model.setChoiceString(selectedItem);
		
	}


	public void clearItemsList() {
		// TODO Auto-generated method stub
		model.items.clear();
		model.update();
	}
}
