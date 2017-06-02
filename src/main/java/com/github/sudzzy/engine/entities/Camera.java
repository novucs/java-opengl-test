package com.github.sudzzy.engine.entities;

import com.github.sudzzy.engine.terrains.Terrain;
import lombok.Data;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

@Data
public class Camera {

    private final Player player;

    private Vector3f position = new Vector3f(0, 1, 0);
    private Vector3f oldPlayerPosition = new Vector3f(0, 0, 0);
    private float pitch = 20;
    private float yaw;
    private float roll;
    private float distanceFromPlayer = 15;
    private float angleAroundPlayer = 0;

    public void move(Terrain terrain) {
        calculateAngleAroundPlayer();
        calculateFrom();
        calculatePitch();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(terrain, horizontalDistance, verticalDistance);
        yaw = 180 - (player.getRotY() + angleAroundPlayer);
        if (!oldPlayerPosition.equals(player.getPosition())) {
            angleAroundPlayer = 0;
        }
        oldPlayerPosition.setX(player.getPosition().getX());
        oldPlayerPosition.setY(player.getPosition().getY());
        oldPlayerPosition.setZ(player.getPosition().getZ());
    }

    private void calculateCameraPosition(Terrain terrain, float horizontalDistance, float verticalDistance) {
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.setX(player.getPosition().getX() - offsetX);
        position.setY(player.getPosition().getY() + verticalDistance);
        position.setZ(player.getPosition().getZ() - offsetZ);
        float height = terrain.getHeightOfTerrain(position.getX(), position.getZ()) + 1;
        if (position.getY() < height) position.setY(height);
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateFrom() {
        distanceFromPlayer -= Mouse.getDWheel() * 0.1f;
        if (distanceFromPlayer < 10) distanceFromPlayer = 10;
        if (distanceFromPlayer > 50) distanceFromPlayer = 50;
    }

    private void calculatePitch() {
        if (Mouse.isButtonDown(1)) {
            pitch -= Mouse.getDY() * 0.1f;
            if (pitch < 0) pitch = 0;
            if (pitch > 90) pitch = 90;
        }
    }

    private void calculateAngleAroundPlayer() {
        if (Mouse.isButtonDown(1)) {
            float angleChange = Mouse.getDX() * 0.3f;
            angleAroundPlayer -= angleChange;
        }
    }

}
