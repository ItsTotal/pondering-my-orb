package net.total.ponder.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class OrbBlock extends Block {
    public OrbBlock(Settings settings) {
        super(settings);
    }
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);


    int cooldown = 20;
    int diceroll = (int)(Math.random()*6+1);
    boolean Ponder = true;
    
    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (cooldown<=0){ 
            diceroll = (int)(Math.random()*6+1);
        }

        String yes = "...Yes...";
        String certain = "...It is Certain..."; 
        String no = "...No...";
        String maybe = "...Maybe...";
        String tryAgain = "...Reply hazy, try again...";
        String doubt = "...Very Doubtful...";
        String how = "...How did you even do this...";
        if (diceroll == 1 && Ponder == true) {
            player.sendMessage(Text.literal(yes), true);
            Ponder = false;
        } else if (diceroll == 2 && Ponder == true) {
            player.sendMessage(Text.literal(certain), true);
            Ponder = false;
        } else if (diceroll == 3 && Ponder == true) {
            player.sendMessage(Text.literal(no), true);
            Ponder = false;
        } else if (diceroll == 4 && Ponder == true) {
            player.sendMessage(Text.literal(maybe), true);
            Ponder = false;
        } else if (diceroll == 5 && Ponder == true) {
            player.sendMessage(Text.literal(tryAgain), true);
            Ponder = false;
        } else if (diceroll == 6 && Ponder == true) {
            player.sendMessage(Text.literal(doubt), true);
            Ponder = false;
        } else {
            Ponder = false;
        }
        for (int i = 0; i < 20; i++) {
            System.out.println(i);
            if (cooldown >= 0){
             cooldown--;
            } else if (cooldown <= 0) {
                cooldown = 20;
                Ponder = true;
                world.playSound(player, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS);
            }
        }
        return super.onUse(state, world, pos, player, hit);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

}
