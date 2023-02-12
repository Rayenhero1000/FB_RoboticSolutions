package net.demoman.roboticsolutions.core.world.dimension;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ModDimensions {
    public static RegistryKey<World> LEVEL_MINUS_1_DIMENSION = RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
            new ResourceLocation(RoboticSolutions.MODID, "level_minus_1_dimension"));
    public static boolean minus1Generated = false;
}
