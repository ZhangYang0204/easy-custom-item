package pers.zhangyang.easycustomitem.executor;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
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

public class SetItemStackEnchantmentExecutor extends ExecutorBase {
    public SetItemStackEnchantmentExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
        super(sender, commandName, args);
    }

    @Override
    protected void run() {

        if (args.length != 2) {
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

        Enchantment enchantment = null;


        for (Enchantment e : Enchantment.values()) {
            if (e.getKey().toString().equalsIgnoreCase(args[0])) {
                enchantment = e;
                break;
            }
        }
        if (enchantment == null) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.invalidArgument");
            if (list != null) {
                ReplaceUtil.replace(list, Collections.singletonMap("{argument}", args[0]));
            }
            MessageUtil.sendMessageTo(sender, list);
            return;
        }
        int level;
        try {
            level = Integer.parseInt(args[1]);

        } catch (NumberFormatException e) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.invalidArgument");
            if (list != null) {
                ReplaceUtil.replace(list, Collections.singletonMap("{argument}", args[1]));
            }
            MessageUtil.sendMessageTo(sender, list);
            return;
        }

        if (level < enchantment.getStartLevel()) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.invalidArgument");
            if (list != null) {
                ReplaceUtil.replace(list, Collections.singletonMap("{argument}", args[1]));
            }
            MessageUtil.sendMessageTo(sender, list);
            return;
        }

        if (level > enchantment.getMaxLevel()) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.invalidArgument");
            if (list != null) {
                ReplaceUtil.replace(list, Collections.singletonMap("{argument}", args[1]));
            }
            MessageUtil.sendMessageTo(sender, list);
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;


        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);


        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.setItemStackEnchantment");
        MessageUtil.sendMessageTo(sender, list);
    }
}
