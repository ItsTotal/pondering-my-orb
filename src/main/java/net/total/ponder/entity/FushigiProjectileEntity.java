package net.total.ponder.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.total.ponder.item.PonderItems;
import org.joml.Vector2f;

public class FushigiProjectileEntity extends ThrownItemEntity {
    private float rotation;
    public Vector2f groundedOffset;

    public FushigiProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }



    public float getRenderingRotation(){
        rotation += 0.5f;
        if (rotation >= 360){
            rotation = 0;
        }
        return rotation;
    }


    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), 8);

        if (!this.getWorld().isClient()){
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);

        if(blockHitResult.getSide() == Direction.DOWN){
            groundedOffset = new Vector2f(215f, 180f);
        }
    }

    @Override
    protected Item getDefaultItem() {
        return PonderItems.FUSHIGI;
    }
}
