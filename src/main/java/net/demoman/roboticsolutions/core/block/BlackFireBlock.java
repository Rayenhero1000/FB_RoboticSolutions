package net.demoman.roboticsolutions.core.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.demoman.roboticsolutions.core.particle.ModParticles;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.demoman.roboticsolutions.core.ModRegistration.BLACKFIRE;
import static net.minecraft.block.CampfireBlock.*;

public class BlackFireBlock extends AbstractFireBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_15;
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final BooleanProperty UP = SixWayBlock.UP;
    private static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter((facingProperty) -> {
        return facingProperty.getKey() != Direction.DOWN;
    }).collect(Util.toMapCollector());
    private static final VoxelShape BLACKFIRE_SHAPE_UP = Block.makeCuboidShape(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape BLACKFIRE_SHAPE_WEST = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    private static final VoxelShape BLACKFIRE_SHAPE_EAST = Block.makeCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape BLACKFIRE_SHAPE_NORTH = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    private static final VoxelShape BLACKFIRE_SHAPE_SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
    private final Map<BlockState, VoxelShape> stateToShapeMap;
    private final Object2IntMap<Block> encouragements = new Object2IntOpenHashMap<>();
    private final Object2IntMap<Block> unflammabilities = new Object2IntOpenHashMap<>();
    public BlackFireBlock(AbstractBlock.Properties builder) {
        super(builder, 1.0F);
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)).with(NORTH, Boolean.valueOf(false)).with(EAST, Boolean.valueOf(false)).with(SOUTH, Boolean.valueOf(false)).with(WEST, Boolean.valueOf(false)).with(UP, Boolean.valueOf(false)));
        this.stateToShapeMap = ImmutableMap.copyOf(this.stateContainer.getValidStates().stream().filter((state) -> {
            return state.get(AGE) == 0;
        }).collect(Collectors.toMap(Function.identity(), BlackFireBlock::getShapeForState)));
    }

    private static VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelshape = VoxelShapes.empty();
        if (state.get(UP)) {
            voxelshape = BLACKFIRE_SHAPE_UP;
        }

        if (state.get(NORTH)) {
            voxelshape = VoxelShapes.or(voxelshape, BLACKFIRE_SHAPE_NORTH);
        }

        if (state.get(SOUTH)) {
            voxelshape = VoxelShapes.or(voxelshape, BLACKFIRE_SHAPE_SOUTH);
        }

        if (state.get(EAST)) {
            voxelshape = VoxelShapes.or(voxelshape, BLACKFIRE_SHAPE_EAST);
        }

        if (state.get(WEST)) {
            voxelshape = VoxelShapes.or(voxelshape, BLACKFIRE_SHAPE_WEST);
        }

        return voxelshape.isEmpty() ? shapeDown : voxelshape;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.stateToShapeMap.get(state.with(AGE, Integer.valueOf(0)));
    }


    protected boolean canBurn(BlockState state) {
        return false;
    }

    protected boolean isNetherrack(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()) == Blocks.NETHERRACK.getDefaultState();
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return super.getLightValue(state, world, pos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        return worldIn.getBlockState(blockpos).isSolidSide(worldIn, blockpos, Direction.UP);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        Random rand2 = new Random();
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, getTickCooldown(worldIn.rand));
        int i = state.get(AGE);
        if (rand.nextFloat() > 0.2F + (float) i * 0.03F) {
            freezeLiquid(worldIn, pos, rand, state);
        }
        if (rand2.nextFloat() > 0.2F + (float) i * 0.03F) {
            extinguishFire(worldIn, pos, rand, state);
        }
        if (!isValidPosition(state, worldIn, pos)) {
            worldIn.removeBlock(pos, false);
            worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
        }
        Random rand3 = new Random();
        if (this.isNetherrack(worldIn, pos) && rand3.nextFloat() < 0.85F) {
            worldIn.removeBlock(pos, false);
            worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
        }

    }

    @Override
    public void catchFire(BlockState state, World world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        super.catchFire(state, world, pos, face, igniter);
    }
    public void freezeLiquid(World worldIn, BlockPos pos, Random rand, BlockState state) {
        int i = state.get(AGE);
        for (Direction direction : Direction.values()) {
            for (Direction direction2 : Direction.values()) {
                for (Direction direction3 : Direction.values()) {
                    if (rand.nextFloat() > 0.35) {
                        BlockPos blockPos = pos.offset(direction);
                        if (this.isWater(worldIn, pos.offset(direction))) {
                            Random rand2 = new Random();
                            if (rand2.nextFloat() > 0.65 - (float) i * 0.03F) {
                                worldIn.setBlockState(blockPos, Blocks.ICE.getDefaultState());
                            }
                        }
                        if (this.isLava(worldIn, pos.offset(direction))) {
                            Random rand2 = new Random();
                            if (rand2.nextFloat() > 0.65 - (float) i * 0.03F) {
                                worldIn.playSound(null, blockPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
                                worldIn.setBlockState(blockPos, Blocks.OBSIDIAN.getDefaultState());
                                worldIn.playEvent(1501, blockPos, 0);
                            }
                        }
                    }
                    if (rand.nextFloat() > 0.5) {
                        BlockPos blockPos = pos.offset(direction).offset(direction2);
                        if (this.isWater(worldIn, pos.offset(direction).offset(direction2))) {
                            Random rand2 = new Random();
                            if (rand2.nextFloat() > 0.65 - (float) i * 0.03F) {
                                worldIn.setBlockState(blockPos, Blocks.ICE.getDefaultState());
                            }
                        }
                        if (this.isLava(worldIn, pos.offset(direction).offset(direction2))) {
                            Random rand2 = new Random();
                            if (rand2.nextFloat() > 0.65 - (float) i * 0.03F) {
                                worldIn.playSound(null, blockPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
                                worldIn.setBlockState(blockPos, Blocks.OBSIDIAN.getDefaultState());
                                worldIn.playEvent(1501, blockPos, 0);
                            }
                        }
                    }
                    if (rand.nextFloat() > 0.75) {
                        BlockPos blockPos = pos.offset(direction).offset(direction2).offset(direction3);
                        if (this.isWater(worldIn, pos.offset(direction).offset(direction2).offset(direction3))) {
                            Random rand2 = new Random();
                            if (rand2.nextFloat() > 0.65 - (float) i * 0.03F) {
                                worldIn.setBlockState(blockPos, Blocks.ICE.getDefaultState());
                            }
                        }
                        if (this.isLava(worldIn, pos.offset(direction).offset(direction2).offset(direction3))) {
                            Random rand2 = new Random();
                            if (rand2.nextFloat() > 0.65 - (float) i * 0.03F) {
                                worldIn.playSound(null, blockPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
                                worldIn.setBlockState(blockPos, Blocks.OBSIDIAN.getDefaultState());
                                worldIn.playEvent(1501, blockPos, 0);
                            }
                        }
                    }
                }
            }
        }

    }

    private void setFireInfo(Block blockIn, int encouragement, int unflammability) { // Currently unused, maybe useful in the future
        if (blockIn == Blocks.AIR) throw new IllegalArgumentException("Tried to set air on fire... This is bad.");
        this.encouragements.put(blockIn, encouragement);
        this.unflammabilities.put(blockIn, unflammability);
    }

    public void extinguishFire(World worldIn, BlockPos pos, Random rand, BlockState state) {
        int i = state.get(AGE);
        for (Direction direction : Direction.values()) {
            for (Direction direction2 : Direction.values()) {
                for (Direction direction3 : Direction.values()) {
                    if (rand.nextFloat() > 0.25) {
                        if (this.isCampFire(worldIn, pos.offset(direction))) {
                            Random rand2 = new Random();
                            if (rand2.nextFloat() > 0.25 - (float) i * 0.03F) {
                                worldIn.setBlockState(pos.offset(direction), worldIn.getBlockState(pos.offset(direction)).with(LIT, Boolean.FALSE));
                                CampfireBlock.extinguish(worldIn, pos.offset(direction), worldIn.getBlockState(pos.offset(direction)));
                                worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
                            }
                        }
                        if (this.isFire(worldIn, pos.offset(direction))) {
                            Random rand2 = new Random();
                            if (rand2.nextFloat() > 0.25 - (float) i * 0.03F) {
                                worldIn.removeBlock(pos.offset(direction), false);
                                worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
                            }
                        }
                    }
                    if (rand.nextFloat() > 0.50) {
                        if (this.isCampFire(worldIn, pos.offset(direction).offset(direction2))) {
                            Random rand2 = new Random();
                            if (rand2.nextFloat() > 0.30 - (float) i * 0.03F) {
                                worldIn.setBlockState(pos.offset(direction).offset(direction2), worldIn.getBlockState(pos.offset(direction).offset(direction2)).with(LIT, Boolean.FALSE));
                                CampfireBlock.extinguish(worldIn, pos.offset(direction).offset(direction2), worldIn.getBlockState(pos.offset(direction).offset(direction2)));
                                worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
                            }
                        }
                        if (this.isFire(worldIn, pos.offset(direction).offset(direction2))) {
                            Random rand2 = new Random();
                            if (rand2.nextFloat() > 0.50 - (float) i * 0.03F) {
                                worldIn.removeBlock(pos.offset(direction).offset(direction2), false);
                                worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
                            }
                        }
                    }
                    if (rand.nextFloat() > 0.65) {
                        if (this.isCampFire(worldIn, pos.offset(direction).offset(direction2).offset(direction3))) {
                            Random rand2 = new Random();
                            if (rand2.nextFloat() > 0.50 - (float) i * 0.03F) {
                                worldIn.setBlockState(pos.offset(direction).offset(direction2).offset(direction3), worldIn.getBlockState(pos.offset(direction).offset(direction2).offset(direction3)).with(LIT, Boolean.FALSE));
                                CampfireBlock.extinguish(worldIn, pos.offset(direction).offset(direction2).offset(direction3), worldIn.getBlockState(pos.offset(direction).offset(direction2).offset(direction3)));
                                worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
                            }
                        }
                        if (this.isFire(worldIn, pos.offset(direction).offset(direction2).offset(direction3))) {
                            Random rand2 = new Random();
                            if (rand2.nextFloat() > 0.50 - (float) i * 0.03F) {
                                worldIn.removeBlock(pos.offset(direction).offset(direction2).offset(direction3), false);
                                worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
                            }
                        }
                    }
                }
            }
        }

    }


    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, getTickCooldown(worldIn.rand));
    }

    private static int getTickCooldown(Random rand) {
        return 30 + rand.nextInt(10);
    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, NORTH, EAST, SOUTH, WEST, UP);
    }

    public int getUnflammability(BlockState state) { // Currently unused, maybe useful in the future
        return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.get(BlockStateProperties.WATERLOGGED) ? 0 : this.unflammabilities.getInt(state.getBlock());
    }


    public boolean isWater(IBlockReader world, BlockPos pos) {
        return world.getFluidState(pos).getFluid() == Fluids.WATER && !(world.getBlockState(pos.up()).matchesBlock(BLACKFIRE.get()) && world.getFluidState(pos).getFluid() == Fluids.WATER);
    }
    public boolean isLava(IBlockReader world, BlockPos pos) {
        return world.getFluidState(pos).getFluid() == Fluids.LAVA && !(world.getBlockState(pos.up()).matchesBlock(BLACKFIRE.get()) && world.getFluidState(pos).getFluid() == Fluids.LAVA);
    }

    public boolean isFire(IBlockReader world, BlockPos pos) {
        return world.getBlockState(pos).matchesBlock(Blocks.FIRE);
    }
    public boolean isCampFire(IBlockReader world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.matchesBlock(Blocks.CAMPFIRE) && state.get(LIT) == Boolean.valueOf(true);
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(24) == 0) {
            worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
        }

        BlockPos blockpos = pos.down();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        if (!this.canBurn(blockstate) && !blockstate.isSolidSide(worldIn, blockpos, Direction.UP)) {
            if (this.canBurn(worldIn.getBlockState(pos.west()))) {
                for(int j = 0; j < 2; ++j) {
                    double d3 = (double)pos.getX() + rand.nextDouble() * (double)0.1F;
                    double d8 = (double)pos.getY() + rand.nextDouble();
                    double d13 = (double)pos.getZ() + rand.nextDouble();
                    worldIn.addParticle(ModParticles.WHITE_SMOKE_PARTICLE.get(), d3, d8, d13, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canBurn(worldIn.getBlockState(pos.east()))) {
                for(int k = 0; k < 2; ++k) {
                    double d4 = (double)(pos.getX() + 1) - rand.nextDouble() * (double)0.1F;
                    double d9 = (double)pos.getY() + rand.nextDouble();
                    double d14 = (double)pos.getZ() + rand.nextDouble();
                    worldIn.addParticle(ModParticles.WHITE_SMOKE_PARTICLE.get(), d4, d9, d14, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canBurn(worldIn.getBlockState(pos.north()))) {
                for(int l = 0; l < 2; ++l) {
                    double d5 = (double)pos.getX() + rand.nextDouble();
                    double d10 = (double)pos.getY() + rand.nextDouble();
                    double d15 = (double)pos.getZ() + rand.nextDouble() * (double)0.1F;
                    worldIn.addParticle(ModParticles.WHITE_SMOKE_PARTICLE.get(), d5, d10, d15, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canBurn(worldIn.getBlockState(pos.south()))) {
                for(int i1 = 0; i1 < 2; ++i1) {
                    double d6 = (double)pos.getX() + rand.nextDouble();
                    double d11 = (double)pos.getY() + rand.nextDouble();
                    double d16 = (double)(pos.getZ() + 1) - rand.nextDouble() * (double)0.1F;
                    worldIn.addParticle(ModParticles.WHITE_SMOKE_PARTICLE.get(), d6, d11, d16, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canBurn(worldIn.getBlockState(pos.up()))) {
                for(int j1 = 0; j1 < 2; ++j1) {
                    double d7 = (double)pos.getX() + rand.nextDouble();
                    double d12 = (double)(pos.getY() + 1) - rand.nextDouble() * (double)0.1F;
                    double d17 = (double)pos.getZ() + rand.nextDouble();
                    worldIn.addParticle(ModParticles.WHITE_SMOKE_PARTICLE.get(), d7, d12, d17, 0.0D, 0.0D, 0.0D);
                }
            }
        } else {
            for(int i = 0; i < 3; ++i) {
                double d0 = (double)pos.getX() + rand.nextDouble();
                double d1 = (double)pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
                double d2 = (double)pos.getZ() + rand.nextDouble();
                worldIn.addParticle(ModParticles.WHITE_SMOKE_PARTICLE.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }
    public static void init() { // Currently unused, maybe useful in the future
        BlackFireBlock blackFireBlock = (BlackFireBlock)BLACKFIRE.get();
        blackFireBlock.setFireInfo(Blocks.OAK_PLANKS, 5, 20);
        blackFireBlock.setFireInfo(Blocks.SPRUCE_PLANKS, 5, 20);
        blackFireBlock.setFireInfo(Blocks.BIRCH_PLANKS, 5, 20);
    }

}