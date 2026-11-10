package com.swaphat.client.ccc.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Pseudo
@Mixin(targets = "net.earthcomputer.clientcommands.command.FindItemCommand", remap = false)
public class FindItemCommandMixin {
    @ModifyVariable(
            method = "makeFindItemsTask",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    private static boolean forceClientSideContainerScan(boolean clickInventories) {
        return true;
    }
}