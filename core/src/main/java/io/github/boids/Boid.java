package io.github.boids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * A class representing a boid.
 */
public class Boid {
    // Instance variables
    Vector2 pos;
    Vector2 vel;
    Vector2 acc;
    float heading;

    /**
     * Constructs a boid with the given position, velocity, and heading.
     * @param pos
     * @param vel
     */
    public Boid(Vector2 pos, Vector2 vel) {
        this.pos = pos;
        this.vel = vel;
        this.acc = new Vector2(0, 0);
        this.heading = vel.angleDeg();
    }

    /**
     * Updates the boid's position.
     */
    public void update(float maxSpeed) {
        vel.add(acc);
        if (vel.len() > maxSpeed) vel.setLength(maxSpeed);
        pos.add(vel);
        if (pos.x > Gdx.graphics.getWidth()) pos.x = vel.x;
        if (pos.x < 0) pos.x = Gdx.graphics.getWidth() - vel.x;
        if (pos.y > Gdx.graphics.getHeight()) pos.y = vel.y;
        if (pos.y < 0) pos.y = Gdx.graphics.getHeight() - vel.y;
    }

    public void changeHeading(float angle) {
        heading += angle;
        vel.setAngleDeg(heading);
    }

    /**
     * Renders the boid.
     */
    public void render(ShapeRenderer renderer) {
        // Render the boid
        renderer.circle(pos.x, pos.y, 3);
        // Draw a line towards velocity for the boid
        renderer.line(pos.x, pos.y, pos.x + vel.x * 5, pos.y + vel.y * 5);
    }
}
