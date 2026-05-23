package e_commerce_backend.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.time.Duration;

@Configuration
@EnableCaching
public class  RedisConfig {
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.host}")
    private String host;

    // configuration redis db(0)
    @Primary
    @Bean
    public LettuceConnectionFactory authConnectionRedis() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        configuration.setDatabase(0);
        return new LettuceConnectionFactory(configuration);
    }
    @Bean(name = "authRedisTemplate")
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(authConnectionRedis());
        return stringRedisTemplate;
    }
    // configuration redis db(1)
    @Bean
    public LettuceConnectionFactory productConnectionRedis() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        configuration.setDatabase(1);
        return new LettuceConnectionFactory(configuration);
    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(productConnectionRedis());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
    // configuration redis db(2)
    @Bean
    public LettuceConnectionFactory cacheConnectionRedis() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        configuration.setDatabase(2);
        return new LettuceConnectionFactory(configuration);
    }
    // save api to db2
    @Bean
    public RedisCacheManager redisCacheManager(LettuceConnectionFactory cacheConnectionRedis) {
        return RedisCacheManager.builder(cacheConnectionRedis).cacheDefaults(
                RedisCacheConfiguration
                        .defaultCacheConfig()
                        .entryTtl(Duration.ofDays(7))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                                                                              )
                .build();
    }

}
