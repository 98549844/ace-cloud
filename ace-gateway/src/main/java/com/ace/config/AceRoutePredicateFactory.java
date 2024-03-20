package com.ace.config;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.AfterRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * @auther zzyy
 * @create 2023-12-31 11:11
 * 需求说明：自定义配置适配是否可以访问
 */
@Component
public class AceRoutePredicateFactory extends AbstractRoutePredicateFactory<AceRoutePredicateFactory.Config> {

    public AceRoutePredicateFactory() {
        super(AceRoutePredicateFactory.Config.class);
    }

    //这个Config类就是我们的路由断言规则，重要
    @Validated
    public static class Config {
        @Setter
        @Getter
        @NotEmpty
        private String aceVersion; //钻/金/银和yml配置的会员等级
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("aceVersion");
    }

    @Override
    public Predicate<ServerWebExchange> apply(AceRoutePredicateFactory.Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                //检查request的参数里面，aceVersion是否为指定的值，符合配置就通过
                //http://localhost:9527/pay/gateway/get/1?aceVersion=diamond
                String aceVersion = serverWebExchange.getRequest().getQueryParams().getFirst("aceVersion");
                if (aceVersion == null) {
                    return false;
                }
                //如果说参数存在，就和config的数据进行比较
                if (aceVersion.equalsIgnoreCase(config.getAceVersion())) {
                    return true;
                }
                return false;
            }
        };
    }
}
