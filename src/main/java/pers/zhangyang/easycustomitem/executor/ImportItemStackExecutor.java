package pers.zhangyang.easycustomitem.executor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easycustomitem.yaml.ItemStackYaml;
import pers.zhangyang.easylibrary.base.ExecutorBase;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.PlayerUtil;
import pers.zhangyang.easylibrary.util.ReplaceUtil;
import pers.zhangyang.easylibrary.yaml.MessageYaml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImportItemStackExecutor extends ExecutorBase {
    public ImportItemStackExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
        super(sender, commandName, args);
    }

    @Override
    protected void run() {

        if (args.length !=1) {
            return;
        }

        if (!(sender instanceof Player)) {

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notPlayer");
            MessageUtil.sendMessageTo(sender, list);
            return;
        }
        Player player = (Player) sender;


        ItemStack itemStack = ItemStackYaml.INSTANCE.getItemStack("itemStack."+args[0]);

        if (itemStack==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notExistItemStack");
            MessageUtil.sendMessageTo(sender, list);
            return;
        }


        if (PlayerUtil.checkSpace(player,itemStack)<= itemStack.getAmount()){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notEnoughSpace");
            MessageUtil.sendMessageTo(sender, list);
            return;
        }

        PlayerUtil.addItem(player,itemStack,itemStack.getAmount());


        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.importItemStack");
        MessageUtil.sendMessageTo(sender, list);
    }
}
