package cn.handyplus.lib.adapter;


/**
 * 服务器类型
 *
 * @author handy
 * @since 1.0.0
 */
public enum ServerTypeEnum {

    /**
     * folia
     */
    FOLIA("io.papermc.paper.threadedregions.RegionizedServerInitEvent"),
    /**
     * bukkit
     */
    BUKKIT("org.bukkit.Bukkit");

    private final String className;

    ServerTypeEnum(String className) {
        this.className = className;
    }

    static ServerTypeEnum getServerType() {
        try {
            Class.forName(FOLIA.className);
            return FOLIA;
        } catch (ClassNotFoundException e) {
            return BUKKIT;
        }
    }

}
