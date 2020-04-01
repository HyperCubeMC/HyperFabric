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
import net.minecraft.text.Style;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static net.hypercubemc.hyperfabric.ansiCodes.*;
import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class Mod implements ModInitializer {
	public void setupAnsiWindows() {
		if (System.getProperty("os.name").startsWith("Windows")) {
			// Set output mode to handle virtual terminal sequences
			Function GetStdHandleFunc = Function.getFunction("kernel32", "GetStdHandle");
			DWORD STD_OUTPUT_HANDLE = new DWORD(-11);
			HANDLE hOut = (HANDLE) GetStdHandleFunc.invoke(HANDLE.class, new Object[]{STD_OUTPUT_HANDLE});

			DWORDByReference p_dwMode = new DWORDByReference(new DWORD(0));
			Function GetConsoleModeFunc = Function.getFunction("kernel32", "GetConsoleMode");
			GetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, p_dwMode});

			int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 4;
			DWORD dwMode = p_dwMode.getValue();
			dwMode.setValue(dwMode.intValue() | ENABLE_VIRTUAL_TERMINAL_PROCESSING);
			Function SetConsoleModeFunc = Function.getFunction("kernel32", "SetConsoleMode");
			SetConsoleModeFunc.invoke(BOOL.class, new Object[]{hOut, dwMode});
		}
	}
	Logger log = LogManager.getLogger("hyperfabric");
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		// The root of the command. This must be a literal argument.
		setupAnsiWindows();
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
						source.sendFeedback(new LiteralText("HyperFabric Help:").setStyle(new Style().setColor(Formatting.BLUE)), false);
						source.sendFeedback(new LiteralText("Arguments: version").setStyle(new Style().setColor(Formatting.BLUE)), false);
						return 1;
					})
			);
	}

	public int hyperfabric(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
			ServerCommandSource source = ctx.getSource();
			String version = FabricLoader.getInstance().getModContainer("hyperfabric").get().getMetadata().getVersion().getFriendlyString();
			source.sendFeedback(new LiteralText("This server is running Justsnoopy30's HyperFabric Server Mod v" + version).setStyle(new Style().setColor(Formatting.GREEN)), false);
			return 1;
	}
}
