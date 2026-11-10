package com.swaphat.client.ccc.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.TagValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Pseudo
@Mixin(targets = "net.earthcomputer.clientcommands.features.ClientcommandsDataQueryHandler", remap = false)
public class ClientcommandsDataQueryHandlerMixin {

    @Inject(method = "queryEntityNbt", at = @At("HEAD"), cancellable = true)
    private void onQueryEntityNbt(int entityId, Consumer<CompoundTag> callback, CallbackInfo ci) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            Entity entity = level.getEntity(entityId);
            if (entity != null) {
                TagValueOutput output = TagValueOutput.createWithContext(
                        ProblemReporter.DISCARDING,
                        level.registryAccess()
                );
                entity.saveWithoutId(output);
                callback.accept(output.buildResult());
            } else {
                callback.accept(null);
            }
        }
        ci.cancel();
    }

    @Inject(method = "queryBlockNbt", at = @At("HEAD"), cancellable = true)
    private void onQueryBlockNbt(BlockPos pos, Consumer<CompoundTag> callback, CallbackInfo ci) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be != null) {
                CompoundTag nbt = be.saveWithFullMetadata(level.registryAccess());
                callback.accept(nbt);
            } else {
                callback.accept(null);
            }
        }
        ci.cancel();
    }
}