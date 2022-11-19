package carpettisaddition.mixins.command.info;

import carpettisaddition.commands.info.QueueAccessibleChunkTickScheduler;
import carpettisaddition.utils.ModIds;
import carpettisaddition.utils.mixin.testers.LithiumEntityWorldTickSchedulerTester;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.world.tick.ChunkTickScheduler;
import net.minecraft.world.tick.OrderedTick;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Queue;

@Restriction(require = {
		@Condition(value = ModIds.minecraft, versionPredicates = ">=1.18"),
		@Condition(type = Condition.Type.TESTER, tester = LithiumEntityWorldTickSchedulerTester.Inverted.class)
})
@Mixin(ChunkTickScheduler.class)
public interface ChunkTickSchedulerAccessor<T> extends QueueAccessibleChunkTickScheduler<T>
{
	@Override
	@Accessor("tickQueue")
	Queue<OrderedTick<T>> getTickQueue$TISCM();
}