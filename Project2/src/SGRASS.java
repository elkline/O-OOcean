import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class SGRASS implements ActiveEntity{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    private static final String SGRASS_KEY = "seaGrass";
    private static final int SGRASS_NUM_PROPERTIES = 5;
    private static final int SGRASS_ID = 1;
    private static final int SGRASS_COL = 2;
    private static final int SGRASS_ROW = 3;
    private static final int SGRASS_ACTION_PERIOD = 4;

    public SGRASS(String id, Point position, int actionPeriod, List<PImage> images) {
        this.id = id;
        this.position = position;
        this.actionPeriod = actionPeriod;
        this.images = images;
    }

    public SGRASS createSgrass(String id, Point position, int actionPeriod, List<PImage> images) {
        return new SGRASS(id, position, actionPeriod, images);
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

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

        scheduler.scheduleEvent(this, new Activity(((Entity) this), world, imageStore),this.actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        executeSgrassActivity(world, imageStore, scheduler);
    }

    public void executeSgrassActivity(WorldModel world,
                                      ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.position);

        if (openPt.isPresent())
        {
            FISH fish = new FISH(FISH_ID_PREFIX + this.id,
                    openPt.get(), FISH_CORRUPT_MIN +
                            rand.nextInt(FISH_CORRUPT_MAX - FISH_CORRUPT_MIN),
                    imageStore.getImageList(FISH_KEY));
            world.addEntity(((Entity) fish));
            fish.scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,new Activity(this, world, imageStore),
                this.actionPeriod);
    }

    public static boolean parseSgrass(String [] properties, WorldModel world,
                               ImageStore imageStore)
    {
        if (properties.length == SGRASS_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[SGRASS_COL]),
                    Integer.parseInt(properties[SGRASS_ROW]));
            SGRASS entity = new SGRASS(properties[SGRASS_ID],
                    pt,
                    Integer.parseInt(properties[SGRASS_ACTION_PERIOD]),
                    imageStore.getImageList(SGRASS_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == SGRASS_NUM_PROPERTIES;
    }
}
