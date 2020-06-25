package net.hypercubemc.hyperfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;

import net.hypercubemc.hyperfabric.commands.HyperFabricCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static net.hypercubemc.hyperfabric.AnsiCodes.*;

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

	public void registerCommands() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			HyperFabricCommand hyperFabricCommand = new HyperFabricCommand(this);
			hyperFabricCommand.register(dispatcher);
		});
	}

    private void setupTriggerCommandAliases() {

    }

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		setupAnsiWindows();
		registerCommands();
		setupTriggerCommandAliases();
		Logger log = LogManager.getLogger("hyperfabric");
		String version = FabricLoader.getInstance().getModContainer("hyperfabric").get().getMetadata().getVersion().getFriendlyString();
		log.info(colorBlue + "[HyperFabric] Loaded HyperFabric v" + version + " successfully!" + formatReset);
	}
}
