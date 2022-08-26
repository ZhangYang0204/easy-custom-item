package pers.zhangyang.easycustomitem.executor;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
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
import java.util.UUID;

public class AddItemStackAttributeModifierExecutor extends ExecutorBase {
    public AddItemStackAttributeModifierExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
        super(sender, commandName, args);
    }

    @Override
    protected void run() {

        if (args.length != 5 && args.length != 6) {
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

        double amount;
        try {
            amount = Double.parseDouble(args[3]);

        } catch (NumberFormatException e) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.invalidArgument");
            if (list != null) {
                ReplaceUtil.replace(list, Collections.singletonMap("{argument}", args[3]));
            }
            MessageUtil.sendMessageTo(sender, list);
            return;
        }

        if (amount < 0) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.invalidArgument");
            if (list != null) {
                ReplaceUtil.replace(list, Collections.singletonMap("{argument}", args[3]));
            }
            MessageUtil.sendMessageTo(sender, list);
            return;
        }

        AttributeModifier.Operation operation;
        try {
            operation = AttributeModifier.Operation.valueOf(args[4]);
        } catch (IllegalArgumentException e) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.invalidArgument");
            if (list != null) {
                ReplaceUtil.replace(list, Collections.singletonMap("{argument}", args[4]));
            }
            MessageUtil.sendMessageTo(sender, list);
            return;
        }
        UUID uuid;
        try {
            uuid=UUID.fromString(args[1]);
        }catch (IllegalArgumentException e){
            MessageUtil.invalidArgument(player,args[1]);
            return;
        }

        AttributeModifier attributeModifier;
        attributeModifier = new AttributeModifier(uuid, args[2], amount, operation);
        EquipmentSlot slot;
        if (args.length == 6) {
            try {
                slot = EquipmentSlot.valueOf(args[5]);
            } catch (IllegalArgumentException e) {
                List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.invalidArgument");
                if (list != null) {
                    ReplaceUtil.replace(list, Collections.singletonMap("{argument}", args[5]));
                }
                MessageUtil.sendMessageTo(sender, list);
                return;
            }
            attributeModifier = new AttributeModifier(uuid, args[2], amount, operation, slot);
        }

        Attribute attribute;
        try {
            attribute = Attribute.valueOf(args[0]);
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

        itemMeta.addAttributeModifier(attribute, attributeModifier);
        itemStack.setItemMeta(itemMeta);

        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.addItemStackAttributeModifier");
        MessageUtil.sendMessageTo(sender, list);
    }
}
