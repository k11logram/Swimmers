//M. M. Kuttel 2024 mkuttel@gmail.com
//Class to represent a swim team - which has four swimmers
package medleySimulation;

import medleySimulation.Swimmer.SwimStroke;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class SwimTeam extends Thread {
	
	public static StadiumGrid stadium; //shared 
	private Swimmer [] swimmers;
  	private int teamNo; //team number 
	public static final int sizeOfTeam=4;
   private CountDownLatch latch=new CountDownLatch(sizeOfTeam);
   private CyclicBarrier l =new CyclicBarrier(sizeOfTeam);
   private Lock lock;
   private Lock lock2 = new ReentrantLock();

	
	SwimTeam( int ID, FinishCounter finish,PeopleLocation [] locArr) {
		this.teamNo=ID;
		swimmers= new Swimmer[sizeOfTeam];
	   SwimStroke[] strokes = SwimStroke.values();  // Get all enum constants
		stadium.returnStartingBlock(ID);
      lock =new ReentrantLock();
		for(int i=teamNo*sizeOfTeam,s=0;i<((teamNo+1)*sizeOfTeam); i++,s++) { //initialise swimmers in team
			locArr[i]= new PeopleLocation(i,strokes[s].getColour());
	      int speed=(int)(Math.random() * (3)+30); //range of speeds 
			swimmers[s] = new Swimmer(i,teamNo,locArr[i],finish,speed,strokes[s],lock,latch); //hardcoded speed for now
         
      
        
		}
	//}
  }
	
	
	public void run() {
		try {	
			for(int s=0;s<sizeOfTeam; s++) { //start swimmer threads
            
				swimmers[s].start();
            lock2.lock();
            swimmers[s].latch2.await();
            lock2.unlock();
            		
			}
			synchronized(this){
			for(int s=0;s<sizeOfTeam-1; s++) {
            while(swimmers[s].check.get()){}
            swimmers[s+1].latch.countDown();
         }}		//don't really need to do this;
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	

