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

package carpettisaddition.mixins.rule.entityPlacementIgnoreCollision;

import carpettisaddition.CarpetTISAdditionSettings;
import carpettisaddition.utils.GameUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

/**
 * 1.16+ mixin is handled in the version-specified class
 */
@Mixin(ArmorStandItem.class)
public abstract class ArmorStandItemItemMixin
{
	private static final ThreadLocal<ItemPlacementContext> currentContext$TISCM = ThreadLocal.withInitial(() -> null);

	/**
	 * Make the first segment of the if statement below always returns true
	 * if (itemPlacementContext.canPlace() && world.getBlockState(blockPos2).canReplace(itemPlacementContext))
	 *           ^ handled here
	 */
	@ModifyVariable(
			method = "useOnBlock",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/ItemPlacementContext;canPlace()Z"
			)
	)
	private ItemPlacementContext entityPlacementIgnoreCollision_makeIfCondition1true(ItemPlacementContext itemPlacementContext)
	{
		if (CarpetTISAdditionSettings.entityPlacementIgnoreCollision)
		{
			((ItemPlacementContextAccessor)itemPlacementContext).setCanReplaceExisting(true);
			currentContext$TISCM.set(itemPlacementContext);
		}
		return itemPlacementContext;
	}

	/**
	 * Make the first segment of the if statement below always returns true
	 * if (itemPlacementContext.canPlace() && world.getBlockState(blockPos2).canReplace(itemPlacementContext))
	 *                                                                         ^ handled here
	 */
	@Redirect(
			method = "useOnBlock",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/block/BlockState;canReplace(Lnet/minecraft/item/ItemPlacementContext;)Z"
			),
			require = 0
	)
	private boolean entityPlacementIgnoreCollision_makeIfCondition2true(BlockState blockState, ItemPlacementContext ctx)
	{
		if (CarpetTISAdditionSettings.entityPlacementIgnoreCollision)
		{
			return true;
		}
		// vanilla
		return blockState.canReplace(ctx);
	}

	@ModifyVariable(
			method = "useOnBlock",
			at = @At(
					value = "INVOKE_ASSIGN",
					target = "Lnet/minecraft/world/World;getEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;)Ljava/util/List;",
					shift = At.Shift.AFTER
			)
	)
	private List<Entity> entityPlacementIgnoreCollision_skipEntityCheck(List<Entity> entities)
	{
		if (CarpetTISAdditionSettings.entityPlacementIgnoreCollision)
		{
			entities.clear();
		}
		return entities;
	}

	@ModifyArg(
			method = "useOnBlock",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"
			),
			require = 2
	)
	private BlockPos entityPlacementIgnoreCollision_dontRemoveBlockIfNotReplaceable(BlockPos pos)
	{
		if (CarpetTISAdditionSettings.entityPlacementIgnoreCollision)
		{
			ItemPlacementContext ctx = currentContext$TISCM.get();
			World world = ctx.getWorld();
			if (!world.getBlockState(pos).canReplace(ctx))
			{
				pos = GameUtil.getInvalidBlockPos();
			}
		}
		return pos;
	}
}
