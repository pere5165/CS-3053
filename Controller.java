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
			else if(model.getChoiceString().equalsIgnoreCase("while(notAtGoal()){")) {
				model.items.add("while(notAtGoal()){");
			}
			else if(model.getChoiceString().equalsIgnoreCase("}endwhile")) {
				model.items.add("}endwhile");
			}
			else if(model.getChoiceString().equalsIgnoreCase("if(pathForward()){")) {
				model.items.add("if(pathForward()){");
			}
			else if(model.getChoiceString().equalsIgnoreCase("if(pathLeft()){")) {
				model.items.add("if(pathLeft()){");
			}
			else if(model.getChoiceString().equalsIgnoreCase("if(pathRight()){")) {
				model.items.add("if(pathRight()){");
			}
			else if(model.getChoiceString().equalsIgnoreCase("}endif")) {
				model.items.add("}endif");
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
