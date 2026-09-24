package cn.handyplus.lib.adapter;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
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
     * Folia 实体周期任务
     */
    private static final Set<ScheduledTask> ENTITY_TASK_SET = ConcurrentHashMap.newKeySet();

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
     * 在实体安全线程周期执行可取消任务
     *
     * @param entity 实体
     * @param task   可通过 cancel() 取消的任务
     * @param delay  首次执行延迟，单位为 tick
     * @param period 执行间隔，单位为 tick
     * @since 1.3.3
     */
    public static void runTaskTimer(@NotNull Entity entity, @NotNull HandyRunnable task, long delay, long period) {
        if (!HandySchedulerUtil.isFolia()) {
            BukkitScheduler.runTaskTimer(task, delay, period);
            return;
        }
        ScheduledTask scheduledTask = runEntityTaskTimer(entity, task, delay, period);
        if (scheduledTask != null) {
            task.setupTask(scheduledTask);
        }
    }

    /**
     * 取消所有实体周期任务
     */
    protected static void cancelTask() {
        ENTITY_TASK_SET.forEach(ScheduledTask::cancel);
        ENTITY_TASK_SET.clear();
    }

    /**
     * 移除实体周期任务
     *
     * @param task 实体任务
     */
    protected static void unregisterTask(@NotNull ScheduledTask task) {
        ENTITY_TASK_SET.remove(task);
    }

    /**
     * 提交实体周期任务
     *
     * @param entity 实体
     * @param task   任务
     * @param delay  首次执行延迟
     * @param period 执行间隔
     * @return 实体已移除时返回 null
     */
    private static @Nullable ScheduledTask runEntityTaskTimer(@NotNull Entity entity, @NotNull Runnable task, long delay, long period) {
        AtomicReference<ScheduledTask> taskReference = new AtomicReference<>();
        ScheduledTask scheduledTask = entity.getScheduler().runAtFixedRate(
                HandySchedulerUtil.BUKKIT_PLUGIN, currentTask -> {
                    try {
                        task.run();
                    } catch (RuntimeException | Error throwable) {
                        currentTask.cancel();
                        unregisterTask(currentTask);
                        throw throwable;
                    }
                }, () -> {
                    ScheduledTask retiredTask = taskReference.get();
                    if (retiredTask != null) {
                        unregisterTask(retiredTask);
                    }
                }, getOneIfNotPositive(delay), getOneIfNotPositive(period));
        if (scheduledTask != null) {
            taskReference.set(scheduledTask);
            ENTITY_TASK_SET.add(scheduledTask);
            ScheduledTask.ExecutionState state = scheduledTask.getExecutionState();
            if (ScheduledTask.ExecutionState.FINISHED.equals(state) || ScheduledTask.ExecutionState.CANCELLED.equals(state) || ScheduledTask.ExecutionState.CANCELLED_RUNNING.equals(state)) {
                unregisterTask(scheduledTask);
            }
        }
        return scheduledTask;
    }

    /**
     * Folia 的时间参数至少为 1 tick
     *
     * @param time 时间
     * @return 合法的时间参数
     */
    private static long getOneIfNotPositive(long time) {
        return time <= 0 ? 1L : time;
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
            return;
        }
        if (isSync && !Bukkit.isPrimaryThread()) {
            BukkitScheduler.runTask(task);
            return;
        }
        task.run();
    }

}
