package net.demoman.roboticsolutions.core.world.dimension;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModDimensionEffects {
    @OnlyIn(Dist.CLIENT)
    DimensionRenderInfo customEffect = new DimensionRenderInfo(Float.NaN, true, DimensionRenderInfo.FogType.NONE, false, false) {
        @Override
        public Vector3d func_230494_a_(Vector3d color, float sunHeight) {
            return new Vector3d(0.2, 0.2, 0.2);
        }

        @Override
        public boolean func_230493_a_(int x, int y) {
            return true;
        }
        private final Object2ObjectMap<ResourceLocation, DimensionRenderInfo> effectsRegistry = Util.make(new Object2ObjectArrayMap<>(), (effectsRegistry) -> {
            effectsRegistry.put(new ResourceLocation("roboticsolutions:level_minus_1_dimension"), customEffect);
        });
    };
}
