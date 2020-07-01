package com.zhangyongsic.framework.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 集群节点配置
 *
 * @author fanchao
 */
@Configuration(value = "redisNodes")
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisNodesConfigure {

	private List<String> nodes = new ArrayList<>();
	private int maxRedirects;

	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

	public int getMaxRedirects() {
		return maxRedirects;
	}

	public void setMaxRedirects(int maxRedirects) {
		this.maxRedirects = maxRedirects;
	}
}
