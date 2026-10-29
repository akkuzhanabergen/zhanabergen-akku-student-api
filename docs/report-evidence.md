# Midterm evidence — checklist (fill with real screenshots)

## Part 1 — Linux administration
- Screenshot: directory tree and all required files.
- Screenshot: running `Zhanabergen_Akku_system_report.sh` and saved report log.
- Screenshot: `ls -l` with permissions `-rwxr--r--` and README backup verified with `cmp`.
- Screenshot: `ps aux`, `tail -n 5`, and `grep` result file.
- Explain: `>` overwrites; `>>` appends.

## Part 2 — Git and GitHub
- Screenshot: GitHub repository URL, main branch and two feature branches.
- Screenshot: `git log --graph --oneline --all --decorate` showing the merges.
- Screenshot: the documented README conflict commit and `git tag`.
- Explain: secrets in `.env` must never be committed.

## Part 3 — Docker and PostgreSQL
- Screenshot: `docker image ls`, `docker image inspect`, `docker compose ps` and logs.
- Screenshot: `docker compose exec app java -version`.
- Screenshot: all CRUD operations + 404 using `./scripts/api_demo.sh`.
- Screenshot: same record before/after `docker compose down` / `up` using named volume.
- Screenshot: restarting app and rebuilding the Docker image after changing app version.

## Part 4 — Jenkins
- Screenshot: `Zhanabergen_Akku_Student_API` job using the GitHub repository.
- Screenshot: all 5 stages completed and console log displaying the student name.
- Screenshot: failed **Test** stage on a private demo branch.
- Screenshot: corrected test, subsequent successful run.
- Screenshot: automatic trigger after new commit, Jenkins build history.
- Explain: Continuous Integration vs Continuous Deployment.

> Do not label planned steps as completed until you actually run them. The repository source is prepared; live Docker, GitHub and Jenkins evidence must be collected on your machine/accounts.
