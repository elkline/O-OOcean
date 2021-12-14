import processing.core.PImage;

import java.util.List;

public class ATLANTIS implements AnimatedEntity{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    private static final String ATLANTIS_KEY = "atlantis";
    private static final int ATLANTIS_NUM_PROPERTIES = 4;
    private static final int ATLANTIS_ID = 1;
    private static final int ATLANTIS_COL = 2;
    private static final int ATLANTIS_ROW = 3;
    private static final int ATLANTIS_ANIMATION_PERIOD = 70;
    private static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;



    public ATLANTIS(String id, Point position, List<PImage> images){
        this.id = id;
        this.position = position;
        this.images = images;
    }


    public ATLANTIS createAtlantis(String id, Point position, List<PImage> images)
    {
        return new ATLANTIS(id, position, images);
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
        scheduler.scheduleEvent(((Entity) this), new Animation(((Entity) this), ATLANTIS_ANIMATION_REPEAT_COUNT), this.getAnimationPeriod());
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        executeAtlantisActivity(world, imageStore, scheduler);
    }

    public void executeAtlantisActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        scheduler.unscheduleAllEvents((Entity)this);
        world.removeEntity((Entity) this);
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

    public static boolean parseAtlantis(String [] properties, WorldModel world, ImageStore imageStore)
   {
      if (properties.length == ATLANTIS_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[ATLANTIS_COL]),
                 Integer.parseInt(properties[ATLANTIS_ROW]));
         ATLANTIS entity = new ATLANTIS(properties[ATLANTIS_ID],
                 pt, imageStore.getImageList(ATLANTIS_KEY));
         world.tryAddEntity(entity);
      }

      return properties.length == ATLANTIS_NUM_PROPERTIES;
   }

    public void nextImage()
    {
        imageIndex = (imageIndex + 1) % this.images.size();
    }
}
