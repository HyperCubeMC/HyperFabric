package net.hypercubemc.hyperfabric.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.hypercubemc.hyperfabric.Mod;

public class HyperFabricCommand {

    private Mod mod;

    public HyperFabricCommand(Mod mod) {
      this.mod = mod;
    }

    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        LiteralCommandNode<ServerCommandSource> node = registerMain(commandDispatcher); // Registers main command
    }

    public LiteralCommandNode<ServerCommandSource> registerMain(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        return commandDispatcher.register(CommandManager.literal("hyperfabric")
                .then(CommandManager.literal("version")
                        // The command to be executed if the command "hyperfabric" is entered with the argument "version"
                        .executes(this::HyperFabricVersion))
                // The command "hyperfabric" to execute if there are no arguments.
                .executes(ctx -> {
                    ServerCommandSource source = ctx.getSource();
                    source.sendFeedback(new LiteralText("HyperFabric Help:").formatted(Formatting.BLUE), false);
                    source.sendFeedback(new LiteralText("Arguments: version").formatted(Formatting.BLUE), false);
                    return Command.SINGLE_SUCCESS;
                })
        );
    }

    public int HyperFabricVersion(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerCommandSource source = ctx.getSource();
        String version = FabricLoader.getInstance().getModContainer("hyperfabric").get().getMetadata().getVersion().getFriendlyString();
        source.sendFeedback(new LiteralText("This server is running Justsnoopy30's HyperFabric Server Mod v" + version).formatted(Formatting.GREEN), false);
        return Command.SINGLE_SUCCESS;
    }
}
