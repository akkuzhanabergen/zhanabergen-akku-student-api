#!/usr/bin/env bash
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
LOG_FILE="$PROJECT_DIR/logs/Zhanabergen_Akku_system_report.log"
mkdir -p "$PROJECT_DIR/logs"

{
    echo '=================================================='
    echo 'Student: Zhanabergen Akku (Жанаберген Аққу)'
    echo "Date and time: $(date '+%Y-%m-%d %H:%M:%S %Z')"
    echo "Username: $(whoami)"
    echo "Operating system: $(uname -s)"
    echo "Kernel version: $(uname -r)"
    echo 'CPU information:'
    if command -v lscpu >/dev/null 2>&1; then
        lscpu | grep -E '^(Architecture|CPU\(s\)|Model name):' || true
    elif command -v sysctl >/dev/null 2>&1; then
        sysctl -n machdep.cpu.brand_string 2>/dev/null || uname -m
    else
        uname -m
    fi
    echo 'Memory information:'
    if command -v free >/dev/null 2>&1; then
        free -h
    elif command -v vm_stat >/dev/null 2>&1; then
        vm_stat | head -8
    else
        echo 'Memory utility not available'
    fi
    echo 'Disk usage:'
    df -h "$PROJECT_DIR"
    echo 'Available disk space (same filesystem):'
    df -h "$PROJECT_DIR" | awk 'NR==2 {print $4}'
    echo '=================================================='
} | tee -a "$LOG_FILE"
echo "Saved to $LOG_FILE"
