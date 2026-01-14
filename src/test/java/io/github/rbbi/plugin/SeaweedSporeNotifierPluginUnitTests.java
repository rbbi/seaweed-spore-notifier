package io.github.rbbi.plugin;

import net.runelite.api.gameval.ItemID;
import net.runelite.api.TileItem;
import net.runelite.api.events.ItemSpawned;
import net.runelite.client.Notifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for {@link SeaweedSporeNotifierPlugin}
 *
 * @author robbie, created on 09/08/2022 21:56
 */
@RunWith(MockitoJUnitRunner.class)
public class SeaweedSporeNotifierPluginUnitTests
{

	private MockedStatic<Region> region;

	@Before
	public void setUp()
	{
		when(config.minQuantity()).thenReturn(1);
		when(config.onlyUnderwater()).thenReturn(true);
		region = Mockito.mockStatic(Region.class);
		region.when(() -> Region.isPlayerUnderwater(any())).thenReturn(true);
	}

	@After
	public void tearDown()
	{
		region.close();
	}

	@Mock
	private Notifier notifier;

	@Mock
	private SeaweedSporeNotifierConfig config;

	@InjectMocks
	private SeaweedSporeNotifierPlugin plugin;

	@Test
	public void testSeaweedSpawnedNotificationMessage()
	{
		plugin.onItemSpawned(newItemWithQuantity(ItemID.SEAWEED_SEED, 1));
		verify(notifier, times(1)).notify("1 seaweed spore has spawned!");
	}

	@Test
	public void testSpawnedQuantitySameAsConfigMinimumNotifies() {
		when(config.minQuantity()).thenReturn(2);
		plugin.onItemSpawned(newItemWithQuantity(ItemID.SEAWEED_SEED, 2));
		verify(notifier, times(1)).notify("2 seaweed spores have spawned!");
	}

	@Test
	public void testSpawnedQuantityAboveConfigMinimumNotifies() {
		when(config.minQuantity()).thenReturn(2);
		plugin.onItemSpawned(newItemWithQuantity(ItemID.SEAWEED_SEED, 3));
		verify(notifier, times(1)).notify("3 seaweed spores have spawned!");
	}

	@Test
	public void testSpawnedQuantityBelowConfigMinimumDoesntNotify() {
		when(config.minQuantity()).thenReturn(3);
		plugin.onItemSpawned(newItemWithQuantity(ItemID.SEAWEED_SEED, 2));
		verify(notifier, times(0)).notify(any());
	}

	@Test
	public void testNonSeaweedSporeItemDoesNotNotify()
	{
		plugin.onItemSpawned(newItemWithQuantity(ItemID.CABBAGE, 1));
		verify(notifier, times(0)).notify(any());
	}

	@Test
	public void testNotUnderwaterDoesNotNotify(){
		region.when(() -> Region.isPlayerUnderwater(any())).thenReturn(false);
		plugin.onItemSpawned(newItemWithQuantity(ItemID.SEAWEED_SEED, 1));
		verify(notifier, times(0)).notify(any());
	}

	@Test
	public void testUnderwaterCheckDisabled(){
		when(config.onlyUnderwater()).thenReturn(false);
		region.when(() -> Region.isPlayerUnderwater(any())).thenReturn(false);
		plugin.onItemSpawned(newItemWithQuantity(ItemID.SEAWEED_SEED, 1));
		verify(notifier, times(1)).notify("1 seaweed spore has spawned!");
	}

	private ItemSpawned newItemWithQuantity(int itemId, int quantity)
	{
		TileItem tileItem = mock(TileItem.class);
		when(tileItem.getId()).thenReturn(itemId);
		when(tileItem.getQuantity()).thenReturn(quantity);
		return new ItemSpawned(null, tileItem);
	}

}
