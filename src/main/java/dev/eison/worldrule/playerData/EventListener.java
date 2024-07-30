package dev.eison.worldrule.playerData;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.level.Level;
import dev.eison.worldrule.Main;

import java.io.File;
import java.util.UUID;

public class EventListener implements Listener {
    public Main instance;
    public File directory;

    public EventListener(Main instance) {
        this.instance = instance;
        this.directory = new File(instance.getDataFolder(), "player_data");
    }

    @EventHandler
    public void onEntityLevelChange(EntityLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        UUID playerUUID = player.getUniqueId();
        Level originLevel = event.getOrigin();
        Level targetLevel = event.getTarget();

        instance.worldDataManager.setPlayerData(originLevel.getName(), playerUUID, new PlayerData(player));
        PlayerData playerData = instance.worldDataManager.getPlayerData(targetLevel.getName(), playerUUID);

        if (playerData == null) {
            playerData = new PlayerData(player);
        }

        instance.worldDataManager.setPlayerData(targetLevel.getName(), playerUUID, playerData);
        WorldDataConfig.save(this.directory, playerUUID, targetLevel.getName(), playerData);
        playerData.applyToPlayer(player);
    }
}
