# If the hidden Git repository did not survive extraction

The normal project archive includes `.git`, real branches, commits and tag. Check with `ls -la` and `git log --oneline --all --graph`. If `.git` is missing, you can initialize a new repository and recreate the workflow using the requirements below, but the original merge history will not be retained.

1. `git init -b main` and `git config user.name "Zhanabergen Akku"` and `git config user.email "YOUR_GITHUB_EMAIL"`.
2. Commit the project with `Initial commit: Zhanabergen Akku Student API`.
3. Create `feature/zhanabergen-akku-crud`, make two meaningful Java or README commits and merge into main with `git merge --no-ff`.
4. Create `feature/zhanabergen-akku-documentation`, change a README line, commit, then change the same line on main, and `git merge --no-ff` the documentation branch.
5. Fix `<<<<<<<`, `=======` and `>>>>>>>` markers manually in README, then `git add README.md && git commit`.
6. `git tag -a v1.0-zhanabergen-akku -m "Zhanabergen Akku first version"`.
