package com.swaphat.client.ccc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.earthcomputer.clientcommands.server.ClientCommandsServer", remap = false)
public class ClientCommandsServerMixin {
    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "requirePrivileges()V", at = @At("HEAD"), cancellable = true, remap = false)
    private static void bypassRequirePrivileges(CallbackInfo ci) {
        ci.cancel();
    }
}