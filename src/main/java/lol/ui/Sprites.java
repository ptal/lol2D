package lol.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

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

  public ImageView championsView(String name, int teamID) {
    if(name.equals("Archer")){
      if (teamID ==0) {return makeView(redArcherImage);}
      else if(teamID ==1){return makeView(blueArcherImage);}
      else{throw new RuntimeException("No such team of number `" + teamID + "`.");}
    }

    else if(name.equals("Warrior")){
      if (teamID ==0) {return makeView(redWarriorImage);}
      else if(teamID ==1){return makeView(blueWarriorImage);}
      else{throw new RuntimeException("No such team of number `" + teamID + "`.");}
    }

    else {throw new RuntimeException("No champion named `" + teamID + "`.");}

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