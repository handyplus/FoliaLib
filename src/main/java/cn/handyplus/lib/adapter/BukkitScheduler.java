package cn.handyplus.lib.adapter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Bukkit内部调度器
 *
 * @author handy
 * @since 1.0.0
 */
public class BukkitScheduler {

    private BukkitScheduler() {

    }

    /**
     * 传送实体
     *
     * @param entity 需要传送的实体
     * @param target 传送目的地
     * @param cause  传送原因
     * @return 传送结果
     */
    protected static boolean teleport(Entity entity, Location target, PlayerTeleportEvent.TeleportCause cause) {
        return entity.teleport(target, cause);
    }

    /**
     * 玩家执行命令
     *
     * @param player  玩家
     * @param command 命令
     */
    protected static void performCommand(Player player, String command) {
        runTask(() -> player.chat("/" + command.trim()));
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