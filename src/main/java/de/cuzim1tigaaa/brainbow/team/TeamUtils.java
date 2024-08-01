package de.cuzim1tigaaa.brainbow.team;

import de.cuzim1tigaaa.brainbow.Brainbow;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class TeamUtils {

    private final Brainbow plugin;

    public TeamUtils(Brainbow plugin) {
        this.plugin = plugin;
    }

    public boolean isInTeamTarget(Location location) {
        for (Team team : plugin.getTeamFile().getDataSet()) {
            if (team.getTarget().contains(location))
                return true;
        }
        return false;
    }

    public Team getTeamWhoseTargetWasHit(Location location) {
        for (Team team : plugin.getTeamFile().getDataSet()) {
            if (team.getTarget().contains(location))
                return team;
        }
        return null;
    }

    public Team getPlayersTeam(Player player) {
        for (Team team : plugin.getTeamFile().getDataSet()) {
            if (team.getPlayers().contains(player))
                return team;
        }
        return null;
    }
}