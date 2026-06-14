package net.total.ponder.entity.client.rendering;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.util.Identifier;
import net.total.ponder.entity.FushigiProjectileEntity;
import org.joml.Quaternionf;

public class FushigiRenderer extends EntityRenderer<FushigiProjectileEntity> {
    public FushigiRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(FushigiProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (entity.age >= 2 || !(this.dispatcher.camera.getFocusedEntity().squaredDistanceTo(entity) < (double)12.25F)) {
            matrices.push();
            var rotationVector = entity.getRotationVector().normalize();
            float ryaw = (float) Math.atan2(rotationVector.x, rotationVector.z);
            float rpitch = (float) Math.asin(rotationVector.y);
            Quaternionf quaternionf = new Quaternionf().rotateY(ryaw).rotateX(rpitch);
            matrices.multiply(quaternionf);
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            itemRenderer.renderItem(entity.getItemStack(), ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), entity.getId());
            matrices.pop();
            super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        }
    }

    @Override
    public Identifier getTexture(FushigiProjectileEntity entity) {
        return null;
    }
}
