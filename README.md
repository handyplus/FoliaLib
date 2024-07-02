# FoliaLib

> 同时适配Folia和bukkit核心

## 使用方法(本jar已经发布到maven中央仓库)

[![Maven Central](https://img.shields.io/maven-central/v/cn.handyplus.lib.adapter/FoliaLib.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22cn.handyplus.lib.adapter%22%20AND%20a:%22FoliaLib%22)

首先: Folia要求 在plugin.yml 中添加配置 `folia-supported: true`

1. maven引入
   ```xml
    <dependency>
        <groupId>cn.handyplus.lib.adapter</groupId>
        <artifactId>FoliaLib</artifactId>
        <version>最新版本</version>
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

## 使用示例

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
    PlayerTeleportEvent.teleport(player, location);
}

// 执行命令
public void test4(Player player, String command) {
    PlayerTeleportEvent.performCommand(player, command);
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

## javadoc

[点击查看](https://handyplus.github.io/FoliaLib/)

## 建议

可以在[issues](https://github.com/handyplus/FoliaLib/issues)提出

## 贡献者

[![Contrib](https://contrib.rocks/image?repo=handyplus/FoliaLib)](https://github.com/handyplus/FoliaLib/graphs/contributors)

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=handyplus/FoliaLib&type=Date)](https://star-history.com/#handyplus/FoliaLib&Date)
