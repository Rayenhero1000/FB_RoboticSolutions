package net.demoman.roboticsolutions.core;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.demoman.roboticsolutions.core.fluid.ModFluids;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRegistration {
    // Register items, blocks, etc here. Renderers, however, are to be registered in ClientEvents
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RoboticSolutions.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RoboticSolutions.MODID);
    public static final RegistryObject<Item> MEMORY_JUICE_BUCKET = ITEMS.register("memory_juice_bucket",
            () -> new BucketItem(() -> ModFluids.MEMORY_JUICE_FLUID.get(), new Item.Properties().maxStackSize(1)));


    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
    }
}
