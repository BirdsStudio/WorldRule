package dev.eison.worldrule.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import dev.eison.worldrule.form.WorldsForm;

public class WorldRuleCommand extends Command {
    public WorldRuleCommand() {
        super("wr", "WorldRule command.", "/wr help", new String[0]);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player player && commandSender.isOp())) {
            commandSender.sendMessage("this command is only available to the operator.");
            return true;
        }

        WorldsForm.showForm(player);
        return true;
    }
}
