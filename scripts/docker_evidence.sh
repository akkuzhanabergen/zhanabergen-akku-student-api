#!/usr/bin/env bash
set -euo pipefail
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$PROJECT_DIR"
mkdir -p evidence
{
  echo '=== Containers ==='
  docker compose ps
  echo '=== App logs (last 40 lines) ==='
  docker compose logs --tail=40 app
  echo '=== Image details ==='
  docker image inspect zhanabergen-akku-student-api:v1.0-zhanabergen-akku --format 'Created: {{.Created}} | Size: {{.Size}} bytes'
  echo '=== App container inspection ==='
  docker inspect "$(docker compose ps -q app)" --format 'Name: {{.Name}}; Image: {{.Config.Image}}; Status: {{.State.Status}}'
  echo '=== Command inside app container ==='
  docker compose exec -T app java -version
} 2>&1 | tee evidence/docker_evidence.txt
echo 'Stopping and restarting only the application:'
docker compose stop app
docker compose start app
