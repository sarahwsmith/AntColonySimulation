
/**
 * Sarah Wigg
 * November 2018
 * Ant Colony Simulation
 * CSC 385
 */
public class AntObject
{
    
    // attributes for all ant types (excl. queen)
    
    int antID;
    int birthTurn;
    int lastTurn;
    Node currentNode;
    String type;
    
    // constructor for ant objects
    // will be extended by other ant classes
    
    public AntObject(Node cNode){
       antID = 0;
       lastTurn = 0;
       currentNode = cNode;
       type = " ";
    }
    
    public AntObject(){
        
    }
    
    // set ID mutator
    public void setAntID(int antID){
        this.antID = antID;
    }
    
    //set birthday
    public void setBirthTurn(int currentTurn){
        birthTurn = currentTurn;
    }
    
public void dies() {
	
}
    
    //get ant type
    public String getType(){
        return type;
    }
    
    public void nextTurn(int thisTurn){
    }
}
