// Skapa basic data
CREATE (P1:Person {kortid:'88880001',namn:'Tobias Leo', antaldagar:'28', sparadedagar:'0'})
CREATE (P2:Person {kortid:'88880002', namn:'Pontus Elsltröm', antaldagar:'28', sparadedagar:'10'})
CREATE (P4:Person {kortid:'88880003', namn:'Mikael Ivarsson', antaldagar:'28', sparadedagar:'10'})
CREATE (P5:Person {kortid:'88880004',namn:'Daniel Edvinsson', antaldagar:'28', sparadedagar:'0'})
CREATE (CHEF:Person {kortid:'88880005', namn:'Mr T', antaldagar:'28', sparadedagar:'5'})
CREATE (R:Roll {title:'Systemutvecklare'})
CREATE (LS:Ledighetstyp {typ:'Semester'})
CREATE (LF:Ledighetstyp {typ:'Föräldraledighet'})
CREATE (LT:Ledighetstyp {typ:'Tjänsteledighet'})
CREATE (LFL:Ledighetstyp {typ:'Flexledighet'})

MERGE
  (P1)-[:HAS_ROLE {roll:['systemutvecklare']}]->(R)
MERGE
  (P2)-[:HAS_ROLE {roll:['systemutvecklare']}]->(R)
MERGE
  (P4)-[:HAS_ROLE {roll:['systemutvecklare']}]->(R)
MERGE
  (P5)-[:HAS_ROLE {roll:['systemutvecklare']}]->(R)
MERGE
  (P1)-[:HAS_CHEF]->(CHEF)
MERGE
  (P2)-[:HAS_CHEF]->(CHEF)
MERGE
  (P4)-[:HAS_CHEF]->(CHEF)
MERGE
  (P5)-[:HAS_CHEF]->(CHEF)

// SKapa ledighetsasökan för P1
MATCH (P1:Person {kortid:'88880001'})
MATCH (LS:Ledighetstyp {typ:'Semester'})
CREATE (L1:Ledighet {id:apoc.create.uuid(), godkänd:false})
MERGE (P1)-[:REQUESTS]->(L1)
MERGE (L1)-[:TYPE]->(LS)
WITH L1
CALL ga.timetree.events.attach({node: L1, time: apoc.date.parse('2019/06/19','ms','yyyy/MM/dd'), relationshipType: "FROM"})
YIELD node as node1
CALL ga.timetree.events.attach({node: L1, time: apoc.date.parse('2019/07/12','ms','yyyy/MM/dd'), relationshipType: "TOM"})
YIELD node RETURN node

// SKapa ledighetsansökan för p2
MATCH (P1:Person {kortid:'88880002'})
MATCH (LS:Ledighetstyp {typ:'Semester'})
CREATE (L1:Ledighet {id:apoc.create.uuid(), godkänd:false})
MERGE (P1)-[:REQUESTS]->(L1)
MERGE (L1)-[:TYPE]->(LS)
WITH L1
CALL ga.timetree.events.attach({node: L1, time: apoc.date.parse('2019/06/25','ms','yyyy/MM/dd'), relationshipType: "FROM"})
YIELD node as node1
CALL ga.timetree.events.attach({node: L1, time: apoc.date.parse('2019/07/30','ms','yyyy/MM/dd'), relationshipType: "TOM"})
YIELD node RETURN node

//Rensa allt
MATCH (n) DETACH DELETE (n)