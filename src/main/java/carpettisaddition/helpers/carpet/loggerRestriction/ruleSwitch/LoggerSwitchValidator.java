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

package carpettisaddition.helpers.carpet.loggerRestriction.ruleSwitch;

import carpettisaddition.settings.validator.AbstractCheckerValidator;
import carpettisaddition.settings.validator.ValidationContext;
import com.google.common.base.Joiner;
import net.minecraft.text.BaseText;

public class LoggerSwitchValidator extends AbstractCheckerValidator<String>
{
	@Override
	protected boolean validateValue(String value)
	{
		return LoggerSwitchRuleCommon.OPTIONS.contains(value.toLowerCase());
	}

	@Override
	public BaseText errorMessage(ValidationContext<String> ctx)
	{
		return tr("logger_switch.message", Joiner.on(", ").join(LoggerSwitchRuleCommon.OPTIONS));
	}
}
