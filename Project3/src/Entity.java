import processing.core.PImage;

import java.util.List;

abstract  class Entity {

    protected String id;
    protected Point position;
    protected List<PImage> images;
    protected int imageIndex;

    public Entity(String id, Point position, List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }

    public Point getPosition() {return this.position;}

    public void setPosition(Point pos) {this.position = pos;}

    public PImage getCurrentImage(Object entity){
        return images.get(imageIndex);
    }
}
