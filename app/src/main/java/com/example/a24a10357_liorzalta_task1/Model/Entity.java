package com.example.a24a10357_liorzalta_task1.Model;

public class Entity {
    private EntityType entityType;   // for next task
    private int imgId;  // for next task
    private final int[] cords;  // entity coordinates on the board

    public  Entity(){
        cords = new int[2];
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public Entity setEntityType(EntityType role) {
        this.entityType = role;
        return this;
    }

    public int getImgId() {
        return imgId;
    }

    public Entity setImgId(int imgId) {
        this.imgId = imgId;
        return this;
    }

    public int[] getCords() {
        return cords;
    }

    public Entity setCords(int x, int y) {
        cords[0] = x;
        cords[1] = y;
        return this;
    }
}
