@echo off
echo ========================================
echo Travel Reservation Vercel Status Check
echo ========================================

echo.
echo Vercel CLIのバージョンを確認...
vercel --version
if %errorlevel% neq 0 (
    echo Vercel CLIがインストールされていません。
    echo install-vercel-cli.bat を実行してください。
    pause
    exit /b 1
)

echo.
echo ログイン状態を確認...
vercel whoami
if %errorlevel% neq 0 (
    echo Vercelにログインしていません。
    echo vercel login を実行してください。
    pause
    exit /b 1
)

echo.
echo プロジェクトのステータスを確認...
vercel ls

echo.
echo 現在のプロジェクトの詳細情報...
vercel inspect

echo.
echo ========================================
echo ステータス確認完了
echo ========================================
pause
