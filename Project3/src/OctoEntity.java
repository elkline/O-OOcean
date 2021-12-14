import processing.core.PImage;

import java.util.List;

abstract class OctoEntity extends MoveAndEats{

    protected int resourceLimit;
    protected int resourceCount;

    public OctoEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int resourceLimit, int resourceCount){
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }


}
