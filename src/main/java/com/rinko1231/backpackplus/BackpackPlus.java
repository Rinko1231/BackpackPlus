package com.rinko1231.backpackplus;


import com.rinko1231.backpackplus.registry.ModBlocksPlus;
import com.rinko1231.backpackplus.registry.ModItemsPlus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.p3pp3rf1y.sophisticatedbackpacks.registry.RegistryLoader;


@Mod(BackpackPlus.MODID)
public class BackpackPlus {

    public static final String MODID = "drinkit";


    private final RegistryLoader registryLoader = new RegistryLoader();


    public BackpackPlus(IEventBus modBus, Dist dist, ModContainer container) {


        ModItemsPlus.registerHandlers(modBus);
        ModBlocksPlus.registerHandlers(modBus);
        modBus.addListener(BackpackPlus::setup);
        IEventBus eventBus = NeoForge.EVENT_BUS;

    }

    private static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModItemsPlus::registerDispenseBehavior);
        event.enqueueWork(ModItemsPlus::registerCauldronInteractions);

    }


}
