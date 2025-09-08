#!/bin/bash

# Setup script for pre-commit hook
# This script sets up the appropriate pre-commit hook based on the operating system

echo "Setting up pre-commit hook for NPM Package Inspector..."

# Check if we're in a git repository
if [ ! -d ".git" ]; then
    echo "Error: Not in a git repository. Please run this from the project root."
    exit 1
fi

# Detect operating system
if [[ "$OSTYPE" == "msys" ]] || [[ "$OSTYPE" == "win32" ]] || [[ -n "$WINDIR" ]]; then
    echo "Windows detected - setting up Windows batch hook..."
    
    # Copy the Windows batch hook
    cp .git/hooks/pre-commit.bat .git/hooks/pre-commit
    
    # Remove the bash version if it exists
    rm -f .git/hooks/pre-commit.sh
    
    echo "Windows pre-commit hook installed successfully!"
else
    echo "Unix-like system detected - setting up bash hook..."
    
    # Copy the bash hook
    cp .git/hooks/pre-commit .git/hooks/pre-commit.sh
    
    # Make it executable
    chmod +x .git/hooks/pre-commit.sh
    
    # Remove the Windows batch version if it exists
    rm -f .git/hooks/pre-commit.bat
    
    echo "Unix pre-commit hook installed successfully!"
fi

echo ""
echo "Pre-commit hook setup complete!"
echo "The hook will now run npm audit before each commit."
echo "High-severity vulnerabilities will block commits."
echo ""
echo "To test the hook, try making a commit:"
echo "  git add ."
echo "  git commit -m 'Test commit'"
