package io.github.rbbi.plugin;

import net.runelite.api.ItemID;
import net.runelite.api.TileItem;
import net.runelite.api.events.ItemSpawned;
import net.runelite.client.Notifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for {@link SeaweedSporeNotifierPlugin}
 *
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
	}

	@Test
	public void testSpawnedQuantitySameAsConfigMinimumNotifies() {
		when(config.minQuantity()).thenReturn(2);
		plugin.onItemSpawned(newItemWithQuantity(ItemID.SEAWEED_SPORE, 2));
		verify(notifier, times(1)).notify("2 seaweed spores have spawned!");
	}

	@Test
	public void testSpawnedQuantityAboveConfigMinimumNotifies() {
		when(config.minQuantity()).thenReturn(2);
		plugin.onItemSpawned(newItemWithQuantity(ItemID.SEAWEED_SPORE, 3));
		verify(notifier, times(1)).notify("3 seaweed spores have spawned!");
	}

	@Test
	public void testSpawnedQuantityBelowConfigMinimumDoesntNotify() {
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
		TileItem tileItem = mock(TileItem.class);
		when(tileItem.getId()).thenReturn(itemId);
		when(tileItem.getQuantity()).thenReturn(quantity);
		return new ItemSpawned(null, tileItem);
	}

}
