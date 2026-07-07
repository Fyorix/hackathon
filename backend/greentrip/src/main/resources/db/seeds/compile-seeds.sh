#!/bin/bash
# Se place dans le dossier du script
CDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

# Concatène tous les populate.sql dans le import.sql global
echo "-- Seed data auto-generated from modular seeds" > "$CDIR/../../import.sql"

# Liste de manière déterministe pour éviter les surprises
find "$CDIR" -name "populate.sql" | sort | while read -r file; do
    echo "-- ========================================================" >> "$CDIR/../../import.sql"
    echo "-- Source: $(basename "$(dirname "$file")")/$(basename "$file")" >> "$CDIR/../../import.sql"
    echo "-- ========================================================" >> "$CDIR/../../import.sql"
    cat "$file" >> "$CDIR/../../import.sql"
    echo "" >> "$CDIR/../../import.sql"
done

echo "import.sql mis à jour avec toutes les entreprises !"
