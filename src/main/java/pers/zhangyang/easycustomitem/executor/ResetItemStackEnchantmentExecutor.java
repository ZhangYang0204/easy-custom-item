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
import pers.zhangyang.easylibrary.yaml.MessageYaml;

import java.util.List;

public class ResetItemStackEnchantmentExecutor extends ExecutorBase {
    public ResetItemStackEnchantmentExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
        super(sender, commandName, args);
    }

    @Override
    protected void run() {

        if (args.length != 0) {
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


        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        for (Enchantment e : Enchantment.values()) {
            if (itemMeta.hasEnchant(e)) {
                itemMeta.removeEnchant(e);
            }
        }
        itemStack.setItemMeta(itemMeta);
        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.resetItemStackEnchantment");
        MessageUtil.sendMessageTo(sender, list);
    }
}
