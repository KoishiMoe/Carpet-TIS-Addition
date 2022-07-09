package carpettisaddition.mixins.carpet.hooks;

import carpet.CarpetServer;
import carpettisaddition.CarpetTISAdditionServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CarpetServer.class)
public abstract class CarpetServerMixin
{
	@Inject(
			method = "onGameStarted",
			at = @At(value = "HEAD"),
			remap = false
	)
	private static void onGameStartedPreHook(CallbackInfo ci)
	{
		// in mc 1.19+ TISCM is registered in carpettisaddition.CarpetTISAdditionMod.onInitialize
		//#if MC < 11900
		CarpetTISAdditionServer.registerExtension();
		//#endif

		CarpetTISAdditionServer.getInstance().onGameStartedPre();
	}
}
