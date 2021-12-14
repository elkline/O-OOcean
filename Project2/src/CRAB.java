import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class CRAB implements MoveAndEats{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    private static final String CRAB_KEY = "crab";
    private static final String CRAB_ID_SUFFIX = " -- crab";
    private static final int CRAB_PERIOD_SCALE = 4;
    private static final int CRAB_ANIMATION_MIN = 50;
    private static final int CRAB_ANIMATION_MAX = 150;

    public CRAB(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images){
        this.id = id;
        this.position = position;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.images = images;
    }

    public CRAB createCrab(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        return new CRAB(id, position, actionPeriod, animationPeriod, images);
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
        scheduler.scheduleEvent(this, new Animation(this, 0), this.getAnimationPeriod());
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        executeCrabActivity(world, imageStore, scheduler);
    }

    public void executeCrabActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> crabTarget = world.findNearest(this.position, this.getClass());
        long nextPeriod = this.actionPeriod;

        if (crabTarget.isPresent()) {
            Point tgtPos = crabTarget.get().getPosition();

            if (this.moveToCrab(world, crabTarget.get(), scheduler)) {
                QUAKE quake = new QUAKE(tgtPos, imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.actionPeriod;
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }
    }

    public int getAnimationPeriod() {
        return this.animationPeriod;
    }

    public boolean moveToCrab(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if (this.position.adjacent(target.getPosition()))
        {
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

    public Point nextPosition(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.position.x);
        Point newPos = new Point(this.position.x + horiz,
                this.position.y);

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 || (occupant.isPresent() && !(occupant.get() instanceof FISH)))
        {
            int vert = Integer.signum(destPos.y - this.position.y);
            newPos = new Point(this.position.x, this.position.y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.get() instanceof FISH)))
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
