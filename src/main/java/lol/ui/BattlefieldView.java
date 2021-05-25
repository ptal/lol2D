package lol.ui;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import lol.game.*;

public class BattlefieldView implements TileVisitor
{
  Battlefield battlefield;
  BattlefieldTraversal battlefieldTraversal;
  Sprites sprites;
  TilePane tiles;
  TilePane wt = new TilePane();
  Stage stage;
  Scene scene;

  private void initTilePane() {
    tiles = new TilePane();
    tiles.setPrefColumns(battlefield.width());
    tiles.setPrefRows(battlefield.height());
    tiles.setTileAlignment(Pos.CENTER);
  }

  public BattlefieldView(Battlefield battlefield, Stage stage) {
    this.battlefield = battlefield;
    this.battlefieldTraversal = new BattlefieldTraversal(battlefield);
    this.stage = stage;
    sprites = new Sprites();
  }

  public void update() {
    Platform.runLater(() -> {
      System.out.println("Updating battefield view...");
      initTilePane();
      battlefieldTraversal.visitFullMap(this);
      scene = new Scene(tiles);
      stage.setScene(scene);
      displayWinningScreen();});
  }

  ImageView groundView(Battlefield.GroundTile tile) {
    switch(tile) {
      case GRASS: return sprites.grassView();
      case ROCK: return sprites.rockView();
      case TREE: return sprites.treeView();
      default: throw new UnsupportedOperationException(
        "Displaying ground tile `" + tile.name() + "` is not yet supported.");
    }
  }

  ImageView championView(Champion champion) {
    String name = champion.name();
    int teamID = champion.teamID();
    return(sprites.championsView(name, teamID));
  }

  ImageView nexusView(Nexus nexus) {
    int teamID = nexus.teamOfNexus();
    double hpPercentage = (nexus.currentHP())/(double)(nexus.initialHP());
    if (hpPercentage<=1.0 && hpPercentage>=0.5){
      return(sprites.nexusesView(teamID));
    }
    else if(hpPercentage< 0.5 && hpPercentage> 0.0){
      return (sprites.nexusesOnfire(teamID));
    }
    else if(hpPercentage == 0.0){
      return (sprites.nexusesDestroyed(teamID));
    }
    else{
      throw new RuntimeException("Unsupported Nexus color");
    }
  }

  ImageView towerView(Tower tower){
    int teamID = tower.teamOfTower();
    return(sprites.towersView(teamID));
  }

  ImageView monsterView(Monster monster) {
    return sprites.monsterView(monster.monsterID());
  }


  @Override public void visitGround(Battlefield.GroundTile tile, int x, int y) {
    tiles.getChildren().add(groundView(tile));
  }

  private void displayDestructible(Destructible d, Node dView) {
    StackPane stack = new StackPane();
    stack.getChildren().add(groundView(battlefield.groundAt(d.x(), d.y())));
    stack.getChildren().add(dView);
    drawLifeBar(stack, d);
    tiles.getChildren().add(stack);
  }

  private void drawLifeBar(StackPane stack, Destructible d){
    double width = d.currentHP()*1.0/d.initialHP();
    double fixedWidth = 50;
    Rectangle lifeBar = new Rectangle(0, 0, (int) (fixedWidth*width), 4);
    Rectangle lifeBarStatic = new Rectangle(0, 0, fixedWidth, 4);
    stack.setAlignment(Pos.TOP_LEFT);
    double greenColor = width*1.0/1;
    double redColor = 1.0-greenColor;
    lifeBar.setFill(Color.color(redColor,greenColor,0));
    lifeBarStatic.setFill(Color.GRAY);
    lifeBarStatic.setStroke(Color.BLACK);
    lifeBarStatic.setStrokeWidth(1);
    stack.getChildren().add(lifeBarStatic);
    stack.getChildren().add(lifeBar);
  }

  private void displayWinningScreen(){
    if (!battlefield.allNexusAlive()) {
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      Nexus nexus = battlefield.nexusOf(Nexus.BLUE);
      if (nexus.isAlive()) {
        displayWinner(Nexus.BLUE);
      } else {
        displayWinner(Nexus.RED);
      }
      scene = new Scene(wt);
    } else {
      scene = new Scene(tiles);
    }
    stage.setScene(scene);
  }

  private void displayWinner(int teamId) {
    wt.setPrefColumns(1);
    wt.setPrefRows(1);
    wt.setTileAlignment(Pos.CENTER);
    wt.setMinSize(640, 640);
    StackPane stack = new StackPane();
    sprites.finalSceneView(teamId).setFitWidth(640);
    sprites.finalSceneView(teamId).setFitHeight(640);
    sprites.finalSceneView(teamId).setPreserveRatio(true);
    stack.setAlignment(Pos.CENTER);
    stack.getChildren().addAll(new Rectangle(640,640, Color.WHITE ),sprites.finalSceneView(teamId));
    wt.getChildren().add(stack);
  }

  @Override public void visitChampion(Champion champion) {
    System.out.println("Display champion ");
    displayDestructible(champion, championView(champion));
  }

  @Override public void visitNexus(Nexus nexus) {
    displayDestructible(nexus, nexusView(nexus));
  }

  @Override public void visitTower(Tower tower) {
    displayDestructible(tower, towerView(tower));
  }

  @Override public void visitMonster(Monster monster) {
    displayDestructible(monster, monsterView(monster));
  }

}
