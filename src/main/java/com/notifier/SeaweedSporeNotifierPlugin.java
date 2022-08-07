package com.notifier;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.TileItem;
import net.runelite.api.events.ItemSpawned;
import net.runelite.client.Notifier;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Seaweed Spore Notifier"
)
public class SeaweedSporeNotifierPlugin extends Plugin
{

	@Inject
	private Notifier notifier;

	@Subscribe
	public void onItemSpawned(ItemSpawned itemSpawned)
	{
		TileItem item = itemSpawned.getItem();
		if (item.getId() == ItemID.SEAWEED_SPORE)
		{
			int sporeQuantity = item.getQuantity();
			boolean singleSpore = sporeQuantity == 1;

			notifier.notify(sporeQuantity + " seaweed spore"
				+ (singleSpore ? " " : "s ")
				+ (singleSpore ? "has" : "have")
				+ " spawned!");
		}
	}

}
