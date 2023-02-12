package net.demoman.roboticsolutions.core;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.demoman.roboticsolutions.core.fluid.ModFluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.demoman.roboticsolutions.core.group.ModItemGroup.ROBOTIC_SOLUTIONS_GROUP;

public class ItemRegistration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RoboticSolutions.MODID);

    public static final RegistryObject<Item> MEMORY_JUICE_BUCKET = ITEMS.register("memory_juice_bucket",
            () -> new BucketItem(() -> ModFluids.MEMORY_JUICE_FLUID.get(), new Item.Properties().group(ROBOTIC_SOLUTIONS_GROUP).maxStackSize(1)));
    // Level -1
    public static final RegistryObject<BlockItem> DOTTED_GRAY_CONCRETE_FLOOR = ITEMS.register("dotted_gray_concrete_floor",
            () -> new BlockItem(net.demoman.roboticsolutions.core.BlockRegistration.DOTTED_GRAY_CONCRETE_FLOOR.get(), (new Item.Properties()).group(ROBOTIC_SOLUTIONS_GROUP)));
    public static final RegistryObject<BlockItem> WHITE_CONCRETE_WALL = ITEMS.register("white_concrete_wall",
            () -> new BlockItem(net.demoman.roboticsolutions.core.BlockRegistration.WHITE_CONCRETE_WALL.get(), (new Item.Properties()).group(ROBOTIC_SOLUTIONS_GROUP)));
    public static final RegistryObject<BlockItem> BLACK_DOOR = ITEMS.register("black_door",
            () -> new TallBlockItem(net.demoman.roboticsolutions.core.BlockRegistration.BLACK_DOOR.get(), (new Item.Properties()).group(ROBOTIC_SOLUTIONS_GROUP)));
    public static final RegistryObject<BlockItem> MISC_PLACEHOLDER_BLOCK = ITEMS.register("misc_placeholder_block",
            () -> new BlockItem(BlockRegistration.MISC_PLACEHOLDER_BLOCK.get(), (new Item.Properties())));
    public static final RegistryObject<BlockItem> LIGHT_PLACEHOLDER_BLOCK = ITEMS.register("light_placeholder_block",
            () -> new BlockItem(BlockRegistration.LIGHT_PLACEHOLDER_BLOCK.get(), (new Item.Properties())));
    public static final RegistryObject<BlockItem> TELEPORT_BLOCK = ITEMS.register("teleport_block",
            () -> new BlockItem(BlockRegistration.TELEPORT_BLOCK.get(), (new Item.Properties())));
    public static final RegistryObject<BlockItem> LEVEL_MINUS_1_GEN_BLOCK = ITEMS.register("level_minus_1_gen_block",
            () -> new BlockItem(BlockRegistration.LEVEL_MINUS_1_GEN_BLOCK.get(), (new Item.Properties())));
    public static final RegistryObject<BlockItem> LEVEL_MINUS_1_GEN_BLOCK_2 = ITEMS.register("level_minus_1_gen_block_2",
            () -> new BlockItem(BlockRegistration.LEVEL_MINUS_1_GEN_BLOCK_2.get(), (new Item.Properties())));

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
