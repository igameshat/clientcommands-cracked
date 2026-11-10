package com.swaphat.client.ccc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Pseudo
@Mixin(targets = "net.earthcomputer.clientcommands.util.RoundTripFence", remap = false)
public class RoundTripFenceMixin {

    /**
     * Targets ONLY no-arg methods returning CompletableFuture (ignoring <init> constructors)
     * and resolves the future immediately without waiting for the server round-trip packet.
     */
    @Inject(
            method = "*()Ljava/util/concurrent/CompletableFuture;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void bypassServerRoundTrip(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        cir.setReturnValue(CompletableFuture.completedFuture(null));
    }
}