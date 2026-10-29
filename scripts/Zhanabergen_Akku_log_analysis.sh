#!/usr/bin/env bash
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
LOG_FILE="$PROJECT_DIR/logs/Zhanabergen_Akku_system_report.log"
RESULT_FILE="$PROJECT_DIR/logs/Zhanabergen_Akku_log_analysis.txt"
if [[ ! -f "$LOG_FILE" ]]; then
    echo 'Run the system report first.' >&2
    exit 1
fi
echo '=== Running Java processes (if any) ==='
ps aux | grep '[j]ava' || true
echo '=== Last 5 lines of the log ==='
tail -n 5 "$LOG_FILE"
echo '=== Search results for Zhanabergen ==='
grep 'Zhanabergen' "$LOG_FILE" > "$RESULT_FILE" || true
cat "$RESULT_FILE"
echo "Search results saved to $RESULT_FILE"
