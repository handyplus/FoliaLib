### FoliaLib

> 同时适配Folia和bukkit核心

### 使用方法(本jar已经发布到maven中央仓库)

1. maven引入
   ```xml
    <dependency>
        <groupId>cn.handyplus.lib.adapter</groupId>
        <artifactId>FoliaLib</artifactId>
        <version>1.0.0</version>
    </dependency>
   ```

2. 注入 HandySchedulerUtil.init();
```java
public class MyPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        初始化
        HandySchedulerUtil.init(this);
    }
}
```

3. HandySchedulerUtil.runTask();

### [javadoc](https://handy-git.github.io/FoliaLib/cn/handyplus/lib/adapter/HandySchedulerUtil.html)
