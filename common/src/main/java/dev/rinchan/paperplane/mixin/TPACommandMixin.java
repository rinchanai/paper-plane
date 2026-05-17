package dev.rinchan.paperplane.mixin;

import dev.ftb.mods.ftbessentials.commands.impl.teleporting.TPACommand;
import dev.rinchan.paperplane.PaperPlane;
import java.util.UUID;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TPACommand.class)
public class TPACommandMixin {
    @Inject(method = "tpaccept", at = @At("RETURN"), remap = false)
    private void paperPlane$consumeAcceptedRequest(ServerPlayer target, String requestId, CallbackInfoReturnable<Integer> cir) {
        if (cir.getReturnValueI() > 0) {
            UUID id = parseRequestId(requestId);
            if (id != null) {
                PaperPlane.finishAcceptedRequest(target, id);
            }
        }
    }

    @Inject(method = "tpdeny", at = @At("RETURN"), remap = false)
    private void paperPlane$forgetDeniedRequest(ServerPlayer target, String requestId, CallbackInfoReturnable<Integer> cir) {
        UUID id = parseRequestId(requestId);
        if (id != null) {
            PaperPlane.forgetRequest(id);
        }
    }

    private static UUID parseRequestId(String requestId) {
        try {
            return UUID.fromString(requestId);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}
