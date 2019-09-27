# DataPolice

#### 介绍
DataPolice : 基于Spring的轻量级数据权限验证框架

#### 作者
李鑫_Laysonx (https://laysonx.com)

#### 使用说明

> Version:1.0.0

```
/** 第一步：配置验证入口扫描器 **/
@EnableDataPolice

/** 第二步：配置验证入口以及验证的数据对象，添加在相关HTTP接口上 **/
@GetMapping(value = "{materialId}")
@EnableDataVerify(value = {Material.class})
public Material getDetail(@PathVariable Integer materialId){
    // 业务逻辑
}

/** 第三步：配置自定义数据验证规则,自定义Handler实现VerifyHandler接口 **/
public class MyVerifyHandler implements VerifyHandler {
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

/** 第四步：注册验证内容配置到容器，提供两种方式 **/
  // 第一种
  @Bean
  public DataVerifyInfoConfigurer dataVerifyInfoConfigurer(){

    Map<Class<?>, VerifyHandler> verifyHelper = new HashMap<>();
    verifyHelper.put(Material.class, new MyVerifyHandler());

    DataVerifyInfoScanner dataVerifyInfoScanner = new DataVerifyInfoScanner();
    dataVerifyInfoScanner.setVerifyHelper(verifyHelper);

    return new DataVerifyInfoConfigurer(dataVerifyInfoScanner);

  }

  // 第二种
  @Bean
  public DataVerifyInfoConfigurer infoConfigurer(){
    Map<Class<?>, VerifyHandler> verifyHelper = new HashMap<>();
    verifyHelper.put(Material.class, new MyVerifyHandler());

    DataVerifyInfoConfigurerFactory factory = new DataVerifyInfoConfigurerFactory();
    factory.setVerifyHelper(verifyHelper);

    return (DataVerifyInfoConfigurer) factory.getObject();
  }
```



