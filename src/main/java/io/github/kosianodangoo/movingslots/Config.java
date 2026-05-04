package io.github.kosianodangoo.movingslots;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = MovingSlots.MODID)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue MIN_INITIAL_VELOCITY = BUILDER.comment("Min Initial Velocity").defineInRange("minInitialVelocity", 40, 0, Double.POSITIVE_INFINITY);
    public static final ModConfigSpec.DoubleValue MAX_INITIAL_VELOCITY = BUILDER.comment("Max Initial Velocity").defineInRange("maxInitialVelocity", 160, 0, Double.POSITIVE_INFINITY);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static double minInitialVelocity;
    private static double randomDelta;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        minInitialVelocity = MIN_INITIAL_VELOCITY.get();
        randomDelta = MAX_INITIAL_VELOCITY.get() - minInitialVelocity;
    }

    public static double getMinInitialVelocity() {
        return minInitialVelocity;
    }

    public static double getRandomDelta() {
        return randomDelta;
    }
}
