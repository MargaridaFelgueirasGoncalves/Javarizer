package org.academiadecodigo.weekendteamwork.javarizer;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public class ServerPic {

    /**
     * fields
     */
    private final int PADDING = 10;

    /**
     * constructor
     */
    public ServerPic() {

        Picture javarizer = new Picture(PADDING, PADDING, "resources/javarizer.png");
        javarizer.draw();
    }
}
