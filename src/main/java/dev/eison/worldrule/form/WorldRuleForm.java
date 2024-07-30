package dev.eison.worldrule.form;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.handler.FormResponseHandler;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.Level;

public class WorldRuleForm {

    public static void showForm(Player player, Level level) {
        FormWindowCustom form = new FormWindowCustom("WorldRule - " + level.getName());

        form.addElement(new ElementLabel( level.getName() + " 的世界规则"));
        form.addElement(new ElementToggle("昼夜交替", level.gameRules.getBoolean(GameRule.DO_DAYLIGHT_CYCLE)));
        form.addElement(new ElementInput("时间设置", String.valueOf(level.getTime()), String.valueOf(level.getTime())));
        form.addElement(new ElementToggle("生物是否生成", level.gameRules.getBoolean(GameRule.DO_MOB_SPAWNING)));
        form.addElement(new ElementInput("随机刻", level.gameRules.getString(GameRule.RANDOM_TICK_SPEED), level.gameRules.getString(GameRule.RANDOM_TICK_SPEED)));

        form.addHandler(FormResponseHandler.withoutPlayer(ignored -> {
                FormResponseCustom response = form.getResponse();
                if (response == null) {
                    return;
                }

                String time = response.getInputResponse(2);
                String randomTickSpeed = response.getInputResponse(4);
                if (time.matches("\\d+") && randomTickSpeed.matches("\\d+")) {
                    level.gameRules.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, response.getToggleResponse(1));
                    level.gameRules.setGameRule(GameRule.DO_MOB_SPAWNING, response.getToggleResponse(3));
                    level.gameRules.setGameRule(GameRule.RANDOM_TICK_SPEED, Integer.parseInt(randomTickSpeed));
                    level.setTime(Integer.parseInt(time));
                    player.sendMessage("§a设置成功！");
                } else {
                    player.sendMessage("§c值必须为数字！");
                }
            }
        ));

        player.showFormWindow(form);
    }
}
