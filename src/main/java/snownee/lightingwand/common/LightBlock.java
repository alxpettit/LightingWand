package snownee.lightingwand.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.thread.EffectiveSide;

public class LightBlock extends Block implements IWaterLoggable
{
    public LightBlock()
    {
        super(Block.Properties.create(Material.AIR).lightValue(15).sound(SoundType.SLIME));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isAir(BlockState state)
    {
        return true;
    }

    @Override
    public boolean isAir(BlockState state, IBlockReader world, BlockPos pos)
    {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_)
    {
        return (EffectiveSide.get() == LogicalSide.CLIENT && hasItem()) ? VoxelShapes.fullCube() : VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_)
    {
        return VoxelShapes.empty();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (hasItem())
        {
            float x = pos.getX() + 0.3F + rand.nextFloat() * 0.4F;
            float y = pos.getY() + 0.5F;
            float z = pos.getZ() + 0.3F + rand.nextFloat() * 0.4F;

            worldIn.addParticle(new RedstoneParticleData(1, 1, 0, 1.0F), x, y, z, 0, 0, 0);
        }
        super.animateTick(stateIn, worldIn, pos, rand);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean hasItem()
    {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null)
        {
            return false;
        }
        Item main = player.getHeldItemMainhand().getItem();
        Item off = player.getHeldItemOffhand().getItem();
        return main == ModConstants.WAND || off == ModConstants.WAND;
    }
}
