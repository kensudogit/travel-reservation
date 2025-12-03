# Google Cloud Run デプロイ手順

## 重要な設定

Google Cloud Runでフロントエンドをデプロイする際は、**`frontend/`ディレクトリをビルドコンテキストとして使用**してください。

## デプロイ方法

### 推奨: frontend/ディレクトリをビルドコンテキストとして使用

```bash
gcloud run deploy travel-reservation-frontend \
  --source ./frontend \
  --region asia-southeast1 \
  --platform managed \
  --allow-unauthenticated
```

この方法では、`frontend/Dockerfile`が使用され、ビルドコンテキストは`frontend/`ディレクトリになります。`package.json`と`package-lock.json`が正しくコピーされます。

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

## トラブルシューティング

### エラー: "/package.json": not found

このエラーが発生する場合、Google Cloud Runの設定で以下を確認してください：

1. **Google Cloud Consoleでの設定確認**
   - Cloud Run → サービスを選択
   - 「編集と新しいリビジョンをデプロイ」をクリック
   - 「コンテナ」タブで「ソース」セクションを確認
   - **「ソースディレクトリ」を`frontend/`に設定**してください
   - 「Dockerfile」のパスが`Dockerfile`（`frontend/`ディレクトリ内）に設定されていることを確認

2. **コマンドラインで明示的に指定**
   ```bash
   gcloud run deploy travel-reservation-frontend \
     --source ./frontend \
     --region asia-southeast1 \
     --platform managed \
     --allow-unauthenticated
   ```

3. **キャッシュをクリア**
   Google Cloud Runが古い設定をキャッシュしている可能性があります。サービスを削除して再作成するか、新しいリビジョンをデプロイしてください。

