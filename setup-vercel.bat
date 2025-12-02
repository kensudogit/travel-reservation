@echo off
echo ========================================
echo Travel Reservation Vercel Setup Script
echo ========================================

echo.
echo このスクリプトはVercelデプロイの初期設定を行います。
echo.

REM Node.jsとnpmの確認
echo Node.jsとnpmのバージョンを確認...
node --version
npm --version

echo.
echo Vercel CLIのインストール...
call npm install -g vercel

echo.
echo Vercelにログイン...
vercel login

echo.
echo フロントエンドディレクトリに移動...
cd frontend

echo.
echo 環境変数ファイルを作成...
echo NEXT_PUBLIC_API_URL=http://localhost:8080/api > .env.local

echo.
echo 依存関係をインストール...
call npm install

echo.
echo ビルドテスト...
call npm run build

echo.
echo ========================================
echo 初期設定完了！
echo ========================================
echo 次に deploy-vercel.bat を実行してデプロイしてください。
echo.
pause
