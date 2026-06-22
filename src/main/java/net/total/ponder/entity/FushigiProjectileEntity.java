package net.total.ponder.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StainedGlassBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.total.ponder.Ponder;
import net.total.ponder.item.PonderItems;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;

import java.util.function.Predicate;

import static net.minecraft.entity.projectile.ProjectileUtil.getEntityCollision;

public class FushigiProjectileEntity extends Entity {

    public FushigiProjectileEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }


    @Nullable
    private BlockState inBlockState;
    private boolean onGround;
    private double lastXno0 = 1;
    private double lastYno0;
    private double lastZno0 = 0;
    public LivingEntity owner;


    @Override
    public void tick() {

        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = this.getWorld().getBlockState(blockPos);

        if (this.isTouchingWaterOrRain() || blockState.isOf(Blocks.POWDER_SNOW)) {
            this.extinguish();
        }

        if (!this.isOnGround()) {
            applyGravity();
        } else {
            applyDrag();
        }

        if (getTotalVelocity() < 0.01) {
            this.setVelocity(0,0,0);
        }

        Vec3d vec3d = this.getVelocity();
        double e = vec3d.x;
        double f = vec3d.y;
        double g = vec3d.z;

        if (Math.abs(vec3d.x) > 0) lastXno0 = vec3d.x;
        if (Math.abs(vec3d.y) > 0) lastYno0 = vec3d.y;
        if (Math.abs(vec3d.z) > 0) lastZno0 = vec3d.z;


        double h = this.getX() + e;
        double j = this.getY() + f;
        double k = this.getZ() + g;

        if (getTotalVelocity()>0.1) {
            this.setYaw((float) (MathHelper.atan2(lastXno0, lastZno0) * (double) (180F / (float) Math.PI)));
            this.setPitch((float) (MathHelper.atan2(-lastYno0, Math.sqrt(lastXno0*lastXno0 + lastZno0*lastZno0)) * (double) (180F / (float) Math.PI)));
        }

        float m = 0.99F;
        if (this.isTouchingWater()) {
            for(int n = 0; n < 4; ++n) {
                float o = 0.25F;
                this.getWorld().addParticle(ParticleTypes.BUBBLE, h - e * (double)0.25F, j - f * (double)0.25F, k - g * (double)0.25F, e, f, g);
            }

            m = 0.75f;
        }

        double l = e + Math.sin(getYaw());
        double n = k + Math.cos(getYaw());

        this.setVelocity(vec3d.multiply((double)m));

        if (this.getTotalVelocity() > 0.3) {
            if (this.getWorld() instanceof ServerWorld serverWorld) {

                for (Entity entity : serverWorld.getOtherEntities(this, new Box(this.getPos().add(-0.2, 0, -0.2), this.getPos().add(0.2, 0.4, 0.2)))) {
                    if (!(entity == owner && age < 10) && (entity.isPushable() || entity.isCollidable())) {
                        if (entity instanceof LivingEntity livingEntity) {
                            livingEntity.damage(livingEntity.getDamageSources().mobProjectile(this, owner), (float) (5 * getTotalVelocity() / 0.5));
                        }
                        entity.setVelocity(this.getVelocity());
                        this.setVelocity(this.getVelocity().multiply(-0.5));
                    }
                }
            }
        }

        if (this.getWorld().getBlockState(blockPos).getBlock() instanceof StainedGlassPaneBlock) {
            this.getWorld().breakBlock(blockPos, false, this);
        }


        this.move(MovementType.SELF, this.getVelocity());
        this.checkBlockCollision();
    }


    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.isSneaking() && player.getMainHandStack().isEmpty()) {
            player.setStackInHand(Hand.MAIN_HAND, PonderItems.FUSHIGI.getDefaultStack());
            this.discard();
        }
        return super.interact(player, hand);
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        this.velocityDirty = true;
        this.velocityModified = true;
        this.setVelocity(source.getAttacker().getRotationVector());
        if (source.getAttacker() instanceof LivingEntity livingEntity) this.owner = livingEntity;
        return super.damage(source, amount);
    }

    @Override
    public boolean isCollidable() {
        return age > 10 && getTotalVelocity() < 0.1;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        super.onPlayerCollision(player);
    }

    private void applyDrag() {
        this.setVelocity(this.getVelocity().multiply(0.75f));
    }

    public double getTotalVelocity() {
        return Math.abs(this.getVelocity().x) + Math.abs(this.getVelocity().y) + Math.abs(this.getVelocity().z);
    }

    @Override
    protected double getGravity() {
        return 0.15;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.setPos(nbt.getDouble("xpos"), nbt.getDouble("ypos"), nbt.getDouble("zpos"));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putDouble("xpos", this.getX());
        nbt.putDouble("ypos", this.getY());
        nbt.putDouble("zpos", this.getZ());
    }

    public ItemStack getDefaultItemStack() {
        return PonderItems.FUSHIGI.getDefaultStack();
    }

}
