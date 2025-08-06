package com.travel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * Web設定クラス
 * 
 * CORS（Cross-Origin Resource Sharing）の設定を管理します。
 * フロントエンドアプリケーションからのAPIアクセスを
 * 安全に許可するための設定を提供します。
 * 
 * @author Travel System
 * @version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * CORSマッピングの設定
     * 
     * フロントエンドアプリケーションからのAPIアクセスを許可します。
     * 開発環境とプロダクション環境の両方に対応した設定です。
     * 
     * @param registry CORSレジストリー
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                // 許可するオリジン（フロントエンドのURL）
                .allowedOrigins("http://localhost:3000", "http://localhost:3001")
                // 許可するHTTPメソッド
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 許可するヘッダー
                .allowedHeaders("*")
                // クレデンシャル（Cookie等）の送信を許可
                .allowCredentials(true)
                // プリフライトリクエストのキャッシュ時間（秒）
                .maxAge(3600);
    }

    /**
     * CORS設定ソースのBean定義
     * 
     * Spring Securityと統合するためのCORS設定を提供します。
     * より詳細なCORS制御が必要な場合に使用されます。
     * 
     * @return CORS設定ソース
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // ローカルホストの任意のポートを許可（開発環境用）
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:*"));
        // 許可するHTTPメソッド
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // 許可するヘッダー
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // クレデンシャルの送信を許可
        configuration.setAllowCredentials(true);
        // プリフライトリクエストのキャッシュ時間
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
