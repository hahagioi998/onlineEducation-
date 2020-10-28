package com.hnguigu.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {

    public static final String PARTS_KEY = "logType";

    public LogGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(PARTS_KEY);
    }

    /**
     * console--->打印控制台日志
     * file--->打印文件日志
     *
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        String logType = config.getLogType();// file
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                if ("file".equals(logType)) {
                    System.out.println("文件日志");
                    System.out.println(logType);
                } else if ("console".equals(logType)) {
                    System.out.println("控制台日志");
                }
                return chain.filter(exchange);
            }
        };
    }

    public static class Config {
        private String logType;

        public String getLogType() {
            return logType;
        }

        public void setLogType(String logType) {
            this.logType = logType;
        }
    }
}
