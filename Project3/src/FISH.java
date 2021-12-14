import processing.core.PImage;

import java.util.List;

public class FISH extends ActiveEntity{

    private  final String CRAB_KEY = "crab";
    private  final String CRAB_ID_SUFFIX = " -- crab";
    private  final int CRAB_PERIOD_SCALE = 4;
    private  final int CRAB_ANIMATION_MIN = 50;
    private  final int CRAB_ANIMATION_MAX = 150;

    public FISH(String id, Point position, List<PImage> images, int actionPeriod)
    {
        super(id, position, images, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Point pos = this.position;  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        CRAB crab = new CRAB(this.id + CRAB_ID_SUFFIX,
                pos, imageStore.getImageList(CRAB_KEY), this.actionPeriod / CRAB_PERIOD_SCALE,
                CRAB_ANIMATION_MIN +
                        rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN)
                );

        world.addEntity(crab);
        crab.scheduleActions(scheduler, world, imageStore);
    }

}
