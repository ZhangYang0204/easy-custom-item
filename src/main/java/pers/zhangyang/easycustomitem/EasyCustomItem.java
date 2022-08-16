package pers.zhangyang.easycustomitem;

import org.bstats.bukkit.Metrics;
import pers.zhangyang.easylibrary.EasyPlugin;

public class EasyCustomItem extends EasyPlugin {
    @Override
    public void onOpen() {
        new Metrics(EasyPlugin.instance, 16148);
    }

    @Override
    public void onClose() {

    }
}
