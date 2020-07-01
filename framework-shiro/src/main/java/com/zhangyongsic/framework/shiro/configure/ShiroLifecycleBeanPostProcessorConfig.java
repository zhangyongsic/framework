package com.zhangyongsic.framework.shiro.configure;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.core.Ordered;


public class ShiroLifecycleBeanPostProcessorConfig {

    /**
     * Shiro生命周期处理器
     *
     * @return
     */
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor(Ordered.LOWEST_PRECEDENCE);
    }
}
