package net.demoman.roboticsolutions.events;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.demoman.roboticsolutions.core.fluid.ModFluids;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = RoboticSolutions.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {

    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(ModFluids.MEMORY_JUICE_FLUID.get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(ModFluids.MEMORY_JUICE_BLOCK.get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(ModFluids.MEMORY_JUICE_FLOWING.get(), RenderType.getTranslucent());
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
}
