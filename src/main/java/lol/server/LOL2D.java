package lol.server;

import javafx.application.Application;
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

public class LOL2D extends Application {
  public static void main(String[] args) {
    ASCIIBattlefieldBuilder battlefieldBuilder = new ASCIIBattlefieldBuilder();
    Battlefield battlefield = battlefieldBuilder.build();
    System.out.println(battlefield);
    launch(args);
  }

  static final int SIZE = 10;
  StackPane[][] grid;

  public void start(Stage stage)
  {
    Thread t = new Thread(new Server(this));
    t.start();

    Image grassImage = new Image("grass-tile.png");
    Image archerImage = new Image("archer.png");

    grid = new StackPane[SIZE][SIZE];
    for(int i = 0; i < SIZE; ++i) {
      for(int j = 0; j < SIZE; ++j) {
        grid[i][j] = new StackPane();
      }
    }

    TilePane tiles = new TilePane();
    tiles.setPrefColumns(SIZE);
    tiles.setPrefRows(SIZE);
    tiles.setTileAlignment(Pos.CENTER);
    for(int i = 0; i < SIZE; ++i) {
      for(int j = 0; j < SIZE; ++j) {
        ImageView grassView = new ImageView();
        grassView.setImage(grassImage);
        if(i == 0 && j == 0) {
          ImageView archerView = new ImageView();
          archerView.setImage(archerImage);
          StackPane stack = new StackPane();
          stack.getChildren().add(grassView);
          stack.getChildren().add(archerView);
          tiles.getChildren().add(stack);
        }
        else {
          tiles.getChildren().add(grassView);
        }
      }
    }
    Scene scene = new Scene(tiles);
    stage.setScene(scene);
    stage.show();
  }
}