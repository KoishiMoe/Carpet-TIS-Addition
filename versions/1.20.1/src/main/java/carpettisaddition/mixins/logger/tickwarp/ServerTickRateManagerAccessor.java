/*
 * This file is part of the Carpet TIS Addition project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2023  Fallen_Breath and contributors
 *
 * Carpet TIS Addition is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Carpet TIS Addition is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Carpet TIS Addition.  If not, see <https://www.gnu.org/licenses/>.
 */

package carpettisaddition.mixins.logger.tickwarp;

import carpet.helpers.ServerTickRateManager;
import carpettisaddition.utils.compat.DummyClass;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerTickRateManager.class)
public interface ServerTickRateManagerAccessor
{
	@Accessor(remap = false)
	long getRemainingWarpTicks();

	@Accessor(remap = false)
	long getTickWarpStartTime();

	@Accessor(remap = false)
	long getScheduledCurrentWarpTicks();

	@Accessor(remap = false)
	ServerPlayerEntity getWarpResponsiblePlayer();

	@Accessor(remap = false)
	String getTickWarpCallback();

	@Accessor(remap = false)
	ServerCommandSource getWarpResponsibleSource();
}
