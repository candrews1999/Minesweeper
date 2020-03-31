import java.util.ArrayList;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

//represents a cell in a Minesweeper game
class Cell {
  boolean bomb;
  boolean flagged;
  boolean revealed;
  ArrayList<Cell> neighbors;

  Cell() {
    this.bomb = false;
    this.flagged = false;
    this.revealed = false;
    this.neighbors = new ArrayList<Cell>();
  }

  Cell(boolean bomb, boolean flagged, boolean revealed, ArrayList<Cell> neighbors) {
    this.bomb = bomb;
    this.flagged = flagged;
    this.revealed = revealed;
    this.neighbors = neighbors;
  }

  Cell(boolean bomb) {
    this.bomb = bomb;
    this.flagged = false;
    this.revealed = false;
    this.neighbors = new ArrayList<Cell>();
  }

  //adds the appropriate cells as neighbors to this cell
  void addNeighbors(ArrayList<Cell> gameCells, int i) {
    // given the nature of the far left column, takes care to add only the neighbors relevant
    if (i % Constant.widthOfGrid == 0) {
      //adds top cell
      new Utils().addCell(this, i - Constant.widthOfGrid, gameCells);
      //adds top right cell
      new Utils().addCell(this, i - (Constant.widthOfGrid - 1), gameCells);
      //adds middle right cell
      new Utils().addCell(this, i + 1, gameCells);
      //adds bottom cell
      new Utils().addCell(this, i + Constant.widthOfGrid, gameCells);
      //adds bottom right cell
      new Utils().addCell(this, i + (Constant.widthOfGrid + 1), gameCells);
    }
    // given the nature of the far right column, takes care to add only the neighbors relevant
    else if (i % Constant.widthOfGrid == Constant.widthOfGrid - 1 ) {
      //adds top left cell
      new Utils().addCell(this, i - (Constant.widthOfGrid + 1), gameCells);
      //adds top cell
      new Utils().addCell(this, i - Constant.widthOfGrid, gameCells);
      //adds middle left cell
      new Utils().addCell(this, i - 1, gameCells);
      //adds bottom left cell
      new Utils().addCell(this, i + (Constant.widthOfGrid - 1), gameCells);
      //adds bottom cell
      new Utils().addCell(this, i + Constant.widthOfGrid, gameCells);
    }
    // for all other cells, the conditions offered in addCell provide enough protection to
    // guarentee only the correct neighbors are added
    else {
      //adds top left cell
      new Utils().addCell(this, i - (Constant.widthOfGrid + 1), gameCells);
      //adds top cell
      new Utils().addCell(this, i - Constant.widthOfGrid, gameCells);
      //adds top right cell
      new Utils().addCell(this, i - (Constant.widthOfGrid - 1), gameCells);
      //adds middle left cell
      new Utils().addCell(this, i - 1, gameCells);
      //adds middle right cell
      new Utils().addCell(this, i + 1, gameCells);
      //adds bottom left cell
      new Utils().addCell(this, i + (Constant.widthOfGrid - 1), gameCells);
      //adds bottom cell
      new Utils().addCell(this, i + Constant.widthOfGrid, gameCells);
      //adds bottom right cell
      new Utils().addCell(this, i + (Constant.widthOfGrid + 1), gameCells);
    }
  }

  // draws this cell
  void draw(WorldScene scene, int x, int y) {
    WorldImage cell = new RectangleImage(Constant.cellWidth,
        Constant.cellHeight, OutlineMode.OUTLINE, Constant.cellColor);
    WorldImage bomb = new CircleImage(Constant.cellWidth / 4, OutlineMode.SOLID, Color.BLACK);
    WorldImage bombCell = new OverlayImage(bomb, cell);
    WorldImage flag = 
        new EquilateralTriangleImage(Constant.cellWidth / 4, OutlineMode.SOLID, Color.GREEN);
    WorldImage flagCell = new OverlayImage(flag, cell);
    //if its a bomb and its revealed, show bomb overlayed on cell
    if (this.bomb && this.revealed) {
      scene.placeImageXY(bombCell, x, y);
    }
    //if its flagged and isn't revealed, show flagged cell
    else if (this.flagged && !this.revealed) {
      scene.placeImageXY(flagCell, x, y);
    }
    //if its not a bomb and is revealed, check neighbors and if neighbors are an unrevealed bomb, 
    //show mine neighbor number, else just gray 
    else if (!this.bomb && this.revealed) {
      scene.placeImageXY(this.numOrGray(), x, y);
    }
    //if its not flagged and not revealed, show normal cell
    else if (!this.flagged && !this.revealed) {
      scene.placeImageXY(cell, x, y);
    }
  }

  //draws the appropriate cell once a cell has been revealed and it is not a bomb
  WorldImage numOrGray() {
    WorldImage cell = new RectangleImage(Constant.cellWidth,
        Constant.cellHeight, OutlineMode.OUTLINE, Constant.cellColor);
    int numNeighboringMines = this.numberMinesNeighboring();
    //if cell has mine neighbors draw its mine count
    if (numNeighboringMines > 0) {
      WorldImage numMines = new TextImage(Integer.toString(numNeighboringMines),
          Constant.FONT_SIZE, Constant.TEXT_COLOR);
      return new OverlayImage(numMines, cell);
    }
    //if no neighbors are bombs draw it as a gray cell
    else {
      return new RectangleImage(Constant.cellWidth,
          Constant.cellHeight, OutlineMode.SOLID, Color.GRAY);

    }
  }

  // calculates the X position this cell should be drawn at
  int calculateX(int index) {
    return Constant.cellWidth / 2  + (Constant.cellWidth
        * (index % Constant.widthOfGrid));
  }

  // calculates the Y position this cell should be spawned at
  int calculateY(int index) {
    return Constant.cellHeight / 2 + (Constant.cellWidth
        * ((int) Math.floor(index / Constant.widthOfGrid)));
  }

  // counts the number of mines neighboring this cell
  int numberMinesNeighboring() {
    int numberMines = 0;
    for (Cell c : this.neighbors) {
      if (c.bomb) {
        numberMines++;
      }
    }
    return numberMines;
  }

  //Floodfills until a cell is reached recursively that has a bomb as a neighbor
  void flood(ArrayList<Cell> checked) {
    checked.add(this);
    this.revealed = true;
    if (this.numberMinesNeighboring() == 0) {
      for (Cell c : this.neighbors) {
        //don't flood if neighbor has been checked, revealed already, or is flagged
        if (!checked.contains(c) && !c.revealed && !c.flagged) {
          c.flood(checked);
        }
      }
    }
  }
}
