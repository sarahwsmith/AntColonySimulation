/**
 * Sarah Wigg
 * November 2018
 * Ant Colony Simulation
 * CSC 385
 */

import java.util.*;

// class governing characteristics of each node in colony
public class Node {
    
    //variables
    ColonyNodeView myColonyNodeView;
    Colony myColony;
    
    int x;
    int y;
    
    int pheromoneLevel;
    int foodLevel;
    
    boolean isVisible;
    boolean queenPresent;
    boolean balaPresent;
    
    int numOfAnts;
    
    LinkedList<AntObject> antsToAdd;
    LinkedList<AntObject> antsToDelete;
    LinkedList<AntObject> presentAnts;
    
    boolean turnInProgress;
    String label = "X: " + x + ", " + "Y: " + y;
    
    //constructors
    Node(ColonyNodeView cnv, int thisx, int thisy){
        
        isVisible = false;
        queenPresent = false;
        balaPresent = false;
        
        numOfAnts = 0;
        
        x = thisx;
        y = thisy;
        
        //25% chance revealed square has 500-1000 units of food
        Random random = new Random();
        if (random.nextInt(100) < 25){
            foodLevel = random.nextInt(501)+500;
            pheromoneLevel= random.nextInt(501)+500;
        }
        
        else {
        	foodLevel = 0;
        	pheromoneLevel = 0;
        
        }
        
        //list of ants leaving/entering cell
        //must be separate -- cannot edit master list while turn in progress
        presentAnts = new LinkedList<AntObject>();
        antsToAdd = new LinkedList<AntObject>();
        antsToDelete = new LinkedList<AntObject>();

        myColonyNodeView = cnv;

        
    }
    
    //mutators
    
    public void setPheromoneLevel(int level){
      pheromoneLevel = level;  
    }
    
    public int getPheromoneLevel(){
        return pheromoneLevel;
    }
    
    public void setFoodLevel(int level){
        foodLevel = level;
    }
    
    public int getFoodLevel(){
        return foodLevel;
    }
    
    public void setVisibility(boolean vis){
        isVisible = vis;
        
        if (isVisible == true){
            myColonyNodeView.showNode();
        }
        
        else if (isVisible == false){
            myColonyNodeView.hideNode();
        }
    }
    
    public void updateNode() {
        this.myColonyNodeView.showNode();
    }
    
    public boolean getVisibility(){
        return isVisible;
    }
    
    public void setX(int thisX){
        x = thisX;
    }
    
    public int returnX(){
        return x;
    }
    
    public void setY(int thisY){
        y = thisY;
    }
    
    public int returnY(){
        return y;
    }
    
    
    public boolean returnQueen(){
        return queenPresent;
    }
    
    public void setQueen(boolean val) {	
    queenPresent = val;
    }
    
    //add ants to node
    public void addAnt(AntObject newAnt){
        
        //bug fix -- only add ants after turn is complete
        if (turnInProgress = false){
            presentAnts.add(newAnt);
        }
        else {
            antsToAdd.add(newAnt);
        }
    }
    
    //remove ants that exited/died
    public void deleteAnt(AntObject goneAnt){
        
        
        if (turnInProgress = false){
            presentAnts.remove(goneAnt);
        }
        
        else{
            antsToDelete.add(goneAnt);
        }
    }
   
    
    //refresh list of ants in node
    public void refreshAntList(){
        
        for(int i = 0 ; i < antsToDelete.size(); i++)
        {
            presentAnts.remove(antsToDelete.get(i));
        }
        
        for(int j = 0; j < antsToAdd.size(); j++){
            presentAnts.add(antsToAdd.get(j));
        }
        
        antsToDelete.clear();
        antsToAdd.clear();
    }
    
    
    //get surrounding nodes for movement
    public LinkedList<Node> getSurroundingNodes(){
        return myColony.getSurroundingNodes(this);
    }
    
    
    //show new view each turn
    public void refreshNode(){
        
        //set food and pheromone levels
        myColonyNodeView.setFoodAmount(foodLevel);
        myColonyNodeView.setPheromoneLevel(pheromoneLevel);
        
        //set count for ant icon display in GUI
        
        myColonyNodeView.setForagerCount(countTypes(new ForagerAnt()));
        if (countTypes(new ForagerAnt()) > 0){
            myColonyNodeView.showForagerIcon();
        }
        else {
            myColonyNodeView.hideForagerIcon();
        }
        
        myColonyNodeView.setScoutCount(countTypes(new ScoutAnt()));
        if (countTypes(new ScoutAnt()) > 0){
            myColonyNodeView.showScoutIcon();
        }
        else{
            myColonyNodeView.hideScoutIcon();
        }
        
        myColonyNodeView.setBalaCount(countTypes(new BalaAnt()));
        if (countTypes(new BalaAnt()) > 0){
            myColonyNodeView.showBalaIcon();
        }
        else{
            myColonyNodeView.hideBalaIcon();
        }
        
        myColonyNodeView.setSoldierCount(countTypes(new SoldierAnt()));
        if (countTypes(new SoldierAnt()) > 0){
            myColonyNodeView.showSoldierIcon();
        }
        else{
            myColonyNodeView.hideSoldierIcon();
        }
        
        //check for queen's presence 
        if (countTypes(new QueenAnt()) > 0){
            queenPresent = true;
            
            myColonyNodeView.setQueen(true);
            myColonyNodeView.showQueenIcon();
        }
    }
    
    //processes next turn
    public void newTurn(int thisTurn){
        //keep track of turn in progress
        //DO NOT modify linked lists while turn in progress
        turnInProgress = true;
        
        //3% chance balas appear once per turn
        QueenAnt queen = new QueenAnt();
        //cannot execute w/o null pointer exception
        Node balaEntry = this.myColony.colonyNodes[0][26];
        queen.hatchBala(balaEntry);
        
        //pheromone needs to decrease by 50% once per day
        if (thisTurn%10 == 0 && thisTurn > 0){
            this.setPheromoneLevel(getPheromoneLevel() / 2);
            
        }
        
        //all ants in node take turn
        for (int i = 0; i < presentAnts.size(); i++){
            presentAnts.get(i).nextTurn(thisTurn);
        }
        
        
        turnInProgress = false;
        
        //refresh ant list and view
        refreshNode();
        refreshAntList();
        refreshNode();
    }
    
    //set colony mutator
    public void setColony(Colony thisColony){
        myColony = thisColony;
    }
    
    //count how many ants in node by type
    public int countTypes(AntObject myAntType) {
    	
        int numAnts = 0;
        
        for (int i = 0; i < presentAnts.size(); i++) {
        	
            if (presentAnts.get(i).getClass() == myAntType.getClass()) {
                numAnts++;
            }
        }
        return numAnts;
    }
    
    //get present ant list(by type)
    public LinkedList<AntObject> getTypes(AntObject thisAnt){
        
        LinkedList<AntObject> listTypes = new LinkedList<AntObject>();
        
        
        for (int i = 0; i < presentAnts.size(); i++) {
        	
        	//bug fix - compare class of each ant in list to target ant
            if (thisAnt.getClass() == presentAnts.get(i).getClass()) {
                listTypes.add(presentAnts.get(i));
            }
        }
        return listTypes;
    }

    
}

