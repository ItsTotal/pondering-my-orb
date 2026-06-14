package net.total.ponder.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.total.ponder.entity.EvilPonderOrb;
import net.total.ponder.entity.PonderEntities;
import net.total.ponder.statistics.PonderingStat;

public class OrbBlock extends Block {
    public OrbBlock(Settings settings) {
        super(settings);
    }
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public static final BooleanProperty NORMAL;

    int cooldown = 20;
    int diceroll = (int)(Math.random()*6+1);
    boolean Ponder = true;
    
    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.getMainHandStack().getItem() instanceof MilkBucketItem milkBucketItem) {
            world.setBlockState(pos, state.with(NORMAL, false));
            if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, player.getMainHandStack());
                serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(milkBucketItem));
            }
            player.setStackInHand(Hand.MAIN_HAND,ItemUsage.exchangeStack(player.getMainHandStack(), player, new ItemStack(Items.BUCKET), false));
            return ActionResult.SUCCESS;
        }

        if (state.get(NORMAL)) {
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
            for (int i = 0; i < cooldown + 1 && cooldown != 0; i++) {
                System.out.println(cooldown);
                if (cooldown >= 0) {
                    cooldown--;
                }
                if (cooldown <= 0) {
                    Ponder = true;
                    diceroll = (int) (Math.random() * 6 + 1);
                    player.incrementStat(PonderingStat.PONDER);
                    world.playSound(player, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS);
                    cooldown = 20;
                }
            }
        } else {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            EvilPonderOrb ponderOrb = PonderEntities.EVIL_PONDER_ORB.create(world);
            ponderOrb.setPos(pos.getX()+0.5, pos.getY(), pos.getZ()+0.5);
            world.spawnEntity(ponderOrb);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double d = pos.getX() + random.nextDouble();
        double e = pos.getY() + random.nextDouble();
        double f = pos.getZ() + random.nextDouble();
        world.addParticle(ParticleTypes.ENCHANT, d, e, f, 0.0, 0.0, 0.0);
        super.randomDisplayTick(state, world, pos, random);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORMAL);
    }

    static {
        NORMAL = BooleanProperty.of("normal");
    }
}
