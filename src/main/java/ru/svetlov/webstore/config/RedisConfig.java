package ru.svetlov.webstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import ru.svetlov.webstore.util.cart.Cart;
import ru.svetlov.webstore.util.cart.impl.CartImpl;

import java.time.Duration;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
    private final JedisPoolConfig poolConfig = buildPoolConfig();

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory(poolConfig);
    }

    @Bean
    public RedisTemplate<String, Cart> redisCartTemplate() {
        RedisTemplate<String, Cart> template = new RedisTemplate<>();
        template.setHashKeySerializer(StringRedisSerializer.US_ASCII);
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(CartImpl.class));
        template.setKeySerializer(StringRedisSerializer.US_ASCII);
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(CartImpl.class));
        template.setConnectionFactory(redisConnectionFactory());
        template.afterPropertiesSet();
        return template;
    }

    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(128);
        config.setMaxIdle(128);
        config.setMinIdle(128);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        config.setTestWhileIdle(true);
        config.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        config.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        config.setNumTestsPerEvictionRun(3);
        config.setBlockWhenExhausted(true);
        return config;
    }
}
