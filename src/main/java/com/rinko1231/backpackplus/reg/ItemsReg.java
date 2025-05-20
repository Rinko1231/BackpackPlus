package com.rinko1231.backpackplus.reg;

import com.rinko1231.backpackplus.BackpackPlus;
import com.rinko1231.backpackplus.config.BackpackConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModBlocks;
import net.p3pp3rf1y.sophisticatedcore.upgrades.stack.StackUpgradeItem;

import java.util.Objects;
import java.util.function.IntSupplier;

public class ItemsReg {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, BackpackPlus.MODID);

    public static final DeferredHolder<Item, BackpackItem> BACKPACK2 = ITEMS.register("backpack2", () -> {
        ModConfigSpec.IntValue var10002 = BackpackConfig.InvSlotCount;
        Objects.requireNonNull(var10002);
        IntSupplier var0 = var10002::get;
        ModConfigSpec.IntValue var10003 = BackpackConfig.upgradeSlotCount;
        Objects.requireNonNull(var10003);
        return new BackpackItem(var0, var10003::get, ModBlocks.BACKPACK);
    });
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }


}
