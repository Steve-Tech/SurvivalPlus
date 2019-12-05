package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.events.ThirstLevelChangeEvent;
import tk.shanebee.survival.managers.PlayerManager;

import java.util.Random;

class ThirstDrain extends BukkitRunnable {

	private PlayerManager playerManager;
	private Config config;

	ThirstDrain(Survival plugin) {
		this.playerManager = plugin.getPlayerManager();
		this.config = plugin.getSurvivalConfig();
		this.runTaskTimer(plugin, -1, 1);
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				// Drain player's thirst when exhaustion is high
				if (player.getExhaustion() >= 4) {
					PlayerData playerData = playerManager.getPlayerData(player);

					Random rand = new Random();
					int change = rand.nextDouble() <= config.MECHANICS_THIRST_DRAIN_RATE ? 1 : 0;

					// Prevent calling thirst event if there is no change
					if (change == 0) continue;

					// Call thirst level change event
					ThirstLevelChangeEvent event = new ThirstLevelChangeEvent(player, change, playerData.getThirst() - change);
					Bukkit.getPluginManager().callEvent(event);
					if (!event.isCancelled()) {
						playerData.increaseThirst(-change);
					}
				}
				// Damage player when thirst is too low
				PlayerData playerData = playerManager.getPlayerData(player);

				if (playerData.getThirst() <= 0) {
					switch (player.getWorld().getDifficulty()) {
						case EASY:
							if (player.getHealth() > 10)
								player.damage(1);
							break;
						case NORMAL:
							if (player.getHealth() > 1)
								player.damage(1);
							break;
						case HARD:
							player.damage(1);
							break;
						default:
					}
				}
			}
		}
	}

}