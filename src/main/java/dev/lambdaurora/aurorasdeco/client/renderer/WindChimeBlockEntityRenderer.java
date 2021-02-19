/*
 * Copyright (c) 2020 LambdAurora <aurora42lambda@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dev.lambdaurora.aurorasdeco.client.renderer;

import dev.lambdaurora.aurorasdeco.AurorasDeco;
import dev.lambdaurora.aurorasdeco.block.entity.WindChimeBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class WindChimeBlockEntityRenderer implements BlockEntityRenderer<WindChimeBlockEntity> {
    public static final EntityModelLayer WIND_CHIME_MODEL_LAYER = new EntityModelLayer(AurorasDeco.id("wind_chime"),
            "main");
    public static final SpriteIdentifier WIND_CHIME_TEXTURE = new SpriteIdentifier(
            SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE,
            AurorasDeco.id("block/wind_chime"));

    private final MinecraftClient client = MinecraftClient.getInstance();

    private final ModelPart root;
    private final List<ModelPart> chimes = new ArrayList<>();

    public WindChimeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.root = ctx.getLayerModelPart(WIND_CHIME_MODEL_LAYER);

        for (int i = 0; i < 6; i++) {
            chimes.add(this.root.getChild("chime" + (i + 1) + "_body"));
        }
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData chimeBody = root.addChild("chime1_body", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-1.f, -12.f, -1.f, 2.f, 10.f, 2.f),
                ModelTransform.pivot(5.f, 12.f, 10.f));
        chimeBody.addChild("string", ModelPartBuilder.create()
                        .uv(8, 2)
                        .cuboid(-.5f, -2.f, -.5f, 1.f, 2.f, 1.f),
                ModelTransform.NONE);

        chimeBody = root.addChild("chime2_body", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-1.f, -11.f, -1.f, 2.f, 9.f, 2.f),
                ModelTransform.pivot(5.f, 12.f, 7.f));
        chimeBody.addChild("string", ModelPartBuilder.create()
                        .uv(8, 2)
                        .cuboid(-.5f, -2.f, -.5f, 1.f, 2.f, 1.f),
                ModelTransform.NONE);

        chimeBody = root.addChild("chime3_body", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-1.f, -10.f, -1.f, 2.f, 8.f, 2.f),
                ModelTransform.pivot(8.f, 12.f, 5.f));
        chimeBody.addChild("string", ModelPartBuilder.create()
                        .uv(8, 2)
                        .cuboid(-.5f, -2.f, -.5f, 1.f, 2.f, 1.f),
                ModelTransform.NONE);

        chimeBody = root.addChild("chime4_body", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-1.f, -8.f, -1.f, 2.f, 6.f, 2.f),
                ModelTransform.pivot(11.f, 12.f, 6.f));
        chimeBody.addChild("string", ModelPartBuilder.create()
                        .uv(8, 2)
                        .cuboid(-.5f, -2.f, -.5f, 1.f, 2.f, 1.f),
                ModelTransform.NONE);

        chimeBody = root.addChild("chime5_body", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-1.f, -11.f, -1.f, 2.f, 9.f, 2.f),
                ModelTransform.pivot(11.f, 12.f, 9.f));
        chimeBody.addChild("string", ModelPartBuilder.create()
                        .uv(8, 2)
                        .cuboid(-.5f, -2.f, -.5f, 1.f, 2.f, 1.f),
                ModelTransform.NONE);

        chimeBody = root.addChild("chime6_body", ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-1.f, -9.f, -1.f, 2.f, 7.f, 2.f),
                ModelTransform.pivot(8.f, 12.f, 11.f));
        chimeBody.addChild("string", ModelPartBuilder.create()
                        .uv(8, 2)
                        .cuboid(-.5f, -2.f, -.5f, 1.f, 2.f, 1.f),
                ModelTransform.NONE);

        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void render(WindChimeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                       int light, int overlay) {
        float ticks = (float) entity.ticks + tickDelta;
        float angle = MathHelper.sin(ticks / (float) Math.PI) / (4.f + ticks / 3.f);

        this.chimes.forEach(model -> {
            model.pitch = angle;
            model.roll = angle;
        });

        this.root.render(matrices, WIND_CHIME_TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid),
                light, overlay);
    }
}