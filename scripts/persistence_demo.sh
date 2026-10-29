#!/usr/bin/env bash
set -euo pipefail
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$PROJECT_DIR"
BASE_URL="${BASE_URL:-http://localhost:8080}"
CREATED="$(curl -fsS -X POST "$BASE_URL/api/students" -H 'Content-Type: application/json'   -d '{"fullName":"Zhanabergen Akku","email":"persist@example.com","major":"DevOps","course":3}')"
ID="$(printf '%s' "$CREATED" | python3 -c 'import json,sys; print(json.load(sys.stdin)["id"])')"
echo "Created persistent student ID=$ID: $CREATED"
echo 'Stopping and removing containers without deleting the named volume...'
docker compose down
docker compose up -d --build
for attempt in $(seq 1 60); do
  if curl -fsS "$BASE_URL/api/students/$ID" > /tmp/zhanabergen-akku-persistence.json 2>/dev/null; then
    echo "SUCCESS: record remained after restart:"; cat /tmp/zhanabergen-akku-persistence.json; echo
    rm -f /tmp/zhanabergen-akku-persistence.json
    exit 0
  fi
  sleep 2
done
echo 'ERROR: API did not return the persisted record.' >&2
exit 1
