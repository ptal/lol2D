package lol.ui;

import javafx.scene.Scene;
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
  Scene scene;

  private void initTilePane() {
    tiles = new TilePane();
    tiles.setPrefColumns(battlefield.width());
    tiles.setPrefRows(battlefield.height());
    tiles.setTileAlignment(Pos.CENTER);
  }

  public BattlefieldView(Battlefield battlefield) {
    this.battlefield = battlefield;
    sprites = new Sprites();
  }

  public void update(Stage stage) {
    initTilePane();
    battlefield.visitFullMap(this);
    scene = new Scene(tiles);
    stage.setScene(scene);
  }

  ImageView groundView(Battlefield.GroundTile tile) {
    switch(tile) {
      case GRASS: return sprites.grass();
      default: throw new UnsupportedOperationException(
        "Displaying ground tile `" + tile.name() + "` is not yet supported.");
    }
  }

  ImageView championView(Champion champion) {
    if(champion.name().equals("archer")) {
      return sprites.archer();
    }
    else {
      throw new UnsupportedOperationException(
        "Displaying Champion tile `" + champion.name() + "` is not yet supported.");
    }
  }

  ImageView nexusView(Nexus nexus) {
    switch(nexus.color()) {
      case BLUE: return sprites.blueNexus();
      case RED: return sprites.redNexus();
      default: throw new RuntimeException("Unsupported Nexus color");
    }
  }

  public void visitGround(Battlefield.GroundTile tile, int x, int y) {
    tiles.getChildren().add(groundView(tile));
  }

  public void visitChampion(Champion champion, int x, int y) {
    StackPane stack = new StackPane();
    stack.getChildren().add(groundView(battlefield.groundAt(x, y)));
    stack.getChildren().add(championView(champion));
    tiles.getChildren().add(stack);
  }

  public void visitNexus(Nexus nexus, int x, int y) {
    StackPane stack = new StackPane();
    stack.getChildren().add(groundView(battlefield.groundAt(x, y)));
    stack.getChildren().add(nexusView(nexus));
    tiles.getChildren().add(stack);
  }
}
