import processing.core.PImage;

import java.util.List;

abstract class MoveAndEats extends AnimatedEntity{

    public MoveAndEats(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);
    public abstract Point nextPosition(WorldModel world, Point destPos);

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(this, new Animation(this, 0), this.getAnimationPeriod());
    }

}

