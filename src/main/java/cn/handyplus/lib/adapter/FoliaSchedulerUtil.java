package cn.handyplus.lib.adapter;

import org.bukkit.Bukkit;

import java.util.concurrent.TimeUnit;

/**
 * 调度器
 *
 * @author handy
 * @since 3.7.7
 */
public class FoliaSchedulerUtil {

    private FoliaSchedulerUtil() {

    }

    /**
     * 同步
     *
     * @param task 方法
     */
    protected static void runTask(Runnable task) {
        Bukkit.getGlobalRegionScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run());
    }

    /**
     * 延迟同步
     *
     * @param task 方法
     */
    protected static void runTaskLater(Runnable task, long delay) {
        Bukkit.getGlobalRegionScheduler().runDelayed(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay);
    }

    /**
     * 循环同步
     *
     * @param task 方法
     */
    protected static void runTaskTimer(Runnable task, long delay, long period) {
        Bukkit.getGlobalRegionScheduler().runAtFixedRate(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay, period);
    }

    /**
     * 异步
     *
     * @param task 方法
     */
    protected static void runTaskAsynchronously(Runnable task) {
        Bukkit.getAsyncScheduler().runNow(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run());
    }

    /**
     * 延迟异步
     *
     * @param task  方法
     * @param delay 延迟
     */
    protected static void runTaskLaterAsynchronously(Runnable task, long delay) {
        Bukkit.getAsyncScheduler().runDelayed(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay / 20, TimeUnit.SECONDS);
    }

    /**
     * 循环同步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    protected static void runTaskTimerAsynchronously(Runnable task, long delay, long period) {
        Bukkit.getAsyncScheduler().runAtFixedRate(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay / 20, period / 20, TimeUnit.SECONDS);
    }

}