public class Parse {

    private static final String OBSTACLE_KEY = "obstacle";
    private static final int OBSTACLE_NUM_PROPERTIES = 4;
    private static final int OBSTACLE_ID = 1;
    private static final int OBSTACLE_COL = 2;
    private static final int OBSTACLE_ROW = 3;

    private static final String SGRASS_KEY = "seaGrass";
    private static final int SGRASS_NUM_PROPERTIES = 5;
    private static final int SGRASS_ID = 1;
    private static final int SGRASS_COL = 2;
    private static final int SGRASS_ROW = 3;
    private static final int SGRASS_ACTION_PERIOD = 4;

    private static final String FISH_KEY = "fish";
    private static final int FISH_NUM_PROPERTIES = 5;
    private static final int FISH_ID = 1;
    private static final int FISH_COL = 2;
    private static final int FISH_ROW = 3;
    private static final int FISH_ACTION_PERIOD = 4;

    private static final String ATLANTIS_KEY = "atlantis";
    private static final int ATLANTIS_NUM_PROPERTIES = 4;
    private static final int ATLANTIS_ID = 1;
    private static final int ATLANTIS_COL = 2;
    private static final int ATLANTIS_ROW = 3;

    private static final String OCTO_KEY = "octo";
    private static final int OCTO_NUM_PROPERTIES = 7;
    private static final int OCTO_ID = 1;
    private static final int OCTO_COL = 2;
    private static final int OCTO_ROW = 3;
    private static final int OCTO_LIMIT = 4;
    private static final int OCTO_ACTION_PERIOD = 5;
    private static final int OCTO_ANIMATION_PERIOD = 6;

    private static final int BGND_NUM_PROPERTIES = 4;
    private static final int BGND_ID = 1;
    private static final int BGND_COL = 2;
    private static final int BGND_ROW = 3;

    public static boolean parseObstacle(String [] properties, WorldModel world,
                                        ImageStore imageStore)
    {
        if (properties.length == OBSTACLE_NUM_PROPERTIES)
        {
            Point pt = new Point(
                    Integer.parseInt(properties[OBSTACLE_COL]),
                    Integer.parseInt(properties[OBSTACLE_ROW]));
            OBSTACLE entity = new OBSTACLE(properties[OBSTACLE_ID],
                    pt, imageStore.getImageList(OBSTACLE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }

    public static boolean parseSgrass(String [] properties, WorldModel world,
                                      ImageStore imageStore)
    {
        if (properties.length == SGRASS_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[SGRASS_COL]),
                    Integer.parseInt(properties[SGRASS_ROW]));
            SGRASS entity = new SGRASS(properties[SGRASS_ID],
                    pt,imageStore.getImageList(SGRASS_KEY),
                    Integer.parseInt(properties[SGRASS_ACTION_PERIOD]));
            world.tryAddEntity(entity);
        }

        return properties.length == SGRASS_NUM_PROPERTIES;
    }

    public static boolean parseFish(String [] properties, WorldModel world,
                                    ImageStore imageStore)
    {
        if (properties.length == FISH_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[FISH_COL]),
                    Integer.parseInt(properties[FISH_ROW]));
            FISH entity = new FISH (properties[FISH_ID],
                    pt,  imageStore.getImageList(FISH_KEY), Integer.parseInt(properties[FISH_ACTION_PERIOD]));
            world.tryAddEntity(entity);
        }

        return properties.length == FISH_NUM_PROPERTIES;
    }

    public static boolean parseAtlantis(String [] properties, WorldModel world, ImageStore imageStore)
    {
        if (properties.length == ATLANTIS_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[ATLANTIS_COL]),
                    Integer.parseInt(properties[ATLANTIS_ROW]));
            ATLANTIS entity = new ATLANTIS(properties[ATLANTIS_ID],
                    pt, imageStore.getImageList(ATLANTIS_KEY), 0, 0);
            world.tryAddEntity(entity);
        }
        return properties.length == ATLANTIS_NUM_PROPERTIES;
    }

    public static boolean parseOcto(String [] properties, WorldModel world,
                                    ImageStore imageStore) {
        if (properties.length == OCTO_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[OCTO_COL]),
                    Integer.parseInt(properties[OCTO_ROW]));
            OCTO_NOT_FULL entity = new OCTO_NOT_FULL(properties[OCTO_ID], pt, imageStore.getImageList(OCTO_KEY), Integer.parseInt(properties[OCTO_ACTION_PERIOD]), Integer.parseInt(properties[OCTO_ANIMATION_PERIOD]), Integer.parseInt(properties[OCTO_LIMIT]), 0);
            world.tryAddEntity(entity);
        }

        return properties.length == OCTO_NUM_PROPERTIES;

    }

    public static boolean parseBackground(String [] properties,
                                          WorldModel world, ImageStore imageStore)
    {
        if (properties.length == BGND_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                    Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            world.setBackground(pt,
                    new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == BGND_NUM_PROPERTIES;
    }

}
