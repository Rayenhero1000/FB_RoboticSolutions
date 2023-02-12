package net.demoman.roboticsolutions.core.world.biome;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class ModBiomes {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(
            ForgeRegistries.BIOMES, RoboticSolutions.MODID);
    public static final RegistryObject<Biome> LEVEL_MINUS_1_BIOME = BIOMES.register("level_minus_1_biome",
            ()-> makeLevelMinus1Biome(() -> ModConfiguredSurfaceBuilders.LEVEL_MINUS_1_SURFACE, -1f, -1f));
    private static Biome makeLevelMinus1Biome(final Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder, float depth, float scale) {
        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
        BiomeGenerationSettings.Builder biomegenerationsettings$builder =
                (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(surfaceBuilder);

        return (new Biome.Builder()).precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(depth).scale(scale)
                .temperature(1.5F).downfall(0.5f).setEffects((new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011)
                        .setFogColor(12638463).withSkyColor(7972607).withFoliageColor(10387789).withGrassColor(9470285)
                        .setAmbientSound(Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("faithfulbackrooms:level0ambience"))))
                        .build())
                .withMobSpawnSettings(mobspawninfo$builder.build()).withGenerationSettings(biomegenerationsettings$builder.build()).build();
    }
    public static void init(IEventBus eventBus) {
        BIOMES.register(eventBus);
    }
}
