package com.travel.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * キャッシュ設定クラス
 * 
 * アプリケーション全体のキャッシュ戦略を管理します。
 * Redisを使用した高性能なキャッシュシステムを提供し、
 * データベースアクセスの負荷を軽減します。
 * 
 * @author Travel System
 * @version 1.0
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Redisキャッシュマネージャーの設定
     * 
     * 各エンティティタイプに応じたTTL（Time To Live）を設定し、
     * 効率的なキャッシュ戦略を提供します。
     * 
     * @param redisConnectionFactory Redis接続ファクトリー
     * @return 設定済みのキャッシュマネージャー
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // デフォルトのキャッシュ設定（30分TTL）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                // ツアー情報は15分でキャッシュ更新（頻繁に変更されるため）
                .withCacheConfiguration("tours",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(15)))
                // 目的地情報は1時間でキャッシュ更新（比較的安定）
                .withCacheConfiguration("destinations",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)))
                // ユーザー情報は10分でキャッシュ更新
                .withCacheConfiguration("users",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
                .build();
    }

    /**
     * フォールバックキャッシュマネージャーの設定
     * 
     * Redisが利用できない場合の代替キャッシュとして、
     * メモリベースのConcurrentMapCacheManagerを提供します。
     * 
     * @return フォールバック用のキャッシュマネージャー
     */
    @Bean
    public CacheManager fallbackCacheManager() {
        return new ConcurrentMapCacheManager("tours", "destinations", "users");
    }
}
