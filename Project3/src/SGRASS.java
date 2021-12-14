import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class SGRASS extends ActiveEntity{

    public static final String FISH_KEY = "fish";
    public static final String FISH_ID_PREFIX = "fish -- ";
    public static final int FISH_CORRUPT_MIN = 20000;
    public static final int FISH_CORRUPT_MAX = 30000;

    public SGRASS(String id, Point position, List<PImage> images, int actionPeriod)
    {
        super(id, position, images, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Point> openPt = world.findOpenAround(this.position);

        if (openPt.isPresent())
        {
            FISH fish = new FISH(FISH_ID_PREFIX + this.id,
                    openPt.get(), imageStore.getImageList(FISH_KEY), FISH_CORRUPT_MIN +
                            rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN));
            world.addEntity(((Entity) fish));
            fish.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,new Activity(this, world, imageStore),
                this.actionPeriod);
    }
}
