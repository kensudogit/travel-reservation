package com.travel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 非同期処理設定クラス
 * 
 * アプリケーション全体の非同期処理を管理します。
 * 複数のスレッドプールを設定し、異なるタスクタイプに
 * 最適化された実行環境を提供します。
 * 
 * @author Travel System
 * @version 1.0
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * 汎用タスク実行用スレッドプールの設定
     * 
     * 一般的な非同期処理（データ処理、ファイル操作など）に使用されます。
     * コアプールサイズ5、最大プールサイズ20で設定し、
     * 高負荷時でも適切にスケールします。
     * 
     * @return 汎用タスク実行用のExecutor
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // コアスレッド数（常時稼働するスレッド数）
        executor.setCorePoolSize(5);
        // 最大スレッド数（高負荷時の最大スレッド数）
        executor.setMaxPoolSize(20);
        // キュー容量（待機タスクの最大数）
        executor.setQueueCapacity(100);
        // スレッド名のプレフィックス（デバッグ時に識別しやすくするため）
        executor.setThreadNamePrefix("TravelAsync-");
        executor.initialize();
        return executor;
    }

    /**
     * メール送信用スレッドプールの設定
     * 
     * メール送信処理専用のスレッドプールです。
     * メール送信は比較的軽量な処理のため、
     * 小さめのプールサイズで設定しています。
     * 
     * @return メール送信用のExecutor
     */
    @Bean(name = "emailExecutor")
    public Executor emailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // メール送信は軽量処理のため、小さめのプールサイズ
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("EmailAsync-");
        executor.initialize();
        return executor;
    }
}
