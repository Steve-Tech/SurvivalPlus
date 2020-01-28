package tk.shanebee.survival.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.listeners.block.BlockBreak;
import tk.shanebee.survival.listeners.block.BlockPlace;
import tk.shanebee.survival.listeners.block.BurnoutTorches;
import tk.shanebee.survival.listeners.block.Campfire;
import tk.shanebee.survival.listeners.block.Chairs;
import tk.shanebee.survival.listeners.block.NoAnvil;
import tk.shanebee.survival.listeners.block.SnowGeneration;
import tk.shanebee.survival.listeners.block.SnowballThrow;
import tk.shanebee.survival.listeners.block.WorkbenchShare;
import tk.shanebee.survival.listeners.entity.BeeKeeperSuit;
import tk.shanebee.survival.listeners.entity.ChestPigmen;
import tk.shanebee.survival.listeners.entity.ChickenSpawn;
import tk.shanebee.survival.listeners.entity.LivingSlime;
import tk.shanebee.survival.listeners.item.*;
import tk.shanebee.survival.listeners.player.BedFatigue;
import tk.shanebee.survival.listeners.player.PlayerDataListener;
import tk.shanebee.survival.listeners.server.Guide;
import tk.shanebee.survival.listeners.server.InventoryUpdate;
import tk.shanebee.survival.listeners.server.LocalChat;
import tk.shanebee.survival.listeners.server.NoPos;
import tk.shanebee.survival.listeners.server.RecipeDiscovery;
import tk.shanebee.survival.listeners.server.SetResourcePack;
import tk.shanebee.survival.util.Utils;

/**
 * Internal use only
 */
public class EventManager {

	private Survival plugin;
	private final int LOCAL_CHAT;
	private final Config config;

	public EventManager(Survival plugin) {
		this.plugin = plugin;
		this.config = plugin.getSurvivalConfig();
		this.LOCAL_CHAT = config.LOCAL_CHAT_DISTANCE;
	}

	public void registerEvents() {
		PluginManager pm = plugin.getServer().getPluginManager();
		pm.registerEvents(this.plugin, this.plugin);
		pm.registerEvents(new RecipeDiscovery(plugin), this.plugin);

		if (config.SURVIVAL_ENABLED) {
			pm.registerEvents(new BlockBreak(plugin), this.plugin);
			pm.registerEvents(new BlockPlace(plugin), this.plugin);
			pm.registerEvents(new FirestrikerClick(plugin), this.plugin);
			pm.registerEvents(new ShivPoison(), this.plugin);
			pm.registerEvents(new WaterBowl(plugin), this.plugin);
			pm.registerEvents(new Campfire(plugin), this.plugin);
			//pm.registerEvents(new Backpack(), this.plugin); needs to be reworked
		}
		pm.registerEvents(new NoAnvil(plugin), this.plugin);
		if (config.MECHANICS_BOW)
			pm.registerEvents(new Bow(plugin), this.plugin);
		if (config.MECHANICS_GRAPPLING_HOOK)
			pm.registerEvents(new GrapplingHook(plugin), this.plugin);
		if (config.LEGENDARY_OBSIDIAN_MACE)
			pm.registerEvents(new ObsidianMaceWeakness(plugin), this.plugin);
		if (config.LEGENDARY_VALKYRIE)
			pm.registerEvents(new Valkyrie(plugin), this.plugin);
		if (config.LEGENDARY_GIANTBLADE)
			pm.registerEvents(new GiantBlade(plugin), this.plugin);
		if (config.LEGENDARY_BLAZESWORD)
			pm.registerEvents(new BlazeSword(), this.plugin);
		if (LOCAL_CHAT > -1)
			pm.registerEvents(new LocalChat(plugin), this.plugin);
		if (config.MECHANICS_COMPASS_WAYPOINT)
			pm.registerEvents(new CompassWaypoint(this.plugin), this.plugin);
		if (config.MECHANICS_MEDIC_KIT)
			pm.registerEvents(new MedicKit(plugin), this.plugin);

		pm.registerEvents(new WaterBottleCrafting(plugin), this.plugin);
		pm.registerEvents(new SpecialItemInteractCancel(plugin), this.plugin);

		pm.registerEvents(new SetResourcePack(plugin), this.plugin);

		if (config.MECHANICS_RAW_MEAT_HUNGER)
			pm.registerEvents(new RawMeatHunger(), this.plugin);
		if (config.MECHANICS_THIRST_ENABLED) {
			pm.registerEvents(new Consume(this.plugin), this.plugin);
			if (config.MECHANICS_THIRST_PURIFY_WATER)
				pm.registerEvents(new CauldronWaterBottle(), this.plugin);
		}
		if (config.MECHANICS_POISON_POTATO)
			pm.registerEvents(new PoisonousPotato(), this.plugin);
		if (config.MECHANICS_SHARED_WORKBENCH)
			pm.registerEvents(new WorkbenchShare(plugin), this.plugin);
		if (config.MECHANICS_CHAIRS_ENABLED)
			pm.registerEvents(new Chairs(plugin), this.plugin);
		if (config.MECHANICS_COOKIE_BOOST)
			pm.registerEvents(new CookieHealthBoost(), this.plugin);
		if (config.MECHANICS_BEET_STRENGTH)
			pm.registerEvents(new BeetrootStrength(), this.plugin);
		if (config.MECHANICS_CLOWN_FISH)
			pm.registerEvents(new Clownfish(), this.plugin);
		if (config.MECHANICS_LIVING_SLIME)
			pm.registerEvents(new LivingSlime(plugin), this.plugin);
		if (config.MECHANICS_ENERGY_ENABLED)
			pm.registerEvents(new BedFatigue(plugin), this.plugin);
		if (config.MECHANICS_FOOD_DIVERSITY_ENABLED)
			pm.registerEvents(new FoodDiversityConsume(plugin), this.plugin);
		if (config.MECHANICS_RECURVED_BOW)
			pm.registerEvents(new RecurvedBow(plugin), this.plugin);
		if (config.MECHANICS_SNOWBALL_REVAMP)
			pm.registerEvents(new SnowballThrow(), this.plugin);
		if (config.MECHANICS_SNOW_GEN_REVAMP)
			pm.registerEvents(new SnowGeneration(plugin), this.plugin);
		pm.registerEvents(new ChickenSpawn(), this.plugin);
		if (config.WELCOME_GUIDE_ENABLED)
			pm.registerEvents(new Guide(plugin), this.plugin);
		if (config.MECHANICS_BURNOUT_TORCH_ENABLED) // TODO experimental feature, not 100% sure about this
			pm.registerEvents(new BurnoutTorches(this.plugin), this.plugin);
		pm.registerEvents(new InventoryUpdate(), this.plugin);

		if (config.ENTITY_MECHANICS_PIGMEN_CHEST_ENABLED)
			pm.registerEvents(new ChestPigmen(this.plugin), this.plugin);

		if (config.NO_POS) {
			Bukkit.getPluginManager().registerEvents(new NoPos(), this.plugin);
			Utils.log("&7NoPos &aimplemented &7- F3 coordinates are disabled!");
		}
		if (Utils.isRunningMinecraft(1, 15)) {
            if (config.ENTITY_MECHANICS_BEEKEEPER_SUIT_ENABLED) {
                Bukkit.getPluginManager().registerEvents(new BeeKeeperSuit(), this.plugin);
            }
        }
		Bukkit.getPluginManager().registerEvents(new PlayerDataListener(this.plugin), this.plugin);

	}

}
