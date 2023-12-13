package cn.handyplus.lib.adapter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
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
     * 传送实体
     *
     * @param entity 需要传送的实体
     * @param target 传送目的地
     * @param cause  传送原因
     * @return 传送结果
     */
    protected static boolean teleport(Entity entity, Location target, PlayerTeleportEvent.TeleportCause cause) {
        return entity.teleportAsync(target, cause).isDone();
    }

    /**
     * 玩家添加药水效果
     *
     * @param player           玩家
     * @param potionEffectList 药水效果
     */
    protected static void addPotionEffects(Player player, List<PotionEffect> potionEffectList) {
        player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> player.addPotionEffects(potionEffectList), () -> {
        });
    }

    /**
     * 玩家移除药水效果
     *
     * @param player       玩家
     * @param potionEffect 药水效果
     */
    protected static void removePotionEffect(Player player, PotionEffectType potionEffect) {
        player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> player.removePotionEffect(potionEffect), () -> {
        });
    }

    /**
     * 玩家执行命令
     *
     * @param player  玩家
     * @param command 命令
     */
    protected static void performCommand(Player player, String command) {
        player.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> player.chat("/" + command.trim()), () -> {
        });
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
     * @param task  方法
     * @param delay 延迟
     */
    protected static void runTaskLater(Runnable task, long delay) {
        Bukkit.getGlobalRegionScheduler().runDelayed(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay);
    }

    /**
     * 循环同步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    protected static void runTaskTimer(Runnable task, long delay, long period) {
        Bukkit.getGlobalRegionScheduler().runAtFixedRate(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay, period);
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
        Bukkit.getAsyncScheduler().runDelayed(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay * 50, TimeUnit.MILLISECONDS);
    }

    /**
     * 循环异步
     *
     * @param task   方法
     * @param delay  延迟
     * @param period 期间
     */
    protected static void runTaskTimerAsynchronously(Runnable task, long delay, long period) {
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
        task.setupTask(Bukkit.getAsyncScheduler().runAtFixedRate(HandySchedulerUtil.BUKKIT_PLUGIN, a -> task.run(), delay * 50, period * 50, TimeUnit.MILLISECONDS));
    }

    /**
     * 取消所有调度任务
     */
    protected static void cancelTask() {
        Bukkit.getGlobalRegionScheduler().cancelTasks(HandySchedulerUtil.BUKKIT_PLUGIN);
        Bukkit.getAsyncScheduler().cancelTasks(HandySchedulerUtil.BUKKIT_PLUGIN);
    }

}