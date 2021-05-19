package lol.ui;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

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
    stage.setTitle("LOL 2D");
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    
    //play button
    Button startbtn = new Button("Play");
    grid.add(startbtn, 2, 2);

    //play button action when pressed (start the server)
    startbtn.setOnAction(e -> {
      ASCIIBattlefieldBuilder battlefieldBuilder = new ASCIIBattlefieldBuilder();
      battlefield = battlefieldBuilder.build();
      battlefieldView = new BattlefieldView(battlefield, stage);
      update();
      stage.show();
      startServer();
    });

    Scene scene = new Scene(grid, 660, 360);
    stage.setScene(scene);
    scene.getStylesheets().add(getClass().getClassLoader().getResource("styles/style.css").toExternalForm());
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