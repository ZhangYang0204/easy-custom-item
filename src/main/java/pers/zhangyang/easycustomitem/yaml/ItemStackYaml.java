package pers.zhangyang.easycustomitem.yaml;

import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easylibrary.base.YamlBase;

public class ItemStackYaml extends YamlBase {

    public static final ItemStackYaml INSTANCE = new ItemStackYaml();

    private ItemStackYaml() {
        super("itemStack.yml");
    }


}
