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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author robbie, created on 09/08/2022 21:56
 */
@RunWith(MockitoJUnitRunner.class)
public class SeaweedSporeNotifierPluginUnitTests
{

	@Mock
	private Notifier notifier;

	@Mock
	private SeaweedSporeNotifierConfig config;

	@InjectMocks
	private SeaweedSporeNotifierPlugin plugin;

	@Test
	public void testSeaweedSpawnedNotificationMessage()
	{
		when(config.minQuantity()).thenReturn(1);
		plugin.onItemSpawned(newItemWithQuantity(ItemID.SEAWEED_SPORE, 1));
		verify(notifier, times(1)).notify("1 seaweed spore has spawned!");
		clearInvocations(notifier);

		// testSpawnedQuantitySameAsConfigMinimumNotifies
		when(config.minQuantity()).thenReturn(2);
		plugin.onItemSpawned(newItemWithQuantity(ItemID.SEAWEED_SPORE, 2));
		verify(notifier, times(1)).notify("2 seaweed spores have spawned!");
		clearInvocations(notifier);

		// testSpawnedQuantityAboveConfigMinimumNotifies
		when(config.minQuantity()).thenReturn(2);
		plugin.onItemSpawned(newItemWithQuantity(ItemID.SEAWEED_SPORE, 3));
		verify(notifier, times(1)).notify("3 seaweed spores have spawned!");
		clearInvocations(notifier);

		// testSpawnedQuantityBelowConfigMinimumDoesntNotify
		when(config.minQuantity()).thenReturn(3);
		plugin.onItemSpawned(newItemWithQuantity(ItemID.SEAWEED_SPORE, 2));
		verify(notifier, times(0)).notify(any());
	}

	@Test
	public void testNonSeaweedSporeItemDoesNotNotify()
	{
		plugin.onItemSpawned(newItemWithQuantity(ItemID.CABBAGE, 1));
		verify(notifier, times(0)).notify(any());
	}

	private ItemSpawned newItemWithQuantity(int itemId, int quantity)
	{
		return new ItemSpawned(null, new TileItem()
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
		});
	}

}
