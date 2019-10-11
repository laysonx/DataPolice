# DataPolice

#### 介绍
DataPolice : 基于Spring的轻量级数据权限验证框架，version1.0.0版本优先支持SpringBoot项目

#### 作者
李鑫_Laysonx (laysonx.com)

#### 使用说明
框架使用Demo 参考：https://gitee.com/layson/DataPoliceDemo

##### Version:1.0.0

> 第一步：使用 @EnableDataPolice注解 开启数据验证
1. scanHelperPackages 值为 `@DataPoliceHelper`标识的 VerifyHandler、DataHandler 所在包路径(建议handler放置在一起)
2. pointcut 值与 AspectJ 中的 `@Pointcut()` 方式相同

```java
@SpringBootApplication
/** 开启数据验证 */
@EnableDataPolice(scanHelperPackages = "com.laysonx.demo.handler", pointcut = "execution(* com..*.api..*.*(..)) || execution(* com..*.service..*.*(..))  ")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```


> 第二步：在相关HTTP接口上使用 @EnableDataVerify 注解，配置验证入口以及验证的数据对象 
1. value 值为要验证的对象
2. errorMessage 值为数据检查不匹配时抛出异常的具体信息(可不填写)

```java
@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("demo")
    @EnableDataVerify(value = {Demo.class}, errorMessage = "无权查看")
    public Demo getById() {
        return demoService.getById();
    }

}
```


> 第三步： 自定义Handler使用 @DataPoliceHelper注解 + 实现VerifyHandler接口，配置自定义数据验证规则
1. @DataPoliceHelper 用来支持框架自动装配filter标识
2. getTargetClass() 返回 验证的对象 
3. verify() 自定义验证对象的验证规则
```java
@DataPoliceHelper
public class MyVerifyHandler implements VerifyHandler {

    @Override
    public Class<?> getTargetClass() {
        return Material.class;
    }
    
    @Override
    public <T> boolean verify(T t) {

        if(t instanceof Material){
            Material m = (Material) t;
            if(m.getId() > 100){
                return true;
            }
        }

        return false;
    }
}
```





