/**
 * Sarah Wigg
 * November 2018
 * Ant Colony Simulation
 * CSC 385
 */

import java.util.*;


public class ForagerAnt extends AntObject{
    
	//use array list vs. linked list -- need to retrieve based on position
    ArrayList<Node> retraceSteps;
    int whichStep;
    boolean returnToNestMode;
    int foodCount;
    
    //constructor
    public ForagerAnt (Node thisNode){
        currentNode = thisNode;
        lastTurn = 0;
        type = "forager";
        
        returnToNestMode = false;
        foodCount = 0;
        
        //need to maintain movement history while searching for food
        retraceSteps = new ArrayList<Node>();
        retraceSteps.add(currentNode);
    }
    
    public ForagerAnt(){
        
    }
    
    //move to new node
    public void moveTo(Node thisNode){
    	
        currentNode.deleteAnt(this);
        currentNode = thisNode;
        currentNode.addAnt(this);
        
        //if queen is in node, leave food!
        boolean isQueen = currentNode.returnQueen();
        
        if (isQueen == true && returnToNestMode == true){

            //you made it back -- clear movement history
            retraceSteps.clear();
            
            //add food to current node
            int foodNow = currentNode.getFoodLevel();
            currentNode.setFoodLevel(foodNow + 1);
            
            returnToNestMode = false;
            foodCount = 0;
            
        }
        
        //add movement to movement history list
        retraceSteps.add(currentNode);
    }
    
    //what happens on each turn
    public void nextTurn(int thisTurn){
                
        //check age
        if ((thisTurn - birthTurn) >= 365 * 10){
            dies();
        }
        
        Node nextNode = null;
        
        //check to see if in return to nest mode
        if (returnToNestMode == true){
            //retrace steps to queen
    
        	whichStep--;
            nextNode = retraceSteps.get(whichStep);
        	
            //drop off pheromone when on way back
            leavePheromone();
        }
        
        //if not, keep looking for food
        else{
            nextNode = maxPheromone();
        }
        
        moveTo(nextNode);
        
        //check to see if food was found after moving
        if (returnToNestMode == false){
            found();
        }
        
        lastTurn = thisTurn;
    }
    
    //forager found food
    public boolean found(){
        
        //pick up food when queen is not present and food is
        boolean isQueen = currentNode.returnQueen();
        
        if (isQueen == false && currentNode.getFoodLevel() > 0){
            
        	//decrement current food level in node
            int foodNow = currentNode.getFoodLevel();
            currentNode.setFoodLevel(foodNow - 1);
            
            //now go back to queen
            returnToNestMode = true;
            
            //need to start moving through move history list
            whichStep = retraceSteps.size()-1;
            
            return true;
        }
        
        else{
        	
            return false;
        }
    }
    
    //determine where max pheromone is
    public Node maxPheromone(){
        
        LinkedList<Node> surroundingNodes = currentNode.getSurroundingNodes();
        LinkedList<Node> validNodes = new LinkedList<Node>();
        
        Node theMost = null;;
        
        //create list of visible nodes from surrounding nodes
        for (int i = 0; i < surroundingNodes.size(); i++){
            
            boolean isVisible = surroundingNodes.get(i).getVisibility();
            
            if (isVisible == true){
                validNodes.add(surroundingNodes.get(i));
            }
        }
        
        //return node that is visible with highest pheromone count
     int temp = 0;
     int position = 0;
     
     for (int j = 0; j < validNodes.size(); j++) {
    	 
    	 if(validNodes.get(j).getPheromoneLevel() > temp) {
    		 
    	 temp = validNodes.get(j).getPheromoneLevel();
    	 position = j;
    	 
    	 }
     }
     theMost = validNodes.get(position);
        
        return theMost;
    }
    
    //drop off pheromone along the way
    public void leavePheromone(){
        //check to see if queen is present
        //only leave pheromone if < 1000 present
        boolean isQueen = currentNode.returnQueen();
        
        if (isQueen == false && currentNode.getPheromoneLevel() < 1000){
            int pheromone = currentNode.getPheromoneLevel();
            currentNode.setPheromoneLevel(pheromone + 10);
        }
    }
    
    public int getFoodCount() {
    	return foodCount;
    }
    
    //remove ants that die
    public void dies(){
        currentNode.deleteAnt(this);
        
    }
    }
    
