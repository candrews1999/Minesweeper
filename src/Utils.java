import java.util.ArrayList;
import java.util.Random;
import javalib.worldimages.*;

class Utils {

  //links the given gameCells to each other accordingly 
  public ArrayList<Cell> linkCells(ArrayList<Cell> gameCells) {
    for (int i = 0; i < gameCells.size(); i++) {
      gameCells.get(i).addNeighbors(gameCells, i);
    }
    return gameCells;  
  }

  //adds the cell associate with the desired index in the gameCells
  public void addCell(Cell currCell, int i, ArrayList<Cell> gameCells) {   
    if (i < gameCells.size() && i > -1) {
      currCell.neighbors.add(gameCells.get(i));
    } 
  }

  //generates the amount of random numbers need according to the number of bombs constant
  public ArrayList<Integer> generateRandomNums(Random rand) {
    ArrayList<Integer> listOfIndex = new ArrayList<Integer>();
    ArrayList<Integer> randomNums = new ArrayList<Integer>();
    //creates list of all indices
    for (int i = 0; i < Constant.numberOfCells; i++) {
      listOfIndex.add(i);
    }
    //shuffles list
    for (int i = 0; i < Constant.numberOfCells; i++) {
      Integer elementAtCurrIndex = listOfIndex.get(i);
      Integer randomIndex = rand.nextInt(Constant.numberOfCells - 1);
      Integer randomElement = listOfIndex.get(randomIndex); 
      listOfIndex.set(randomIndex, elementAtCurrIndex);
      listOfIndex.set(i, randomElement);
    }
    //chooses first three elements and places them in a randomNums list
    for (int i = 0; i < Constant.numberOfBombs; i++) {
      randomNums.add(listOfIndex.get(i));
    }
    return randomNums;
  }
  
  // calculates the cell that was clicked on 
  public Cell calculateCellClicked(Posn pos, ArrayList<Cell> cells) {
    int xGrid = (int) Math.floor((pos.x / Constant.cellWidth));
    int yGrid = (int) Math.floor((pos.y / Constant.cellHeight));
    int indexForCell = 0;
    for (int m = 0; m < yGrid; m++) {
      for (int i = 0; i < Constant.widthOfGrid; i++) {
        indexForCell++;
      }
    }
    
    for (int l = 0; l < xGrid; l++) {
      indexForCell++;
    }
   
    return cells.get(indexForCell);
  }
}

