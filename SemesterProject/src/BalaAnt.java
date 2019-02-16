/**
 * Sarah Wigg
 * November 2018
 * Ant Colony Simulation
 * CSC 385
 */

import java.util.*;

public class BalaAnt extends AntObject
{
    
    //constructors
    BalaAnt(Node node){
        currentNode = node;
        lastTurn = 0;
        type = "bala";
    }
    
    BalaAnt(){
        
    }
    
    //move to new node
    //move to new node
    public void moveTo(Node thisNode){
        
        //remove from current node
        currentNode.deleteAnt(this);
        
        currentNode = thisNode;
        
        //add ant to new node
        currentNode.addAnt(this);
    }
    
    //what happens with each turn
    public void nextTurn(int currentTurn){
        
        
        //check age 
        //death after 1 year
        if ((currentTurn - birthTurn) >= 10 * 365){
            dies();
        }
        
        //check to see if there are non-bala ants in square
        LinkedList<AntObject> nonBalas = new LinkedList<AntObject>();
        
        if (currentNode.getTypes(new SoldierAnt()).size() >= 1){
            
            for (int i = 0; i < currentNode.getTypes(new SoldierAnt()).size(); i++){
                nonBalas.add(currentNode.getTypes(new SoldierAnt()).get(i));
            }
        }
        
        else if (currentNode.getTypes(new QueenAnt()).size() >= 1){
            
            for (int j = 0; j < currentNode.getTypes(new QueenAnt()).size(); j++){
                nonBalas.add(currentNode.getTypes(new QueenAnt()).get(j));
            }
        }
        
        else if (currentNode.getTypes(new ScoutAnt()).size() >= 1){
            
            for (int k = 0; k < currentNode.getTypes(new ScoutAnt()).size(); k++){
                nonBalas.add(currentNode.getTypes(new ScoutAnt()).get(k));
            }
        }
        
        else if (currentNode.getTypes(new ForagerAnt()).size() >= 1){
            
            for (int l = 0; l < currentNode.getTypes(new ForagerAnt()).size(); l++){
                nonBalas.add(currentNode.getTypes(new ForagerAnt()).get(l));
            }
        }
        
        Node nextNode; 
        
        if (nonBalas.size() >= 1){
            //randomly fight one of the non-bala ants
            Random fight = new Random();
            int thisOne = fight.nextInt(nonBalas.size());
        
            Random chance = new Random();
            int chnc = chance.nextInt(2);
        
            if (chnc == 1){
                nonBalas.get(thisOne).dies();
            }
        }
        
        //else move to random adjacent square
        else {
            Random move = new Random();
            int goTo = move.nextInt(currentNode.getSurroundingNodes().size());
            
            nextNode = currentNode.getSurroundingNodes().get(goTo);
            
            moveTo(nextNode);
        }
        
        
        lastTurn = currentTurn;
    }
    
    //remove ants that die
    public void dies(){
        currentNode.deleteAnt(this);
        
    }
}
