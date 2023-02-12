package net.demoman.roboticsolutions.core.world.biome;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.demoman.roboticsolutions.core.BlockRegistration;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class ModConfiguredSurfaceBuilders {

    public static ConfiguredSurfaceBuilder<?> LEVEL_MINUS_1_SURFACE = register("level_minus_1_surface",
            SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(
                    BlockRegistration.WHITE_CONCRETE_WALL.get().getDefaultState(),
                    BlockRegistration.WHITE_CONCRETE_WALL.get().getDefaultState(),
                    BlockRegistration.WHITE_CONCRETE_WALL.get().getDefaultState()
            )));
    private static <SC extends ISurfaceBuilderConfig>ConfiguredSurfaceBuilder<SC> register(String name,
                                                                                           ConfiguredSurfaceBuilder<SC> csb) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER,
                new ResourceLocation(RoboticSolutions.MODID, name), csb);
    }
}
