package net.demoman.roboticsolutions.core.events;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.demoman.roboticsolutions.core.BlockRegistration;
import net.demoman.roboticsolutions.core.fluid.ModFluids;
import net.demoman.roboticsolutions.core.world.dimension.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.demoman.roboticsolutions.core.block.gen.LevelMinus1GenBlock.VARIANT;
import static net.demoman.roboticsolutions.core.world.dimension.ModDimensions.minus1Generated;

@Mod.EventBusSubscriber(modid = RoboticSolutions.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {

    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(ModFluids.MEMORY_JUICE_FLUID.get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(ModFluids.MEMORY_JUICE_BLOCK.get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(ModFluids.MEMORY_JUICE_FLOWING.get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(BlockRegistration.BLACKFIRE.get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(BlockRegistration.BLACKFIRE.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(BlockRegistration.TELEPORT_BLOCK.get(), RenderType.getTranslucentNoCrumbling());
        });
    }
    @SubscribeEvent
    public static void memoryJuiceFog (EntityViewRenderEvent.FogColors event) {
        PlayerEntity player = Minecraft.getInstance().player;
        assert player != null;
        double eyeHeight = player.getPosYEye() - 1 / 9d;
        FluidState fluidState = player.getEntityWorld().getFluidState(new BlockPos(player.getPosX(), eyeHeight, player.getPosZ()));
        if (fluidState.getFluid() == ModFluids.MEMORY_JUICE_FLUID.get() || fluidState.getFluid() == ModFluids.MEMORY_JUICE_FLOWING.get()) {
            event.setBlue(0.31f);
            event.setGreen(0.39f);
            event.setRed(1);
        }
    }
    @SubscribeEvent
    public static void cancelWaterOverlay (RenderBlockOverlayEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        assert player != null;
        double eyeHeight = player.getPosYEye() - 1 / 9d;
        FluidState fluidState = player.getEntityWorld().getFluidState(new BlockPos(player.getPosX(), eyeHeight, player.getPosZ()));
        if (fluidState.getFluid() == ModFluids.MEMORY_JUICE_FLUID.get() || fluidState.getFluid() == ModFluids.MEMORY_JUICE_FLOWING.get()) {
            if (event.isCancelable()) {
                event.setCanceled(true);
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getTo() == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(RoboticSolutions.MODID, "level_minus_1_dimension"))) {
            Entity entity = event.getPlayer();
            entity.setPositionAndUpdate(1, 60, 0);
            if (!minus1Generated) {
                event.getPlayer().getEntityWorld().setBlockState(new BlockPos(-4, 59, -4), BlockRegistration.LEVEL_MINUS_1_GEN_BLOCK.get().getDefaultState().with(VARIANT, 0));
                minus1Generated = true;
            }
        }
    }
}
