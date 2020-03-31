import tester.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javalib.impworld.*;
import javalib.worldimages.*;

//used for testing
class ExamplesMinesweeper {
  //used to run the game
  void testGame(Tester t) {
    Minesweeper game = new Minesweeper();
    game.bigBang(Constant.sceneWidth,  Constant.sceneHeight, Constant.tickRate);
  }
  
  Cell zeroCell;
  Cell oneCell;
  Cell twoCell;
  Cell threeCell;
  Cell fourCell;
  Cell fiveCell;
  Cell sixCell;
  Cell sevenCell;
  Cell eightCell;
  ArrayList<Cell> nineBoardCellList;
  ArrayList<Cell> emptyArrayList;
  Random seed0;
  Random seed1;
  Random seed2;
  Random seed3;
  WorldScene emptyWS;
  Minesweeper ms1;

  void initCell() {
    this.seed0 = new Random(0);
    this.seed1 = new Random(1);
    this.seed2 = new Random(2);
    this.seed3 = new Random(3);
    this.zeroCell = new Cell();
    this.oneCell = new Cell();
    this.twoCell = new Cell();
    this.threeCell = new Cell();
    this.fourCell = new Cell();
    this.fiveCell = new Cell();
    this.sixCell = new Cell();
    this.sevenCell = new Cell();
    this.eightCell = new Cell();
    this.nineBoardCellList = new ArrayList<Cell>(Arrays.asList(zeroCell, oneCell,twoCell, threeCell,
        fourCell, fiveCell, sixCell, sevenCell, eightCell));
    this.emptyArrayList = new ArrayList<Cell>();
    this.emptyWS = new WorldScene(Constant.sceneWidth, Constant.sceneHeight);
    this.ms1 = new Minesweeper(this.nineBoardCellList);
  }

  //tests addNeighbors
  void testAddNeighbors(Tester t) {
    initCell();
    zeroCell.addNeighbors(this.nineBoardCellList, 0);
    oneCell.addNeighbors(this.nineBoardCellList, 1);
    twoCell.addNeighbors(this.nineBoardCellList, 2);
    threeCell.addNeighbors(this.nineBoardCellList, 3);
    fourCell.addNeighbors(this.nineBoardCellList, 4);
    fiveCell.addNeighbors(this.nineBoardCellList, 5);
    sixCell.addNeighbors(this.nineBoardCellList, 6);
    sevenCell.addNeighbors(this.nineBoardCellList, 7);
    eightCell.addNeighbors(this.nineBoardCellList, 8);
    t.checkExpect(zeroCell.neighbors, new ArrayList<Cell>(Arrays.asList(this.oneCell,
        this.threeCell, this.fourCell)));
    t.checkExpect(oneCell.neighbors, new ArrayList<Cell>(Arrays.asList(this.zeroCell,
        this.twoCell, this.threeCell, this.fourCell, this.fiveCell)));
    t.checkExpect(twoCell.neighbors, new ArrayList<Cell>(Arrays.asList(this.oneCell,
        this.fourCell, this.fiveCell)));
    t.checkExpect(threeCell.neighbors, new ArrayList<Cell>(Arrays.asList(this.zeroCell,
        this.oneCell, this.fourCell, this.sixCell, this.sevenCell)));

    t.checkExpect(fourCell.neighbors, new ArrayList<Cell>(Arrays.asList(this.zeroCell,
        this.oneCell, this.twoCell, this.threeCell, this.fiveCell,
        this.sixCell, this.sevenCell, this.eightCell)));
    t.checkExpect(fiveCell.neighbors, new ArrayList<Cell>(Arrays.asList(this.oneCell,
        this.twoCell, this.fourCell, this.sevenCell, this.eightCell)));
    t.checkExpect(sixCell.neighbors, new ArrayList<Cell>(Arrays.asList(this.threeCell,
        this.fourCell, this.sevenCell)));
    t.checkExpect(sevenCell.neighbors, new ArrayList<Cell>(Arrays.asList(this.threeCell,
        this.fourCell, this.fiveCell, this.sixCell, this.eightCell)));
    t.checkExpect(eightCell.neighbors, new ArrayList<Cell>(Arrays.asList(this.fourCell,
        this.fiveCell, this.sevenCell)));
  }

  //tests addCell
  void testAddCell(Tester t) {
    initCell();

    new Utils().addCell(zeroCell, 9, nineBoardCellList);
    t.checkExpect(zeroCell.neighbors, emptyArrayList);

    new Utils().addCell(zeroCell, -1, nineBoardCellList);
    t.checkExpect(zeroCell.neighbors, emptyArrayList);

    new Utils().addCell(zeroCell, 1, nineBoardCellList);
    t.checkExpect(zeroCell.neighbors, new ArrayList<Cell>(Arrays.asList(oneCell)));
  }

  //tests linkCells
  void testLinkCells(Tester t) {
    initCell();
    Cell zeroCellClone = new Cell();
    Cell oneCellClone = new Cell();
    Cell twoCellClone = new Cell();
    Cell threeCellClone = new Cell();
    Cell fourCellClone = new Cell();
    Cell fiveCellClone = new Cell();
    Cell sixCellClone = new Cell();
    Cell sevenCellClone = new Cell();
    Cell eightCellClone = new Cell();

    ArrayList<Cell> nineBoardCellListUnLinked = new ArrayList<Cell>(Arrays.asList(zeroCellClone,
        oneCellClone, twoCellClone, threeCellClone, fourCellClone, fiveCellClone, sixCellClone,
        sevenCellClone, eightCellClone));
    new Utils().linkCells(nineBoardCellList);
    zeroCellClone.addNeighbors(this.nineBoardCellList, 0);
    oneCellClone.addNeighbors(this.nineBoardCellList, 1);
    twoCellClone.addNeighbors(this.nineBoardCellList, 2);
    threeCellClone.addNeighbors(this.nineBoardCellList, 3);
    fourCellClone.addNeighbors(this.nineBoardCellList, 4);
    fiveCellClone.addNeighbors(this.nineBoardCellList, 5);
    sixCellClone.addNeighbors(this.nineBoardCellList, 6);
    sevenCellClone.addNeighbors(this.nineBoardCellList, 7);
    eightCellClone.addNeighbors(this.nineBoardCellList, 8);
    t.checkExpect(nineBoardCellList, nineBoardCellListUnLinked);
  }

  //tests generateRandomNums
  void testGenerateRandomNums(Tester t) {
    initCell();
    ArrayList<Integer> randomNumsGenerated0 = new Utils().generateRandomNums(seed0);
    ArrayList<Integer> randomNumsGenerated1 = new Utils().generateRandomNums(seed1);
    ArrayList<Integer> randomNumsGenerated3 = new Utils().generateRandomNums(seed3);
    t.checkExpect(randomNumsGenerated0, new ArrayList<Integer>(Arrays.asList(7, 2, 3)));
    t.checkExpect(randomNumsGenerated1, new ArrayList<Integer>(Arrays.asList(0, 4, 6)));
    t.checkExpect(randomNumsGenerated3, new ArrayList<Integer>(Arrays.asList(1, 8, 5)));
  }

  //tests MinesweeperConstructor
  void testMinesweeperConstructor(Tester t) {
    initCell();
    zeroCell.addNeighbors(this.nineBoardCellList, 0);
    oneCell.addNeighbors(this.nineBoardCellList, 1);
    twoCell.addNeighbors(this.nineBoardCellList, 2);
    threeCell.addNeighbors(this.nineBoardCellList, 3);
    fourCell.addNeighbors(this.nineBoardCellList, 4);
    fiveCell.addNeighbors(this.nineBoardCellList, 5);
    sixCell.addNeighbors(this.nineBoardCellList, 6);
    sevenCell.addNeighbors(this.nineBoardCellList, 7);
    eightCell.addNeighbors(this.nineBoardCellList, 8);

    Minesweeper seeded2Minesweeper = new Minesweeper(seed2);
    this.zeroCell.bomb = true;
    this.twoCell.bomb = true;
    this.sevenCell.bomb = true;
    t.checkExpect(seeded2Minesweeper.cells, new ArrayList<Cell>(Arrays.asList(
        zeroCell, oneCell, twoCell, 
        threeCell, fourCell, fiveCell, sixCell, sevenCell, eightCell)));
    Minesweeper seeded3Minesweeper = new Minesweeper(seed3);
    this.zeroCell.bomb = false;
    this.twoCell.bomb = false;
    this.sevenCell.bomb = false;

    this.oneCell.bomb = true;
    this.eightCell.bomb = true;
    this.fiveCell.bomb = true;
    t.checkExpect(seeded3Minesweeper.cells, new ArrayList<Cell>(Arrays.asList(
        zeroCell, oneCell, twoCell, 
        threeCell, fourCell, fiveCell, sixCell, sevenCell, eightCell)));
  }

  //tests numberMinesNeighboring
  void testNumberMinesNeighboring(Tester t) {
    initCell();
    zeroCell.addNeighbors(this.nineBoardCellList, 0);
    oneCell.addNeighbors(this.nineBoardCellList, 1);
    twoCell.addNeighbors(this.nineBoardCellList, 2);
    threeCell.addNeighbors(this.nineBoardCellList, 3);
    fourCell.addNeighbors(this.nineBoardCellList, 4);
    fiveCell.addNeighbors(this.nineBoardCellList, 5);
    sixCell.addNeighbors(this.nineBoardCellList, 6);
    sevenCell.addNeighbors(this.nineBoardCellList, 7);
    eightCell.addNeighbors(this.nineBoardCellList, 8);

    Minesweeper seeded3Minesweeper = new Minesweeper(seed3);
    t.checkExpect(seeded3Minesweeper.cells.get(0).numberMinesNeighboring(), 1);
    t.checkExpect(seeded3Minesweeper.cells.get(1).numberMinesNeighboring(), 1);
    t.checkExpect(seeded3Minesweeper.cells.get(2).numberMinesNeighboring(), 2);
    t.checkExpect(seeded3Minesweeper.cells.get(3).numberMinesNeighboring(), 1);
    t.checkExpect(seeded3Minesweeper.cells.get(4).numberMinesNeighboring(), 3);
    t.checkExpect(seeded3Minesweeper.cells.get(5).numberMinesNeighboring(), 2);
    t.checkExpect(seeded3Minesweeper.cells.get(6).numberMinesNeighboring(), 0);
    t.checkExpect(seeded3Minesweeper.cells.get(7).numberMinesNeighboring(), 2);
    t.checkExpect(seeded3Minesweeper.cells.get(8).numberMinesNeighboring(), 1);

    Minesweeper seeded2Minesweeper = new Minesweeper(seed2);
    t.checkExpect(seeded2Minesweeper.cells.get(0).numberMinesNeighboring(), 0);
    t.checkExpect(seeded2Minesweeper.cells.get(1).numberMinesNeighboring(), 2);
    t.checkExpect(seeded2Minesweeper.cells.get(2).numberMinesNeighboring(), 0);
    t.checkExpect(seeded2Minesweeper.cells.get(3).numberMinesNeighboring(), 2);
    t.checkExpect(seeded2Minesweeper.cells.get(4).numberMinesNeighboring(), 3);
    t.checkExpect(seeded2Minesweeper.cells.get(5).numberMinesNeighboring(), 2);
    t.checkExpect(seeded2Minesweeper.cells.get(6).numberMinesNeighboring(), 1);
    t.checkExpect(seeded2Minesweeper.cells.get(7).numberMinesNeighboring(), 0);
    t.checkExpect(seeded2Minesweeper.cells.get(8).numberMinesNeighboring(), 1);
  }

  //tests calculateX
  void testCalculateX(Tester t) {
    initCell();
    t.checkExpect(zeroCell.calculateX(0), 50);
    t.checkExpect(oneCell.calculateX(1), 150);
    t.checkExpect(twoCell.calculateX(2), 250);
    t.checkExpect(threeCell.calculateX(3), 50);
    t.checkExpect(sixCell.calculateX(6), 50);
  }

  //tests calculateY
  void testCalculateY(Tester t) {
    initCell();
    t.checkExpect(zeroCell.calculateY(0), 50);
    t.checkExpect(oneCell.calculateY(1), 50);
    t.checkExpect(twoCell.calculateY(2), 50);
    t.checkExpect(threeCell.calculateY(3), 150);
    t.checkExpect(sixCell.calculateY(6), 250);
  }

  //tests draw
  void testDraw(Tester t) {
    initCell();
    WorldImage cell = new RectangleImage(Constant.cellWidth,
        Constant.cellHeight, OutlineMode.OUTLINE, Constant.cellColor);
    WorldImage bomb = new CircleImage(Constant.cellWidth / 4, OutlineMode.SOLID, Color.BLACK);
    WorldImage bombCell = new OverlayImage(bomb, cell);
    WorldImage flag = new EquilateralTriangleImage(Constant.cellWidth / 4, OutlineMode.SOLID, 
        Color.GREEN);
    WorldImage flagCell = new OverlayImage(flag, cell);
    WorldImage blankCell = new RectangleImage(Constant.cellWidth,
        Constant.cellHeight, OutlineMode.SOLID, Color.GRAY);
    WorldImage twoNeighborsText = new TextImage("2",
        Constant.FONT_SIZE, Constant.TEXT_COLOR);
    WorldImage twoNeighborsCell = new OverlayImage(twoNeighborsText, cell);
    this.sixCell.draw(this.emptyWS, 50, 250);
    this.zeroCell.draw(this.emptyWS, 50, 50);
    WorldScene returnTestScene = new WorldScene(Constant.sceneWidth, Constant.sceneHeight);
    returnTestScene.placeImageXY(cell, 50, 250);
    returnTestScene.placeImageXY(cell, 50, 50);
    t.checkExpect(this.emptyWS, returnTestScene);
    this.zeroCell.revealed = true;
    this.zeroCell.bomb = true;
    WorldScene draw2 = new WorldScene(Constant.sceneWidth, Constant.sceneHeight);
    this.zeroCell.draw(draw2, 0, 0);
    WorldScene returnTestScene2 = new WorldScene(Constant.sceneWidth, Constant.sceneHeight);
    returnTestScene2.placeImageXY(bombCell, 0, 0);
    t.checkExpect(draw2, returnTestScene2);
    this.oneCell.revealed = false;
    this.oneCell.flagged = true;
    WorldScene draw3 = new WorldScene(Constant.sceneWidth, Constant.sceneHeight);
    this.oneCell.draw(draw3, 20, 10);
    WorldScene returnTestScene3 = new WorldScene(Constant.sceneWidth, Constant.sceneHeight);
    returnTestScene3.placeImageXY(flagCell, 20, 10);
    t.checkExpect(draw3, returnTestScene3);
    this.twoCell.revealed = true;
    this.twoCell.bomb = false;
    this.twoCell.neighbors = new ArrayList<Cell>();
    WorldScene draw4 = new WorldScene(Constant.sceneWidth, Constant.sceneHeight);
    this.twoCell.draw(draw4, 50, 20);
    WorldScene returnTestScene4 = new WorldScene(Constant.sceneWidth, Constant.sceneHeight);
    returnTestScene4.placeImageXY(blankCell, 50, 20);
    t.checkExpect(draw4, returnTestScene4);
    this.fourCell.bomb = true;
    this.fiveCell.bomb = true;
    this.threeCell.revealed = true;
    this.threeCell.bomb = false;
    this.threeCell.neighbors = new ArrayList<Cell>(Arrays.asList(this.fourCell, this.fiveCell));
    WorldScene draw5 = new WorldScene(Constant.sceneWidth, Constant.sceneHeight);
    this.threeCell.draw(draw5, 43, 23);
    WorldScene returnTestScene5 = new WorldScene(Constant.sceneWidth, Constant.sceneHeight);
    returnTestScene5.placeImageXY(twoNeighborsCell, 43, 23);
    t.checkExpect(draw5, returnTestScene5);
  }

  //tests makeScene
  void testMakeScene(Tester t) {
    initCell();
    WorldImage cellImage = new RectangleImage(10, 10, OutlineMode.OUTLINE, Color.RED); 
    WorldImage compositImage = new BesideImage(new AboveImage(cellImage, cellImage, cellImage),
        new BesideImage(new AboveImage(cellImage, cellImage, cellImage),
        new AboveImage(cellImage, cellImage, cellImage)));
    Minesweeper minesweeperToDraw = new Minesweeper();
    WorldScene ws = new WorldScene(0, 0);
    ws.placeImageXY(compositImage, 0, 0);
    t.checkExpect(minesweeperToDraw.makeScene(), ws);
  }
  
  //tests worldEnds
  void testWorldEnds(Tester t) {
    initCell();
    this.oneCell.bomb = true;
    this.oneCell.revealed = true;
    WorldEnd worldEnd1 = new WorldEnd(true, ms1.createLastScene(false));
    t.checkExpect(ms1.worldEnds(), worldEnd1);
    this.oneCell.bomb = false;
    this.oneCell.revealed = false;
    Minesweeper seeded2Minesweeper = new Minesweeper(seed2);
    this.zeroCell.bomb = true;
    this.zeroCell.revealed = false;
    this.oneCell.revealed = true;
    this.twoCell.bomb = true;
    this.twoCell.revealed = false;
    this.threeCell.revealed = true;
    this.fourCell.revealed = true;
    this.fiveCell.revealed = true;
    this.sixCell.revealed = true;
    this.sevenCell.bomb = true;
    this.sevenCell.revealed = false;
    this.eightCell.revealed = true;
    WorldEnd worldEnd2 =  new WorldEnd(true, seeded2Minesweeper.createLastScene(true));
    t.checkExpect(seeded2Minesweeper.worldEnds(), worldEnd2);
    this.zeroCell.bomb = true;
    this.zeroCell.revealed = false;
    this.oneCell.revealed = false;
    this.twoCell.bomb = true;
    this.twoCell.revealed = false;
    this.threeCell.revealed = false;
    this.fourCell.revealed = false;
    this.fiveCell.revealed = false;
    this.sixCell.revealed = false;
    this.sevenCell.bomb = true;
    this.sevenCell.revealed = false;
    this.eightCell.revealed = false;
    this.threeCell.revealed = false;
    this.threeCell.revealed = true;
    Minesweeper ms2 = new Minesweeper();
    WorldEnd worldEnd3 = new WorldEnd(false, ms2.makeScene());
    t.checkExpect(ms2.worldEnds(), worldEnd3);
  }
  
  //tests createLastScene
  void testCreateLastScene(Tester t) {
    initCell();
    Minesweeper seeded2Minesweeper = new Minesweeper(seed2);
    WorldScene scene = seeded2Minesweeper.makeScene();
    scene.placeImageXY(new TextImage("GAME OVER", Constant.FONT_SIZE, Color.RED), 
        Constant.sceneWidth / 2, Constant.sceneHeight / 2);
    t.checkExpect(seeded2Minesweeper.createLastScene(false), scene);
    Minesweeper seeded3Minesweeper = new Minesweeper(seed3);
    WorldScene scene2 = seeded3Minesweeper.makeScene();
    scene2.placeImageXY(new TextImage("YOU WON", Constant.FONT_SIZE, Color.GREEN), 
        Constant.sceneWidth / 2, Constant.sceneHeight / 2);
    t.checkExpect(seeded3Minesweeper.createLastScene(true), scene2);
  }
  
  //tests revealAll
  void testsRevealAll(Tester t) {
    initCell();
    Minesweeper seeded2Minesweeper = new Minesweeper(seed2);
    Minesweeper seeded2MinesweeperCopy = seeded2Minesweeper;
    for (Cell c : seeded2Minesweeper.cells) {
      c.revealed = true;
    }
    seeded2MinesweeperCopy.revealAll();
    t.checkExpect(seeded2MinesweeperCopy, seeded2Minesweeper);
  }
  
  //tests onMouseClicked
  void testOnMouseClicked(Tester t) {
    initCell();
    this.ms1.onMouseClicked(new Posn(105,10), "RightButton");
    t.checkExpect(this.oneCell.flagged, true);
    this.ms1.onMouseClicked(new Posn(105,10), "RightButton");
    t.checkExpect(this.oneCell.flagged, false);
    this.ms1.onMouseClicked(new Posn(250,160), "RightButton");
    t.checkExpect(this.fiveCell.flagged, true);
    this.ms1.onMouseClicked(new Posn(250, 160), "LeftButton");
    t.checkExpect(this.fiveCell.revealed, false);
    this.ms1.onMouseClicked(new Posn(250, 160), "RightButton");
    t.checkExpect(this.fiveCell.revealed, false);
    this.ms1.onMouseClicked(new Posn(250, 160), "LeftButton");
    t.checkExpect(this.fiveCell.revealed, true);
    t.checkExpect(this.sevenCell.revealed, false);
    t.checkExpect(this.sixCell.revealed, false);
    zeroCell.addNeighbors(this.nineBoardCellList, 0);
    oneCell.addNeighbors(this.nineBoardCellList, 1);
    twoCell.addNeighbors(this.nineBoardCellList, 2);
    threeCell.addNeighbors(this.nineBoardCellList, 3);
    fourCell.addNeighbors(this.nineBoardCellList, 4);
    fiveCell.addNeighbors(this.nineBoardCellList, 5);
    sixCell.addNeighbors(this.nineBoardCellList, 6);
    sevenCell.addNeighbors(this.nineBoardCellList, 7);
    eightCell.addNeighbors(this.nineBoardCellList, 8);
    this.fiveCell.revealed = false;
    this.sixCell.bomb = true;
    this.zeroCell.flagged = true;
    this.fourCell.revealed = true;
    this.ms1.onMouseClicked(new Posn(250, 50), "LeftButton");
    t.checkExpect(this.zeroCell.revealed, false);
    t.checkExpect(this.twoCell.revealed, true);
    t.checkExpect(this.oneCell.revealed, true);
    t.checkExpect(this.fiveCell.revealed, true);
    t.checkExpect(this.eightCell.revealed, true);
    t.checkExpect(this.sevenCell.revealed, true);
    t.checkExpect(this.sixCell.revealed, false);
  }
  
  //tests numOrGray
  void testNumOrGray(Tester t) {
    initCell();
    WorldImage cell = new RectangleImage(Constant.cellWidth,
        Constant.cellHeight, OutlineMode.OUTLINE, Constant.cellColor);
    WorldImage zeroMineN = new RectangleImage(Constant.cellWidth,
        Constant.cellHeight, OutlineMode.SOLID, Color.GRAY);
    zeroCell.addNeighbors(this.nineBoardCellList, 0);
    oneCell.addNeighbors(this.nineBoardCellList, 1);
    twoCell.addNeighbors(this.nineBoardCellList, 2);
    threeCell.addNeighbors(this.nineBoardCellList, 3);
    fourCell.addNeighbors(this.nineBoardCellList, 4);
    fiveCell.addNeighbors(this.nineBoardCellList, 5);
    sixCell.addNeighbors(this.nineBoardCellList, 6);
    sevenCell.addNeighbors(this.nineBoardCellList, 7);
    eightCell.addNeighbors(this.nineBoardCellList, 8);
    this.sixCell.bomb = true;
    this.threeCell.bomb = true;
    this.zeroCell.revealed = true;
    this.fourCell.revealed = true;
    this.fiveCell.revealed = true;
    this.fiveCell.revealed = true;
    WorldImage oneBomb = new TextImage("1", Constant.FONT_SIZE, Constant.TEXT_COLOR);
    WorldImage twoBomb = new TextImage("2", Constant.FONT_SIZE, Constant.TEXT_COLOR);
    t.checkExpect(this.fiveCell.numOrGray(), zeroMineN);
    t.checkExpect(this.zeroCell.numOrGray(), new OverlayImage(oneBomb, cell));
    t.checkExpect(this.fourCell.numOrGray(), new OverlayImage(twoBomb, cell));
  }
  
  //tests flood
  void testFlood(Tester t) {
    initCell();   
    zeroCell.addNeighbors(this.nineBoardCellList, 0);
    oneCell.addNeighbors(this.nineBoardCellList, 1);
    twoCell.addNeighbors(this.nineBoardCellList, 2);
    threeCell.addNeighbors(this.nineBoardCellList, 3);
    fourCell.addNeighbors(this.nineBoardCellList, 4);
    fiveCell.addNeighbors(this.nineBoardCellList, 5);
    sixCell.addNeighbors(this.nineBoardCellList, 6);
    sevenCell.addNeighbors(this.nineBoardCellList, 7);
    eightCell.addNeighbors(this.nineBoardCellList, 8);
    this.sixCell.bomb = true;
    this.fourCell.flood(new ArrayList<Cell>());
    t.checkExpect(this.fourCell.revealed, true);
    t.checkExpect(this.twoCell.revealed, false);
    this.twoCell.flood(new ArrayList<Cell>(Arrays.asList(this.oneCell)));
    t.checkExpect(this.twoCell.revealed, true);
    t.checkExpect(this.fiveCell.revealed, true);
    t.checkExpect(this.eightCell.revealed, true);
    t.checkExpect(this.sevenCell.revealed, true);
    t.checkExpect(this.oneCell.revealed, false);
    t.checkExpect(this.threeCell.revealed, false);
    this.threeCell.flagged = true;
    this.zeroCell.flood(new ArrayList<Cell>(Arrays.asList(this.oneCell)));
    t.checkExpect(this.threeCell.revealed, false);
    t.checkExpect(this.zeroCell.revealed, true);
    t.checkExpect(this.sixCell.revealed, false);  
  }
  
  void testCalculateCellClicked(Tester t) {
    initCell();
    Utils test = new Utils();
    t.checkExpect(test.calculateCellClicked(new Posn(105, 10), this.nineBoardCellList), 
        this.oneCell);
    t.checkExpect(test.calculateCellClicked(new Posn(50, 10), this.nineBoardCellList), 
        this.zeroCell);
    t.checkExpect(test.calculateCellClicked(new Posn(250, 10), this.nineBoardCellList), 
        this.twoCell);
    t.checkExpect(test.calculateCellClicked(new Posn(99, 101), this.nineBoardCellList), 
        this.threeCell);
    t.checkExpect(test.calculateCellClicked(new Posn(100, 101), this.nineBoardCellList), 
        this.fourCell);
    t.checkExpect(test.calculateCellClicked(new Posn(200, 101), this.nineBoardCellList), 
        this.fiveCell);
    t.checkExpect(test.calculateCellClicked(new Posn(199, 101), this.nineBoardCellList), 
        this.fourCell);
    t.checkExpect(test.calculateCellClicked(new Posn(10, 201), this.nineBoardCellList), 
        this.sixCell);
    t.checkExpect(test.calculateCellClicked(new Posn(192, 299), this.nineBoardCellList), 
        this.sevenCell);
    t.checkExpect(test.calculateCellClicked(new Posn(299, 299), this.nineBoardCellList), 
        this.eightCell);
  }
}
