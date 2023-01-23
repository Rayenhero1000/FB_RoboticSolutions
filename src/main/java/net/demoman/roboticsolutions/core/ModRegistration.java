package net.demoman.roboticsolutions.core;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.demoman.roboticsolutions.core.block.BlackFireBlock;
import net.demoman.roboticsolutions.core.fluid.ModFluids;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.demoman.roboticsolutions.core.ModItemGroup.ROBOTIC_SOLUTIONS_GROUP;

public class ModRegistration {
    // Register items, blocks, etc here. Renderers, however, are to be registered in ClientEvents
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RoboticSolutions.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RoboticSolutions.MODID);
    public static final RegistryObject<Item> MEMORY_JUICE_BUCKET = ITEMS.register("memory_juice_bucket",
            () -> new BucketItem(() -> ModFluids.MEMORY_JUICE_FLUID.get(), new Item.Properties().group(ROBOTIC_SOLUTIONS_GROUP).maxStackSize(1)));
    public static final RegistryObject<Block> BLACKFIRE = BLOCKS.register("blackfire",
            () -> new BlackFireBlock(AbstractBlock.Properties.create(Material.FIRE).doesNotBlockMovement().zeroHardnessAndResistance().noDrops().sound(SoundType.CLOTH).setLightLevel((state) -> {
                return 10; })));


    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
    }
}
