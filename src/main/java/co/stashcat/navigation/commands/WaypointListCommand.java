package co.stashcat.navigation.commands;

import co.stashcat.navigation.Main;
import co.stashcat.navigation.WaypointManager;
import co.stashcat.navigation.types.Waypoint;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public class WaypointListCommand implements CommandExecutor {
    Main pl;

    public WaypointListCommand(Main p) {
        pl = p;
        p.getCommand("waypoints").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        Collection<Waypoint> waypointList = WaypointManager.getWaypointList();
        if (waypointList.isEmpty()) {
            Main.sendMsg(s, "&cNo waypoints registered.");
            return true;
        }
        if (Main.isSpigot()) {
            TextComponent msg = new TextComponent("");
            boolean addSpace = false;
            for (Waypoint w : waypointList) {
                TextComponent t = new TextComponent(w.getName());
                t.setColor(ChatColor.GREEN);
                t.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(String.format("Click to navigate to %s...", w.getId())).create()));
                t.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/navigate %s", w.getId())));
                if (addSpace)
                    msg.addExtra(new TextComponent(" "));
                msg.addExtra(t);
                addSpace = true;
            }
            s.spigot().sendMessage(msg);
        } else {
            StringBuilder msg = new StringBuilder("&a");
            for (Waypoint w : waypointList) {
                if (!msg.toString().equals("&a"))
                    msg.append(" ");
                msg.append(w.getId());
            }
            Main.sendMsg(s, msg.toString());
        }
        return true;
    }
}