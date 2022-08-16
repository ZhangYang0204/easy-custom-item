package pers.zhangyang.easycustomitem.executor;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RemoveItemStackAttributeModifierExecutor extends ExecutorBase {
    public RemoveItemStackAttributeModifierExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
        super(sender, commandName, args);
    }

    @Override
    protected void run() {

        if (args.length != 1) {
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


        UUID uuid;
        try {
            uuid = UUID.fromString(args[1]);
        } catch (IllegalArgumentException e) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.invalidArgument");
            if (list != null) {
                ReplaceUtil.replace(list, Collections.singletonMap("{argument}", args[0]));
            }
            MessageUtil.sendMessageTo(sender, list);
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        boolean remove = false;
        for (Attribute attribute : Attribute.values()) {
            AttributeModifier attributeModifier = null;
            Collection<AttributeModifier> collection = itemMeta.getAttributeModifiers(attribute);
            if (collection == null) {
                continue;
            }
            for (AttributeModifier a : collection) {
                if (a.getUniqueId().equals(uuid)) {
                    attributeModifier = a;
                    break;
                }
            }
            if (attributeModifier == null) {
                continue;
            }

            itemMeta.removeAttributeModifier(attribute, attributeModifier);
            remove = true;

        }
        if (!remove) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notExistAttributeModifier");
            if (list != null) {
                ReplaceUtil.replace(list, Collections.singletonMap("{argument}", args[1]));
            }
            MessageUtil.sendMessageTo(sender, list);
            return;
        }
        itemStack.setItemMeta(itemMeta);
        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.removeItemStackAttributeModifier");
        MessageUtil.sendMessageTo(sender, list);
    }
}
