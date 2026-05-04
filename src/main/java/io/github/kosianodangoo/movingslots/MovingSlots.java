package io.github.kosianodangoo.movingslots;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(MovingSlots.MODID)
public class MovingSlots {
    public static final String MODID = "moving_slots";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MovingSlots(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }
}
