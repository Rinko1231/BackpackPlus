package com.rinko1231.backpackplus;



import com.rinko1231.backpackplus.config.BackpackConfig;
import com.rinko1231.backpackplus.reg.BlocksReg;
import com.rinko1231.backpackplus.reg.ItemsReg;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.p3pp3rf1y.sophisticatedbackpacks.registry.RegistryLoader;


@Mod(BackpackPlus.MODID)
public class BackpackPlus {

    public static final String MODID = "backpackplus";


    private final RegistryLoader registryLoader = new RegistryLoader();


    public BackpackPlus(IEventBus modEventBus, ModContainer modContainer) {

        modContainer.registerConfig(ModConfig.Type.COMMON, BackpackConfig.SPEC,"BackpackPlus.toml");
        NeoForge.EVENT_BUS.register(this);
        ItemsReg.register(modEventBus);
        BlocksReg.register(modEventBus);

    }
    private void commonSetup(final FMLCommonSetupEvent event) {

    }
    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }


}
