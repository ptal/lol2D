package lol.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lol.game.Nexus;
import lol.game.Projectile;

public class Sprites {
  private Image grassImage;
  private Image treeImage;
  private Image rockImage;
  private Image[] projectileImages = new Image[1];
  private Image[] archerImages = new Image[2];
  private Image[] warriorImages = new Image[2];
  private Image[] towerImages = new Image[2];
  private Image[] nexusImages = new Image[2];

  public Sprites() {
    grassImage = new Image("sprites/grass-tile.png");
    rockImage = new Image("sprites/rock-tile.png");
    treeImage = new Image("sprites/tree-tile.png");

    projectileImages[Projectile.ARROW] = new Image("sprites/arrow.png");

    archerImages[Nexus.BLUE] = new Image("sprites/blue-archer.png");
    archerImages[Nexus.RED] = new Image("sprites/red-archer.png");

    warriorImages[Nexus.BLUE] = new Image("sprites/blue-warrior.png");
    warriorImages[Nexus.RED] = new Image("sprites/red-warrior.png");

    nexusImages[Nexus.BLUE] = new Image("sprites/blue-nexus.png");
    nexusImages[Nexus.RED] = new Image("sprites/red-nexus.png");

    towerImages[Nexus.BLUE] = new Image("sprites/blue-tower.png");
    towerImages[Nexus.RED] = new Image("sprites/red-tower.png");

  }

  private ImageView makeView(Image image) {
    ImageView view = new ImageView();
    view.setImage(image);
    return view;
  }

  private boolean checkTeamID(int teamID, String errorMessage){
    if (teamID > 2){
      throw new RuntimeException(errorMessage);
    }
    return true;
  }

  private boolean checkTypeID(int typeID, String errorMessage){
    if (typeID > 1){
      throw new RuntimeException(errorMessage);
    }
    return true;
  }

  public ImageView championsView(String name, int teamID){
    checkTeamID(teamID,"Sprites are only provided for two teams at most.");

    if(name.equals("Archer")){
      return makeView(archerImages[teamID]);
    }
    else if(name.equals("Warrior")){
      return makeView(warriorImages[teamID]);
    }
    else {throw new RuntimeException("No champion named `" + name + "`.");}
  }

  public ImageView nexusesView(int teamID) {
    checkTeamID(teamID, "Unsupported Nexus color. There is only 2 teams.");
    return makeView(nexusImages[teamID]);
  }

  public ImageView towersView(int teamID) {
    checkTeamID(teamID, "Unsupported Tower's color. There is only 2 teams.");
    return makeView(towerImages[teamID]);
  }

  public ImageView projectileView(int typeID) {
    checkTypeID(typeID, "Unsupported projectile type. There is only 1 type.");
    return makeView(projectileImages[typeID]);
  }

  public ImageView treeView() {
    return makeView(treeImage);
  }

  public ImageView rockView() {
    return makeView(rockImage);
  }

  public ImageView grassView() {
    return makeView(grassImage);
  }
}