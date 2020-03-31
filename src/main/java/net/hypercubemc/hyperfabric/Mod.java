package net.hypercubemc.hyperfabric;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.text.LiteralText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mod implements ModInitializer {
	Logger log = LogManager.getLogger("hyperfabric");
	String formatReset = "\033[0m";
	String formatBold = "\033[1m";
	String formatLight = "\033[2m";
	String formatItalic = "\033[3m";
	String formatUnderline = "\033[4m";
	String formatBlink = "\033[5m";
	String formatCrazyBlink = "\033[6m";
	String formatInvertVideo = "\033[7m";
	String formatHide = "\033[8m";
	String formatStrike = "\033[9m";
	String formatDefaultFont = "\033[10m";
	String formatFirstAltFont = "\033[11m";
	String formatSecondAltFont = "\033[12m";
	String formatFraktur = "\033[20m";
	String formatDoubleUnderline = "\033[21m";
	String formatDefaultIntensity = "\033[22m";
	String formatFrakturItalicOff = "\033[23m";
	String formatUnderlineOff = "\033[24m";
	String formatBlinkOff = "\033[25m";
	String formatInvertVideoOff = "\033[27m";
	String formatReveal = "\033[28m";
	String formatStikeOff = "\033[29m";
	String formatFramed = "\033[51m";
	String formatCircled = "\033[52m";
	String formatOverlined = "\033[53m";
	String formatFramedCircledOff = "\033[54m";
	String formatOverlinedOff = "\033[55m";
	String colorBlack = "\033[1;30m";
	String colorRed = "\033[1;31m";
	String colorGreen = "\033[1;32m";
	String colorYellow = "\033[1;33m";
	String colorBlue = "\033[1;34m";
	String colorMagenta = "\033[1;35m";
	String colorCyan = "\033[1;36m";
	String colorLightGrey = "\033[1;37m";
	String colorDarkGrey = "\033[1;90m";
	String colorBrightRed = "\033[1;91m";
	String colorBrightGreen = "\033[1;92m";
	String colorBrightYellow = "\033[1;93m";
	String colorBrightBlue = "\033[1;94m";
	String colorPink = "\033[1;95m";
	String colorBrightCyan = "\033[1;96m";
	String colorWhite = "\033[1;97m";
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		// The root of the command. This must be a literal argument.

		CommandRegistry.INSTANCE.register(false, this::register);
		String version = FabricLoader.getInstance().getModContainer("hyperfabric").get().getMetadata().getVersion().getFriendlyString();
		log.info(colorBlue + "[HyperFabric] Loaded HyperFabric v" + version + " successfully!" + formatReset);
	}

	public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
			commandDispatcher.register(CommandManager.literal("hyperfabric")
					.then(CommandManager.literal("version")
							// The command to be executed if the command "hyperfabric" is entered with the argument "version"
							.executes(this::hyperfabric))
					// The command "hyperfabric" to execute if there are no arguments.
					.executes(ctx -> {
						ServerCommandSource source = ctx.getSource();
						source.sendFeedback(new LiteralText("HyperFabric Help:"), false);
						source.sendFeedback(new LiteralText("Arguments: version"), false);
						return 1;
					})
			);
	}

	public int hyperfabric(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
			ServerCommandSource source = ctx.getSource();
			String version = FabricLoader.getInstance().getModContainer("hyperfabric").get().getMetadata().getVersion().getFriendlyString();
			source.sendFeedback(new LiteralText("This server is running Justsnoopy30's HyperFabric Server Mod v" + version), false);
			return 1;
	}
}
