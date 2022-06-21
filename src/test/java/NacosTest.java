import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import org.junit.Test;

import java.time.Duration;
import java.util.Properties;

/**
 * NacosTest
 *
 * @author diyiliu
 * @create 2022-06-20 17:41
 **/
public class NacosTest {
    String server = "192.168.2.100";
    String namespace = "923b0691-519a-4761-aff7-b26722dd2fdd";
    String username = "nacos";
    String password = "ksjq@zks";


    ConfigService configService;

    @Test
    public void test() throws Exception {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, server);
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        properties.put(PropertyKeyConst.USERNAME, username);
        properties.put(PropertyKeyConst.PASSWORD, password);

        configService = NacosFactory.createConfigService(properties);

        String dataId = "kafka-config.yml";
        String group = "MQ_GROUP";

        String config = configService.getConfig(dataId, group, Duration.ofSeconds(30).toMillis());
        System.out.println(config);
    }
}
