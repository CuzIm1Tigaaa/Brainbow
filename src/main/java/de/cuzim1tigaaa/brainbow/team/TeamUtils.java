package de.cuzim1tigaaa.brainbow.team;

import de.cuzim1tigaaa.brainbow.Brainbow;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeamUtils {

    private final Brainbow plugin;

    public TeamUtils(Brainbow plugin) {
        this.plugin = plugin;
    }

    public boolean isInTeamTarget(Location location) {
        for (Team team : plugin.getFile().getDataSet()) {
            if (team.getTarget().contains(location))
                return true;
        }
        return false;
    }

    public Team getTeamWhoseTargetWasHit(Location location) {
        for (Team team : plugin.getFile().getDataSet()) {
            if (team.getTarget().contains(location))
                return team;
        }
        return null;
    }

    public Team getPlayersTeam(Player player) {
        for (Team team : plugin.getFile().getDataSet()) {
            if (team.getPlayers().contains(player))
                return team;
        }
        return null;
    }
}
