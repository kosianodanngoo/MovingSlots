package io.github.kosianodangoo.movingslots;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = MovingSlots.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.DoubleValue MIN_INITIAL_VELOCITY = BUILDER.comment("Min Initial Velocity").defineInRange("minInitialVelocity", 40, 0, Double.POSITIVE_INFINITY);
    public static final ForgeConfigSpec.DoubleValue MAX_INITIAL_VELOCITY = BUILDER.comment("Max Initial Velocity").defineInRange("maxInitialVelocity", 160, 0, Double.POSITIVE_INFINITY);

    static final ForgeConfigSpec SPEC = BUILDER.build();

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
