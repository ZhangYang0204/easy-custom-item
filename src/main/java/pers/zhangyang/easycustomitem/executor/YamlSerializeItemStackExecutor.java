package pers.zhangyang.easycustomitem.executor;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easylibrary.base.ExecutorBase;
import pers.zhangyang.easylibrary.util.ItemStackUtil;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.PlayerUtil;
import pers.zhangyang.easylibrary.util.VersionUtil;
import pers.zhangyang.easylibrary.yaml.MessageYaml;

import java.util.List;

public class YamlSerializeItemStackExecutor extends ExecutorBase {
    public YamlSerializeItemStackExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
        super(sender, commandName, args);
    }

    @Override
    protected void run() {

        if (args.length!=0){
            return;
        }

        if (!(sender instanceof Player)){

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notPlayer");
            MessageUtil.sendMessageTo(sender,list);
            return;
        }
        Player player= (Player) sender;


        ItemStack itemStack= PlayerUtil.getItemInMainHand(player);
        if (itemStack.getType().equals(Material.AIR)){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notItemInMainHand");
            MessageUtil.sendMessageTo(sender,list);
            return;
        }

        String s=ItemStackUtil.itemStackSerialize(itemStack);
        TextComponent textComponent=new TextComponent(s);
        if (VersionUtil.getMinecraftBigVersion()==1&&VersionUtil.getMinecraftMiddleVersion()>=16) {
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, s));
        }

        Bukkit.getConsoleSender().sendMessage(s);

            String hover = MessageYaml.INSTANCE.getNonemptyString("message.component.yamlSerializeItemStack");
            if (hover != null) {
                hover = ChatColor.translateAlternateColorCodes('&', hover);
                if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() < 16) {
                    textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(hover)}));
                } else {
                    textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hover)));
                }
            }

        player.spigot().sendMessage(textComponent);
    }
}
