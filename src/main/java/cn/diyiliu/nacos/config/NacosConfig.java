package cn.diyiliu.nacos.config;

import cn.diyiliu.nacos.SpringUtil;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * NacosConfig
 *
 * @author diyiliu
 * @create 2022-06-21 8:42
 **/

@Configuration
@ComponentScan("cn.diyiliu.nacos")
@PropertySource(value = "classpath:conf.properties")
@EnableNacosConfig(globalProperties =
@NacosProperties(serverAddr = "${serverAddr}", namespace = "${namespace}", username = "${username}", password = "${password}"))
@NacosPropertySource(dataId = "${data-id}", autoRefreshed = true)
public class NacosConfig {


    @NacosValue(value = "${interval}", autoRefreshed = true)
    private int interval;

    @Value("${serverAddr}")
    private String serverAddr;

    @Value("${data-id}")
    private String dataId;

    @NacosInjected
    private ConfigService configService;


    @Bean
    public SpringUtil springUtil() {

        return new SpringUtil();
    }


    @PostConstruct
    public void init() throws Exception {
        System.out.println(interval);
        System.out.println(serverAddr);
        System.out.println(dataId);

        String group = "DEFAULT_GROUP";

        String config = configService.getConfig(dataId, group, Duration.ofSeconds(30).toMillis());
        System.out.println(config);

        new Thread(()  -> {
            try {
                while (true) {
                    System.out.println(interval);
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @NacosConfigListener(dataId = "${data-id}")
    public void onMessage(String config) {
        System.out.println(config);
    }
}
