import processing.core.PImage;

import java.util.List;

public class QUAKE implements AnimatedEntity{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    private static final String QUAKE_KEY = "quake";
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;


    public QUAKE(Point position, List<PImage> images){
        this.id = QUAKE_ID;
        this.position = position;
        this.images = images;
        this.actionPeriod = QUAKE_ACTION_PERIOD;
        this.animationPeriod = QUAKE_ANIMATION_PERIOD;
    }

    public QUAKE createQuake(Point position, List<PImage> images) {
        return new QUAKE(position, images);
    }

    public Point getPosition(){return this.position;}

    public void setPosition(Point pos){this.position = pos;}

    public PImage getCurrentImage(Object entity)
    {
        if (entity instanceof Background)
        {
            return ((Background)entity).getImages()
                    .get(((Background)entity).getImageIndex());
        }
        else if (entity instanceof Entity)
        {
            return images.get(imageIndex);
        }
        else
        {
            throw new UnsupportedOperationException(
                    String.format("getCurrentImage not supported for %s",
                            entity));
        }
    }

    public int getActionPeriod(){return this.actionPeriod;}

    public int getImageIndex() {return this.imageIndex;}

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore),
                this.actionPeriod);
        scheduler.scheduleEvent(this, new Animation(this, QUAKE_ANIMATION_REPEAT_COUNT),
                this.getAnimationPeriod());
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        executeQuakeActivity(world, imageStore, scheduler);
    }

    public void executeQuakeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    public int getAnimationPeriod()
    {

        return this.animationPeriod;
//        switch (this.kind)
//        {
//            case OCTO_FULL:
//            case OCTO_NOT_FULL:
//            case CRAB:
//            case QUAKE:
//            case ATLANTIS:
//                return this.animationPeriod;
//            default:
//                throw new UnsupportedOperationException(
//                        String.format("getAnimationPeriod not supported for %s",
//                                this.kind));
//        }
    }

    public void nextImage()
    {
        imageIndex = (imageIndex + 1) % this.images.size();
    }





}
