package co.stashcat.navigation.commands;

import co.stashcat.navigation.Main;
import co.stashcat.navigation.Tracker;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrackCommand implements CommandExecutor {
    public TrackCommand(Main p) {
        p.getCommand("track").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) {
            Main.sendMsg(s, "&cThis command can only be used by players.");
            return true;
        }
        Player p = (Player) s;
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null && target.isOnline()) {
                Tracker.track(p, target);
                Main.sendMsg(p, "&aNow tracking %s.", target.getDisplayName());
                Main.sendMsg(p, "Type \"&a/%s&r\" to stop tracking.", label);
                return true;
            } else {
                Main.sendMsg(s, "&aPlayer \"%s\" is not online.", args[0]);
                return true;
            }
        } else if (args.length == 0 && Tracker.isTracking(p)) {
            Main.sendMsg(p, "&aStopped tracking &2%s&a.", Tracker.getTracking(p).getDisplayName());
            Tracker.stopTracking(p);
            return true;
        }
        Main.sendMsg(s, "&cInvalid arguments.");
        return false;
    }
}