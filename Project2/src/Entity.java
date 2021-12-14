import processing.core.PImage;

import java.util.List;

public interface Entity {

//    private String id;
//    private Point position;
//    private List<PImage> images;
//    private int imageIndex;
//    private int resourceLimit;
//    private int resourceCount;
//    private int actionPeriod;
//    private int animationPeriod;

    public Point getPosition();
    public void setPosition(Point pos);
    public PImage getCurrentImage(Object entity);
}
