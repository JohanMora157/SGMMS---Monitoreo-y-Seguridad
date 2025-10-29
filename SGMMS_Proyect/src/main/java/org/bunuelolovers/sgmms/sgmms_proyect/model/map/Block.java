
package org.bunuelolovers.sgmms.sgmms_proyect.model.map;

import javafx.scene.shape.Rectangle;

public class Block {
    private Rectangle bounds;

    public Block(double x, double y, double width, double height) {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
    public double getX() {
        return bounds.getX();
    }

    public double getY() {
        return bounds.getY();
    }

}