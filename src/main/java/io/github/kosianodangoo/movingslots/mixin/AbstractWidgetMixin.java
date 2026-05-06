package io.github.kosianodangoo.movingslots.mixin;

import io.github.kosianodangoo.movingslots.Config;
import io.github.kosianodangoo.movingslots.WidgetMotion;
import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.*;

@Mixin(AbstractWidget.class)
public class AbstractWidgetMixin implements WidgetMotion {
    @Shadow private int x;
    @Shadow private int y;
    @Shadow protected int width;
    @Shadow protected int height;

    @Unique
    private boolean movingSlots$initialized;
    @Unique
    private float movingSlots$vx;
    @Unique
    private float movingSlots$vy;
    @Unique
    private float movingSlots$dxAcc;
    @Unique
    private float movingSlots$dyAcc;
    @Unique
    private long movingSlots$lastNanos;

    @Override
    @Unique
    public void movingSlots$tick(int screenWidth, int screenHeight) {
        long now = System.nanoTime();
        if (!movingSlots$initialized) {
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

        int maxX = Math.max(0, screenWidth - this.width);
        int maxY = Math.max(0, screenHeight - this.height);

        movingSlots$dxAcc += movingSlots$vx * dt;
        movingSlots$dyAcc += movingSlots$vy * dt;

        int idx = (int) movingSlots$dxAcc;
        int idy = (int) movingSlots$dyAcc;
        movingSlots$dxAcc -= idx;
        movingSlots$dyAcc -= idy;

        int newX = this.x + idx;
        int newY = this.y + idy;

        if (newX < 0) {
            newX = -newX;
            movingSlots$vx = -movingSlots$vx;
            movingSlots$dxAcc = -movingSlots$dxAcc;
        } else if (newX > maxX) {
            newX = 2 * maxX - newX;
            movingSlots$vx = -movingSlots$vx;
            movingSlots$dxAcc = -movingSlots$dxAcc;
        }
        if (newY < 0) {
            newY = -newY;
            movingSlots$vy = -movingSlots$vy;
            movingSlots$dyAcc = -movingSlots$dyAcc;
        } else if (newY > maxY) {
            newY = 2 * maxY - newY;
            movingSlots$vy = -movingSlots$vy;
            movingSlots$dyAcc = -movingSlots$dyAcc;
        }

        this.x = newX;
        this.y = newY;
    }
}
