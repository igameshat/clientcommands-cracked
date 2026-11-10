package com.swaphat.client.ccc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.earthcomputer.clientcommands.command.ClientCommandHelper", remap = false)
public class ClientCommandHelperMixin {

    /**
     * Bypasses the "server has to have opted into using the mod" exception
     * thrown by ClientCommandHelper.requirePrivileges() in /cfindblock, /cfind, /cfinditem, etc.
     */
    @Inject(
            method = "requirePrivileges",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void bypassRequirePrivileges(CallbackInfo ci) {
        ci.cancel();
    }

    /**
     * If any command or suggestion provider calls a boolean check like hasPrivileges(),
     * force it to return true.
     */
    @Inject(
            method = "hasPrivileges",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void forceHasPrivileges(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}