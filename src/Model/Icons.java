package Model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Icons {
    String shipPath = "resources/boat.png";
    Image shipImage = new Image(shipPath);
    ImagePattern shipIcon = new ImagePattern(shipImage);

    String krakenPath = "resources/krispin.png";
    Image krakenImage = new Image(krakenPath);
    ImagePattern krakenIcon = new ImagePattern(krakenImage);

    String missImagePath = "resources/miss.png";
    Image missImage = new Image(missImagePath);
    ImagePattern missIcon = new ImagePattern(missImage);

    String hitImagePath = "resources/fire.png";
    Image hitImage = new Image(hitImagePath);
    ImagePattern hitIcon = new ImagePattern(hitImage);

    public ImagePattern getShipIcon() {
        return shipIcon;
    }

    public ImagePattern getKrakenIcon() {
        return krakenIcon;
    }

    public ImagePattern getMissIcon() {
        return missIcon;
    }

    public ImagePattern getHitIcon() {
        return hitIcon;
    }
}
