package cn.handyplus.lib.adapter;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 实体相关调度器
 *
 * @author handy
 * @since 1.2.2
 */
public class EntitySchedulerUtil {

    /**
     * 构造器
     */
    private EntitySchedulerUtil() {
    }

    /**
     * 传送实体
     *
     * @param entity 需要传送的实体
     * @param target 目的地
     * @return Bukkit 下返回传送结果，Folia 下返回传送任务是否已提交
     * @since 1.3.0
     */
    public static boolean teleport(@NotNull Entity entity, @NotNull Location target) {
        return teleport(entity, target, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    /**
     * 传送实体
     *
     * @param entity 需要传送的实体
     * @param target 传送目的地
     * @param cause  传送原因
     * @return Bukkit 下返回传送结果，Folia 下返回传送任务是否已提交
     * @since 1.3.0
     */
    public static boolean teleport(@NotNull Entity entity, @NotNull Location target, @NotNull PlayerTeleportEvent.TeleportCause cause) {
        if (HandySchedulerUtil.isFolia()) {
            entity.teleportAsync(target, cause);
            return true;
        }
        return entity.teleport(target, cause);
    }

    /**
     * 调度传送实体
     *
     * @param entity 需要传送的实体
     * @param target 传送目的地
     * @since 1.3.0
     */
    public static void syncTeleport(@NotNull Entity entity, @NotNull Location target) {
        syncTeleport(entity, target, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    /**
     * 调度传送实体
     *
     * @param entity 需要传送的实体
     * @param target 传送目的地
     * @param cause  传送原因
     * @since 1.3.0
     */
    public static void syncTeleport(@NotNull Entity entity, @NotNull Location target, @NotNull PlayerTeleportEvent.TeleportCause cause) {
        if (HandySchedulerUtil.isFolia()) {
            entity.teleportAsync(target, cause);
            return;
        }
        runEntityTask(entity, () -> entity.teleport(target, cause));
    }

    /**
     * 调度添加药水效果
     *
     * @param entity           实体
     * @param potionEffectList 药水效果
     * @since 1.3.0
     */
    public static void addPotionEffects(@NotNull LivingEntity entity, @NotNull List<PotionEffect> potionEffectList) {
        if (potionEffectList.isEmpty()) {
            return;
        }
        runEntityTask(entity, () -> entity.addPotionEffects(potionEffectList));
    }

    /**
     * 调度添加药水效果
     *
     * @param entity       实体
     * @param potionEffect 药水效果
     * @since 1.3.0
     */
    public static void addPotionEffects(@NotNull LivingEntity entity, @NotNull PotionEffect potionEffect) {
        addPotionEffects(entity, Collections.singletonList(potionEffect));
    }

    /**
     * 调度移除药水效果
     *
     * @param entity       实体
     * @param potionEffect 药水效果
     * @since 1.3.0
     */
    public static void removePotionEffect(@NotNull LivingEntity entity, @NotNull PotionEffectType potionEffect) {
        runEntityTask(entity, () -> entity.removePotionEffect(potionEffect));
    }

    /**
     * 在线程安全的实体调度器中执行任务（无返回值）
     *
     * @param entity 实体
     * @param task   要执行的任务
     * @since 1.3.0
     */
    public static void runSafeOnEntityScheduler(@NotNull LivingEntity entity, @NotNull Runnable task) {
        runSafeOnEntityScheduler(entity, task, true);
    }

    /**
     * 在线程安全的实体调度器中执行任务（无返回值）
     *
     * @param entity 实体
     * @param task   要执行的任务
     * @param isSync Bukkit 下是否使用主线程调度
     * @since 1.3.0
     */
    public static void runSafeOnEntityScheduler(@NotNull LivingEntity entity, @NotNull Runnable task, boolean isSync) {
        runSafeOnEntityScheduler(entity, (() -> {
            task.run();
            return null;
        }), isSync);
    }

    /**
     * 在线程安全的实体调度器中执行任务
     *
     * @param entity 实体
     * @param task   要执行的任务（返回 T）
     * @param isSync Bukkit 下是否使用主线程调度
     * @param <T>    返回类型
     * @since 1.3.0
     */
    public static <T> void runSafeOnEntityScheduler(@NotNull LivingEntity entity, @NotNull Supplier<T> task, boolean isSync) {
        runSafeOnEntityScheduler(entity, task, null, isSync);
    }

    /**
     * 在线程安全的实体调度器中执行任务
     *
     * @param entity  实体
     * @param task    要执行的任务（返回 T）
     * @param success 成功回调（接收 T）
     * @param isSync  Bukkit 下是否使用主线程调度
     * @param <T>     返回类型
     * @since 1.3.0
     */
    public static <T> void runSafeOnEntityScheduler(@NotNull LivingEntity entity, @NotNull Supplier<T> task, @Nullable Consumer<T> success, boolean isSync) {
        Runnable runner = () -> {
            T result = task.get();
            if (success != null) {
                success.accept(result);
            }
        };
        runEntityTask(entity, runner, isSync);
    }

    /**
     * 调度到实体安全线程执行
     *
     * @param entity 实体
     * @param task   任务
     * @since 1.3.0
     */
    private static void runEntityTask(@NotNull Entity entity, @NotNull Runnable task) {
        runEntityTask(entity, task, true);
    }

    /**
     * 调度到实体安全线程执行
     *
     * @param entity 实体
     * @param task   任务
     * @param isSync Bukkit 下是否切回主线程执行
     * @since 1.3.0
     */
    private static void runEntityTask(@NotNull Entity entity, @NotNull Runnable task, boolean isSync) {
        if (HandySchedulerUtil.isFolia()) {
            entity.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, scheduledTask -> task.run(), () -> {
            });
        } else if (isSync) {
            BukkitScheduler.runTask(task);
        } else {
            task.run();
        }
    }

}
