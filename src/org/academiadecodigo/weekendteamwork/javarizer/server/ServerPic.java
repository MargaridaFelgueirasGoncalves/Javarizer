package org.academiadecodigo.weekendteamwork.javarizer.server;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class ServerPic {

    /**
     * fields
     */
    private Rectangle canvas;
    private final int PADDING = 10;

    /**
     * constructor
     */
    public ServerPic() {

        canvas = new Rectangle(PADDING, PADDING, 610, 400);
        canvas.fill();
        addPicture();
    }

    public void addPicture() {

        Picture javarizer = new Picture(10, 10, "resources/javarizer.png");
        javarizer.draw();
    }
}
