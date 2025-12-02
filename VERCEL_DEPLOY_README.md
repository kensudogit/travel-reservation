# Travel Reservation Vercel デプロイガイド

## 概要
このプロジェクトをVercelにデプロイして、他のPCからHTTP接続できるようにする手順です。

## 前提条件
- Node.js 18以上がインストールされている
- npmが利用可能
- Vercelアカウントがある

## クイックデプロイ手順

### 1. 初期設定（初回のみ）
```bash
# 提供されているスクリプトを使用（推奨）
setup-vercel.bat

# このスクリプトは以下を自動実行します：
# - Vercel CLIのインストール
# - Vercelへのログイン
# - 環境変数ファイルの作成
# - 依存関係のインストール
# - ビルドテスト
```

### 2. デプロイの実行
```bash
# 提供されているスクリプトを使用（推奨）
deploy-vercel.bat

# または手動で実行
cd frontend
npm install
npm run build
vercel --prod
```

## 手動設定手順（スクリプトを使用しない場合）

### 1. Vercel CLIのインストール
```bash
npm install -g vercel
```

### 2. Vercelへのログイン
```bash
vercel login
```

### 3. 環境変数ファイルの作成
```bash
cd frontend
echo NEXT_PUBLIC_API_URL=http://localhost:8080/api > .env.local
```

### 4. 依存関係のインストール
```bash
npm install
```

### 5. ビルドテスト
```bash
npm run build
```

## 詳細設定手順

### 初回デプロイ時の設定
初回実行時は以下の設定が必要：

1. **Vercelアカウントへのログイン**
   - ブラウザが開き、Vercelアカウントでログイン

2. **プロジェクト設定**
   - プロジェクト名: `travel-reservation`（推奨）
   - フレームワーク: `Next.js`
   - ルートディレクトリ: `frontend`

3. **環境変数の設定**
   - Vercelダッシュボードで環境変数を設定
   - `NEXT_PUBLIC_API_URL`をバックエンドの実際のURLに設定

### 環境変数の設定
フロントエンドディレクトリに`.env.local`ファイルを作成し、以下を設定：

```bash
# ローカル開発用
NEXT_PUBLIC_API_URL=http://localhost:8080/api

# Vercelデプロイ用（バックエンドの実際のURLに変更）
NEXT_PUBLIC_API_URL=https://your-backend-domain.com/api
```

## デプロイ後の設定

### カスタムドメインの設定
1. Vercelダッシュボードにアクセス
2. プロジェクトの設定 → Domains
3. カスタムドメインを追加

### 環境変数の更新
1. Vercelダッシュボード → Settings → Environment Variables
2. `NEXT_PUBLIC_API_URL`を更新
3. 再デプロイを実行

## トラブルシューティング

### ビルドエラー
- Node.jsのバージョンを確認（18以上推奨）
- `npm install`を再実行
- `node_modules`を削除して再インストール

### デプロイエラー
- Vercel CLIのバージョンを確認
- ログイン状態を確認
- プロジェクト設定を確認

### 環境変数の問題
- `.env.local`ファイルの構文を確認
- Vercelダッシュボードでの設定を確認
- 再デプロイを実行

## アクセス方法
デプロイ完了後、以下のURLでアクセス可能：

- **Vercel提供URL**: `https://your-project.vercel.app`
- **カスタムドメイン**: `https://your-domain.com`

## セキュリティ注意事項
- 本番環境では適切なCORS設定を行う
- 環境変数に機密情報を含めない
- 定期的なセキュリティアップデートを実施

## サポート
問題が発生した場合は、以下を確認してください：
1. Vercelの公式ドキュメント
2. プロジェクトのログ
3. 環境変数の設定
4. ネットワーク接続の状態

## 更新履歴
- 2024年: Vercel設定ファイルの最適化
- デプロイスクリプトの改善
- Next.js設定の最適化
- 環境変数ファイル作成手順の追加
- 初期設定スクリプトの追加
- 手動設定手順の追加
