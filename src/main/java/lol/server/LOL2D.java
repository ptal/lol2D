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
import lol.ui.*;

public class LOL2D extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  Battlefield battlefield;
  BattlefieldView battlefieldView;

  public void start(Stage stage)
  {
    ASCIIBattlefieldBuilder battlefieldBuilder = new ASCIIBattlefieldBuilder();
    battlefield = battlefieldBuilder.build();
    battlefieldView = new BattlefieldView(battlefield, stage);
    update();
    stage.show();
    startServer();
  }

  public void update() {
    battlefieldView.update();
  }

  private void startServer() {
    Thread t = new Thread(new Server(this, battlefield));
    t.start();
  }
}