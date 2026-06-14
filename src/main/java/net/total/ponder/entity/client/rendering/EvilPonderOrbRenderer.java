package net.total.ponder.entity.client.rendering;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import net.total.ponder.block.PonderOrbs;
import net.total.ponder.entity.EvilPonderOrb;
import org.joml.Matrix4f;

public class EvilPonderOrbRenderer extends EntityRenderer<EvilPonderOrb> {
    public EvilPonderOrbRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(EvilPonderOrb entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
        matrices.translate(0,-0.5,0);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.age+tickDelta)*10));
        MinecraftClient.getInstance().getItemRenderer().renderItem(PonderOrbs.PONDERING_ORB.asItem().getDefaultStack(), ModelTransformationMode.FIXED, 255, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();

        matrices.push();
        matrices.scale(0.035f, 0.035f, 0.035f);
        matrices.translate(0, -60, 0);
        renderLightning(matrices, vertexConsumers);
        matrices.pop();
    }

    public void renderLightning(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider) {
        float[] fs = new float[8];
        float[] gs = new float[8];
        float h = 0.0F;
        float j = 0.0F;

        for(int k = 7; k >= 0; --k) {
            fs[k] = h;
            gs[k] = j;
        }

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getLightning());
        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();

        for(int l = 0; l < 4; ++l) {
            Random random2 = Random.create();

            for(int m = 0; m < 3; ++m) {
                int n = 0;
                int o = 0;


                float p = fs[n] - h;
                float q = gs[n] - j;

                for(int r = 3; r >= o; --r) {
                    float s = p;
                    float t = q;
                    if (m == 0) {
                        p += (float)(random2.nextInt(11) - 5);
                        q += (float)(random2.nextInt(11) - 5);
                    } else {
                        p += (float)(random2.nextInt(31) - 15);
                        q += (float)(random2.nextInt(31) - 15);
                    }

                    float u = 0.5F;
                    float v = 0.45F;
                    float w = 0.45F;
                    float x = 0.5F;
                    float y = 0.1F + (float)l * 0.2F;
                    if (m == 0) {
                        y *= (float)r * 0.1F + 1.0F;
                    }

                    float z = 0.1F + (float)l * 0.2F;
                    if (m == 0) {
                        z *= ((float)r - 1.0F) * 0.1F + 1.0F;
                    }

                    drawBranch(matrix4f, vertexConsumer, p, q, r, s, t, 0.45F, 0.45F, 0.5F, y, z, false, false, true, false);
                    drawBranch(matrix4f, vertexConsumer, p, q, r, s, t, 0.45F, 0.45F, 0.5F, y, z, true, false, true, true);
                    drawBranch(matrix4f, vertexConsumer, p, q, r, s, t, 0.45F, 0.45F, 0.5F, y, z, true, true, false, true);
                    drawBranch(matrix4f, vertexConsumer, p, q, r, s, t, 0.45F, 0.45F, 0.5F, y, z, false, true, false, false);
                }
            }
        }

    }

    private static void drawBranch(Matrix4f matrix, VertexConsumer buffer, float x1, float z1, int y, float x2, float z2, float red, float green, float blue, float offset2, float offset1, boolean shiftEast1, boolean shiftSouth1, boolean shiftEast2, boolean shiftSouth2) {
        buffer.vertex(matrix, x1 + (shiftEast1 ? offset1 : -offset1), (float)(y * 16), z1 + (shiftSouth1 ? offset1 : -offset1)).color(red, green, blue, 0.3F);
        buffer.vertex(matrix, x2 + (shiftEast1 ? offset2 : -offset2), (float)((y + 1) * 16), z2 + (shiftSouth1 ? offset2 : -offset2)).color(red, green, blue, 0.3F);
        buffer.vertex(matrix, x2 + (shiftEast2 ? offset2 : -offset2), (float)((y + 1) * 16), z2 + (shiftSouth2 ? offset2 : -offset2)).color(red, green, blue, 0.3F);
        buffer.vertex(matrix, x1 + (shiftEast2 ? offset1 : -offset1), (float)(y * 16), z1 + (shiftSouth2 ? offset1 : -offset1)).color(red, green, blue, 0.3F);
    }

    @Override
    public Identifier getTexture(EvilPonderOrb entity) {
        return null;
    }
}
