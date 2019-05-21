#!/usr/bin/env bash

CQL_FILE=$1

if [[ -z "$NEO4J" ]]; then
	echo "\$NEO4J not set. Please point it to your Neo4j installation"
	return 1
fi

if [[ -f "$CQL_FILE" ]]; then
	cat "$CQL_FILE" | $NEO4J/bin/cypher-shell
fi
