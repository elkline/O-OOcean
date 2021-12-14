import processing.core.PImage;

import java.util.List;

abstract class AnimatedEntity extends ActiveEntity{

    protected int animationPeriod;

    public AnimatedEntity(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){

        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod(){return this.actionPeriod;}

    public void nextImage(){
        imageIndex = (imageIndex + 1) % this.images.size();
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        super.scheduleActions(scheduler, world, imageStore);
    }
}
