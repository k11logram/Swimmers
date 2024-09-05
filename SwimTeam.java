//M. M. Kuttel 2024 mkuttel@gmail.com
//Class to represent a swim team - which has four swimmers
package medleySimulation;

import medleySimulation.Swimmer.SwimStroke;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class SwimTeam extends Thread {
	
	public static StadiumGrid stadium; //shared 
	private Swimmer [] swimmers;
   private Lock lock = new ReentrantLock();
	private int teamNo; //team number 
   public CountDownLatch latch = new CountDownLatch(4);
	
	public static final int sizeOfTeam=4;
	
	SwimTeam( int ID, FinishCounter finish,PeopleLocation [] locArr ) {
		this.teamNo=ID;
		//synchronized(this){
		swimmers= new Swimmer[sizeOfTeam];
	    SwimStroke[] strokes = SwimStroke.values();  // Get all enum constants
		stadium.returnStartingBlock(ID);

		for(int i=teamNo*sizeOfTeam,s=0;i<((teamNo+1)*sizeOfTeam); i++,s++) { //initialise swimmers in team
          
			locArr[i]= new PeopleLocation(i,strokes[s].getColour());
	      	int speed=(int)(Math.random() * (3)+30); //range of speeds 
			swimmers[s] = new Swimmer(i,teamNo,locArr[i],finish,speed,strokes[s],latch); //hardcoded speed for now
      
        
		}
	//}
  }
	
	
	public void run() {
		try {	
			for(int s=0;s<sizeOfTeam; s++) { //start swimmer threads
            lock.tryLock();
				swimmers[s].start();
            lock.unlock();
				
			}
			
			for(int s=0;s<sizeOfTeam; s++) swimmers[s].join();			//don't really need to do this;
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	

