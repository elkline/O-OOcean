import java.util.Random;

public interface ActiveEntity extends Entity {

    public static final Random rand = new Random();

    public static final String CRAB_KEY = "crab";
    public static final String CRAB_ID_SUFFIX = " -- crab";
    public static final int CRAB_PERIOD_SCALE = 4;
    public static final int CRAB_ANIMATION_MIN = 50;
    public static final int CRAB_ANIMATION_MAX = 150;

    public static final String FISH_KEY = "fish";
    public static final String FISH_ID_PREFIX = "fish -- ";
    public static final int FISH_CORRUPT_MIN = 20000;
   public static final int FISH_CORRUPT_MAX = 30000;

    public static final String QUAKE_KEY = "quake";

//   public static final int FISH_REACH = 1;
//    public static final int FISH_NUM_PROPERTIES = 5;
//    public static final int FISH_ID = 1;
//    public static final int FISH_COL = 2;
//    public static final int FISH_ROW = 3;
//    public static final int FISH_ACTION_PERIOD = 4;

    public int getActionPeriod();
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

}
