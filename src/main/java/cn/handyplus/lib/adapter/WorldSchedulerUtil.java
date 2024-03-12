package cn.handyplus.lib.adapter;

import org.bukkit.Location;

/**
 * 世界调度工具
 *
 * @author handy
 * @since 1.1.3
 */
public class WorldSchedulerUtil {

    /**
     * 异步加载对应坐标的区块
     *
     * @param location 坐标
     * @since 1.1.3
     */
    public static void getChunkAtAsync(Location location) {
        if (location == null || location.getWorld() == null) {
            return;
        }
        if (HandySchedulerUtil.isFolia()) {
            location.getWorld().getChunkAtAsync(location);
            return;
        }
        location.getWorld().getChunkAt(location).load();
    }

}
