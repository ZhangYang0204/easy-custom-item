package pers.zhangyang.easycustomitem.executor;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easylibrary.EasyPlugin;
import pers.zhangyang.easylibrary.base.ExecutorBase;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.PlayerUtil;
import pers.zhangyang.easylibrary.util.ReplaceUtil;
import pers.zhangyang.easylibrary.util.VersionUtil;
import pers.zhangyang.easylibrary.yaml.MessageYaml;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class RemoveItemStackPersistentDataExecutor extends ExecutorBase {
    public RemoveItemStackPersistentDataExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
        super(sender, commandName, args);
    }


    @Override
    protected void run() {
        if (args.length != 1) {
            return;
        }
        if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() < 14) {
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


        if (!Pattern.matches("^[a-z0-9/._-]+$", args[0])) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.invalidArgument");
            if (list != null) {
                ReplaceUtil.replace(list, Collections.singletonMap("{argument}", args[0]));
            }
            MessageUtil.sendMessageTo(sender, list);
            return;
        }


        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        NamespacedKey namespacedKey = new NamespacedKey(EasyPlugin.instance, args[0]);
        itemMeta.getPersistentDataContainer().remove(namespacedKey);
        itemStack.setItemMeta(itemMeta);

        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.removeItemStackPersistentData");
        MessageUtil.sendMessageTo(sender, list);
    }
}
