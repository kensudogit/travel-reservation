@echo off
echo ========================================
echo Travel Reservation Vercel Deploy Script
echo ========================================

REM Vercel CLIがインストールされているかチェック
vercel --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Vercel CLIがインストールされていません。
    echo install-vercel-cli.bat を実行してください。
    pause
    exit /b 1
)

echo.
echo 現在のディレクトリ: %CD%
echo フロントエンドディレクトリに移動...
cd frontend

echo.
echo 依存関係をインストール...
call npm install
if %errorlevel% neq 0 (
    echo 依存関係のインストールに失敗しました。
    pause
    exit /b 1
)

echo.
echo ビルドを実行...
call npm run build
if %errorlevel% neq 0 (
    echo ビルドに失敗しました。
    pause
    exit /b 1
)

echo.
echo Vercelにデプロイ...
echo 初回実行時はVercelアカウントへのログインが必要です。
vercel --prod

if %errorlevel% neq 0 (
    echo デプロイに失敗しました。
    pause
    exit /b 1
)

echo.
echo ========================================
echo デプロイ完了！
echo ========================================
echo 他のPCからアクセス可能なURLが表示されます。
echo.
echo 注意事項:
echo - 初回デプロイ後、Vercelダッシュボードで環境変数を設定してください
echo - バックエンドAPIのURLを適切に設定してください
echo - カスタムドメインの設定も可能です
echo.
pause
