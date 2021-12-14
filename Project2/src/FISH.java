import processing.core.PImage;

import java.util.List;

public class FISH implements ActiveEntity{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    private static final String FISH_KEY = "fish";
    private static final int FISH_NUM_PROPERTIES = 5;
    private static final int FISH_ID = 1;
    private static final int FISH_COL = 2;
    private static final int FISH_ROW = 3;
    private static final int FISH_ACTION_PERIOD = 4;

    public FISH(String id, Point position, int actionPeriod, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.actionPeriod = actionPeriod;
        this.images = images;
    }

    public FISH createFish(String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new FISH(id, position, actionPeriod, images);
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point pos) {
        this.position = pos;
    }

    public PImage getCurrentImage(Object entity) {
        if (entity instanceof Background) {
            return ((Background) entity).getImages()
                    .get(((Background) entity).getImageIndex());
        } else if (entity instanceof Entity) {
            //return ((Entity)entity).images.get(((Entity)entity).imageIndex);
            return images.get(imageIndex);
        } else {
            throw new UnsupportedOperationException(
                    String.format("getCurrentImage not supported for %s",
                            entity));
        }
    }

    public int getActionPeriod() {
        return this.actionPeriod;
    }

    public int getImageIndex() {return this.imageIndex;}

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore), this.actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        executeFishActivity(world, imageStore, scheduler);
    }

    public void executeFishActivity(WorldModel world,
                                    ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.position;  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        CRAB crab = new CRAB(this.id + CRAB_ID_SUFFIX,
                pos, this.actionPeriod / CRAB_PERIOD_SCALE,
                CRAB_ANIMATION_MIN +
                        rand.nextInt(CRAB_ANIMATION_MAX - CRAB_ANIMATION_MIN),
                imageStore.getImageList(CRAB_KEY));

        world.addEntity(crab);
        crab.scheduleActions(scheduler, world, imageStore);
    }

    public static boolean parseFish(String [] properties, WorldModel world,
                                    ImageStore imageStore)
    {
        if (properties.length == FISH_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[FISH_COL]),
                    Integer.parseInt(properties[FISH_ROW]));
            FISH entity = new FISH (properties[FISH_ID],
                    pt, Integer.parseInt(properties[FISH_ACTION_PERIOD]),
                    imageStore.getImageList(FISH_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == FISH_NUM_PROPERTIES;
    }
}
