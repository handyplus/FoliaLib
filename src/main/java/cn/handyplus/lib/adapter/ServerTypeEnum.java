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
    FOLIA("io.papermc.paper.threadedregions.RegionizedServer"),

    /**
     * paper
     *
     * @since 1.2.1
     */
    PAPER("com.destroystokyo.paper.PaperConfig"),

    /**
     * spigot
     *
     * @since 1.2.1
     */
    SPIGOT("org.spigotmc.SpigotConfig"),

    /**
     * bukkit
     */
    BUKKIT("org.bukkit.Bukkit");

    private final String className;

    ServerTypeEnum(String className) {
        this.className = className;
    }

    /**
     * 获取当前服务器类型
     *
     * @return 服务器类型枚举
     */
    public static ServerTypeEnum getServerType() {
        if (isClassPresent(FOLIA.className)) {
            return FOLIA;
        }
        if (isClassPresent(PAPER.className)) {
            return PAPER;
        }
        if (isClassPresent(SPIGOT.className)) {
            return SPIGOT;
        }
        return BUKKIT;
    }

    private static boolean isClassPresent(String className) {
        try {
            Class.forName(className, false, ServerTypeEnum.class.getClassLoader());
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

}
