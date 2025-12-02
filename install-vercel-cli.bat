@echo off
echo ========================================
echo Vercel CLI Install Script
echo ========================================

echo.
echo Node.jsとnpmがインストールされているかチェック...
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Node.jsがインストールされていません。
    echo https://nodejs.org/ からNode.jsをインストールしてください。
    pause
    exit /b 1
)

npm --version >nul 2>&1
if %errorlevel% neq 0 (
    echo npmがインストールされていません。
    echo Node.jsの再インストールが必要です。
    pause
    exit /b 1
)

echo.
echo Vercel CLIをグローバルにインストール...
call npm install -g vercel

echo.
echo インストール完了！
echo 次に deploy-vercel.bat を実行してください。
pause
