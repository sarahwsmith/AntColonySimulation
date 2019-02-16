
/**
 * Sarah Wigg
 * November 2018
 * Ant Colony Simulation
 * CSC 385
 */

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Simulation implements SimulationEventListener, ActionListener {

    Colony myColony;
    AntSimGUI gui;
    
    int turnCounter = 0;
    
    Boolean queen;
    
    Timer timer;
    String time = "";
    

    public static void main(String[] args){
        
        AntSimGUI gui = new AntSimGUI();
        gui.addSimulationEventListener(new Simulation(gui));
        
    }
    
    //constructors
    Simulation(AntSimGUI newGui) {
    	
    	queen = true;
        gui = newGui;
        myColony = new Colony(new ColonyView(27, 27), this);
        gui.initGUI(myColony.getColonyView());
         

    }
    
    Simulation(){
    	
    }

    //end simulation when queen is dead
    public void gameOver() {
    	queen = false;
    	System.exit(0);
        }

    @Override
	public void actionPerformed(ActionEvent arg0) {
    	if (queen = false)
		{
			timer.stop();
		}
		else
		{
			// execute the next turn of the simulation
			nextTurn();
		}
    
    }

//pushes the colony through new turn
    public void nextTurn() {
            myColony.newTurn(turnCounter);
            
            int days = turnCounter / 10;
            int turns = turnCounter % 10;
            String time = (days + " Days & " + turns + " Turns");
            gui.setTime(time);

            turnCounter++;
    }

    
    public void simulationEventOccurred(SimulationEvent simEvent) {

        if (simEvent.getEventType() == SimulationEvent.NORMAL_SETUP_EVENT) {
            //initialize colony -- normal setup
        	myColony.startColony();
        }
        
        //run button pressed
        if (simEvent.getEventType() == SimulationEvent.RUN_EVENT) {
        	timer = new Timer(300, this);
			
			// start the timer
			timer.start();
        	
        }
        
        //step button pressed -- turn by turn
        if (simEvent.getEventType() == SimulationEvent.STEP_EVENT) {
            nextTurn();
        }
    }
}