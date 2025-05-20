package com.rinko1231.backpackplus.registry;

import com.mojang.datafixers.types.Type;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlock;

import java.util.function.Supplier;

public class ModBlocksPlus {

    private static final DeferredRegister<Block> BLOCKS;
    public static final Supplier<BackpackBlock> BACKPACK2;
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES;
    public static final Supplier<BlockEntityType<BackpackBlockEntityPlus>> BACKPACK_TILE_TYPE;

    private ModBlocksPlus() {
    }

    public static void registerHandlers(IEventBus modBus) {
        BLOCKS.register(modBus);
        BLOCK_ENTITY_TYPES.register(modBus);
       NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, BackpackBlockPlus::playerInteract);
        modBus.addListener(ModBlocksPlus::registerCapabilities);

    }
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, (BlockEntityType)BACKPACK_TILE_TYPE.get(), BackpackBlockEntityPlus::getExternalItemHandler);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, (BlockEntityType)BACKPACK_TILE_TYPE.get(), BackpackBlockEntityPlus::getExternalFluidHandler);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, (BlockEntityType)BACKPACK_TILE_TYPE.get(), BackpackBlockEntityPlus::getExternalEnergyStorage);
    }


    static {
        BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, "backpackplus");
       BACKPACK2 = BLOCKS.register("backpack2", () -> new BackpackBlockPlus());
        BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "backpackplus");

        BACKPACK_TILE_TYPE = BLOCK_ENTITY_TYPES.register("backpack2", () -> BlockEntityType.Builder.of(BackpackBlockEntityPlus::new, new Block[]{(Block)BACKPACK2.get()}).build((Type)null));

    }
}
