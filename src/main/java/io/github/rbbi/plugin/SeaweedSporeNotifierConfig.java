package io.github.rbbi.plugin;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

/**
 * @author robbie, created on 09/08/2022 21:47
 */
@ConfigGroup("seaweedsporenotifier")
public interface SeaweedSporeNotifierConfig extends Config
{

	@Range(
		min = 1,
		max = 3
	)
	@ConfigItem(
		keyName = "minQuantity",
		name = "Minimum quantity",
		description = "The minimum quantity of seaweed spores to trigger a notification."
	)
	default int minQuantity()
	{
		return 1;
	}

}
