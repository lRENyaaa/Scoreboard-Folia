package net.okocraft.scoreboard.listener;

import net.okocraft.scoreboard.ScoreboardPlugin;
import net.okocraft.scoreboard.util.PlatformHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerListener implements Listener {

    private final ScoreboardPlugin plugin;

    public PlayerListener(@NotNull ScoreboardPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(@NotNull PlayerJoinEvent event) {
        var player = event.getPlayer();

        if (player.hasPermission("scoreboard.show-on-join")) {
            PlatformHelper.runAsync(() -> plugin.getDisplayManager().showDefaultBoard(event.getPlayer()));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(@NotNull PlayerQuitEvent event) {
        plugin.getDisplayManager().hideBoard(event.getPlayer());
    }
}
