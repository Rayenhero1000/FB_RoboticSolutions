package net.demoman.roboticsolutions.core;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.demoman.roboticsolutions.core.block.BlackFireBlock;
import net.demoman.roboticsolutions.core.block.TeleportBlock;
import net.demoman.roboticsolutions.core.block.gen.LevelMinus1GenBlock;
import net.demoman.roboticsolutions.core.block.gen.LevelMinus1GenBlock2;
import net.demoman.roboticsolutions.core.block.placeholder.LightPlaceholderBlock;
import net.demoman.roboticsolutions.core.block.placeholder.MiscPlaceholderBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistration {
    // Register items, blocks, etc here. Renderers, however, are to be registered in ClientEvents
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RoboticSolutions.MODID);
    public static final RegistryObject<Block> BLACKFIRE = BLOCKS.register("blackfire",
            () -> new BlackFireBlock(AbstractBlock.Properties.create(Material.FIRE).doesNotBlockMovement().zeroHardnessAndResistance().noDrops().sound(SoundType.CLOTH).setLightLevel((state) -> {
                return 10; })));
    // Level -1
    public static final RegistryObject<Block> DOTTED_GRAY_CONCRETE_FLOOR = BLOCKS.register("dotted_gray_concrete_floor",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 3600000.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WHITE_CONCRETE_WALL = BLOCKS.register("white_concrete_wall",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 3600000.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BLACK_DOOR = BLOCKS.register("black_door",
            () -> new DoorBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MISC_PLACEHOLDER_BLOCK = BLOCKS.register("misc_placeholder_block",
            () -> new MiscPlaceholderBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 3600000.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> LIGHT_PLACEHOLDER_BLOCK = BLOCKS.register("light_placeholder_block",
            () -> new LightPlaceholderBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 3600000.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> TELEPORT_BLOCK = BLOCKS.register("teleport_block",
            () -> new TeleportBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 3600000.0F).sound(SoundType.STONE).doesNotBlockMovement()));
    public static final RegistryObject<Block> LEVEL_MINUS_1_GEN_BLOCK = BLOCKS.register("level_minus_1_gen_block",
            () -> new LevelMinus1GenBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 3600000.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> LEVEL_MINUS_1_GEN_BLOCK_2 = BLOCKS.register("level_minus_1_gen_block_2",
            () -> new LevelMinus1GenBlock2(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 3600000.0F).sound(SoundType.STONE)));
    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
