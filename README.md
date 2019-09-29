# DataPolice

#### 介绍
DataPolice : 基于Spring的轻量级数据权限验证框架

#### 作者
李鑫_Laysonx (https://laysonx.com)

#### 使用说明

> Version:1.0.0

```
/** 第一步：使用 @EnableDataPolice注解 开启 **/
@EnableDataPolice

/** 第二步：在相关HTTP接口上使用 @EnableDataVerify 注解，配置验证入口以及验证的数据对象 **/
@GetMapping(value = "{materialId}")
@EnableDataVerify(value = {Material.class})
public Material getDetail(@PathVariable Integer materialId){
    // 业务逻辑
}

/** 第三步： 自定义Handler使用 @DataPoliceHelper注解 + 实现VerifyHandler接口，配置自定义数据验证规则 **/
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





