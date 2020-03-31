import java.util.ArrayList;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Random;
//TESTED WITH A 3x3 GRID BUT INCREASE GRID SIZE CONSTANTS TO PLAY A LARGER GAME
//represents the constants used in Minesweeper

class Constant {
  static final int heightOfGrid = 3;
  static final int widthOfGrid = 3;
  static final int numberOfCells = heightOfGrid * widthOfGrid;
  static final int numberOfBombs = 3;
  static final int cellWidth = 100;
  static final int cellHeight = 100;
  static final int sceneWidth = cellWidth * widthOfGrid;
  static final int sceneHeight = cellHeight * heightOfGrid;
  static final Color cellColor = Color.red;
  static final Color TEXT_COLOR = Color.BLACK;
  static final int FONT_SIZE = 30;
  static final double tickRate = 0.1;
}  

//represents a Minesweeper game
class Minesweeper extends World {
  ArrayList<Cell> cells;
  Random rand;

  //the constructor used for the actual game
  Minesweeper() {
    this.rand = new Random();
    ArrayList<Cell> cellsInGame = new ArrayList<>();
    ArrayList<Integer> bombIndices = new Utils().generateRandomNums(this.rand);
    //randomly places bombs to cells
    for (int i = 0; i < Constant.numberOfCells; i++) {
      if (bombIndices.contains(i)) {
        cellsInGame.add(new Cell(true));
      }
      else {
        cellsInGame.add(new Cell());
      }
    }
    //links the cells neighbors to the cells field of Minesweeper
    this.cells = new Utils().linkCells(cellsInGame);
  }

  Minesweeper(ArrayList<Cell> cells) {
    this.cells = cells;
  }

  //give it a specific Random seed for testing purposes
  Minesweeper(Random rand) {
    this.rand = rand;
    ArrayList<Cell> cellsInGame = new ArrayList<>();
    ArrayList<Integer> bombIndices = new Utils().generateRandomNums(this.rand);
    //randomly places bombs to cells
    for (int i = 0; i < Constant.numberOfCells; i++) {
      if (bombIndices.contains(i)) {
        cellsInGame.add(new Cell(true));
      }
      else {
        cellsInGame.add(new Cell());
      }
    }
    //links the cells neighbors to the cells field of Minesweeper
    this.cells = new Utils().linkCells(cellsInGame);
  }

  //draws the game
  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene();
    for (int i = 0; i < this.cells.size(); i++) {
      this.cells.get(i).draw(scene, 
          this.cells.get(i).calculateX(i), this.cells.get(i).calculateY(i));
    }
    return scene;
  }

  //Determines if the end of the World has been reached, which is when there is a cell that is a 
  //bomb and has been revealed or if all the cells except for the bomb cells have been revealed
  public WorldEnd worldEnds() {
    boolean lost = false;
    int won = 0;
    for (Cell c : this.cells) {
      if (!c.bomb && c.revealed) {
        won = won + 1;
      }
      else if (c.bomb & c.revealed) {
        lost = lost || (c.bomb && c.revealed);
        won = 0;
      }
    }
    if (lost) { 
      return new WorldEnd(true, this.createLastScene(false));
    }
    else if (won == (Constant.numberOfCells - Constant.numberOfBombs)) {
      return new WorldEnd(true, this.createLastScene(true)); 
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  //draws the final scene of Minesweeper with all the cells revealed
  public WorldScene createLastScene(boolean won) {
    this.revealAll();
    WorldScene scene = this.makeScene();
    if (won) {
      scene.placeImageXY(new TextImage("YOU WON", Constant.FONT_SIZE, Color.GREEN), 
          Constant.sceneWidth / 2, Constant.sceneHeight / 2);
    } else {
      scene.placeImageXY(new TextImage("GAME OVER", Constant.FONT_SIZE, Color.RED), 
          Constant.sceneWidth / 2, Constant.sceneHeight / 2);
    }
    return scene;
  }
  
  //changes the revealed fields to true for each of the cells in the game
  public void revealAll() {
    for (Cell c : this.cells) {
      c.revealed = true;
    }
  }

  //handles the right and left mouse click
  public void onMouseClicked(Posn pos, String button) {
    Cell cellClicked = new Utils().calculateCellClicked(pos, this.cells);
    //flag cell if right clicked
    if (button.equals("RightButton")) {
      cellClicked.flagged = !cellClicked.flagged;
    }
    //reveal this cell and floodfill if left button is pressed
    else if (button.equals("LeftButton") && (!cellClicked.flagged)) {
      cellClicked.flood(new ArrayList<Cell>());
    }
  }
}
