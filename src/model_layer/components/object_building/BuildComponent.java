package model_layer.components.object_building;

public class BuildComponent {
    private Integer ticksToUpdate;
    private Integer ticksUpdated;
    private Boolean beingUpdated;

    public BuildComponent(Integer ticksToUpdate) {
        this.ticksToUpdate = ticksToUpdate;
        this.ticksUpdated = 0;
    }

    public Integer getTicksToUpdate() {
        return ticksToUpdate;
    }

    public Integer getTicksUpdated() {
        return ticksUpdated;
    }

    public void incrementTicks() {
        ticksUpdated += 1;
    }

    public double getPercentUpdated() {
        return ticksUpdated / ticksToUpdate;
    }

    public Boolean getBeingUpdated() {
        return beingUpdated;
    }

    public void setBeingUpdated(Boolean beingUpdated) {
        this.beingUpdated = beingUpdated;
    }
}
