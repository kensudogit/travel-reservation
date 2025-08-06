# Travel Reservation Management System

大手旅行会社向けの予約管理・共通マスタ管理システムです。Spring Boot、PostgreSQL、MyBatis、Spring Batch、React/TypeScript/NextJSを使用して構築されています。

## 機能概要

### バックエンド機能
- **ユーザー管理**: 認証・認可、ユーザー登録・更新・削除
- **目的地管理**: 旅行先の登録・更新・削除、アクティブ/非アクティブ切り替え
- **ツアー管理**: ツアーパッケージの作成・更新・削除、キャパシティ管理
- **予約管理**: ツアー予約の作成・確認・キャンセル、支払い状況管理
- **バッチ処理**: ユーザーデータのCSVエクスポート機能
- **共通マスタ管理**: 目的地、ツアー、ユーザー情報の一元管理

### フロントエンド機能
- **SPA**: React/TypeScript/NextJSによるシングルページアプリケーション
- **レスポンシブデザイン**: モバイル・タブレット・デスクトップ対応
- **リアルタイム検索**: ツアー検索・フィルタリング機能
- **ユーザー認証**: JWTベースの認証システム
- **管理画面**: 管理者向けダッシュボード

## 技術スタック

### バックエンド
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security**: 認証・認可
- **Spring Data JPA**: データアクセス
- **Spring Batch**: バッチ処理
- **MyBatis**: SQLマッピング
- **PostgreSQL**: メインデータベース
- **H2**: 開発用データベース
- **JWT**: トークンベース認証

### フロントエンド
- **React 18**
- **TypeScript 5.2**
- **Next.js 14**: App Router
- **Tailwind CSS**: スタイリング
- **React Query**: データフェッチング
- **React Hook Form**: フォーム管理
- **Axios**: HTTP通信

## セットアップ

### 前提条件
- Java 17以上
- Node.js 18以上
- PostgreSQL 13以上
- Maven 3.6以上

### バックエンドセットアップ

1. **データベース設定**
```bash
# PostgreSQLデータベース作成
createdb traveldb
```

2. **アプリケーション設定**
```bash
# application.ymlの設定を確認
# データベース接続情報を更新
```

3. **アプリケーション起動**
```bash
cd travel-reservation
mvn spring-boot:run
```

### フロントエンドセットアップ

1. **依存関係インストール**
```bash
cd frontend
npm install
```

2. **開発サーバー起動**
```bash
npm run dev
```

## API エンドポイント

### 認証
- `POST /api/auth/login` - ログイン
- `POST /api/auth/logout` - ログアウト

### ユーザー管理
- `GET /api/users` - ユーザー一覧取得
- `GET /api/users/{id}` - ユーザー詳細取得
- `POST /api/users` - ユーザー作成
- `PUT /api/users/{id}` - ユーザー更新
- `DELETE /api/users/{id}` - ユーザー削除

### 目的地管理
- `GET /api/destinations` - 目的地一覧取得
- `GET /api/destinations/active` - アクティブな目的地取得
- `GET /api/destinations/{id}` - 目的地詳細取得
- `POST /api/destinations` - 目的地作成
- `PUT /api/destinations/{id}` - 目的地更新
- `DELETE /api/destinations/{id}` - 目的地削除

### ツアー管理
- `GET /api/tours` - ツアー一覧取得
- `GET /api/tours/available` - 利用可能なツアー取得
- `GET /api/tours/{id}` - ツアー詳細取得
- `POST /api/tours` - ツアー作成
- `PUT /api/tours/{id}` - ツアー更新
- `DELETE /api/tours/{id}` - ツアー削除

### 予約管理
- `GET /api/reservations` - 予約一覧取得
- `GET /api/reservations/{id}` - 予約詳細取得
- `POST /api/reservations` - 予約作成
- `PUT /api/reservations/{id}` - 予約更新
- `DELETE /api/reservations/{id}` - 予約削除
- `PATCH /api/reservations/{id}/confirm` - 予約確認
- `PATCH /api/reservations/{id}/cancel` - 予約キャンセル

### バッチ処理
- `POST /api/batch/export-users` - ユーザーデータエクスポート

## データベース設計

### 主要テーブル
- `users` - ユーザー情報
- `destinations` - 目的地情報
- `tours` - ツアー情報
- `reservations` - 予約情報
- `BATCH_*` - Spring Batch用テーブル

### リレーション
- ユーザー ↔ 予約 (1:N)
- 目的地 ↔ ツアー (1:N)
- ツアー ↔ 予約 (1:N)

## 開発ガイドライン

### コーディング規約
- Java: Google Java Style Guide準拠
- TypeScript: ESLint + Prettier
- コミットメッセージ: Conventional Commits

### テスト
- バックエンド: JUnit 5 + Mockito
- フロントエンド: Jest + React Testing Library

### デプロイ
- バックエンド: Docker + Docker Compose
- フロントエンド: Vercel/Netlify

## ライセンス

MIT License

## 貢献

プルリクエストやイシューの報告を歓迎します。 "# travel-reservation" 
