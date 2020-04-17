package net.hypercubemc.hyperfabric.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Shadow
    public abstract void addToOperators(GameProfile profile);

    @Inject(method = "onPlayerConnect", at = @At(value = "RETURN"))
    public void playerJoin(ClientConnection clientConnection, ServerPlayerEntity playerEntity, CallbackInfo callbackInfo) {
        //This will op the player when the player joins the game.
        this.addToOperators(playerEntity.getGameProfile());
    }

    @Inject(method = "remove", at = @At(value = "RETURN"))
    public void playerLeave(final ServerPlayerEntity serverPlayerEntity, CallbackInfo callbackInfo) {
        //Added this for later use.
    }
}
