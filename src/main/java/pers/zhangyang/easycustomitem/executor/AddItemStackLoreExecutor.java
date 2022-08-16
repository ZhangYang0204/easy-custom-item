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
import pers.zhangyang.easylibrary.yaml.MessageYaml;

import java.util.ArrayList;
import java.util.List;

public class AddItemStackLoreExecutor extends ExecutorBase {
    public AddItemStackLoreExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
        super(sender, commandName, args);
    }

    @Override
    protected void run() {

        if (args.length <= 0) {
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
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notItemInMainHand");
            MessageUtil.sendMessageTo(sender, list);
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        String l = args[1];
        for (int i = 0; i < args.length; i++) {
            l += " " + ChatColor.translateAlternateColorCodes('&',args[i]);
        }


        lore.add(l);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);


        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.addStackItemLore");
        MessageUtil.sendMessageTo(sender, list);
    }
}
