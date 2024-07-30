package dev.eison.worldrule.form;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.handler.FormResponseHandler;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.Level;

import java.util.Map;

public class WorldsForm {
    public static void showForm(Player player) {
        FormWindowSimple form = new FormWindowSimple("WorldRule", "选择一个世界，更改它的规则。");

        Map<Integer, Level> levels =  Server.getInstance().getLevels();
        for (int i = 1; i < levels.size(); i++) {
            form.addButton(new ElementButton(levels.get(i).getName()));
        }

        form.addHandler(FormResponseHandler.withoutPlayer(ignored -> {
                FormResponseSimple response = form.getResponse();
                if (response == null) {
                    return;
                }

                WorldRuleForm.showForm(player, levels.get(form.getResponse().getClickedButtonId() + 1));
            }
        ));

        player.showFormWindow(form);
    }
}
