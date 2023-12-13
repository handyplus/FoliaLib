package cn.handyplus.lib.adapter;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
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

    /**
     * 插件
     */
    protected static Plugin BUKKIT_PLUGIN;

    /**
     * 服务器类型
     */
    private static ServerTypeEnum SERVER_TYPE;

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
     * 传送实体
     *
     * @param entity 需要传送的实体
     * @param target 目的地
     * @return 传送结果
     */
    public static boolean teleport(Entity entity, Location target) {
        return teleport(entity, target, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    /**
     * 传送实体
     *
     * @param entity 需要传送的实体
     * @param target 传送目的地
     * @param cause  传送原因
     * @return 传送结果
     */
    public static boolean teleport(Entity entity, Location target, PlayerTeleportEvent.TeleportCause cause) {
        if (isFolia()) {
            return FoliaScheduler.teleport(entity, target, cause);
        }
        return BukkitScheduler.teleport(entity, target, cause);
    }

    /**
     * 玩家执行命令
     *
     * @param player  玩家
     * @param command 命令
     */
    public static void performCommand(Player player, String command) {
        if (isFolia()) {
            FoliaScheduler.performCommand(player, command);
            return;
        }
        BukkitScheduler.performCommand(player, command);
    }

    /**
     * 同步
     *
     * @param task 方法
     */
    public static void runTask(Runnable task) {
        if (isFolia()) {
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
        if (isFolia()) {
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
        if (isFolia()) {
            FoliaScheduler.runTaskTimer(task, delay, period);
            return;
        }
        BukkitScheduler.runTaskTimer(task, delay, period);
    }

    /**
     * 循环同步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    public static void runTaskTimer(HandyRunnable task, long delay, long period) {
        if (isFolia()) {
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
        if (isFolia()) {
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
        if (isFolia()) {
            FoliaScheduler.runTaskLaterAsynchronously(task, delay);
            return;
        }
        BukkitScheduler.runTaskLaterAsynchronously(task, delay);
    }

    /**
     * 循环异步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    public static void runTaskTimerAsynchronously(Runnable task, long delay, long period) {
        if (isFolia()) {
            FoliaScheduler.runTaskTimerAsynchronously(task, delay, period);
            return;
        }
        BukkitScheduler.runTaskTimerAsynchronously(task, delay, period);
    }

    /**
     * 循环异步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    public static void runTaskTimerAsynchronously(HandyRunnable task, long delay, long period) {
        if (isFolia()) {
            FoliaScheduler.runTaskTimerAsynchronously(task, delay, period);
            return;
        }
        BukkitScheduler.runTaskTimerAsynchronously(task, delay, period);
    }

    /**
     * 取消所有调度任务
     */
    public static void cancelTask() {
        if (isFolia()) {
            FoliaScheduler.cancelTask();
        } else {
            BukkitScheduler.cancelTask();
        }
    }

    /**
     * 是否Folia
     *
     * @return true是
     */
    public static boolean isFolia() {
        return ServerTypeEnum.FOLIA.equals(SERVER_TYPE);
    }

}