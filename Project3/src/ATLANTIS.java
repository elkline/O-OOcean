import processing.core.PImage;

import java.util.List;

public class ATLANTIS extends AnimatedEntity{

    private final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;


    public ATLANTIS(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){

        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(((Entity) this), new Animation(((Entity) this), ATLANTIS_ANIMATION_REPEAT_COUNT), this.getAnimationPeriod());
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.unscheduleAllEvents((Entity)this);
        world.removeEntity((Entity) this);
    }

}
