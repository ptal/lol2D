package lol.ui;

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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


import lol.game.*;
import lol.server.*;
import lol.ui.*;

public class StartMenu extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  Battlefield battlefield;
  BattlefieldView battlefieldView;

  @Override
  public void start(Stage stage) {
    Button startBtn = new Button();
    startBtn.setText("Start game");
    startBtn.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        ASCIIBattlefieldBuilder battlefieldBuilder = new ASCIIBattlefieldBuilder();
        battlefield = battlefieldBuilder.build();
        battlefieldView = new BattlefieldView(battlefield, stage);
        update();
        stage.show();
        startServer();
      }
    });

    StackPane root = new StackPane();
    root.getChildren().add(startBtn);

    Scene scene = new Scene(root, 300, 250);

    stage.setTitle("LOL 2D");
    stage.setScene(scene);
    stage.show();
  }

  public void update() {
      battlefieldView.update();
  }

  private void startServer() {
      Thread t = new Thread(new Server(this, battlefield));
      t.start();
  }
}