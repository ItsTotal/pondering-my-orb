package net.total.ponder.entity;

import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Collections;

public class EvilPonderOrb extends LivingEntity {


    public EvilPonderOrb(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        this.noClip = true;
        if (age < 20) {
            this.setVelocity(0, 0.125, 0);
        } else {
            if (this.getWorld() instanceof ServerWorld world) {
                PlayerEntity target = world.getClosestPlayer(this, 15f);
                if (target != null) {
                    float distance = this.distanceTo(target);
                    lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getPos().add(0, 1, 0));
                    this.setVelocity(this.getRotationVector().multiply(0.05 + (0.05 * distance)));
                }
            }

            if (age % 10 == 0) {
                playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value(), 0.05f, 1f);
            }
        }
        super.tick();
    }

    @Override
    public Arm getMainArm() {
        return null;
    }

    @Override
    public boolean canTakeDamage() {
        return false;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean canHit() {
        return false;
    }

    @Override
    protected void checkBlockCollision() {

    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        player.damage(player.getDamageSources().magic(), 5f);
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return Collections.singleton(ItemStack.EMPTY);
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    public static DefaultAttributeContainer.Builder createCubeAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 5)
                .add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, 5)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 5);
    }
}
