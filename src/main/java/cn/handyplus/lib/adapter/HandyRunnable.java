package cn.handyplus.lib.adapter;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

/**
 * 抽象Runnable
 *
 * @author handy
 */
public abstract class HandyRunnable implements Runnable {
    private BukkitTask bukkitTask;
    private ScheduledTask scheduledTask;

    /**
     * 构造
     */
    public HandyRunnable() {
    }

    /**
     * 取消
     *
     * @throws IllegalStateException IllegalStateException
     */
    public synchronized void cancel() throws IllegalStateException {
        // 检查任务
        this.checkScheduled();
        // 取消
        if (ServerTypeEnum.BUKKIT.equals(HandySchedulerUtil.SERVER_TYPE)) {
            Bukkit.getScheduler().cancelTask(this.bukkitTask.getTaskId());
            return;
        }
        scheduledTask.cancel();
    }

    /**
     * 检查是否存在任务
     */
    private void checkScheduled() {
        if (ServerTypeEnum.FOLIA.equals(HandySchedulerUtil.SERVER_TYPE) && this.scheduledTask == null) {
            throw new IllegalStateException("Not scheduled yet");
        }
        if (ServerTypeEnum.BUKKIT.equals(HandySchedulerUtil.SERVER_TYPE) && this.bukkitTask == null) {
            throw new IllegalStateException("Not scheduled yet");
        }
    }

    /**
     * 设置任务
     *
     * @param task 任务
     * @return BukkitTask
     */
    public @NotNull BukkitTask setupTask(@NotNull BukkitTask task) {
        this.bukkitTask = task;
        return task;
    }

    /**
     * 设置任务
     *
     * @param task 任务
     * @return ScheduledTask
     */
    public @NotNull ScheduledTask setupTask(@NotNull ScheduledTask task) {
        this.scheduledTask = task;
        return task;
    }

}