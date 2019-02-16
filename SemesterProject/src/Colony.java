/**
 * Sarah Wigg
 * November 2018
 * Ant Colony Simulation
 * CSC 385
 */

import java.util.*;

public class Colony
{
    //GUI variables
    ColonyView myColonyView;
    ColonyNodeView myColonyNodeView;
    
    //Use 2d array to store nodes
    Node[][] colonyNodes;
    
    Node thisNode;
    Simulation thisSim;
    
    //constructors
    Colony(ColonyView cView, Simulation sim){
        
        thisSim = sim;
        colonyNodes = new Node[27][27];
        myColonyView = cView;
        
    }
    
    Colony(){
    	
    }
    
    //add a node to colony
    public void addNewNode(Node newNode, int x, int y){
        
        //add node object at corresponding cell in 2d array
        colonyNodes[x][y] = newNode;
       
    }
    
    
    //initial state of new colony
    public void startColony() {
    	
        for (int x = 0; x < 27; x++) {
            for (int y = 0; y < 27; y++) {
            	
                myColonyNodeView = new ColonyNodeView();
                thisNode = new Node(myColonyNodeView, x, y);
                
                addNewNode(thisNode, x, y);
                
                myColonyView.addColonyNodeView(myColonyNodeView, x, y);

                thisNode.setColony(this);

                //define parameters for center node
                if (x == 13 && y == 13) {
                	
                	//1000 units of food
                	thisNode.setFoodLevel(1000);
                	
                    QueenAnt newQueen = new QueenAnt(thisNode);
                    thisNode.addAnt(newQueen);
                    thisNode.setQueen(true);

                    //10 soldiers
                    //50 foragers
                    //4 scouts
                    for (int a = 0; a < 10; a++) {
                        newQueen.newAnt(new SoldierAnt(thisNode));
                    }
                    for (int b = 0; b < 50; b++) {
                        newQueen.newAnt(new ForagerAnt(thisNode));
                    }
                    for (int m = 0; m < 4; m++) {
                        newQueen.newAnt(new ScoutAnt(thisNode));
                    }
                }
                
                //set visibility of nodes surrounding center
                if (x == 12 && y == 12) {
                	  thisNode.setVisibility(true);
                      thisNode.setQueen(false);
                }
                else if (x == 12 && y == 13) {
                	  thisNode.setVisibility(true);
                      thisNode.setQueen(false);
                }
                else if (x == 12 && y == 14) {
                	  thisNode.setVisibility(true);
                      thisNode.setQueen(false);
                }
                else if(x == 13 && y == 12) {
                	  thisNode.setVisibility(true);
                      thisNode.setQueen(false);
                }
                else if (x == 13 && y == 13) {
                	  thisNode.setVisibility(true);
                      thisNode.setQueen(false);
                }
                else if (x == 13 && y == 14) {
                	  thisNode.setVisibility(true);
                      thisNode.setQueen(false);
                }
                else if (x == 14 && y == 12) {
                	  thisNode.setVisibility(true);
                      thisNode.setQueen(false);
                }
                else if (x == 14 && y == 13) {
                	  thisNode.setVisibility(true);
                      thisNode.setQueen(false);
                }
                else if (x == 14 && y == 14) {
                	  thisNode.setVisibility(true);
                      thisNode.setQueen(false);
                }
                
                //set coordinate display
                myColonyNodeView.setID("X: " + x + ", Y: " + y);
                  
                }


            }
        }
    
        //push nodes to next turn
        public void newTurn(int thisTurn){
            
            for (int x = 0; x < 27; x++){
                for (int y = 0; y < 27; y++){
                    
                    colonyNodes[x][y].newTurn(thisTurn);
                    
                }
                
            }
                
        }
        
        //get nodes surrounding a given node
        public LinkedList<Node> getSurroundingNodes(Node aNode){
        	
           LinkedList<Node> surroundingNodes;
           surroundingNodes = new LinkedList<Node>();
           
            //get coordinates of given node
           int x = aNode.returnX();
           int y = aNode.returnY();
           
           //loop through looking at all X and Y values +/-1 to given node 
           //this finds neighboring nodes
           for (int newX = -1; newX <= 1; newX++){
               for (int newY = -1; newY <= 1; newY++){
            	   
                   //bug fix - index out of bounds exception -- add try catch block
                   
                   if (newX != 0 || newY != 0){
                       try {
                	   Node newNode = colonyNodes[newX + x][newY + y];
                       
                       surroundingNodes.add(newNode);
                       }
                       catch (Exception e) {}
                    }
                   
                }
                
            }
            
           return surroundingNodes;
        }
        
        //get colony view
        public ColonyView getColonyView(){
            return myColonyView;
        }
        
        
    }

