package com.rinko1231.backpackplus.reg;

import com.rinko1231.backpackplus.BackpackPlus;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlock;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;

import java.util.function.Supplier;

import static com.rinko1231.backpackplus.reg.ItemsReg.ITEMS;

public class BlocksReg {
    private static final DeferredRegister<Block> BLOCKS;
  public static final Supplier<BackpackBlock> BACKPACK2;

    static {
        BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, "backpackplus");
        BACKPACK2 = BLOCKS.register("backpack2", () -> new BackpackBlock());}

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
