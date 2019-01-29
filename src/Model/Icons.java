package Model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Icons {
    String shipPath = "resources/boat.png";
    Image shipImage = new Image(shipPath);
    ImagePattern shipIcon = new ImagePattern(shipImage);

    String missImagePath = "resources/miss.png";
    Image missImage = new Image(missImagePath);
    ImagePattern missIcon = new ImagePattern(missImage);

    String hitImagePath = "resources/fire.png";
    Image hitImage = new Image(hitImagePath);
    ImagePattern hitIcon = new ImagePattern(hitImage);

    String krispinImagePath = "resources/krispin.png";
    Image krispinImage = new Image(krispinImagePath);
    ImagePattern krispinIcon = new ImagePattern(krispinImage);

    public ImagePattern getKrispinIcon() {
        return krispinIcon;
    }

    public ImagePattern getShipIcon() {
        return shipIcon;
    }


    public ImagePattern getMissIcon() {
        return missIcon;
    }

    public ImagePattern getHitIcon() {
        return hitIcon;
    }
}
