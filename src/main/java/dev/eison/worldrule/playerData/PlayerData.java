package dev.eison.worldrule.playerData;

import cn.nukkit.Player;
import cn.nukkit.item.Item;

public class PlayerData {
    private final Item[] inventory;
    private final Item[] enderChest;
    private final Item offhand;
    private final int gamemode;
    private final int foodLevel;
    private final int expLevel;
    private final float health;

    public PlayerData(Item[] inventory, Item[] enderChest, Item offhand, int gamemode, int foodLevel, int expLevel, float health) {
        this.inventory = inventory;
        this.enderChest = enderChest;
        this.offhand = offhand;
        this.gamemode = gamemode;
        this.foodLevel = foodLevel;
        this.expLevel = expLevel;
        this.health = health;
    }

    public PlayerData(Player player) {
        this(player.getInventory().slots.values().toArray(new Item[40]), player.getEnderChestInventory().slots.values().toArray(new Item[27]), player.getOffhandInventory().getItem(0), player.getGamemode(), player.getFoodData().getLevel(), player.getExperienceLevel(), player.getHealth());
    }

    public Item[] getInventory() {
        return inventory;
    }

    public Item[] getEnderChest() {
        return enderChest;
    }

    public Item getOffhand() {
        return offhand;
    }

    public int getGamemode() {
        return gamemode;
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public float getHealth() {
        return health;
    }

    public int getExpLevel() {
        return expLevel;
    }

    public void applyToPlayer(Player player) {
        player.getInventory().clearAll();

        for (int i = 4; i < 40; i++) {
            if (inventory[i] != null) {
                player.getInventory().setItem(i, inventory[i]);
            }
        }

        player.getInventory().setArmorContents(new Item[]{inventory[0], inventory[1], inventory[2], inventory[3]});
        player.getOffhandInventory().setItem(0, offhand);

        player.getEnderChestInventory().clearAll();
        for (int i = 0; i < 27; i++) {
            if (enderChest[i] != null) {
                player.getEnderChestInventory().setItem(i, enderChest[i]);
            }
        }

        player.setGamemode(gamemode);
        player.getFoodData().setLevel(foodLevel);
        player.setExperience(expLevel);
        player.setHealth(health);
    }
}
