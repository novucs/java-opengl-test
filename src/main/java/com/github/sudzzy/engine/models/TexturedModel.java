package com.github.sudzzy.engine.models;

import com.github.sudzzy.engine.textures.ModelTexture;
import lombok.Data;

@Data
public class TexturedModel {

    private final RawModel rawModel;
    private final ModelTexture texture;

}
