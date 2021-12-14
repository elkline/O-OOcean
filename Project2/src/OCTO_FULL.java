import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class OCTO_FULL implements OctoEntity{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    private static final String OCTO_KEY = "octo";
    private static final int OCTO_NUM_PROPERTIES = 7;
    private static final int OCTO_ID = 1;
    private static final int OCTO_COL = 2;
    private static final int OCTO_ROW = 3;
    private static final int OCTO_LIMIT = 4;
    private static final int OCTO_ACTION_PERIOD = 5;
    private static final int OCTO_ANIMATION_PERIOD = 6;

    public OCTO_FULL(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images){
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    public OCTO_FULL createOctoFull(String id, int resourceLimit, Point position, int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new OCTO_FULL(id, resourceLimit, position, actionPeriod, animationPeriod, images);
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
        scheduler.scheduleEvent(this, new Animation(this, 0), getAnimationPeriod());
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        executeOctoFullActivity(world, imageStore, scheduler);
    }

    public void executeOctoFullActivity(WorldModel world,
                                        ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(this.position, this.getClass());

        if (fullTarget.isPresent() &&
                this.moveToFull(world, fullTarget.get(), scheduler))
        {
            //at atlantis trigger animation
//            fullTarget.get().scheduleActions(scheduler, world, imageStore);
            scheduleActions(scheduler, world, imageStore);

            //transform to unfull
            this.transformFull(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore),
                    this.actionPeriod);
        }
    }

    public int getAnimationPeriod() {
        return this.animationPeriod;
    }

    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (this.position.adjacent(target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public boolean moveToNotFull(WorldModel world,
                                 Entity target, EventScheduler scheduler)
    {
        if (this.position.adjacent(target.getPosition()))
        {
            this.resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public void transformFull(WorldModel world,
                              EventScheduler scheduler, ImageStore imageStore)
    {
        OCTO_NOT_FULL octo = new OCTO_NOT_FULL(this.id, this.resourceLimit,
                this.position, this.actionPeriod, this.animationPeriod,
                this.images);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(octo);
        octo.scheduleActions(scheduler, world, imageStore);
    }

    public Point nextPosition(WorldModel world,
                                  Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.position.x);
        Point newPos = new Point(this.position.x + horiz,
                this.position.y);

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.y - this.position.y);
            newPos = new Point(this.position.x,
                    this.position.y + vert);

            if (vert == 0 || world.isOccupied(newPos))
            {
                newPos = this.position;
            }
        }

        return newPos;
    }

    public void nextImage()
    {
        imageIndex = (imageIndex + 1) % this.images.size();
    }




}
