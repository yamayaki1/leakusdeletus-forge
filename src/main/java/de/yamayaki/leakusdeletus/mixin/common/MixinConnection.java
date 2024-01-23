package de.yamayaki.leakusdeletus.mixin.common;

import de.yamayaki.leakusdeletus.LeakusDeletus;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Queue;

@Mixin(Connection.class)
public class MixinConnection {
    @Unique
    private boolean leakusdeletus$doQueue = true;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void checkFakePlayer(PacketFlow receiving, CallbackInfo ci) {
        for (final StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if(stackTraceElement.getClassName().contains("FakePlayer")) {
                LeakusDeletus.LOGGER.info("Found FakePlayer in stacktrace, disabling packet processing.");

                this.leakusdeletus$doQueue = false;
                break;
            }
        }
    }

    @Redirect(method = "send(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;)V", at = @At(value = "INVOKE", target = "Ljava/util/Queue;add(Ljava/lang/Object;)Z"))
    private  <E> boolean abortIfFakePlayer(Queue<E> instance, E e) {
        if(this.leakusdeletus$doQueue) {
            return instance.add(e);
        }

        return false;
    }
}
