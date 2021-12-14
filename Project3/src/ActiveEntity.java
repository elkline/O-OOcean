import processing.core.PImage;

import java.util.List;
import java.util.Random;

abstract class ActiveEntity extends Entity {


    protected int actionPeriod;
    protected static final Random rand = new Random();

    public ActiveEntity(String id, Point position, List<PImage> images, int actionPeriod){

        super(id, position, images);
        this.actionPeriod = actionPeriod;

    }

    public int getActionPeriod() {
        return this.actionPeriod;
    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, new Activity(((Entity) this), world, imageStore),this.actionPeriod);
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

}
