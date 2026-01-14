package io.github.rbbi.plugin;

import net.runelite.api.Client;
import net.runelite.api.coords.WorldPoint;

public class Region
{
	private static final int[] UNDERWATER = {15008, 15264};

	public static boolean isPlayerUnderwater(Client client)
	{
		int regionID = WorldPoint.fromLocalInstance(client, client.getLocalPlayer().getLocalLocation()).getRegionID();
		return regionID == UNDERWATER[0] || regionID == UNDERWATER[1];
	}
}
