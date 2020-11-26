package carpettisaddition.mixins.command.lifetime.removal;

import carpettisaddition.commands.lifetime.interfaces.IEntity;
import carpettisaddition.commands.lifetime.removal.LiteralRemovalReason;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin
{
	@Inject(
			method = "checkDespawn",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/mob/MobEntity;remove()V"
			),
			require = 3
	)
	private void onDespawnLifeTimeTracker(CallbackInfo ci)
	{
		((IEntity)this).recordRemoval(LiteralRemovalReason.DESPAWN);
	}
}
