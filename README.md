### FoliaLib

> 同时适配Folia和bukkit核心

### 使用方法(本jar已经发布到maven中央仓库)

1. maven引入
   ```xml
    <dependency>
        <groupId>cn.handyplus.lib.adapter</groupId>
        <artifactId>FoliaLib</artifactId>
        <version>1.0.5</version>
    </dependency>
   ```

2. 初始化;
   ```java
   public class MyPlugin extends JavaPlugin {
       @Override
       public void onEnable() {
           // 初始化
           HandySchedulerUtil.init(this);
       }
   }
   ```

3. 部分使用示例
   ```java
   // 同步方法
   public void test1(Player player) {
      HandySchedulerUtil.runTask(player::closeInventory);
   }
   
   // 异步方法
   public void test2() {
      HandySchedulerUtil.runTaskAsynchronously(() -> {
         // 执行方法
      });
   }
   
   // TP方法
   public void test3(Player player, Location location) {
      HandySchedulerUtil.teleport(player, location);
   }
   
   // 执行命令
   public void test4(Player player, String command) {
       HandySchedulerUtil.performCommand(player, command);
   }
   
   // 定时方法
   public void test5() {
      HandyRunnable handyRunnable = new HandyRunnable() {
         @Override
         public void run() {
            try {
               // 执行逻辑
            } catch (Exception ignored) {
               this.cancel();
            }
         }
      };
      HandySchedulerUtil.runTaskTimerAsynchronously(handyRunnable, 20 * 2, 20 * 60);
   }
   ```
4. 玩家Tp等相关方法 请使用PlayerSchedulerUtil

5. [javadoc](https://handy-git.github.io/FoliaLib/cn/handyplus/lib/adapter/HandySchedulerUtil.html)
