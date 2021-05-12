package lol.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lol.game.Nexus;

public class Sprites {
  private Image grassImage;
  private Image redArcherImage;
  private Image blueArcherImage;
  private Image redWarriorImage;
  private Image blueWarriorImage;
  private Image blueNexusImage;
  private Image redNexusImage;
  private Image blueTowerImage;
  private Image redTowerImage;
  private Image treeImage;
  private Image rockImage;
  private Image[] archerImages = new Image[2];
  private Image[] warriorImages = new Image[2];
  private Image[] towerImages = new Image[2];
  private Image[] nexusImages = new Image[2];

  public Sprites() {
    grassImage = new Image("sprites/grass-tile.png");
    rockImage = new Image("sprites/rock-tile.png");
    treeImage = new Image("sprites/tree-tile.png");

    blueArcherImage = new Image("sprites/blue-archer.png");
    redArcherImage = new Image("sprites/red-archer.png");
    archerImages[Nexus.BLUE] = blueArcherImage;
    archerImages[Nexus.RED] = redArcherImage;

    blueWarriorImage = new Image("sprites/blue-warrior.png");
    redWarriorImage = new Image("sprites/red-warrior.png");
    warriorImages[Nexus.BLUE] = blueWarriorImage;
    warriorImages[Nexus.RED] = redWarriorImage;

    blueNexusImage = new Image("sprites/blue-nexus.png");
    redNexusImage = new Image("sprites/red-nexus.png");
    nexusImages[Nexus.BLUE] = blueNexusImage;
    nexusImages[Nexus.RED] = redNexusImage;

    blueTowerImage = new Image("sprites/blue-tower.png");
    redTowerImage = new Image("sprites/red-tower.png");
    towerImages[Nexus.BLUE] = blueTowerImage;
    towerImages[Nexus.RED] = redTowerImage;

  }

  private ImageView makeView(Image image) {
    ImageView view = new ImageView();
    view.setImage(image);
    return view;
  }

  public ImageView grass() {
    return makeView(grassImage);
  }

  public ImageView championsView(String name, int teamID){
    assert teamID < 2 : "Sprites are only provided for two teams at most.";

    if(name.equals("Archer")){
      return makeView(archerImages[teamID]);
    }
    else if(name.equals("Warrior")){
      return makeView(warriorImages[teamID]);
    }
    else {throw new RuntimeException("No champion named `" + name + "`.");}
  }

  public ImageView nexusesView(int teamID) {
    if(teamID >2 ){ throw new RuntimeException("Unsupported Nexus color. There is only 2 teams.");}
    else {return makeView(nexusImages[teamID]);}
  }

  public ImageView towersView(int teamID) {
    if(teamID >2 ){ throw new RuntimeException("Unsupported Tower's color. There is only 2 teams.");}
    else {return makeView(towerImages[teamID]);}
  }

  public ImageView tree() {
    return makeView(treeImage);
  }

  public ImageView rock() {
    return makeView(rockImage);
  }
}