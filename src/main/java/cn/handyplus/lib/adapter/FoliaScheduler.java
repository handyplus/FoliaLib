package cn.handyplus.lib.adapter;

import org.bukkit.Bukkit;

import java.util.concurrent.TimeUnit;

/**
 * Folia 内部调度器
 *
 * @author handy
 * @since 1.0.0
 */
class FoliaScheduler {

    /**
     * 构造器
     */
    private FoliaScheduler() {

    }

    /**
     * 全局区域调度
     *
     * @param task 方法
     */
    protected static void runTask(Runnable task) {
        Bukkit.getGlobalRegionScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run());
    }

    /**
     * 延迟全局区域调度
     *
     * @param task  方法
     * @param delay 延迟
     */
    protected static void runTaskLater(Runnable task, long delay) {
        delay = getOneIfNotPositive(delay);
        Bukkit.getGlobalRegionScheduler().runDelayed(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay);
    }

    /**
     * 延迟全局区域调度
     *
     * @param task  方法
     * @param delay 延迟
     * @since 1.0.5
     */
    protected static void runTaskLater(HandyRunnable task, long delay) {
        delay = getOneIfNotPositive(delay);
        task.setupTask(Bukkit.getGlobalRegionScheduler().runDelayed(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay));
    }

    /**
     * 循环全局区域调度
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    protected static void runTaskTimer(Runnable task, long delay, long period) {
        delay = getOneIfNotPositive(delay);
        period = getOneIfNotPositive(period);
        Bukkit.getGlobalRegionScheduler().runAtFixedRate(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay, period);
    }

    /**
     * 循环全局区域调度
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    protected static void runTaskTimer(HandyRunnable task, long delay, long period) {
        delay = getOneIfNotPositive(delay);
        period = getOneIfNotPositive(period);
        task.setupTask(Bukkit.getGlobalRegionScheduler().runAtFixedRate(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay, period));
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
        delay = getOneIfNotPositive(delay);
        Bukkit.getAsyncScheduler().runDelayed(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay * 50, TimeUnit.MILLISECONDS);
    }

    /**
     * 延迟异步
     *
     * @param task  方法
     * @param delay 延迟
     * @since 1.0.5
     */
    protected static void runTaskLaterAsynchronously(HandyRunnable task, long delay) {
        delay = getOneIfNotPositive(delay);
        task.setupTask(Bukkit.getAsyncScheduler().runDelayed(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay * 50, TimeUnit.MILLISECONDS));
    }

    /**
     * 循环异步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    protected static void runTaskTimerAsynchronously(Runnable task, long delay, long period) {
        delay = getOneIfNotPositive(delay);
        period = getOneIfNotPositive(period);
        Bukkit.getAsyncScheduler().runAtFixedRate(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay * 50, period * 50, TimeUnit.MILLISECONDS);
    }

    /**
     * 循环异步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    protected static void runTaskTimerAsynchronously(HandyRunnable task, long delay, long period) {
        delay = getOneIfNotPositive(delay);
        period = getOneIfNotPositive(period);
        task.setupTask(Bukkit.getAsyncScheduler().runAtFixedRate(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay * 50, period * 50, TimeUnit.MILLISECONDS));
    }

    /**
     * 取消所有调度任务
     */
    protected static void cancelTask() {
        Bukkit.getGlobalRegionScheduler().cancelTasks(HandySchedulerUtil.BUKKIT_PLUGIN);
        Bukkit.getAsyncScheduler().cancelTasks(HandySchedulerUtil.BUKKIT_PLUGIN);
    }

    /**
     * Folia异常：时间参数不能<=0
     *
     * @param time 时间
     * @return 时间
     * @since 1.0.1
     */
    private static long getOneIfNotPositive(long time) {
        return time <= 0 ? 1L : time;
    }

}
