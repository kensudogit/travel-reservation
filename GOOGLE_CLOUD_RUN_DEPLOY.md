# Google Cloud Run デプロイ手順

## 問題の解決

Google Cloud Runでフロントエンドをデプロイする際、ビルドコンテキストの設定が重要です。

## デプロイ方法

### オプション1: frontend/ディレクトリをビルドコンテキストとして使用（推奨）

```bash
gcloud run deploy travel-reservation-frontend \
  --source ./frontend \
  --region asia-southeast1 \
  --platform managed \
  --allow-unauthenticated
```

この方法では、`frontend/Dockerfile`が使用され、ビルドコンテキストは`frontend/`ディレクトリになります。

### オプション2: ルートディレクトリをビルドコンテキストとして使用

```bash
gcloud run deploy travel-reservation-frontend \
  --source . \
  --region asia-southeast1 \
  --platform managed \
  --allow-unauthenticated
```

この方法では、ルートの`Dockerfile`が使用されます。

## Google Cloud Consoleでの設定

1. Google Cloud Consoleにアクセス
2. Cloud Runサービスを選択
3. 「編集と新しいリビジョンをデプロイ」をクリック
4. 「コンテナ」タブで、「ソース」セクションを確認
5. 「ソースディレクトリ」を`frontend/`に設定（オプション1の場合）
   または、ルートディレクトリ`.`に設定（オプション2の場合）
6. 「Dockerfile」のパスを確認
   - オプション1の場合: `frontend/Dockerfile`
   - オプション2の場合: `Dockerfile`（ルート）

## 現在のエラーについて

現在、Google Cloud Runが`frontend/Dockerfile`を検出していますが、ビルドコンテキストがルートディレクトリになっているため、`package.json`が見つかりません。

**解決策**: 上記のオプション1を使用して、ビルドコンテキストを`frontend/`ディレクトリに設定してください。

