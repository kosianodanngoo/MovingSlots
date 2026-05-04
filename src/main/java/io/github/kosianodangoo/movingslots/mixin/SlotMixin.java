package io.github.kosianodangoo.movingslots.mixin;

import io.github.kosianodangoo.movingslots.Config;
import io.github.kosianodangoo.movingslots.SlotMotion;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.*;

@Mixin(Slot.class)
public abstract class SlotMixin implements SlotMotion {
    @Shadow
    @Final
    @Mutable
    public int x;
    @Shadow
    @Final
    @Mutable
    public int y;

    @Unique
    private boolean movingSlots$initialized;
    @Unique
    private float movingSlots$fx;
    @Unique
    private float movingSlots$fy;
    @Unique
    private float movingSlots$vx;
    @Unique
    private float movingSlots$vy;
    @Unique
    private long movingSlots$lastNanos;

    @Override
    @Unique
    public void movingSlots$tick(int screenWidth, int screenHeight, int leftPos, int topPos) {
        long now = System.nanoTime();
        if (!movingSlots$initialized) {
            movingSlots$fx = leftPos + this.x;
            movingSlots$fy = topPos + this.y;
            double angle = Math.random() * 2.0 * Math.PI;
            double speed = Config.getMinInitialVelocity() + Math.random() * Config.getRandomDelta();
            movingSlots$vx = (float) (Math.cos(angle) * speed);
            movingSlots$vy = (float) (Math.sin(angle) * speed);
            movingSlots$lastNanos = now;
            movingSlots$initialized = true;
            return;
        }

        float dt = (now - movingSlots$lastNanos) / 1.0e9f;
        if (dt < 0.0f) dt = 0.0f;
        else if (dt > 0.1f) dt = 0.1f;
        movingSlots$lastNanos = now;

        float maxX = Math.max(0.0f, screenWidth - 16.0f);
        float maxY = Math.max(0.0f, screenHeight - 16.0f);

        movingSlots$fx += movingSlots$vx * dt;
        movingSlots$fy += movingSlots$vy * dt;

        if (movingSlots$fx < 0.0f) {
            movingSlots$fx = -movingSlots$fx;
            movingSlots$vx = -movingSlots$vx;
        } else if (movingSlots$fx > maxX) {
            movingSlots$fx = 2.0f * maxX - movingSlots$fx;
            movingSlots$vx = -movingSlots$vx;
        }
        if (movingSlots$fy < 0.0f) {
            movingSlots$fy = -movingSlots$fy;
            movingSlots$vy = -movingSlots$vy;
        } else if (movingSlots$fy > maxY) {
            movingSlots$fy = 2.0f * maxY - movingSlots$fy;
            movingSlots$vy = -movingSlots$vy;
        }

        this.x = Math.round(movingSlots$fx - leftPos);
        this.y = Math.round(movingSlots$fy - topPos);
    }
}
