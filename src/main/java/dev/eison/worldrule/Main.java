package dev.eison.worldrule;

import cn.nukkit.plugin.PluginBase;
import dev.eison.worldrule.command.WorldRuleCommand;
import dev.eison.worldrule.playerData.EventListener;
import dev.eison.worldrule.playerData.PlayerData;
import dev.eison.worldrule.playerData.WorldDataConfig;
import dev.eison.worldrule.playerData.WorldDataManager;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Main extends PluginBase {
    public final WorldDataManager worldDataManager = new WorldDataManager();

    @Override
    public void onEnable() {
        this.getServer().getCommandMap().register("", new WorldRuleCommand());
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);

        File directory = new File(this.getDataFolder(), "player_data");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                this.getLogger().info("player_data folder created!");
            } else {
                this.getLogger().error("player_data folder not created!");
                this.setEnabled(false);
                return;
            }
        }
        this.loadConfig(directory);

        this.getLogger().info("plugin load!");
    }

    public void loadConfig(File directory) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (!(file.isFile() && file.getName().endsWith(".yml"))) {
                continue;
            }

            for (Map.Entry<String, PlayerData> entry : WorldDataConfig.load(file).entrySet()) {
                worldDataManager.setPlayerData(entry.getKey(), UUID.fromString(file.getName().substring(0, file.getName().length() - 4)), entry.getValue());
            }
        }
    }
}
