# Student CRUD API — Zhanabergen Akku

**Student:** Zhanabergen Akku (Жанаберген Аққу)  
**Course:** DevOps midterm  
**Stack:** Java 21, Spring Boot 3.5.6, Gradle, PostgreSQL 16, Docker Compose, Jenkins

## What I built

This is my personal Student CRUD API for the DevOps midterm. The API saves students in PostgreSQL, lists them by ID, validates request data, and returns HTTP 404 if a student does not exist. The project also includes a Linux system-report script, Git feature branches and tag, a multi-stage Dockerfile, and a Jenkins pipeline.

## Project structure

- `src/` — Spring Boot Java code, application config and automated tests
- `scripts/` — Bash system monitoring, log analysis, API/Docker demos
- `logs/` — generated report and analysis (not committed)
- `backups/` — README backup
- `Dockerfile` — JDK build stage and smaller JRE runtime stage
- `docker-compose.yml` — app + PostgreSQL, health check, network and volume
- `Jenkinsfile` — Checkout, Build, Test, Docker Build, Verify
- `build.gradle`, `settings.gradle`, `gradlew`, `gradle/` — build files (included minimal Gradle downloader; can be replaced by official wrapper)
- `.env.example` — example database settings (real `.env` is ignored by Git)

## 1. Start the application

Requirements: Docker Desktop (with Docker Compose) and internet access for the first Gradle dependency download. To run Gradle separately, install Java 21. Docker builds include the JDK.

```bash
cp .env.example .env
# Open .env and choose your own strong PostgreSQL password.
docker compose up -d --build
docker compose ps
docker compose logs --tail=50 app
curl http://localhost:8080/api/info
```

> If `8080` is already in use, set `APP_PORT=8081` in `.env` and use port 8081 in curl commands. Never use `docker compose down -v` when demonstrating data persistence.

## 2. CRUD API endpoints

| Method | URL | Description | Successful status |
|---|---|---|---|
| GET | `/api/info` | Student name and project version | 200 |
| GET | `/api/students` | Read all students | 200 |
| GET | `/api/students/{id}` | Read one student | 200 |
| POST | `/api/students` | Create student | 201 |
| PUT | `/api/students/{id}` | Update student | 200 |
| DELETE | `/api/students/{id}` | Delete student | 204 |
| GET | `/api/students/999999` | Missing student | 404 |

For example:

```bash
curl -X POST http://localhost:8080/api/students \
  -H 'Content-Type: application/json' \
  -d '{"fullName":"Zhanabergen Akku","email":"akku@example.com","major":"Information Systems","course":3}'

curl http://localhost:8080/api/students
```

Run all six CRUD operations and 404 test automatically (uses Python 3):

```bash
./scripts/api_demo.sh
```

## 3. Gradle automated tests

```bash
./gradlew clean test
```

The tests use an isolated H2 test database (PostgreSQL compatibility mode). Production/Docker uses PostgreSQL, not H2. Test methods cover Create, Read, Update, Delete, 404, invalid email (400), and the owner's name.

## 4. Linux administration and backup

```bash
./scripts/task1_setup.sh
./scripts/Zhanabergen_Akku_system_report.sh
./scripts/Zhanabergen_Akku_log_analysis.sh
ls -l scripts/Zhanabergen_Akku_system_report.sh  # should show -rwxr--r--
```

The setup uses `mkdir -p`, creates the initial empty report log and copies README to a personal backup. It uses chmod `744` (owner `rwx`, group and others `r`). System reporting uses `uname`, `whoami`, `date`, `lscpu`/`sysctl`, `free`/`vm_stat` and `df`. Each report run appends via `tee -a`. The analysis script searches for `Zhanabergen` and writes `logs/Zhanabergen_Akku_log_analysis.txt`.

`>` replaces the file content; `>>` appends to the end.

## 5. Docker checks and persistent PostgreSQL data

```bash
docker compose ps
./scripts/docker_evidence.sh
./scripts/persistence_demo.sh
```

`persistence_demo.sh` creates a record, runs `docker compose down` **without** `-v`, recreates containers, and verifies that the same record still exists in the named volume. The app connects to host `postgres` (Docker service name), not to `localhost` inside the app container.

## 6. Git workflow and GitHub

This archive contains a local `.git` repository with real commits, two named feature branches, a manually resolved README merge conflict, and the annotated tag `v1.0-zhanabergen-akku`. Before pushing:

1. Create an **empty** GitHub repository named `zhanabergen-akku-student-api` under your account. Do not auto-add README or .gitignore.
2. Set your real GitHub email (substitute YOUR_GITHUB_EMAIL):

```bash
git config user.name "Zhanabergen Akku"
git config user.email "YOUR_GITHUB_EMAIL"
git remote add origin https://github.com/akkuzhanabergen/zhanabergen-akku-student-api.git
git push -u origin main
git push origin feature/zhanabergen-akku-crud feature/zhanabergen-akku-documentation
git push origin v1.0-zhanabergen-akku
git log --oneline --graph --all --decorate
git remote -v
```

If your GitHub username is not `akkuzhanabergen`, change the URL above accordingly. If the archive was extracted without the hidden `.git` folder, see `docs/git-recovery.md`.

The actual merge-conflict history can be inspected with `git log --all --graph -p -- README.md`.

**Security:** `.env`, passwords and real database secrets must stay out of Git history; they could otherwise be copied or abused by other people. `docker-compose.yml` uses environment variables rather than hard-coded secrets.

## 7. Jenkins pipeline

- Create a Pipeline job named `Zhanabergen_Akku_Student_API`.
- Configure it as **Pipeline script from SCM** → Git → GitHub repository URL → branch `*/main` → Script Path `Jenkinsfile`.
- Run on a Jenkins agent labelled `docker-gradle-jdk21`, with working **Docker CLI + Docker daemon access** and network access to Gradle/Maven repositories. JDK 21 must be installed; the Gradle wrapper downloads Gradle 8.14.3.
- Jenkinsfile has five stages: Checkout, Build, Test, Docker Build, Verify.
- `pollSCM('H/2 * * * *')` checks for new Git commits roughly every two minutes; verify automatic runs in the Jenkins job history.
- Follow `docs/jenkins-failure-demo.md` to show one controlled failed build and recovery on a test branch.

**Never paste credentials into the Jenkinsfile.** Use Jenkins Credentials if the repository is private or registries need authentication. Automated deployment is deliberately not included, since the assignment does not require it.

## My development note

I organized the API with controller, service and repository classes. I added validation and tests before the Docker and Jenkins stages, so a failed test stops the pipeline. I used a Docker volume because a container can be removed without losing PostgreSQL records. I kept the main branch clean and used personal feature branches to show the Git workflow.

## Evidence to attach to the submission

Screenshots should come from your real terminal and Jenkins/GitHub account. Use the checklists in `docs/quick-commands.md` and `docs/report-evidence.md`. Do not submit sample or invented screenshots as actual execution evidence.
