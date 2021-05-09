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
  Sprites sprites;
  TilePane tiles;
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
    this.stage = stage;
    sprites = new Sprites();
  }

  public void update() {
    Platform.runLater(() -> {
      System.out.println("Updating battefield view...");
      initTilePane();
      battlefield.visitFullMap(this);
      scene = new Scene(tiles);
      stage.setScene(scene);});
  }

  ImageView groundView(Battlefield.GroundTile tile) {
    switch(tile) {
      case GRASS: return sprites.grass();
      case WATER: return sprites.water();
      case ROCK: return sprites.rock();
      case PATH: return sprites.path();
      default: throw new UnsupportedOperationException(
              "Displaying ground tile `" + tile.name() + "` is not yet supported.");
    }
  }

  ImageView championView(Champion champion) {
    String name = champion.name();
    if(name.equals("Archer")) { return sprites.archer(); }
    else if (name.equals("Warrior")) { return sprites.warrior(); }
    else if (name.equals("Scientist")) { return  sprites.scientist();}
    else {
      throw new UnsupportedOperationException(
              "Displaying Champion tile `" + name + "` is not yet supported.");
    }
  }

  ImageView nexusView(Nexus nexus) {
    switch(nexus.teamOfNexus()) {
      case Nexus.BLUE: return sprites.blueNexus();
      case Nexus.RED: return sprites.redNexus();
      default: throw new RuntimeException("Unsupported Nexus color");
    }
  }

  @Override public void visitGround(Battlefield.GroundTile tile, int x, int y) {
    tiles.getChildren().add(groundView(tile));
  }

  private void displayDestructible(Destructible d, Node dView) {
    StackPane stack = new StackPane();
    stack.getChildren().add(groundView(battlefield.groundAt(d.x(), d.y())));
    stack.getChildren().add(dView);
    tiles.getChildren().add(stack);
  }

  @Override public void visitChampion(Champion champion) {
    displayDestructible(champion, championView(champion));
  }

  @Override public void visitNexus(Nexus nexus) {
    displayDestructible(nexus, nexusView(nexus));
  }
}
