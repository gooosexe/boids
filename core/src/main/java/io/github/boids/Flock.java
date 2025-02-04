package io.github.boids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Flock {
    // Instance variables
    Boid[] boids;
    float s_w; // steer to avoid crowding local flockmates
    float a_w; // steer towards the average heading of local flockmates
    float c_w; // steer to move towards the average position of local flockmates
    float vision; // radius of vision
    float maxSpeed; // maximum speed of the boids

    /**
     * Constructs a flock with the given size, separation, alignment, cohesion, max speed, and vision.
     *
     * @param size       the number of boids in the flock
     * @param s_w the separation factor
     * @param a_w  the alignment factor
     * @param c_w   the cohesion factor
     * @param maxSpeed   the maximum speed of the boids
     */
    public Flock(int size, float s_w, float a_w, float c_w, float vision, float maxSpeed) {
        boids = new Boid[size];
        this.s_w = s_w;
        this.a_w = a_w;
        this.c_w = c_w;
        this.vision = vision;
        this.maxSpeed = maxSpeed;
        for (int i = 0; i < size; i++) {
            Vector2 position = new Vector2(
                (float) (Math.random() * Gdx.graphics.getWidth()),
                (float) (Math.random() * Gdx.graphics.getHeight())
            );
            Vector2 velocity = new Vector2((float) (Math.random() * 4 - 1), (float) (Math.random() * 4 - 1)).nor();

            boids[i] = new Boid(position, velocity);
        }
    }

    /**
     * Updates the flock.
     */
    public void update() {
        int count;
        Vector2 acc = new Vector2(0, 0);
        for (Boid boid : boids) {
            count = 0;
            Vector2 avgPos = new Vector2(0, 0);
            acc.set(0, 0);
            float avgHeading = 0;
            for (Boid other : boids) {
                if (boid != other) {
                    float distance = boid.pos.dst(other.pos);
                    if (distance < vision) {
                        acc.add(boid.pos.cpy().sub(other.pos).nor().scl((float) (s_w / Math.pow(distance, 2))));
                        avgPos.add(other.pos);
                        avgHeading += other.vel.angleDeg();
                        count++;
                    }
                }
            }
            if (count > 0) {
                Vector2 coh_direction = avgPos.scl(1f / count).sub(boid.pos).nor();
                acc.add(coh_direction.scl((float) Math.sqrt(coh_direction.len())).scl(c_w));
                avgHeading /= count;
                Vector2 finalHeading = boid.vel.cpy().setAngleDeg((1-a_w) * boid.vel.angleDeg() + a_w * avgHeading);
                acc.add(finalHeading.sub(boid.vel).scl(a_w));
            }
            boid.acc = acc;
            boid.update(maxSpeed);
        }
    }

    public void render(ShapeRenderer renderer) {
        for (Boid boid : boids) {
            boid.render(renderer);
        }
    }
}
