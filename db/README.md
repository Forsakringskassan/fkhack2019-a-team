# MFT Database stuff

Use Neo4j database, developed on Neo4j 3.5.5
Requires the following Neo4j plugins:
- [APOC](https://github.com/neo4j-contrib/neo4j-apoc-procedures)
- [GraphAware TimeTree](https://github.com/graphaware/neo4j-timetree)

[test.cql](test.cql) contains some useful Cypher to generate basic data for the application.

# Configure Neo4j
Neo4j requires the following configuration added to `$NEO4J/conf/neo4j.conf`:
``` conf
dbms.security.auth_enabled=false
dbms.security.procedures.unrestricted=apoc.*,ga.timetree.*
dbms.security.procedures.whitelist=apoc.*,ga.timetree.*
```