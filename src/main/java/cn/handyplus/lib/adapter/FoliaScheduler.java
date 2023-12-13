package cn.handyplus.lib.adapter;

import org.bukkit.Bukkit;

import java.util.concurrent.TimeUnit;

/**
 * Folia内部调度器
 *
 * @author handy
 * @since 1.0.0
 */
public class FoliaScheduler {

    private FoliaScheduler() {

    }

    /**
     * 同步
     *
     * @param task 方法
     */
    protected static void runTask(HandyRunnable task) {
        task.setupTask(Bukkit.getGlobalRegionScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run()));
    }

    /**
     * 延迟同步
     *
     * @param task  方法
     * @param delay 延迟
     */
    protected static void runTaskLater(HandyRunnable task, long delay) {
        task.setupTask(Bukkit.getGlobalRegionScheduler().runDelayed(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay));
    }

    /**
     * 循环同步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    protected static void runTaskTimer(HandyRunnable task, long delay, long period) {
        task.setupTask(Bukkit.getGlobalRegionScheduler().runAtFixedRate(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay, period));
    }

    /**
     * 异步
     *
     * @param task 方法
     */
    protected static void runTaskAsynchronously(HandyRunnable task) {
        task.setupTask(Bukkit.getAsyncScheduler().runNow(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run()));
    }

    /**
     * 延迟异步
     *
     * @param task  方法
     * @param delay 延迟
     */
    protected static void runTaskLaterAsynchronously(HandyRunnable task, long delay) {
        task.setupTask(Bukkit.getAsyncScheduler().runDelayed(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay * 50, TimeUnit.MILLISECONDS));
    }

    /**
     * 循环同步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    protected static void runTaskTimerAsynchronously(HandyRunnable task, long delay, long period) {
        task.setupTask(Bukkit.getAsyncScheduler().runAtFixedRate(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay * 50, period * 50, TimeUnit.MILLISECONDS));
    }

}