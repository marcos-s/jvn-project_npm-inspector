@echo off
REM Setup script for pre-commit hook
REM This script sets up the appropriate pre-commit hook based on the operating system

echo 🔧 Setting up pre-commit hook for NPM Package Inspector...

REM Check if we're in a git repository
if not exist ".git" (
    echo ❌ Error: Not in a git repository. Please run this from the project root.
    exit /b 1
)

echo 🪟 Windows detected - setting up Windows batch hook...

REM Copy the Windows batch hook
copy ".git\hooks\pre-commit.bat" ".git\hooks\pre-commit"

REM Remove the bash version if it exists
if exist ".git\hooks\pre-commit.sh" del ".git\hooks\pre-commit.sh"

echo ✅ Windows pre-commit hook installed successfully!

echo.
echo 🎉 Pre-commit hook setup complete!
echo 💡 The hook will now run npm audit before each commit.
echo 💡 High-severity vulnerabilities will block commits.
echo.
echo To test the hook, try making a commit:
echo   git add .
echo   git commit -m "Test commit"
