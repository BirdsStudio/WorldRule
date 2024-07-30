package dev.eison.worldrule.playerData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorldDataManager {
    private final Map<String, Map<UUID, PlayerData>> worldPlayerData;

    public WorldDataManager() {
        worldPlayerData = new HashMap<>();
    }

    public void setPlayerData(String levelName, UUID playerUUID, PlayerData playerData) {
        if (!worldPlayerData.containsKey(levelName)) {
            worldPlayerData.put(levelName, new HashMap<>());
        }
        worldPlayerData.get(levelName).put(playerUUID, playerData);
    }

    public PlayerData getPlayerData(String levelName, UUID playerUUID) {
        if (worldPlayerData.containsKey(levelName)) {
            return worldPlayerData.get(levelName).get(playerUUID);
        }
        return null;
    }
}
