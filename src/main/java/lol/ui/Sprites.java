package lol.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprites {
  private Image grassImage;
  private Image redArcherImage;
  private Image blueArcherImage;
  private Image redWarriorImage;
  private Image blueWarriorImage;
  private Image blueNexusImage;
  private Image redNexusImage;
  private Image treeImage;
  private Image rockImage;


  public Sprites() {
    grassImage = new Image("sprites/grass-tile.png");
    blueArcherImage = new Image("sprites/blue-archer.png");
    redArcherImage = new Image("sprites/red-archer.png");
    blueWarriorImage = new Image("sprites/blue-warrior.png");
    redWarriorImage = new Image("sprites/red-warrior.png");
    blueNexusImage = new Image("sprites/blue-nexus.png");
    redNexusImage = new Image("sprites/red-nexus.png");
    rockImage = new Image("sprites/rock-tile.png");
    treeImage = new Image("sprites/tree-tile.png");
  }

  private ImageView makeView(Image image) {
    ImageView view = new ImageView();
    view.setImage(image);
    return view;
  }

  public ImageView grass() {
    return makeView(grassImage);
  }

  public ImageView redArcher() {
    return makeView(redArcherImage);
  }

  public ImageView blueArcher() {
    return makeView(blueArcherImage);
  }

  public ImageView blueWarrior() {
    return makeView(blueWarriorImage);
  }

  public ImageView redWarrior() {
    return makeView(redWarriorImage);
  }

  public ImageView blueNexus() {
    return makeView(blueNexusImage);
  }

  public ImageView redNexus() {
    return makeView(redNexusImage);
  }

  public ImageView tree() {
    return makeView(treeImage);
  }

  public ImageView rock() {
    return makeView(rockImage);
  }
}