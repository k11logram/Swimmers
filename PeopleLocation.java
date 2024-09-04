//M. M. Kuttel 2024 mkuttel@gmail.com
// Class for storing  locations of people (swimmers only for now, but could add other types) in the simulation

package medleySimulation;

import java.awt.Color;

public class PeopleLocation  { // this is made a separate class so don't have to access thread
	
	private final int ID; //each person has an ID
	private Color myColor; //colour of the person
	
	private boolean inStadium; //are they here?
	private boolean arrived; //have they arrived at the event?
	private GridBlock location; //which GridBlock are they on?
	
	//constructor
	PeopleLocation(int ID , Color c) {
		myColor = c;
		inStadium = false; //not in pool
		arrived = false; //have not arrived
		this.ID=ID;
	}
	
	//setter
	public synchronized void setInStadium(boolean in) {
		inStadium = in;
	}
	
	//getter and setter
	public boolean getArrived() {
		return arrived;
	}
	public void setArrived() {
		arrived=true;
	}

//getter and setter
	public GridBlock getLocation() {
		return location;
	}
	public synchronized void setLocation(GridBlock location) {
		this.location = location;
	}

	//getter
	public synchronized int getX() { return location.getX();}	
	
	//getter
	public synchronized int getY() {	return location.getY();	}
	
	//getter
	public synchronized int getID() {	return ID;	}

	//getter
	public synchronized boolean inPool() {
		return inStadium;
	}
	//getter and setter     sync these2
	public synchronized Color getColor() { return myColor; }
	public synchronized void setColor(Color myColor) { this.myColor= myColor; }
}
