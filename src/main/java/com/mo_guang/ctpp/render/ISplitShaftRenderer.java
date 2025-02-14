package com.mo_guang.ctpp.render;

import com.jozufozu.flywheel.backend.Backend;
import com.mo_guang.ctpp.common.blockentity.KineticMachineBlockEntity;
import com.mo_guang.ctpp.common.machine.IKineticMachine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ISplitShaftRenderer extends IKineticMachineRenderer {

    @Override
    @OnlyIn(Dist.CLIENT)
    default void renderSafe(KineticMachineBlockEntity te, float partialTicks, PoseStack ms,
                            MultiBufferSource bufferSource, int light, int overlay) {
        if (!Backend.canUseInstancing(te.getLevel())) {
            Block block = te.getBlockState().getBlock();
            Direction.Axis boxAxis = ((IRotate) block).getRotationAxis(te.getBlockState());
            BlockPos pos = te.getBlockPos();
            float time = AnimationTickHolder.getRenderTime(te.getLevel());

            for (Direction direction : Iterate.directions) {
                Direction.Axis axis = direction.getAxis();
                if (boxAxis == axis) {
                    float offset = KineticBlockEntityRenderer.getRotationOffsetForPosition(te, pos, axis);
                    float angle = time * te.getSpeed() * 3.0F / 10.0F % 360.0F;
                    float modifier = 1.0F;
                    if (te.getMetaMachine() instanceof IKineticMachine kineticMachine) {
                        modifier = kineticMachine.getRotationSpeedModifier(direction);
                    }
                    angle *= modifier;
                    angle += offset;
                    angle = angle / 180.0F * 3.1415927F;
                    SuperByteBuffer superByteBuffer = CachedBufferer.partialFacing(AllPartialModels.SHAFT_HALF,
                            te.getBlockState(), direction);
                    KineticBlockEntityRenderer.kineticRotationTransform(superByteBuffer, te, axis, angle, light);
                    superByteBuffer.renderInto(ms, bufferSource.getBuffer(RenderType.solid()));
                }
            }

        }
    }
}
