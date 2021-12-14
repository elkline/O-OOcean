public class Animation implements Action {

    private Entity entity;
    private int repeatCount;

    public Animation(Entity entity, int repeatCount)
   {
      this.entity = entity;
      this.repeatCount = repeatCount;
   }

    public void executeAction(EventScheduler scheduler)
    {
        executeAnimationAction(scheduler);
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

    public void executeAnimationAction(EventScheduler scheduler)
    {
        if(this.entity instanceof OCTO_FULL){
            ((OCTO_FULL) this.entity).nextImage();
            if (this.repeatCount != 1)
            {
                scheduler.scheduleEvent(this.entity,
                        new Animation(this.entity, Math.max(this.repeatCount - 1, 0)), ((OCTO_FULL) this.entity).getAnimationPeriod());
            }
        }
        else if(this.entity instanceof OCTO_NOT_FULL) {
            ((OCTO_NOT_FULL) this.entity).nextImage();
            if (this.repeatCount != 1)
            {
                scheduler.scheduleEvent(this.entity,
                        new Animation(this.entity, Math.max(this.repeatCount - 1, 0)), ((OCTO_NOT_FULL) this.entity).getAnimationPeriod());
            }
        }
        else if(this.entity instanceof CRAB){
            ((CRAB) this.entity).nextImage();
            if (this.repeatCount != 1)
            {
                scheduler.scheduleEvent(this.entity,
                        new Animation(this.entity, Math.max(this.repeatCount - 1, 0)), ((CRAB) this.entity).getAnimationPeriod());
            }
        }
        else if(this.entity instanceof QUAKE){
            ((QUAKE) this.entity).nextImage();
            if (this.repeatCount != 1)
            {
                scheduler.scheduleEvent(this.entity,
                        new Animation(this.entity, Math.max(this.repeatCount - 1, 0)), ((QUAKE) this.entity).getAnimationPeriod());
            }
        }
        else if(this.entity instanceof ATLANTIS){
            ((ATLANTIS) this.entity).nextImage();;
            if (this.repeatCount != 1)
            {
                scheduler.scheduleEvent(this.entity,
                        new Animation(this.entity, Math.max(this.repeatCount - 1, 0)), ((ATLANTIS) this.entity).getAnimationPeriod());
            }
        }
        else{
            throw new UnsupportedOperationException(
                    String.format("executeAnimationAction not supported for %s",
                            this.entity.getClass()));
        }
    }

//    public void nextImage(Entity entity)
//    {
//        entity.getImageIndex() = (entity.imageIndex + 1) % this.images.size();
//    }

}
