# Git Hooks Setup

This project includes pre-commit hooks to enforce code quality and security standards.

## Pre-commit Hook

The pre-commit hook runs `npm audit` and blocks commits if high-severity vulnerabilities are found in the frontend dependencies.

### Features

- ✅ Runs `npm audit` with high-severity level checking
- ✅ Blocks commits if high-severity vulnerabilities are found
- ✅ Shows warnings for lower-severity vulnerabilities
- ✅ Provides helpful error messages and suggestions
- ✅ Cross-platform support (Windows and Unix-like systems)

### Setup

#### Windows
```bash
setup-hooks.bat
```

#### Linux / macOS
```bash
chmod +x setup-hooks.sh
./setup-hooks.sh
```

### Manual Setup

### Testing the Hook

To test if the hook is working:

1. Make some changes to your code
2. Stage the changes: `git add .`
3. Try to commit: `git commit -m "Test commit"`

The hook will run automatically and either:
- Allow the commit if no high-severity vulnerabilities are found
- Block the commit if high-severity vulnerabilities are found

### Bypassing the Hook

If you need to bypass the hook, you can use:
```bash
git commit --no-verify -m "Your commit message"
```

### Troubleshooting

- **Hook not running**: Make sure the hook file is executable and in the correct location
- **npm not found**: Ensure Node.js and npm are installed and in your PATH
- **Permission denied**: Run the setup script with appropriate permissions

### What the Hook Checks

The hook specifically:
1. Changes to the frontend directory (`starter/src/frontend`)
2. Runs `npm audit --audit-level=high`
3. Blocks commits if exit code is non-zero (high-severity vulnerabilities found)
4. Shows warnings for any vulnerabilities found
5. Provides helpful suggestions for fixing issues
