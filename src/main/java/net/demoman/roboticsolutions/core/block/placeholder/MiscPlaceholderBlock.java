package net.demoman.roboticsolutions.core.block.placeholder;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.forameus.faithful.block.CrateBlock;
import net.forameus.faithful.block.FireAlarmBlock;
import net.forameus.faithful.block.FireExtinguisherBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import static net.forameus.faithful.block.FireAlarmBlock.CustomBlock.FACING;

public class MiscPlaceholderBlock extends Block {
    public MiscPlaceholderBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, getTickCooldown(worldIn.rand));
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, getTickCooldown(worldIn.rand));
        if (worldIn.getDimensionKey() == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(RoboticSolutions.MODID, "level_minus_1_dimension"))) {
            if (rand.nextFloat() > 0.85f) {
                Random rand2 = new Random();
                if (rand2.nextFloat() >= 0.5f) {
                    worldIn.removeBlock(pos, false);
                    worldIn.setBlockState(pos.down(), CrateBlock.block.getDefaultState());
                } else if (rand2.nextFloat() < 0.5f && rand2.nextFloat() > 0.25f) {
                    if (pos.getX() == 3f) {
                        worldIn.setBlockState(pos, FireAlarmBlock.block.getDefaultState().with(FACING, Direction.WEST));
                    } else {
                        worldIn.setBlockState(pos, FireAlarmBlock.block.getDefaultState().with(FACING, Direction.EAST));
                    }
                } else {
                    worldIn.removeBlock(pos, false);
                    if (pos.getX() == 3f) {
                        worldIn.setBlockState(pos.down(), FireExtinguisherBlock.block.getDefaultState().with(FACING, Direction.WEST));
                    } else {
                        worldIn.setBlockState(pos.down(), FireExtinguisherBlock.block.getDefaultState().with(FACING, Direction.EAST));
                    }
                }
            } else {
                worldIn.removeBlock(pos, false);
            }
        }
    }
    private static int getTickCooldown(Random rand) {
        return 3;
    }

}
