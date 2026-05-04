package io.github.kosianodangoo.movingslots;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MovingSlots.MODID)
@SuppressWarnings("removal")
public class MovingSlots {
    public static final String MODID = "moving_slots";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MovingSlots() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }
}
