package com.enovell.yunwei.home.home;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 类描述:  webSocket配置类
 * 创建人：Bili
 * 创建时间：2017/11/23 14:14
 * 修改人：Bili
 * 修改时间：2017/11/23 14:14
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
