package net.demoman.roboticsolutions.core.block.placeholder;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.forameus.faithful.block.CeilingLightBlock;
import net.forameus.faithful.block.CeilingLightBrokenBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Random;

public class LightPlaceholderBlock extends Block {
    public LightPlaceholderBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (worldIn.getDimensionKey() == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(RoboticSolutions.MODID, "level_minus_1_dimension"))) {
            Random rand = new Random();
            if (rand.nextFloat() > 0.05f) {
                worldIn.setBlockState(pos, CeilingLightBlock.block.getDefaultState());
            } else {
                worldIn.setBlockState(pos, CeilingLightBrokenBlock.block.getDefaultState());
            }
        }
    }
}
