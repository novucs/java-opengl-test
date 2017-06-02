package com.github.sudzzy.engine.textures;

import lombok.Data;

@Data
public class ModelTexture {

    private final int textureID;

    private final float shineDamper;

    private final float reflectivity;

    private boolean transparent = false;

    private boolean fakeLighting = false;

    private int rows = 1;

}
