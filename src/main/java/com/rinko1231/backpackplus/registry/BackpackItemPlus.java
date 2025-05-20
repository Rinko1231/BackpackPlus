package com.rinko1231.backpackplus.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlock;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlockEntity;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.ServerStorageSoundHandler;
import net.p3pp3rf1y.sophisticatedcore.util.WorldHelper;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nullable;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class BackpackItemPlus extends BackpackItem {

    private final Supplier<BackpackBlock> myBlockSupplier;

    public BackpackItemPlus(IntSupplier numberOfSlots, IntSupplier numberOfUpgradeSlots, Supplier<BackpackBlock> blockSupplier) {
        super(numberOfSlots, numberOfUpgradeSlots, blockSupplier);

        this.myBlockSupplier = blockSupplier; // 自己保存一份
    }


    public InteractionResult tryPlace(@Nullable Player player, Direction direction, BlockPlaceContext blockItemUseContext) {
        if (!blockItemUseContext.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            Level level = blockItemUseContext.getLevel();
            BlockPos pos = blockItemUseContext.getClickedPos();
            FluidState fluidstate = blockItemUseContext.getLevel().getFluidState(pos);
            BlockState placementState = (BlockState)((BlockState)((BackpackBlockPlus)this.myBlockSupplier.get()).defaultBlockState().setValue(BackpackBlockPlus.FACING, direction)).setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER);
            if (!this.canPlace(blockItemUseContext, placementState)) {
                return InteractionResult.FAIL;
            } else if (level.setBlockAndUpdate(pos, placementState)) {
                ItemStack backpack = blockItemUseContext.getItemInHand();
                WorldHelper.getBlockEntity(level, pos, BackpackBlockEntityPlus.class).ifPresent((be) -> {
                    be.setBackpack(this.getBackpackCopy(player, backpack));
                    be.refreshRenderState();
                    be.tryToAddToController();
                });
                if (!level.isClientSide) {
                    stopBackpackSounds(backpack, level, pos);
                }

                SoundType soundtype = placementState.getSoundType(level, pos, player);
                level.playSound(player, pos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                if (player == null || !player.isCreative()) {
                    backpack.shrink(1);
                }

                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }
}
    private ItemStack getBackpackCopy(@Nullable Player player, ItemStack backpack) {
        return player != null && player.isCreative() ? BackpackWrapper.fromStack(backpack).cloneBackpack() : backpack.copy();
    }
    private static void stopBackpackSounds(ItemStack backpack, Level level, BlockPos pos) {
        BackpackWrapper.fromStack(backpack).getContentsUuid().ifPresent((uuid) -> ServerStorageSoundHandler.stopPlayingDisc(level, Vec3.atCenterOf(pos), uuid));
    }


}
