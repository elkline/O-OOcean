public class Activity extends Action {

    private WorldModel world;
    private ImageStore imageStore;

    public Activity(Entity entity, WorldModel world, ImageStore imageStore) {
      this.entity = entity;
      this.world = world;
      this.imageStore = imageStore;
   }

    public void executeAction(EventScheduler scheduler)
    {
        if(this.entity instanceof OCTO_FULL){
            ((OCTO_FULL) this.entity).executeActivity(this.world,
                    this.imageStore, scheduler);
        }
        else if(this.entity instanceof OCTO_NOT_FULL) {
            ((OCTO_NOT_FULL) this.entity).executeActivity(this.world,
                    this.imageStore, scheduler);
        }
        else if(this.entity instanceof FISH){
            ((FISH) this.entity).executeActivity(this.world, this.imageStore,
                    scheduler);
        }
        else if(this.entity instanceof CRYSTAL){
            ((CRYSTAL) this.entity).executeActivity(this.world, this.imageStore,
                    scheduler);
        }
        else if(this.entity instanceof CRAB){
            ((CRAB) this.entity).executeActivity(this.world,
                    this.imageStore, scheduler);
        }
        else if(this.entity instanceof QUAKE){
            ((QUAKE) this.entity).executeActivity(this.world, this.imageStore,
                    scheduler);
        }
        else if(this.entity instanceof SGRASS){
            ((SGRASS) this.entity).executeActivity(this.world, this.imageStore,
                    scheduler);
        }
        else if(this.entity instanceof ATLANTIS){
            ((ATLANTIS) this.entity).executeActivity(this.world, this.imageStore,
                    scheduler);
        }
        else if(this.entity instanceof GOLD){
            ((GOLD) this.entity).executeActivity(this.world, this.imageStore,
                    scheduler);
        }
        else if(this.entity instanceof DIVER_NOT_FULL) {
            ((DIVER_NOT_FULL) this.entity).executeActivity(this.world,
                    this.imageStore, scheduler);
        }
        else if(this.entity instanceof DIVER_FULL){
            System.out.println("Reached activity");
            ((DIVER_FULL) this.entity).executeActivity(this.world,
                    this.imageStore, scheduler);
        }
        else{
            throw new UnsupportedOperationException(
                    String.format("executeActivityAction not supported for %s",
                            this.entity.getClass()));
        }




//        switch (this.entity.getKind())
//        {
//            case OCTO_FULL:
//                this.entity.executeOctoFullActivity(this.world,
//                        this.imageStore, scheduler);
//                break;
//
//            case OCTO_NOT_FULL:
//                this.entity.executeOctoNotFullActivity(this.world,
//                        this.imageStore, scheduler);
//                break;
//
//            case FISH:
//                this.entity.executeFishActivity(this.world, this.imageStore,
//                        scheduler);
//                break;
//
//            case CRAB:
//                this.entity.executeCrabActivity(this.world,
//                        this.imageStore, scheduler);
//                break;
//
//            case QUAKE:
//                this.entity.executeQuakeActivity(this.world, this.imageStore,
//                        scheduler);
//                break;
//
//            case SGRASS:
//                this.entity.executeSgrassActivity(this.world, this.imageStore,
//                        scheduler);
//                break;
//
//            case ATLANTIS:
//                this.entity.executeAtlantisActivity(this.world, this.imageStore,
//                        scheduler);
//                break;
//
//            default:
//                throw new UnsupportedOperationException(
//                        String.format("executeActivityAction not supported for %s",
//                                this.entity.getKind()));
        }
    }

