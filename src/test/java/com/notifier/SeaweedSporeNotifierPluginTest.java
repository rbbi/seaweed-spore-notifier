package com.notifier;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class SeaweedSporeNotifierPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(SeaweedSporeNotifierPlugin.class);
		RuneLite.main(args);
	}
}