package com.ace.config;

import com.ace.entities.Logs;
import com.ace.service.LogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义全局过虑器
 *
 * @auther Garlam Au
 * @create 2023-12-31 21:05
 */
@Component
@Slf4j
public class AceGlobalFilter implements GlobalFilter, Ordered {
    public static final String BEGIN_VISIT_TIME = "begin_visit_time";//开始调用方法的时间
    private LogsService logsService;

    @Autowired
    public AceGlobalFilter(LogsService logsService) {
        this.logsService = logsService;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1 先记录下访问接口的开始时间
        exchange.getAttributes().put(BEGIN_VISIT_TIME, System.currentTimeMillis());
        //2 返回统计的各个结果给后台
        Logs logs = new Logs();
        StringBuilder description = new StringBuilder();
        description.append("host: " + exchange.getRequest().getURI().getHost());
        description.append("port: " + exchange.getRequest().getURI().getPort());
        description.append("url: " + exchange.getRequest().getURI().getPath());
        description.append("param: " + exchange.getRequest().getURI().getRawQuery());
        logs.setDescription(description.toString());
        logsService.save(logs);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long beginVisitTime = exchange.getAttribute(BEGIN_VISIT_TIME);
            if (beginVisitTime != null) {
                log.info("访问接口主机：" + exchange.getRequest().getURI().getHost());
                log.info("访问接口端口：" + exchange.getRequest().getURI().getPort());
                log.info("访问接口URL：" + exchange.getRequest().getURI().getPath());
                log.info("访问接口URL后面参数：" + exchange.getRequest().getURI().getRawQuery());
                log.info("访问接口时长：" + (System.currentTimeMillis() - beginVisitTime) + "毫秒");
                System.out.println();
            }
        }));
    }

    /**
     * 数字越小，优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
