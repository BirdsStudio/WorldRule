package dev.eison.worldrule.playerData;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ItemSerializer {

    public static Map<String, Object> toJson(Item item) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", item.getId());
        data.put("damage", item.getDamage());
        data.put("count", item.getCount());

        if (item.hasCompoundTag()) {
            CompoundTag compoundTag = item.getNamedTag();
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); DataOutputStream dos = new DataOutputStream(baos)) {
                NBTIO.write(compoundTag, dos);
                byte[] nbtBytes = baos.toByteArray();
                String nbtBase64 = Base64.getEncoder().encodeToString(nbtBytes);
                data.put("nbt_b64", nbtBase64);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return data;
    }
}
