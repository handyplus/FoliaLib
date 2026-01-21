package cn.handyplus.lib.adapter;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * 总任务调度器
 *
 * @author handy
 * @since 1.0.0
 */
public class HandySchedulerUtil {

    /**
     * 构造器
     */
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
    public static void init(@NotNull Plugin plugin) {
        BUKKIT_PLUGIN = plugin;
        SERVER_TYPE = ServerTypeEnum.getServerType();
    }

    /**
     * 同步
     *
     * @param task 方法
     */
    public static void runTask(@NotNull Runnable task) {
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
    public static void runTaskLater(@NotNull Runnable task, long delay) {
        if (isFolia()) {
            FoliaScheduler.runTaskLater(task, delay);
            return;
        }
        BukkitScheduler.runTaskLater(task, delay);
    }

    /**
     * 延迟同步 可取消
     *
     * @param task  方法
     * @param delay 延迟
     * @since 1.0.5
     */
    public static void runTaskLater(@NotNull HandyRunnable task, long delay) {
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
    public static void runTaskTimer(@NotNull Runnable task, long delay, long period) {
        if (isFolia()) {
            FoliaScheduler.runTaskTimer(task, delay, period);
            return;
        }
        BukkitScheduler.runTaskTimer(task, delay, period);
    }

    /**
     * 循环同步 可取消
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    public static void runTaskTimer(@NotNull HandyRunnable task, long delay, long period) {
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
    public static void runTaskAsynchronously(@NotNull Runnable task) {
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
    public static void runTaskLaterAsynchronously(@NotNull Runnable task, long delay) {
        if (isFolia()) {
            FoliaScheduler.runTaskLaterAsynchronously(task, delay);
            return;
        }
        BukkitScheduler.runTaskLaterAsynchronously(task, delay);
    }

    /**
     * 延迟异步 可取消
     *
     * @param task  方法
     * @param delay 延迟
     * @since 1.0.5
     */
    public static void runTaskLaterAsynchronously(@NotNull HandyRunnable task, long delay) {
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
    public static void runTaskTimerAsynchronously(@NotNull Runnable task, long delay, long period) {
        if (isFolia()) {
            FoliaScheduler.runTaskTimerAsynchronously(task, delay, period);
            return;
        }
        BukkitScheduler.runTaskTimerAsynchronously(task, delay, period);
    }

    /**
     * 循环异步 可取消
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    public static void runTaskTimerAsynchronously(@NotNull HandyRunnable task, long delay, long period) {
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
     * 是否 Folia
     *
     * @return true是
     */
    public static boolean isFolia() {
        return ServerTypeEnum.FOLIA.equals(SERVER_TYPE);
    }

    /**
     * 是否 Paper
     *
     * @return true是
     * @since 1.2.7
     */
    public static boolean isPaper() {
        return ServerTypeEnum.PAPER.equals(SERVER_TYPE);
    }

}