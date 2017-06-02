package com.github.sudzzy.engine.textures;

import lombok.Data;
import org.lwjgl.util.vector.Vector3f;

@Data
public class Vertex {

    private static final int NO_INDEX = -1;

    private final int index;
    private final Vector3f position;

    private int textureIndex = NO_INDEX;
    private int normalIndex = NO_INDEX;
    private float length;
    private Vertex duplicateVertex = null;

    public boolean isSet() {
        return textureIndex != NO_INDEX && normalIndex != NO_INDEX;
    }

    public boolean hasSameTextureAndNormal(int textureIndexOther, int normalIndexOther) {
        return textureIndexOther == textureIndex && normalIndexOther == normalIndex;
    }

}
