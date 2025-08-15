package cn.handyplus.lib.adapter;

import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
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
     * 在线程安全的实体调度器中执行任务（无返回值）
     *
     * @param entity 实体
     * @param task   要执行的任务
     * @since 1.2.2
     */
    public static void runSafeOnPlayerScheduler(@NotNull LivingEntity entity, @NotNull Runnable task) {
        runSafeOnPlayerScheduler(entity, task, true);
    }

    /**
     * 在线程安全的实体调度器中执行任务（无返回值）
     *
     * @param entity 玩家
     * @param task   要执行的任务
     * @param isSync 是否同步
     * @since 1.2.2
     */
    public static void runSafeOnPlayerScheduler(@NotNull LivingEntity entity, @NotNull Runnable task, boolean isSync) {
        runSafeOnPlayerScheduler(entity, (() -> {
            task.run();
            return null;
        }), isSync);
    }

    /**
     * 在线程安全的实体调度器中执行任务
     *
     * @param entity 玩家
     * @param task   要执行的任务（返回 T）
     * @param isSync 是否同步
     * @param <T>    返回类型
     * @since 1.2.2
     */
    public static <T> void runSafeOnPlayerScheduler(@NotNull LivingEntity entity, @NotNull Supplier<T> task, boolean isSync) {
        runSafeOnPlayerScheduler(entity, task, null, isSync);
    }

    /**
     * 在线程安全的实体调度器中执行任务
     *
     * @param entity  实体
     * @param task    要执行的任务（返回 T）
     * @param success 成功回调（接收 T）
     * @param isSync  是否同步
     * @param <T>     返回类型
     * @since 1.2.2
     */
    public static <T> void runSafeOnPlayerScheduler(@NotNull LivingEntity entity, @NotNull Supplier<T> task, @Nullable Consumer<T> success, boolean isSync) {
        Runnable runner = () -> {
            T result = task.get();
            if (success != null) {
                success.accept(result);
            }
        };
        if (HandySchedulerUtil.isFolia()) {
            entity.getScheduler().run(HandySchedulerUtil.BUKKIT_PLUGIN, a -> runner.run(), () -> {
            });
        } else if (isSync) {
            BukkitScheduler.runTask(runner);
        } else {
            runner.run();
        }
    }

}