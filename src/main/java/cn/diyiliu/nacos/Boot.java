package cn.diyiliu.nacos;

import cn.diyiliu.nacos.config.NacosConfig;

/**
 * Boot
 *
 * @author diyiliu
 * @create 2022-06-20 18:55
 **/
public class Boot {

    public static void main(String[] args) {

        SpringUtil.init(NacosConfig.class);
    }
}
