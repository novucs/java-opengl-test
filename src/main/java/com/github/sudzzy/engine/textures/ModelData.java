package com.github.sudzzy.engine.textures;

import lombok.Data;

@Data
public class ModelData {

    private final float[] vertices;
    private final float[] textureCoords;
    private final float[] normals;
    private final int[] indices;
    private final float furthestPoint;

}
