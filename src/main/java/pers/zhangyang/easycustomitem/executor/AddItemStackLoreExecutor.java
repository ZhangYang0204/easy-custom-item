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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddItemStackLoreExecutor extends ExecutorBase {
    public AddItemStackLoreExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
        super(sender, commandName, args);
    }

    @Override
    protected void run() {

        if (args.length <= 1) {
            return;
        }

        if (!(sender instanceof Player)) {

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notPlayer");
            MessageUtil.sendMessageTo(sender, list);
            return;
        }
        Player player = (Player) sender;


        ItemStack itemStack = PlayerUtil.getItemInMainHand(player);
        if (itemStack.getType().equals(Material.AIR)) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notItemStackInMainHand");
            MessageUtil.sendMessageTo(sender, list);
            return;
        }

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

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }

        if (lineIndex!=0&&lineIndex!=lore.size()&&lineIndex>lore.size()){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.skipElement");
            MessageUtil.sendMessageTo(sender, list);
            return;
        }



        String l = args[1];
        for (int i = 2; i < args.length; i++) {
            l += " " + ChatColor.translateAlternateColorCodes('&',args[i]);
        }

        lore.add(lineIndex,l);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);


        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.addItemStackLore");
        MessageUtil.sendMessageTo(sender, list);
    }
}
