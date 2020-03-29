package net.hypercubemc.hyperfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class Mod implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		String version = FabricLoader.getInstance().getModContainer("hyperfabric").get().getMetadata().getVersion().getFriendlyString();
		System.out.println(String.format("Loaded HyperFabric v%s successfilly!",version));
	}
}
