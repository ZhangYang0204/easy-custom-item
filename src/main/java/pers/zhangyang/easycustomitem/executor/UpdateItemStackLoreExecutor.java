package pers.zhangyang.easycustomitem.executor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easylibrary.base.ExecutorBase;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.PlayerUtil;
import pers.zhangyang.easylibrary.util.ReplaceUtil;
import pers.zhangyang.easylibrary.yaml.MessageYaml;

import java.util.Collections;
import java.util.List;

public class UpdateItemStackLoreExecutor extends ExecutorBase {
    public UpdateItemStackLoreExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
        super(sender, commandName, args);
    }

    @Override
    protected void run() {

        if (args.length < 2) {
            return;
        }

        if (!(sender instanceof Player)) {

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notPlayer");
            MessageUtil.sendMessageTo(sender, list);
            return;
        }
        Player player = (Player) sender;

        int lineIndex;

        try {
            lineIndex = Integer.parseInt(args[0]) - 1;
        } catch (NumberFormatException e) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.invalidArgument");
            if (list != null) {
                ReplaceUtil.replace(list, Collections.singletonMap("{argument}", args[0]));
            }
            MessageUtil.sendMessageTo(sender, list);
            return;
        }
        if (lineIndex < 0) {

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.invalidArgument");
            if (list != null) {
                ReplaceUtil.replace(list, Collections.singletonMap("{argument}", args[0]));
            }
            MessageUtil.sendMessageTo(sender, list);
            return;
        }

        ItemStack itemStack = PlayerUtil.getItemInMainHand(player);
        if (itemStack.getType().equals(Material.AIR)) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notItemStackInMainHand");
            MessageUtil.sendMessageTo(sender, list);
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        List<String> lore = itemMeta.getLore();

        if (lore == null) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notExistLineWhenUpdateItemStackLore");
            MessageUtil.sendMessageTo(sender, list);
            return;
        }

        if (lore.size() < lineIndex) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notExistLineWhenUpdateItemStackLore");
            MessageUtil.sendMessageTo(sender, list);
            return;
        }
        String l = args[1];
        for (int i = 1; i < args.length; i++) {
            l += " " + ChatColor.translateAlternateColorCodes('&',args[i]);
        }

        lore.set(lineIndex, l);

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.updateItemStackLore");
        MessageUtil.sendMessageTo(sender, list);
    }
}
