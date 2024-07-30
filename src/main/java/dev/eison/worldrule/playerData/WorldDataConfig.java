package dev.eison.worldrule.playerData;

import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;

import java.io.File;
import java.util.*;

public class WorldDataConfig {

    public static Map<String, PlayerData> load(File file) {
        List<Item> inventory = new ArrayList<>();
        List<Item> enderChest = new ArrayList<>();
        Item offhand;
        int gamemode;
        int foodLevel;
        int expLevel;
        float health;

        Config config = new Config(file);
        Map<String, PlayerData> playerData = new HashMap<>();

        for (String key : config.getKeys()) {
            for (Map map : config.getMapList(key + ".inventory")) {
                inventory.add(Item.fromJson(map));
            }

            for (Map map : config.getMapList(key + ".enderChest")) {
                enderChest.add(Item.fromJson(map));
            }

            offhand = Item.fromJson(config.getSection(key + ".offhand"));
            gamemode = config.getInt(key + ".gamemode");
            foodLevel = config.getInt(key + ".foodLevel");
            expLevel = config.getInt(key + ".expLevel");
            health = (float) config.getDouble(key + ".health");

            playerData.put(key, new PlayerData(inventory.toArray(new Item[40]), enderChest.toArray(new Item[27]), offhand, gamemode, foodLevel, expLevel, health));
        }

        return playerData;
    }

    public static void save(File directory, UUID uuid, String worldName, PlayerData playerData) {
        Config config = new Config(new File(directory, uuid.toString() + ".yml"));
        Map<String, Object>[] inventory = new HashMap[40];

        for (int i = 0; i < 40; i++) {
            Item item = playerData.getInventory()[i];
            inventory[i] = item == null ? null : ItemSerializer.toJson(item);
        }

        Map<String, Object>[] enderChest = new HashMap[27];
        for (int i = 0; i < 27; i++) {
            Item item = playerData.getEnderChest()[i];
            enderChest[i] = item == null ? null : ItemSerializer.toJson(item);
        }

        config.set(worldName + ".inventory", inventory);
        config.set(worldName + ".enderChest", enderChest);
        config.set(worldName + ".offhand", ItemSerializer.toJson(playerData.getOffhand()));
        config.set(worldName + ".gamemode", playerData.getGamemode());
        config.set(worldName + ".foodLevel", playerData.getFoodLevel());
        config.set(worldName + ".expLevel", playerData.getExpLevel());
        config.set(worldName + ".health", playerData.getHealth());

        config.save();
    }
}
