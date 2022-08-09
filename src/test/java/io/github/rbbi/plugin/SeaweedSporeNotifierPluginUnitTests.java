package io.github.rbbi.plugin;

import net.runelite.api.ItemID;
import net.runelite.api.Model;
import net.runelite.api.Node;
import net.runelite.api.TileItem;
import net.runelite.api.events.ItemSpawned;
import net.runelite.client.Notifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author robbie, created on 09/08/2022 21:56
 */
@RunWith(MockitoJUnitRunner.class)
public class SeaweedSporeNotifierPluginUnitTests
{

	@Mock
	private Notifier notifier;

	@Test
	public void testSingleSeaweedSpawnedNotificationMessage()
	{
		TileItem item = itemWithQuantity(ItemID.SEAWEED_SPORE, 1);
		SeaweedSporeNotifierPlugin plugin = new SeaweedSporeNotifierPlugin(notifier, minQuantity(1));
		plugin.onItemSpawned(new ItemSpawned(null, item));
		verify(notifier, times(1)).notify("1 seaweed spore has spawned!");
	}

	@Test
	public void testSpawnedQuantityAboveConfigMinimumNotifies()
	{
		TileItem item = itemWithQuantity(ItemID.SEAWEED_SPORE, 3);
		SeaweedSporeNotifierPlugin plugin = new SeaweedSporeNotifierPlugin(notifier, minQuantity(2));
		plugin.onItemSpawned(new ItemSpawned(null, item));
		verify(notifier, times(1)).notify("3 seaweed spores have spawned!");
	}

	@Test
	public void testSpawnedQuantitySameAsConfigMinimumNotifies()
	{
		TileItem item = itemWithQuantity(ItemID.SEAWEED_SPORE, 2);
		SeaweedSporeNotifierPlugin plugin = new SeaweedSporeNotifierPlugin(notifier, minQuantity(2));
		plugin.onItemSpawned(new ItemSpawned(null, item));
		verify(notifier, times(1)).notify("2 seaweed spores have spawned!");
	}

	@Test
	public void testSpawnedQuantityBelowConfigMinimumDoesntNotify()
	{
		TileItem item = itemWithQuantity(ItemID.SEAWEED_SPORE, 2);
		SeaweedSporeNotifierPlugin plugin = new SeaweedSporeNotifierPlugin(notifier, minQuantity(3));
		plugin.onItemSpawned(new ItemSpawned(null, item));
		verify(notifier, times(0)).notify(any());
	}

	@Test
	public void testNonSeaweedSporeItemDoesNotNotify()
	{
		TileItem item = itemWithQuantity(ItemID.CABBAGE, 1);
		SeaweedSporeNotifierPlugin plugin = new SeaweedSporeNotifierPlugin(notifier, minQuantity(1));
		plugin.onItemSpawned(new ItemSpawned(null, item));
		verify(notifier, times(0)).notify(any());
	}

	private TileItem itemWithQuantity(int itemId, int quantity)
	{
		return new TileItem()
		{
			@Override
			public int getId()
			{
				return itemId;
			}

			@Override
			public int getQuantity()
			{
				return quantity;
			}

			@Override
			public Model getModel()
			{
				return null;
			}

			@Override
			public int getModelHeight()
			{
				return 0;
			}

			@Override
			public void setModelHeight(int modelHeight)
			{

			}

			@Override
			public void draw(int orientation, int pitchSin, int pitchCos, int yawSin, int yawCos, int x, int y, int z, long hash)
			{

			}

			@Override
			public Node getNext()
			{
				return null;
			}

			@Override
			public Node getPrevious()
			{
				return null;
			}

			@Override
			public long getHash()
			{
				return 0;
			}
		};
	}

	private SeaweedSporeNotifierConfig minQuantity(int minQuantity)
	{
		return new SeaweedSporeNotifierConfig()
		{
			@Override
			public int minQuantity()
			{
				return minQuantity;
			}
		};
	}
}
