package lol.server;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Platform;

public class LOL2D extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  Label l;

  public void start(Stage stage)
  {
    Thread t = new Thread(new Server(this));
    t.start();
    l = new Label("Nothing yet!");
    Scene scene = new Scene(new StackPane(l), 640, 480);
    stage.setScene(scene);
    stage.show();
  }

  public void print(String s) {
    Platform.runLater(() -> l.setText(s));
  }
}