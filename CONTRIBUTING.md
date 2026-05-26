# Contributing to Reddit Clone

Thank you for considering contributing to the Reddit Clone project! 🎉

## How to Contribute

### Reporting Bugs
1. Check if the bug has already been reported in [Issues](https://github.com/infonavodigital/Reddit-linkedin-/issues)
2. If not, create a new issue using the Bug Report template
3. Provide as much detail as possible

### Suggesting Features
1. Check if the feature has already been suggested
2. Create a new issue using the Feature Request template
3. Describe your idea clearly

### Code Contributions

#### Getting Started
1. Fork the repository
2. Clone your fork: `git clone https://github.com/YOUR_USERNAME/Reddit-linkedin-.git`
3. Create a new branch: `git checkout -b feature/your-feature-name`
4. Make your changes
5. Test thoroughly
6. Commit with clear messages
7. Push to your fork
8. Create a Pull Request

#### Development Setup
```bash
# Open in Android Studio
# File → Open → Select project directory

# Build the project
./gradlew build

# Run tests
./gradlew test

# Run lint
./gradlew lint
```

#### Coding Standards
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Comment complex logic
- Keep functions small and focused
- Use Jetpack Compose best practices
- Follow Material Design guidelines

#### Commit Messages
Format: `type: description`

Types:
- `feat:` New feature
- `fix:` Bug fix
- `docs:` Documentation changes
- `style:` Code style changes (formatting, etc)
- `refactor:` Code refactoring
- `test:` Adding or updating tests
- `chore:` Maintenance tasks

Examples:
```
feat: Add dark mode toggle in settings
fix: Resolve crash on post detail screen
docs: Update README with build instructions
```

#### Pull Request Process
1. Update documentation if needed
2. Ensure all tests pass
3. Update the CHANGELOG.md if applicable
4. Fill out the PR template completely
5. Request review from maintainers
6. Address any review feedback

#### Code Review Guidelines
- Be respectful and constructive
- Explain your reasoning
- Suggest improvements
- Test the changes locally
- Approve when satisfied

## Project Structure
```
app/src/main/java/com/reddit/clone/
├── data/           # Data models and repositories
├── navigation/     # Navigation configuration
├── ui/
│   ├── components/ # Reusable UI components
│   ├── screens/    # Screen composables
│   └── theme/      # Theme configuration
└── MainActivity.kt
```

## Testing
- Write unit tests for business logic
- Test on multiple Android versions
- Test on different screen sizes
- Test dark and light themes

## Questions?
Feel free to open a discussion or reach out to the maintainers.

## License
By contributing, you agree that your contributions will be licensed under the same license as the project.

---

Thank you for contributing! 🙌
