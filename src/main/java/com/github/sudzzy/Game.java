package com.github.sudzzy;

import com.github.sudzzy.engine.entities.Camera;
import com.github.sudzzy.engine.entities.Entity;
import com.github.sudzzy.engine.entities.Light;
import com.github.sudzzy.engine.entities.Player;
import com.github.sudzzy.engine.loaders.Loader;
import com.github.sudzzy.engine.models.TexturedModel;
import com.github.sudzzy.engine.render.MasterRenderer;
import com.github.sudzzy.engine.terrains.Terrain;
import com.github.sudzzy.engine.textures.ModelData;
import com.github.sudzzy.engine.textures.ModelTexture;
import com.github.sudzzy.engine.textures.TerrainTexture;
import com.github.sudzzy.engine.textures.TerrainTexturePack;
import com.github.sudzzy.engine.utils.DisplayManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.github.sudzzy.engine.loaders.OBJLoader.loadOBJ;

public class Game {

    private static final Loader LOADER = new Loader();
    private static TexturedModel LONG_GRASS;
    private static TexturedModel FERN;
    private static TexturedModel BUBBLE_TREE;
    private static TexturedModel TREE;
    private static TexturedModel BUNNY;
    private static TexturedModel PLAYER;

    private static TexturedModel createTexturedModel(ModelTexture texture, ModelData data) {
        return new TexturedModel(LOADER.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
                data.getIndices()), texture);
    }

    public static void main(String[] args) {
        // Open a new window for the game.
        DisplayManager.createDisplay();
        LONG_GRASS = createTexturedModel(new ModelTexture(LOADER.loadTexture("long_grass"), 1, 0), loadOBJ("long_grass"));
        LONG_GRASS.getTexture().setFakeLighting(true);
        LONG_GRASS.getTexture().setTransparent(true);
        FERN = createTexturedModel(new ModelTexture(LOADER.loadTexture("fern"), 1, 0), loadOBJ("fern"));
        FERN.getTexture().setFakeLighting(true);
        FERN.getTexture().setTransparent(true);
        FERN.getTexture().setRows(2);
        BUBBLE_TREE = createTexturedModel(new ModelTexture(LOADER.loadTexture("bubble_tree"), 1, 0), loadOBJ("bubble_tree"));
        TREE = createTexturedModel(new ModelTexture(LOADER.loadTexture("tree"), 1, 0), loadOBJ("tree"));
        BUNNY = createTexturedModel(new ModelTexture(LOADER.loadTexture("white"), 1, 0), loadOBJ("bunny"));
        PLAYER = createTexturedModel(new ModelTexture(LOADER.loadTexture("player"), 1, 0), loadOBJ("player"));

        // ***** MAP GENERATION ***** //
        Terrain terrain = createTerrain();
        List<Entity> entities = createEntities(terrain);
        Light light = new Light(new Vector3f(20000, 40000, 20000), new Vector3f(1, 1, 1));
        // ***** MAP GENERATION ***** //

        Player player = new Player(PLAYER, new Vector3f(55, 0, 30), 0.2f);

        Camera camera = new Camera(player);
        camera.setYaw(180);
        camera.getPosition().set(50, 0.5f, 25);

        MasterRenderer renderer = new MasterRenderer();

        while (!Display.isCloseRequested()) {
            player.move(terrain);
            camera.move(terrain);

            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            entities.forEach(renderer::processEntity);
            renderer.render(light, camera);

            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        LOADER.cleanUp();
        DisplayManager.closeDisplay();
    }

    private static List<Entity> createEntities(Terrain terrain) {
        List<Entity> entities = new ArrayList<>();
        Random random = new Random(Long.MAX_VALUE);
        for (int i = 0; i < 500; i++) {
            if (i % 7 == 0) {
                entities.add(new Entity(LONG_GRASS, getTopLocation(terrain, random.nextFloat() * 800, random.nextFloat() * 800), 0, 2));
                entities.add(new Entity(FERN, getTopLocation(terrain, random.nextFloat() * 800, random.nextFloat() * 800), random.nextInt(4), 0.5f));
            }

            if (i % 3 == 0) {
                entities.add(new Entity(BUBBLE_TREE, getTopLocation(terrain, random.nextFloat() * 800, random.nextFloat() * 800), 0, 1));
                entities.add(new Entity(TREE, getTopLocation(terrain, random.nextFloat() * 800, random.nextFloat() * 800), 0, 5));
            }
        }
        return entities;
    }

    private static Vector3f getTopLocation(Terrain terrain, float x, float z) {
        return new Vector3f(x, terrain.getHeightOfTerrain(x, z), z);
    }

    private static Terrain createTerrain() {
        TerrainTexture backgroundTexture = new TerrainTexture(LOADER.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(LOADER.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(LOADER.loadTexture("pink_flowers"));
        TerrainTexture bTexture = new TerrainTexture(LOADER.loadTexture("path"));
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(LOADER.loadTexture("blend_map"));
        return new Terrain(0, 0, LOADER, texturePack, blendMap, "heightmap");
    }

}
