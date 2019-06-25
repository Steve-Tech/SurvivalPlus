package tk.shanebee.survival.managers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import tk.shanebee.survival.Survival;

@SuppressWarnings("unused")
public class Placeholders extends PlaceholderExpansion {

    private Survival plugin;

    public Placeholders(Survival plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "survivalplus";
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        // Shows player's health, kinda useless but here it is
        if (identifier.equalsIgnoreCase("player_health")) {
            return String.valueOf(p.getHealth());
        }
        // Shows a player's total hunger (including saturation)
        if (identifier.equalsIgnoreCase("player_hunger_total")) {
            return String.valueOf(StatusManager.getHunger(p));
        }
        // Shows player's hunger
        if (identifier.equalsIgnoreCase("player_hunger")) {
            return String.valueOf(p.getFoodLevel());
        }
        // Shows player's saturation
        if (identifier.equalsIgnoreCase("player_saturation")) {
            return String.valueOf(p.getSaturation());
        }
        // Shows player's hunger bar (hunger part)
        if (identifier.equalsIgnoreCase("player_hunger_bar_1")) {
            return Survival.ShowHunger(p).get(1);
        }
        // Shows player's hunger bar (saturation part)
        if (identifier.equalsIgnoreCase("player_hunger_bar_2")) {
            return Survival.ShowHunger(p).get(2);
        }
        // Shows player's thirst
        if (identifier.equalsIgnoreCase("player_thirst")) {
            return String.valueOf(StatusManager.getThirst(p));
        }
        // Shows player's thirst bar (top part - first half out of 40)
        if (identifier.equalsIgnoreCase("player_thirst_bar_1")) {
            return Survival.ShowThirst(p).get(1);
        }
        // Shows player's thirst bar (bottom part - second half out of 40)
        if (identifier.equalsIgnoreCase("player_thirst_bar_2")) {
            return Survival.ShowThirst(p).get(2);
        }
        // Shows player's fatigue
        if (identifier.equalsIgnoreCase("player_fatigue")) {
            return Survival.ShowFatigue(p);
        }
        // Shows player's nutrients bars (<amount> <nutrient>)
        if (identifier.equalsIgnoreCase("player_nutrients_carbs_bar")) {
            return Survival.ShowNutrients(p).get(0);
        }
        if (identifier.equalsIgnoreCase("player_nutrients_proteins_bar")) {
            return Survival.ShowNutrients(p).get(1);
        }
        if (identifier.equalsIgnoreCase("player_nutrients_salts_bar")) {
            return Survival.ShowNutrients(p).get(2);
        }
        // Shows player's nutrients (just the <amount>)
        if (identifier.equalsIgnoreCase("player_nutrients_carbs")) {
            return String.valueOf(StatusManager.getNutrients(p, StatusManager.Nutrients.CARBS));
        }
        if (identifier.equalsIgnoreCase("player_nutrients_proteins")) {
            return String.valueOf(StatusManager.getNutrients(p, StatusManager.Nutrients.PROTEIN));
        }
        if (identifier.equalsIgnoreCase("player_nutrients_salts")) {
            return String.valueOf(StatusManager.getNutrients(p, StatusManager.Nutrients.SALTS));
        }
        return null;

    }

}