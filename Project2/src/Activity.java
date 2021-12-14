public class Activity implements Action {

    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;

    public Activity(Entity entity, WorldModel world, ImageStore imageStore) {
      this.entity = entity;
      this.world = world;
      this.imageStore = imageStore;
   }

    public void executeAction(EventScheduler scheduler)
    {
        executeActivityAction(scheduler);
//        switch (this.kind)
//        {
//            case ACTIVITY:
//                executeActivityAction(scheduler);
//                break;
//
//            case ANIMATION:
//                executeAnimationAction(scheduler);
//                break;
//        }
    }

    public void executeActivityAction(EventScheduler scheduler)
    {

        if(this.entity instanceof OCTO_FULL){
            ((OCTO_FULL) this.entity).executeOctoFullActivity(this.world,
                    this.imageStore, scheduler);
        }
        else if(this.entity instanceof OCTO_NOT_FULL) {
            ((OCTO_NOT_FULL) this.entity).executeOctoNotFullActivity(this.world,
                    this.imageStore, scheduler);
        }
        else if(this.entity instanceof FISH){
            ((FISH) this.entity).executeFishActivity(this.world, this.imageStore,
                    scheduler);
        }
        else if(this.entity instanceof CRAB){
            ((CRAB) this.entity).executeCrabActivity(this.world,
                    this.imageStore, scheduler);
        }
        else if(this.entity instanceof QUAKE){
            ((QUAKE) this.entity).executeQuakeActivity(this.world, this.imageStore,
                    scheduler);
        }
        else if(this.entity instanceof SGRASS){
            ((SGRASS) this.entity).executeSgrassActivity(this.world, this.imageStore,
                    scheduler);
        }
        else if(this.entity instanceof ATLANTIS){
            ((ATLANTIS) this.entity).executeAtlantisActivity(this.world, this.imageStore,
                    scheduler);
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

