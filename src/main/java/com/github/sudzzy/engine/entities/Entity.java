package com.github.sudzzy.engine.entities;

import com.github.sudzzy.engine.models.TexturedModel;
import lombok.Data;
import org.lwjgl.util.vector.Vector3f;

@Data
public class Entity {

    private final TexturedModel model;
    private final Vector3f position;
    private final int textureIndex;
    private final float scale;
    private float rotX, rotY, rotZ = 0;

    public float getTextureXOffset() {
        int column = textureIndex % model.getTexture().getRows();
        return (float) column / (float) model.getTexture().getRows();
    }

    public float getTextureYOffset() {
        int row = textureIndex / model.getTexture().getRows();
        return (float) row / (float) model.getTexture().getRows();
    }

    public void increasePosition(float dx, float dy, float dz) {
        position.x += dx;
        position.y += dy;
        position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        rotX += dx;
        rotY += dy;
        rotZ += dz;
    }

}
