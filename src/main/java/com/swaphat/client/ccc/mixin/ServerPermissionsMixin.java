package com.swaphat.client.ccc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.earthcomputer.clientcommands.features.ServerPermissions", remap = false)
public class ServerPermissionsMixin {

    /**
     * Forces ServerPermissions.has(ServerPermission) to always return false,
     * ensuring clientcommands never attempts server-side task delegation.
     */
    @Inject(method = "has", at = @At("HEAD"), cancellable = true)
    private static void forceVanillaServerFallback(
            @ org.spongepowered.asm.mixin.injection.Coerce Object permission,
            CallbackInfoReturnable<Boolean> cir
    ) {
        cir.setReturnValue(false);
    }
}