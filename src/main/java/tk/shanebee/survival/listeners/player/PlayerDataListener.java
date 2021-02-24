package tk.shanebee.survival.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.config.PlayerDataConfig;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.managers.ScoreBoardManager;

import static org.bukkit.Bukkit.getServer;

public class PlayerDataListener implements Listener {

    private final Survival plugin;
    private final PlayerManager playerManager;
    private final PlayerDataConfig playerDataConfig;
    private final ScoreBoardManager scoreboardManager;
    private final Config config;

    public PlayerDataListener(Survival plugin) {
        this.plugin = plugin;
        this.playerManager = plugin.getPlayerManager();
        this.playerDataConfig = plugin.getPlayerDataConfig();
        this.scoreboardManager = plugin.getScoreboardManager();
        this.config = plugin.getSurvivalConfig();
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Creating player files may be slow, lets do this asynchronously
        getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            PlayerData playerData;
            if (!playerDataConfig.hasPlayerDataFile(player)) {
                playerData = playerManager.createNewPlayerData(player);
            } else {
                playerData = playerManager.loadPlayerData(player);
            }

            // Can't do this asynchronously
            getServer().getScheduler().runTask(this.plugin, () -> {
                if (config.MECHANICS_STATUS_SCOREBOARD)
                    scoreboardManager.setupScoreboard(player);
            });

            // Appears you can only set a compass target after a delay
            if (config.MECHANICS_COMPASS_WAYPOINT) {
                getServer().getScheduler().runTaskLater(this.plugin, () -> {
                    player.setCompassTarget(playerData.getCompassWaypoint(player.getWorld()));
                }, 1);
            }
        });
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        // Seems to lag my server, lets do this asynchronously
        getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            playerManager.unloadPlayerData(player);
            scoreboardManager.unloadScoreboard(player);
        });
    }

}
