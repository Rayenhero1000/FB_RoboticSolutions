package net.demoman.roboticsolutions.core.fluid;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.demoman.roboticsolutions.core.ModRegistration;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModFluids {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

    public static final DeferredRegister<Fluid> FLUIDS
            = DeferredRegister.create(ForgeRegistries.FLUIDS, RoboticSolutions.MODID);


    public static final RegistryObject<FlowingFluid> MEMORY_JUICE_FLUID
            = FLUIDS.register("memory_juice_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.MEMORY_JUICE_PROPERTIES));

    public static final RegistryObject<FlowingFluid> MEMORY_JUICE_FLOWING
            = FLUIDS.register("memory_juice_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.MEMORY_JUICE_PROPERTIES));


    public static final ForgeFlowingFluid.Properties MEMORY_JUICE_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> MEMORY_JUICE_FLUID.get(), () -> MEMORY_JUICE_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
            .density(15).luminosity(2).gaseous().viscosity(5).sound(SoundEvents.ITEM_BUCKET_FILL, SoundEvents.ITEM_BUCKET_EMPTY).overlay(WATER_OVERLAY_RL)
                .color(0xffff6550)).slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(() -> ModFluids.MEMORY_JUICE_BLOCK.get()).bucket(() -> ModRegistration.MEMORY_JUICE_BUCKET.get());

    public static final RegistryObject<FlowingFluidBlock> MEMORY_JUICE_BLOCK = ModRegistration.BLOCKS.register("memory_juice",
            () -> new FlowingFluidBlock(() -> ModFluids.MEMORY_JUICE_FLUID.get(), AbstractBlock.Properties.create(Material.WATER)
                    .doesNotBlockMovement().hardnessAndResistance(100f).noDrops()));

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}