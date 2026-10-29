#!/usr/bin/env bash
set -euo pipefail
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$PROJECT_DIR"
mkdir -p scripts logs backups src evidence
: > logs/Zhanabergen_Akku_system_report.log
chmod 744 scripts/Zhanabergen_Akku_system_report.sh
cp README.md backups/Zhanabergen_Akku_README_backup.md
printf 'Permissions:
'
ls -l scripts/Zhanabergen_Akku_system_report.sh
printf '
Backup:
'
ls -l backups/Zhanabergen_Akku_README_backup.md
cmp README.md backups/Zhanabergen_Akku_README_backup.md && echo 'Backup contents verified.'
printf '
Project files:
'
find . -maxdepth 2 -not -path './.git/*' -not -path './.gradle/*' | sort
