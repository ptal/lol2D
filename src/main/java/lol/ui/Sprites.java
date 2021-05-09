package lol.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprites {
  private Image grassImage;
  private Image waterImage;
  private Image pathImage;
  private Image archerImage;
  private Image warriorImage;
  private Image blueNexusImage;
  private Image redNexusImage;

  public Sprites() {

    grassImage = new Image("sprites/grass-tile.png");
    waterImage = new Image("sprites/water-tile.png");
    pathImage = new Image("sprites/path-tile.png");
    archerImage = new Image("sprites/archer.png");
    warriorImage = new Image("sprites/warrior.png");
    blueNexusImage = new Image("sprites/blue-nexus.png");
    redNexusImage = new Image("sprites/red-nexus.png");
  }

  private ImageView makeView(Image image) {
    ImageView view = new ImageView();
    view.setImage(image);
    return view;
  }

  public ImageView grass() {
    return makeView(grassImage);
  }

  public ImageView water() {
    return makeView(waterImage);
  }

  public ImageView path() {
    return makeView(pathImage);
  }

  public ImageView rock() {
    return makeView(pathImage);
  }

  public ImageView archer() {
    return makeView(archerImage);
  }

  public ImageView warrior() {
    return makeView(warriorImage);
  }

  public ImageView blueNexus() {
    return makeView(blueNexusImage);
  }

  public ImageView redNexus() {
    return makeView(redNexusImage);
  }
}