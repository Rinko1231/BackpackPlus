package com.rinko1231.backpackplus.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.EmptyItemHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;

import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class ModItemsPlus {

    public static void registerHandlers(IEventBus modBus) {
        ITEMS.register(modBus);

        modBus.addListener(ModItemsPlus::registerCapabilities);

    }

    public static void registerDispenseBehavior() {
        DispenserBlock.registerBehavior((ItemLike)BACKPACK2.get(), new ModItemsPlus.BackpackDispenseBehavior());
    }

    public static void registerCauldronInteractions() {
        CauldronInteraction.WATER.map().put((Item)BACKPACK2.get(), new ModItemsPlus.BackpackCauldronInteraction());
 }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.ItemHandler.ITEM, (stack, v) -> {
            IBackpackWrapper backpackWrapper = BackpackWrapper.fromStack(stack);
            return (IItemHandler)(backpackWrapper.getContentsUuid().isEmpty() ? EmptyItemHandler.INSTANCE : backpackWrapper.getInventoryForInputOutput());
        }, new ItemLike[]{(ItemLike)BACKPACK2.get()});
        event.registerItem(Capabilities.FluidHandler.ITEM, (stack, v) -> Boolean.FALSE.equals(Config.SERVER.itemFluidHandlerEnabled.get()) ? null : (IFluidHandlerItem)BackpackWrapper.fromStack(stack).getItemFluidHandler().orElse((IFluidHandlerItem) null), new ItemLike[]{(ItemLike)BACKPACK2.get()});
        event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, v) -> (IEnergyStorage)BackpackWrapper.fromStack(stack).getEnergyStorage().orElse((IEnergyStorage) null), new ItemLike[]{(ItemLike)BACKPACK2.get()});
    }




    private static class BackpackCauldronInteraction implements CauldronInteraction {
        private BackpackCauldronInteraction() {
        }

        private static boolean hasDefaultColor(IStorageWrapper wrapper) {
            return wrapper.getAccentColor() == -10342886 && wrapper.getMainColor() == -3382982;
        }

        public ItemInteractionResult interact(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
            IBackpackWrapper backpackWrapper = BackpackWrapper.fromStack(stack);
            if (hasDefaultColor(backpackWrapper)) {
                return ItemInteractionResult.FAIL;
            } else {
                if (!level.isClientSide) {
                    backpackWrapper.setColors(-3382982, -10342886);
                    LayeredCauldronBlock.lowerFillLevel(state, level, pos);
                }

                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }
    }

    private static class BackpackDispenseBehavior extends OptionalDispenseItemBehavior {
        private BackpackDispenseBehavior() {
        }

        protected ItemStack execute(BlockSource source, ItemStack stack) {
            this.setSuccess(false);
            Item item = stack.getItem();
            if (item instanceof BackpackItemPlus backpackItem) {
                Direction dispenserDirection = (Direction)source.state().getValue(DispenserBlock.FACING);
                BlockPos blockpos = source.pos().relative(dispenserDirection);
                Direction against = source.level().isEmptyBlock(blockpos.below()) ? dispenserDirection.getOpposite() : Direction.UP;
                this.setSuccess(backpackItem.tryPlace((Player)null, dispenserDirection.getAxis() == Direction.Axis.Y ? Direction.NORTH : dispenserDirection.getOpposite(), new DirectionalPlaceContext(source.level(), blockpos, dispenserDirection, stack, against)).consumesAction());
            }

            return stack;
        }
    }


    public static final DeferredRegister<Item> ITEMS;
    public static final Supplier<BackpackItemPlus> BACKPACK2;


    static {
        ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, "backpackplus");
        BACKPACK2 = ITEMS.register("backpack2", () -> {
            ModConfigSpec.IntValue var10002 = Config.SERVER.netheriteBackpack.inventorySlotCount;
            Objects.requireNonNull(var10002);
            IntSupplier var0 = var10002::get;
            ModConfigSpec.IntValue var10003 = Config.SERVER.leatherBackpack.upgradeSlotCount;
            Objects.requireNonNull(var10003);
            return new BackpackItemPlus(var0, var10003::get, ModBlocksPlus.BACKPACK2);
        });

    }


}
