package cn.handyplus.lib.adapter;

import org.bukkit.plugin.Plugin;

/**
 * HandyLib调度器
 *
 * @author handy
 * @since 1.0.0
 */
public class HandySchedulerUtil {

    private HandySchedulerUtil() {

    }

    protected static Plugin BUKKIT_PLUGIN;

    protected static ServerTypeEnum SERVER_TYPE;

    /**
     * 初始化方法
     *
     * @param plugin 插件
     */
    public static void init(Plugin plugin) {
        BUKKIT_PLUGIN = plugin;
        SERVER_TYPE = ServerTypeEnum.getServerType();
    }

    /**
     * 同步
     *
     * @param task 方法
     */
    public static void runTask(Runnable task) {
        if (ServerTypeEnum.FOLIA.equals(SERVER_TYPE)) {
            FoliaScheduler.runTask(task);
            return;
        }
        BukkitScheduler.runTask(task);
    }

    /**
     * 延迟同步
     *
     * @param task  方法
     * @param delay 延迟
     */
    public static void runTaskLater(Runnable task, long delay) {
        if (ServerTypeEnum.FOLIA.equals(SERVER_TYPE)) {
            FoliaScheduler.runTaskLater(task, delay);
            return;
        }
        BukkitScheduler.runTaskLater(task, delay);
    }

    /**
     * 循环同步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    public static void runTaskTimer(Runnable task, long delay, long period) {
        if (ServerTypeEnum.FOLIA.equals(SERVER_TYPE)) {
            FoliaScheduler.runTaskTimer(task, delay, period);
            return;
        }
        BukkitScheduler.runTaskTimer(task, delay, period);
    }

    /**
     * 异步
     *
     * @param task 方法
     */
    public static void runTaskAsynchronously(Runnable task) {
        if (ServerTypeEnum.FOLIA.equals(SERVER_TYPE)) {
            FoliaScheduler.runTaskAsynchronously(task);
            return;
        }
        BukkitScheduler.runTaskAsynchronously(task);
    }

    /**
     * 延迟异步
     *
     * @param task  方法
     * @param delay 延迟
     */
    public static void runTaskLaterAsynchronously(Runnable task, long delay) {
        if (ServerTypeEnum.FOLIA.equals(SERVER_TYPE)) {
            FoliaScheduler.runTaskLaterAsynchronously(task, delay);
            return;
        }
        BukkitScheduler.runTaskLaterAsynchronously(task, delay);
    }

    /**
     * 循环同步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    public static void runTaskTimerAsynchronously(Runnable task, long delay, long period) {
        if (ServerTypeEnum.FOLIA.equals(SERVER_TYPE)) {
            FoliaScheduler.runTaskTimerAsynchronously(task, delay, period);
            return;
        }
        BukkitScheduler.runTaskTimerAsynchronously(task, delay, period);
    }

}