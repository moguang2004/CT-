package com.mo_guang.ctpp.mixin;

import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorkableCasingMachineRenderer.class)
public interface WorkableCasingMachineRendererAccessor {
    @Accessor(value = "baseCasing",remap = false)
    ResourceLocation getBaseCasing();
}
