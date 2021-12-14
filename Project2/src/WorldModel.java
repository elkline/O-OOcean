import processing.core.PImage;

import java.util.*;

/*
WorldModel ideally keeps track of the actual size of our grid world and what is in that world
in terms of entities and background elements
 */

final class WorldModel
{
   private int numRows;
   private int numCols;
   private Background background[][];
   private Entity occupancy[][];
   private Set<Entity> entities;

   private static final int FISH_REACH = 1;
   private static final int PROPERTY_KEY = 0;

   private static final String BGND_KEY = "background";
   private static final String OCTO_KEY = "octo";
   private static final String OBSTACLE_KEY = "obstacle";
   private static final String FISH_KEY = "fish";
   private static final String ATLANTIS_KEY = "atlantis";
   private static final String SGRASS_KEY = "seaGrass";
   private static final String CRAB_KEY = "crab";
   private static final String QUAKE_KEY = "quake";

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public int getNumRows(){return this.numRows;}

   public int getNumCols(){return this.numCols;}

   public Set<Entity> getEntities(){return this.entities;}

   public Optional<Point> findOpenAround(Point pos)
   {
      for (int dy = -(FISH_REACH); dy <= FISH_REACH; dy++)
      {
         for (int dx = -(FISH_REACH); dx <= FISH_REACH; dx++)
         {
            Point newPt = new Point(pos.x + dx, pos.y + dy);
            if (this.withinBounds(newPt) &&
                    !this.isOccupied(newPt))
            {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }

   public void load(Scanner in, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!processLine(in.nextLine(), this, imageStore))
            {
               System.err.println(String.format("invalid entry on line %d",
                       lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line %d",
                    lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }



   public void tryAddEntity(Entity entity)
   {
      if (this.isOccupied(entity.getPosition()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      this.addEntity(entity);
   }

   public boolean withinBounds(Point pos)
   {
      return pos.y >= 0 && pos.y < this.numRows &&
              pos.x >= 0 && pos.x < this.numCols;
   }

   public boolean isOccupied(Point pos)
   {
      return this.withinBounds(pos) &&
              this.getOccupancyCell(pos) != null;
   }

   public void addEntity(Entity entity)
   {
      if (this.withinBounds(entity.getPosition()))
      {
         this.setOccupancyCell(entity.getPosition(), entity);
         this.entities.add(entity);
      }
   }

   public void moveEntity(Entity entity, Point pos)
   {
      Point oldPos = entity.getPosition();
      if (this.withinBounds(pos) && !pos.equals(oldPos))
      {
         this.setOccupancyCell(oldPos, null);
         this.removeEntityAt(pos);
         this.setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }

   public void removeEntity(Entity entity)
   {
      this.removeEntityAt(entity.getPosition());
   }

   public void removeEntityAt(Point pos)
   {
      if (this.withinBounds(pos)
              && this.getOccupancyCell(pos) != null)
      {
         Entity entity = this.getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         this.entities.remove(entity);
         this.setOccupancyCell(pos, null);
      }
   }

   public Optional<PImage> getBackgroundImage(Point pos)
   {
      if (this.withinBounds(pos))
      {
         return Optional.of(getCurrentImage(this.getBackgroundCell(pos)));
      }
      else
      {
         return Optional.empty();
      }
   }

   public PImage getCurrentImage(Object entity)
   {
         return ((Background)entity).getImages().get(((Background)entity).getImageIndex());
   }

   public void setBackground(Point pos,
                                    Background background)
   {
      if (this.withinBounds(pos))
      {
         this.setBackgroundCell(pos, background);
      }
   }

   public Optional<Entity> getOccupant(Point pos)
   {
      if (this.isOccupied(pos))
      {
         return Optional.of(this.getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }

   public Entity getOccupancyCell(Point pos)
   {
      return this.occupancy[pos.y][pos.x];
   }

   public void setOccupancyCell(Point pos,
                                       Entity entity)
   {
      this.occupancy[pos.y][pos.x] = entity;
   }

   public Background getBackgroundCell(Point pos)
   {
      return this.background[pos.y][pos.x];
   }

   public void setBackgroundCell(Point pos,
                                        Background background)
   {
      this.background[pos.y][pos.x] = background;
   }

   public static Optional<Entity> nearestEntity(List<Entity> entities,
                                                  Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);
         int nearestDistance = nearest.getPosition().distanceSquared(pos);

         for (Entity other : entities)
         {
            int otherDistance = other.getPosition().distanceSquared(pos);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

//   public Optional<Entity> findNearest(Point pos,
//                                         Entity type)
//   {
//      List<Entity> ofType = new LinkedList<>();
//
//      if(type instanceof CRAB){
//         for (Entity entity : this.entities)
//         {
//            if (entity instanceof SGRASS)
//            {
//               ofType.add(entity);
//            }
//         }
//      }
//
//      if(type instanceof OCTO_NOT_FULL){
//         for (Entity entity : this.entities)
//         {
//            if (entity instanceof FISH)
//            {
//               ofType.add(entity);
//            }
//         }
//      }
//
//      if(type instanceof OCTO_FULL){
//         for (Entity entity : this.entities)
//         {
//            if (entity instanceof ATLANTIS)
//            {
//               ofType.add(entity);
//            }
//         }
//      }
//
//
////      for (Entity entity : this.entities)
////      {
////         if (entity.getClass() == kind)
////         {
////            ofType.add(entity);
////         }
////      }
//
//      return nearestEntity(ofType, pos);
//   }

   public Optional<Entity> findNearest(Point pos,
                                              Class kind)
   {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : this.entities)
      {
         if (entity.getClass() == kind)
         {
            ofType.add(entity);
         }
      }

      return nearestEntity(ofType, pos);
   }

   public boolean processLine(String line, WorldModel world, ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0)
      {
         switch (properties[PROPERTY_KEY])
         {
            case BGND_KEY:
               return Background.parseBackground(properties, world, imageStore);
            case OCTO_KEY:
               return OCTO_NOT_FULL.parseOcto(properties, world, imageStore);
            case OBSTACLE_KEY:
               return OBSTACLE.parseObstacle(properties, world, imageStore);
            case FISH_KEY:
               return FISH.parseFish(properties, world, imageStore);
            case ATLANTIS_KEY:
               return ATLANTIS.parseAtlantis(properties, world, imageStore);
            case SGRASS_KEY:
               return SGRASS.parseSgrass(properties, world, imageStore);
         }
      }

      return false;
   }

}
