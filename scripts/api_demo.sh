#!/usr/bin/env bash
set -euo pipefail
BASE_URL="${BASE_URL:-http://localhost:8080}"
echo 'API information:'
curl -fsS "$BASE_URL/api/info"; echo
printf 'Creating student...
'
CREATED="$(curl -fsS -X POST "$BASE_URL/api/students" -H 'Content-Type: application/json'     -d '{"fullName":"Zhanabergen Akku","email":"akku.demo@example.com","major":"Information Systems","course":3}')"
echo "$CREATED"
STUDENT_ID="$(printf '%s' "$CREATED" | python3 -c 'import json,sys; print(json.load(sys.stdin)["id"])')"
echo "GET all:"
curl -fsS "$BASE_URL/api/students"; echo
echo "GET student ID $STUDENT_ID:"
curl -fsS "$BASE_URL/api/students/$STUDENT_ID"; echo
echo "UPDATE student ID $STUDENT_ID:"
curl -fsS -X PUT "$BASE_URL/api/students/$STUDENT_ID" -H 'Content-Type: application/json'     -d '{"fullName":"Zhanabergen Akku","email":"akku.updated@example.com","major":"Data Science","course":3}'; echo
echo "DELETE student ID $STUDENT_ID:"
curl -sS -o /dev/null -w 'HTTP %{http_code}
' -X DELETE "$BASE_URL/api/students/$STUDENT_ID"
echo "GET deleted student (expected 404):"
curl -sS -o /dev/null -w 'HTTP %{http_code}
' "$BASE_URL/api/students/$STUDENT_ID"
echo 'GET ID 999999 (expected 404):'
curl -sS -o /dev/null -w 'HTTP %{http_code}
' "$BASE_URL/api/students/999999"
