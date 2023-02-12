package net.demoman.roboticsolutions.core.block.gen;

import net.demoman.roboticsolutions.RoboticSolutions;
import net.demoman.roboticsolutions.core.BlockRegistration;
import net.demoman.roboticsolutions.core.state.properties.ModBlockStateProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class LevelMinus1GenBlock2 extends Block {
    public static IntegerProperty VARIANT = ModBlockStateProperties.VARIANT_0_2;

    public LevelMinus1GenBlock2(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(VARIANT, 2));
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, getTickCooldown(worldIn.rand));
        generate(worldIn, pos);
    }
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, getTickCooldown(worldIn.rand));
    }
    public void generate(ServerWorld worldIn, BlockPos pos) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        TemplateManager templateManager = worldIn.getStructureTemplateManager();
        Template template = templateManager.getTemplateDefaulted(new ResourceLocation(RoboticSolutions.MODID
                , "level_minus_one/level_minus_1_main"));
        PlacementSettings placementSettings = new PlacementSettings().setRotation(Rotation.NONE).setMirror(Mirror.NONE).setIgnoreEntities(false);
        template.func_237146_a_(worldIn, pos, pos, placementSettings, worldIn.rand, Constants.BlockFlags.DEFAULT);
        BlockPos offsetPos = new BlockPos(x, y, z - 5f);
        worldIn.setBlockState(offsetPos, BlockRegistration.LEVEL_MINUS_1_GEN_BLOCK_2.get().getDefaultState().with(VARIANT, 2));
    }
    private static int getTickCooldown(Random rand) {
        return 5;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(VARIANT);
    }
}

