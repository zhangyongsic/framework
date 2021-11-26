package com.zhangyongsic.framework.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author zhang yong
 */
@Configuration
@ConditionalOnClass(RedisConfigure.class)
@EnableConfigurationProperties(RedisNodesConfigure.class)
@EnableCaching
public class RedisConfigure extends CachingConfigurerSupport {

    @Value("${spring.redis.clustered}")
    private boolean clustered;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.password}")
    private String password;

    /*
    @Value("${spring.redis.timeout}")
    private int timeout;
    */

    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;

    /*
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private int maxWait;
    */

    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;

    @Autowired
    @Qualifier(value = "redisNodes")
    private RedisNodesConfigure redisNodes;


    @Bean(name = "redisConnectionFactory")
    public LettuceConnectionFactory connectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = null;
        RedisPassword redisPassword = RedisPassword.of(password);
        // 连接池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxTotal(maxActive);
        // poolConfig.setMaxWaitMillis(maxWait);
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig).build();
        // 集群配置
        if (clustered) {
            RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
            List<String> nodeList = redisNodes.getNodes();
            for (String node : nodeList) {
                String[] tmp = node.split(":");
                RedisNode redisNode = new RedisNode(tmp[0], Integer.parseInt(tmp[1]));
                clusterConfiguration.addClusterNode(redisNode);
            }
            clusterConfiguration.setPassword(redisPassword);
            lettuceConnectionFactory = new LettuceConnectionFactory(clusterConfiguration, clientConfiguration);
        }
        // 单节点
        else {
            RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration(host, port);
            standaloneConfig.setDatabase(database);
            standaloneConfig.setPassword(redisPassword);
            lettuceConnectionFactory = new LettuceConnectionFactory(standaloneConfig, clientConfiguration);
        }
        return lettuceConnectionFactory;
    }

    /**
     * String - Jdk
     *
     * @param redisConnectionFactory
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean(name = "redisTemplate")
    public RedisTemplate redisTemplate(
            @Qualifier(value = "redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        /*
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        */
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /**
     * String - String
     *
     * @param redisConnectionFactory
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(
            @Qualifier(value = "redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
        return stringRedisTemplate;
    }


    /**
     * String - jackson2Json
     *
     * @param redisConnectionFactory
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean(name = "jacksonRedisTemplate")
    public RedisTemplate jacksonRedisTemplate(
            @Qualifier(value = "redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate jacksonRedisTemplate = new RedisTemplate();
        jacksonRedisTemplate.setKeySerializer(new StringRedisSerializer());
        jacksonRedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        jacksonRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        jacksonRedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        jacksonRedisTemplate.setConnectionFactory(redisConnectionFactory);
        jacksonRedisTemplate.afterPropertiesSet();
        return jacksonRedisTemplate;
    }


    @Bean
    public RedisCacheManager redisCacheManager(@Qualifier(value = "redisTemplate") RedisTemplate redisTemplate) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }


    /*
    @Bean
    public CacheManager cacheManager(@Qualifier(value = "redisConnectionFactory") LettuceConnectionFactory connectionFactory) {
        RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory).build();
        return redisCacheManager;
    }
    */

    /**
     * 自定义缓存key 防止不同的缓存数据key相同造成覆盖
     *
     * @return
     */
    @Bean
    public KeyGenerator customKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

}
