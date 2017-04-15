package application;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class Model {

	ObservableList<String> items =FXCollections.observableArrayList ();
	static LinkedList<Point> validPoints = new LinkedList<Point>(); //changed to static to call accessor in Robot
	String choice;

	
	public LinkedList<Point> getValidPointList() {
		return validPoints;
		
	}
	
	public static boolean isValidPoint(Point point) {
		if(validPoints.contains(point)) return true;
		else return false;	
	}
	
	public void update(){
		notifyListeners();
	}
	
	
	
	private List<Listener> listeners = new ArrayList<Listener>();
	
	public void addListener( final Listener listener )
	{
		listeners.add( listener );
	}
	
	private void notifyListeners()
	{
		for( final Listener listener : listeners ) {
			listener.updated();
		}
	}

	public void setChoiceString(String selectedItem) {
		// TODO Auto-generated method stub
		choice = selectedItem;
	}

	public String getChoiceString() {
		// TODO Auto-generated method stub
		return choice;
	}
	
}
