package co.stashcat.navigation;

import co.stashcat.navigation.types.Waypoint;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaypointManager {
    public static Map<String, Waypoint> waypoints = new HashMap<>();
    static Configuration currentConfig;

    public static void loadWaypoints(Configuration c, boolean clear) {
        currentConfig = c;
        if (clear) waypoints.clear();
        if (c.get("waypoints") == null)
            return;
        List<String> keys = c.getStringList("waypoints");
        for (String k : keys) {
            ConfigurationSection cs = c.getConfigurationSection("waypoints." + k);
            Location loc = (Location) cs.get("location");
            int destinationRadius = cs.getInt("destinationRadius");
            boolean ignoreHeight = cs.getBoolean("ignoreHeight");
            String name = cs.getString("name");
            String desc = cs.getString("description");
            ItemStack item = (ItemStack) cs.get("item");
            waypoints.put(k, new Waypoint(loc, destinationRadius, ignoreHeight, k, name, desc, item));
        }
    }

    public static void loadWaypoints(Configuration c) {
        loadWaypoints(c, true);
    }

    public static Waypoint getWaypoint(String s) {
        return waypoints.get(s);
    }

    public static Map<String, Waypoint> getWaypoints() {
        return waypoints;
    }

    public static Collection<Waypoint> getWaypointList() {
        return waypoints.values();
    }

    public static Configuration getCurrentConfig() {
        return currentConfig;
    }
}
