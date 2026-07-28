package cn.handyplus.lib.adapter;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 区域相关调度器
 *
 * @author handy
 * @since 1.3.3
 */
public class RegionSchedulerUtil {

    /**
     * Folia 区域任务
     */
    private static final Set<ScheduledTask> REGION_TASK_SET = ConcurrentHashMap.newKeySet();

    /**
     * 构造器
     */
    private RegionSchedulerUtil() {
    }

    /**
     * 指定区域安全线程调度
     *
     * @param location 区域坐标
     * @param task     任务
     * @since 1.3.3
     */
    public static void runTask(@NotNull Location location, @NotNull Runnable task) {
        if (!HandySchedulerUtil.isFolia()) {
            BukkitScheduler.runTask(task);
            return;
        }
        ScheduledTask scheduledTask = Bukkit.getRegionScheduler().run(
                HandySchedulerUtil.BUKKIT_PLUGIN, location, currentTask -> {
                    try {
                        task.run();
                    } finally {
                        REGION_TASK_SET.remove(currentTask);
                    }
                });
        registerTask(scheduledTask);
    }

    /**
     * 指定区域延迟安全线程调度
     *
     * @param location 区域坐标
     * @param task     任务
     * @param delay    延迟
     * @since 1.3.3
     */
    public static void runTaskLater(@NotNull Location location, @NotNull Runnable task, long delay) {
        if (!HandySchedulerUtil.isFolia()) {
            BukkitScheduler.runTaskLater(task, delay);
            return;
        }
        delay = getOneIfNotPositive(delay);
        ScheduledTask scheduledTask = Bukkit.getRegionScheduler().runDelayed(
                HandySchedulerUtil.BUKKIT_PLUGIN, location, currentTask -> {
                    try {
                        task.run();
                    } finally {
                        REGION_TASK_SET.remove(currentTask);
                    }
                }, delay);
        registerTask(scheduledTask);
    }

    /**
     * 指定区域延迟安全线程调度 可取消
     *
     * @param location 区域坐标
     * @param task     任务
     * @param delay    延迟
     * @since 1.3.3
     */
    public static void runTaskLater(@NotNull Location location, @NotNull HandyRunnable task, long delay) {
        if (!HandySchedulerUtil.isFolia()) {
            BukkitScheduler.runTaskLater(task, delay);
            return;
        }
        delay = getOneIfNotPositive(delay);
        ScheduledTask scheduledTask = Bukkit.getRegionScheduler().runDelayed(
                HandySchedulerUtil.BUKKIT_PLUGIN, location, currentTask -> {
                    try {
                        task.run();
                    } finally {
                        REGION_TASK_SET.remove(currentTask);
                    }
                }, delay);
        task.setupTask(registerTask(scheduledTask));
    }

    /**
     * 指定区域循环安全线程调度
     *
     * @param location 区域坐标
     * @param task     任务
     * @param delay    延迟
     * @param period   期间
     * @since 1.3.3
     */
    public static void runTaskTimer(@NotNull Location location, @NotNull Runnable task, long delay, long period) {
        if (!HandySchedulerUtil.isFolia()) {
            BukkitScheduler.runTaskTimer(task, delay, period);
            return;
        }
        delay = getOneIfNotPositive(delay);
        period = getOneIfNotPositive(period);
        ScheduledTask scheduledTask = Bukkit.getRegionScheduler().runAtFixedRate(
                HandySchedulerUtil.BUKKIT_PLUGIN, location, currentTask -> {
                    try {
                        task.run();
                    } catch (RuntimeException | Error throwable) {
                        currentTask.cancel();
                        REGION_TASK_SET.remove(currentTask);
                        throw throwable;
                    }
                }, delay, period);
        registerTask(scheduledTask);
    }

    /**
     * 指定区域循环安全线程调度 可取消
     *
     * @param location 区域坐标
     * @param task     任务
     * @param delay    延迟
     * @param period   期间
     * @since 1.3.3
     */
    public static void runTaskTimer(@NotNull Location location, @NotNull HandyRunnable task, long delay, long period) {
        if (!HandySchedulerUtil.isFolia()) {
            BukkitScheduler.runTaskTimer(task, delay, period);
            return;
        }
        delay = getOneIfNotPositive(delay);
        period = getOneIfNotPositive(period);
        ScheduledTask scheduledTask = Bukkit.getRegionScheduler().runAtFixedRate(
                HandySchedulerUtil.BUKKIT_PLUGIN, location, currentTask -> {
                    try {
                        task.run();
                    } catch (RuntimeException | Error throwable) {
                        currentTask.cancel();
                        REGION_TASK_SET.remove(currentTask);
                        throw throwable;
                    }
                }, delay, period);
        task.setupTask(registerTask(scheduledTask));
    }

    /**
     * 取消所有区域任务
     */
    protected static void cancelTask() {
        REGION_TASK_SET.forEach(ScheduledTask::cancel);
        REGION_TASK_SET.clear();
    }

    /**
     * 移除区域任务
     *
     * @param task 区域任务
     */
    protected static void unregisterTask(@NotNull ScheduledTask task) {
        REGION_TASK_SET.remove(task);
    }

    /**
     * 登记区域任务
     *
     * @param task 区域任务
     * @return 区域任务
     */
    private static @NotNull ScheduledTask registerTask(@NotNull ScheduledTask task) {
        REGION_TASK_SET.add(task);
        ScheduledTask.ExecutionState state = task.getExecutionState();
        if (ScheduledTask.ExecutionState.FINISHED.equals(state)
                || ScheduledTask.ExecutionState.CANCELLED.equals(state)
                || ScheduledTask.ExecutionState.CANCELLED_RUNNING.equals(state)) {
            REGION_TASK_SET.remove(task);
        }
        return task;
    }

    /**
     * Folia异常：时间参数不能<=0
     *
     * @param time 时间
     * @return 时间
     */
    private static long getOneIfNotPositive(long time) {
        return time <= 0 ? 1L : time;
    }

}
