/**
 * Sarah Wigg
 * November 2018
 * Ant Colony Simulation
 * CSC 385
 */

import java.util.*;

public class ScoutAnt extends AntObject
{

    //constructors 
    
    ScoutAnt (Node thisNode){
        currentNode = thisNode;
        lastTurn = 0;
        type = "scout";
    }
    
    ScoutAnt(){
        
    }
    
    //move to new node
    public void moveTo(Node thisNode){
        
        //remove from current node
        currentNode.deleteAnt(this);
        
        currentNode = thisNode;
        
        //add ant to new node
        currentNode.addAnt(this);
        
        //is new node visible? if not, it is now
        boolean visibility = currentNode.getVisibility();
        if (visibility == false){
            currentNode.setVisibility(true);
            currentNode.refreshNode();
            currentNode.updateNode();
        }
    }
    
    
    //what happens during each turn
    public void nextTurn(int currentTurn){
        
        //check age
        if ((currentTurn - birthTurn) >= 365 * 10){
            dies();
        }
        
        //randomly move to surrounding node
        LinkedList<Node> surroundingNodes = new LinkedList<Node>();
        surroundingNodes = currentNode.getSurroundingNodes();
        
        Random move = new Random();
        int goTo = move.nextInt(surroundingNodes.size());
        Node nextNode;
        nextNode = surroundingNodes.get(goTo);
        
        moveTo(nextNode);
        
        lastTurn = currentTurn;
    }
    
    //remove ants that die
    public void dies(){
        currentNode.deleteAnt(this);
        
    }
}