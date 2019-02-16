/**
 * Sarah Wigg
 * November 2018
 * Ant Colony Simulation
 * CSC 385
 */

import java.util.*;
import javax.swing.*;

public class QueenAnt extends AntObject
{
    //attributed unique to queen
    int id;
    int lastIdHatched;
    int currentTurn;
    String typeOfAnt;
    
    //queen constructors
    public QueenAnt(Node node){
        lastIdHatched = 0;
        id = 0;
        currentNode = node;
        typeOfAnt = "queen";
    }
    
    public QueenAnt(){
        
    }
    
    
    //hatch method
    //1 ant hatches per day (per 10 turns)
    //50% chance of forager ant, 25% scout, 25% soldier
    public void newAnt(AntObject thisAnt){
      //generate random number to determine ant type
      Random num = new Random();
      int antType = num.nextInt(100);
      
      AntObject newAnt;
      
        if (antType < 50){
          newAnt = new ForagerAnt(currentNode);
        }
        else if (antType >= 50 && antType < 75){
           newAnt = new ScoutAnt(currentNode); 
        }
        else {
            newAnt = new SoldierAnt(currentNode);
        }
        
        //calling method to hatch specific ant vs. random ant generation
        if (thisAnt != null){
            newAnt = thisAnt;
        }
        
        //increment ID counter
        ++lastIdHatched;
        
        //set ID and birthday
        newAnt.setAntID(lastIdHatched);
        newAnt.setBirthTurn(currentTurn);
        
        //add ant to current node
        currentNode.addAnt(newAnt);
    }
    
    //queen death method
    public void dies(){
        
        //remove queen from current node
        currentNode.myColonyNodeView.hideQueenIcon();
        
        //display pop-up window explaining death
        JOptionPane.showMessageDialog(null, "The queen is dead. Game over!", 
	                "Aw, shucks!", JOptionPane.ERROR_MESSAGE);
	                
        //end simulation with queen's death
        currentNode.myColony.thisSim.gameOver();
        
    }
    
    //queen eats method
    public void eatFood(){
        //get food amount in queen's node
        int food = currentNode.getFoodLevel();
        
        //queen dies if there is no food in node
        if (food < 1){
            this.dies();
        }
        
        //decrement food 
        --food;
        
        //set new food amount in node
        currentNode.setFoodLevel(food);
    }
    
    //bala hatch method
    //queen does not give birth to bala ant
    //they appear at the periphery of grid
    //3% chance/turn bala ant will appear
    public void hatchBala(Node thisNode){
        Random ranNum = new Random();
        int randomNum = ranNum.nextInt(100);
        
        
        //3% chance of bala appearing each turn
        if (randomNum < 3){
        	
        	Node balaEntry = thisNode;
        	
            BalaAnt newBala = new BalaAnt(balaEntry);
            
            //set ID and birth turn
            ++lastIdHatched;
            newBala.setAntID(lastIdHatched);
            
            newBala.setBirthTurn(currentTurn);
            
            //add bala ant to entry node
            balaEntry.addAnt(newBala);
            
        }
        else {
        	return;
        }
        }
    
    
    //what happens during each turn
    public void nextTurn(int currentTurn){
        
        //first, check queen's age 
        //if over 20 years old, she dies
        if (currentTurn > (10 * 20 * 365)){
            dies();
        }
        //if there's no food, queen dies
        if (currentNode.getFoodLevel() <= 0){
            dies();
        }
        
        //queen eats once per turn
        eatFood();
        
        //queen gives birth once per day
        if (currentTurn % 10 == 0){
            this.newAnt(null);
        }
        
    }
}
