// SKapa ledighetsasökan för P1
MATCH (P1:Person {kortid:'88880001'})
MATCH (LS:Ledighetstyp {typ:'Semester'})
CREATE (L1:Ledighet {id:apoc.create.uuid(), godkand:false})
MERGE (P1)-[:REQUESTS]->(L1)
MERGE (L1)-[:TYPE]->(LS)
WITH L1
CALL ga.timetree.events.attach({node: L1, time: apoc.date.parse('2019-05-19','ms','yyyy-MM-dd'), relationshipType: "FROM"})
YIELD node as node1
CALL ga.timetree.events.attach({node: L1, time: apoc.date.parse('2019-06-12','ms','yyyy-MM-dd'), relationshipType: "TOM"})
YIELD node RETURN node;

// SKapa ledighetsansökan för p2
MATCH (P1:Person {kortid:'88880002'})
MATCH (LS:Ledighetstyp {typ:'Semester'})
CREATE (L1:Ledighet {id:apoc.create.uuid(), godkand:false})
MERGE (P1)-[:REQUESTS]->(L1)
MERGE (L1)-[:TYPE]->(LS)
WITH L1
CALL ga.timetree.events.attach({node: L1, time: apoc.date.parse('2019-05-03','ms','yyyy-MM-dd'), relationshipType: "FROM"})
YIELD node as node1
CALL ga.timetree.events.attach({node: L1, time: apoc.date.parse('2019-07-30','ms','yyyy-MM-dd'), relationshipType: "TOM"})
YIELD node RETURN node;