# Submission command checklist (macOS/Linux terminal)

```bash
cd zhanabergen-akku-student-api
chmod +x gradlew scripts/*.sh  # needed with some ZIP extractors
# Task 1: Linux (on Mac reports show Darwin, use Ubuntu if Linux evidence is mandatory)
./scripts/task1_setup.sh
./scripts/Zhanabergen_Akku_system_report.sh
./scripts/Zhanabergen_Akku_log_analysis.sh
ls -l scripts/Zhanabergen_Akku_system_report.sh
cmp README.md backups/Zhanabergen_Akku_README_backup.md

# Task 2: local Git history is already prepared in this archive
git log --oneline --graph --all --decorate
git branch -a
git tag -l
git remote -v

# Task 3
cp .env.example .env   # edit the password in .env
./gradlew clean test bootJar
docker compose up -d --build
docker compose ps
docker compose logs --tail=40 app
./scripts/api_demo.sh
./scripts/docker_evidence.sh
./scripts/persistence_demo.sh

# When everything is submitted
docker compose down    # DO NOT use -v if you want to preserve PostgreSQL data
```

Do not run `./scripts/task1_setup.sh` again after collecting your log evidence: it clears the log file intentionally as required by Task 1.1.

If your terminal says `permission denied`, run `chmod +x scripts/*.sh gradlew`.
