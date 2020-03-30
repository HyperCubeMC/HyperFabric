package net.hypercubemc.hyperfabric;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.minecraft.command.arguments.ColorArgumentType.getColor;
import static net.minecraft.server.command.CommandManager.literal; // literal("foo")
import static net.minecraft.server.command.CommandManager.argument; // argument("bar", word())
import static net.minecraft.server.command.CommandManager.*; // Import everything

import net.minecraft.command.arguments.ColorArgumentType;
import net.minecraft.server.command.CommandManager;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Mod implements ModInitializer {
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		// The root of the command. This must be a literal argument.

		String version = FabricLoader.getInstance().getModContainer("hyperfabric").get().getMetadata().getVersion().getFriendlyString();
		System.out.println(String.format("Loaded HyperFabric v%s successfully!",version));
	}

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
			dispatcher.register(literal("broadcast")
			.requires(source -> source.hasPermissionLevel(2)) // Must be a game master to use the command. Command will not show up in tab completion or execute to non op's or any op that is permission level 1.
					.then(argument("color", ColorArgumentType.color())
						.then(argument("message", greedyString())
							.executes(ctx -> broadcast(ctx.getSource(), getColor(ctx, "color"), getString(ctx, "message")))))); // You can deal with the arguments out here and pipe them into the command.
	}

	public static int broadcast(ServerCommandSource source, Formatting formatting, String message) {
			Text text = new LiteralText(message).formatted(formatting);

			source.getMinecraftServer().getPlayerManager().broadcastChatMessage(text, false);
			return Command.SINGLE_SUCCESS; // Success
	}
}
