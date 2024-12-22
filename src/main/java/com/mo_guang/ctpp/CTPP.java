package com.mo_guang.ctpp;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.gregtechceu.gtceu.common.data.GTRecipeConditions;
import com.mo_guang.ctpp.core.EventHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CTPP.MODID)
public class CTPP {
    public static final String MODID = "ctpp";

    public CTPP() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addGenericListener(MachineDefinition.class, EventHandler::registerMachines);
        modEventBus.addGenericListener(RecipeConditionType.class, EventHandler::registerRecipeConditions);
        modEventBus.addGenericListener(GTRecipeType.class, EventHandler::registerRecipeTypes);
        DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MODID, name);
    }
}
