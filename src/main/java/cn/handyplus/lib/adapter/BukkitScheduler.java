package cn.handyplus.lib.adapter;

import org.bukkit.Bukkit;

/**
 * Bukkit内部调度器
 *
 * @author handy
 * @since 1.0.0
 */
public class BukkitScheduler {

    /**
     * 构造器
     */
    private BukkitScheduler() {

    }

    /**
     * 同步
     *
     * @param task 方法
     */
    protected static void runTask(Runnable task) {
        Bukkit.getScheduler().runTask(HandySchedulerUtil.BUKKIT_PLUGIN, task);
    }

    /**
     * 延迟同步
     *
     * @param task  方法
     * @param delay 延迟
     */
    protected static void runTaskLater(Runnable task, long delay) {
        Bukkit.getScheduler().runTaskLater(HandySchedulerUtil.BUKKIT_PLUGIN, task, delay);
    }

    /**
     * 延迟同步
     *
     * @param task  方法
     * @param delay 延迟
     */
    protected static void runTaskLater(HandyRunnable task, long delay) {
        task.setupTask(Bukkit.getScheduler().runTaskLater(HandySchedulerUtil.BUKKIT_PLUGIN, task, delay));
    }

    /**
     * 循环同步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    protected static void runTaskTimer(Runnable task, long delay, long period) {
        Bukkit.getScheduler().runTaskTimer(HandySchedulerUtil.BUKKIT_PLUGIN, task, delay, period);
    }

    /**
     * 循环同步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    protected static void runTaskTimer(HandyRunnable task, long delay, long period) {
        task.setupTask(Bukkit.getScheduler().runTaskTimer(HandySchedulerUtil.BUKKIT_PLUGIN, task, delay, period));
    }

    /**
     * 异步
     *
     * @param task 方法
     */
    protected static void runTaskAsynchronously(Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(HandySchedulerUtil.BUKKIT_PLUGIN, task);
    }

    /**
     * 延迟异步
     *
     * @param task  方法
     * @param delay 延迟
     */
    protected static void runTaskLaterAsynchronously(Runnable task, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(HandySchedulerUtil.BUKKIT_PLUGIN, task, delay);
    }

    /**
     * 延迟异步
     *
     * @param task  方法
     * @param delay 延迟
     * @since 1.0.5
     */
    protected static void runTaskLaterAsynchronously(HandyRunnable task, long delay) {
        task.setupTask(Bukkit.getScheduler().runTaskLaterAsynchronously(HandySchedulerUtil.BUKKIT_PLUGIN, task, delay));
    }

    /**
     * 循环异步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    protected static void runTaskTimerAsynchronously(Runnable task, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(HandySchedulerUtil.BUKKIT_PLUGIN, task, delay, period);
    }

    /**
     * 循环异步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    protected static void runTaskTimerAsynchronously(HandyRunnable task, long delay, long period) {
        task.setupTask(Bukkit.getScheduler().runTaskTimerAsynchronously(HandySchedulerUtil.BUKKIT_PLUGIN, task, delay, period));
    }

    /**
     * 取消所有调度任务
     */
    protected static void cancelTask() {
        Bukkit.getScheduler().cancelTasks(HandySchedulerUtil.BUKKIT_PLUGIN);
    }

}