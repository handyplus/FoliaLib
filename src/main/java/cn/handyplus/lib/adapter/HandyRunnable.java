package cn.handyplus.lib.adapter;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

/**
 * @author handy
 */
public abstract class HandyRunnable implements Runnable {
    private BukkitTask bukkitTask;
    private ScheduledTask scheduledTask;

    public HandyRunnable() {
    }

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

    private void checkScheduled() {
        if (ServerTypeEnum.FOLIA.equals(HandySchedulerUtil.SERVER_TYPE) && this.scheduledTask == null) {
            throw new IllegalStateException("Not scheduled yet");
        }
        if (ServerTypeEnum.BUKKIT.equals(HandySchedulerUtil.SERVER_TYPE) && this.bukkitTask == null) {
            throw new IllegalStateException("Not scheduled yet");
        }
    }

    public @NotNull BukkitTask setupTask(@NotNull BukkitTask task) {
        this.bukkitTask = task;
        return task;
    }

    public @NotNull ScheduledTask setupTask(@NotNull ScheduledTask task) {
        this.scheduledTask = task;
        return task;
    }
}
