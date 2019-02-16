/**
 * Sarah Wigg
 * November 2018
 * Ant Colony Simulation
 * CSC 385
 */

import java.util.*;

public class SoldierAnt extends AntObject
{
    
    //constructors
    
    SoldierAnt(Node thisNode){
        currentNode = thisNode;
        lastTurn = 0;
        type = "soldier";
    }
    
    SoldierAnt(){
        
    }
    
    //move to new node
    public void moveTo(Node thisNode){
        
        //remove from current node
        currentNode.deleteAnt(this);
        
        currentNode = thisNode;
        
        //add ant to new node
        currentNode.addAnt(this);
    }
    
    //what happens in event of bala fight
    //50% chance soldier wins
    public void fightBala(){
        
        //get bala ants in node
        LinkedList<AntObject> balaAnts = currentNode.getTypes(new BalaAnt());
        
        Random chance = new Random();
        int chnc = chance.nextInt(2);
        
        if (chnc == 1){
            balaAnts.get(0).dies();
        }
    }
    
    //what happens during each soldier turn
    public void nextTurn(int currentTurn){
        
        
        //check soldier age
        //if over one year, die
        if ((currentTurn - birthTurn ) >= 365 * 10){
            dies();
        }
        
        //fight balas in existing node
        if (currentNode.getTypes(new BalaAnt()).size() >= 1){
            fightBala();
        }
        
        //if no balas in current node, move
        LinkedList<Node> surroundingNodes = currentNode.getSurroundingNodes();
        LinkedList<Node> visibleNodes = new LinkedList<Node>();
        
        
        //loop through list searching for visible nodes
        for (int i = 0; i < surroundingNodes.size(); i++){
            boolean isVisible = surroundingNodes.get(i).getVisibility();
            
            //add to new linkedlist if visible
            if (isVisible = true){
                visibleNodes.add(surroundingNodes.get(i));
            }
        }
        
        Node nextNode = null;
        
        //move to node in visible list if there is bala
        for (int j = 0; j < visibleNodes.size(); j++){
            if (visibleNodes.get(j).getTypes(new BalaAnt()).size() >= 1){
                nextNode = visibleNodes.get(j);
            
            }
        }
        
        //else move to random node
        if (nextNode == null){
            Random move = new Random();
            int goTo = move.nextInt(visibleNodes.size());
            
            nextNode = visibleNodes.get(goTo);
        }
      
        lastTurn = currentTurn;
        moveTo(nextNode);
    }
    
    //remove ants that die
    public void dies(){
        currentNode.deleteAnt(this);
        
    }
}
