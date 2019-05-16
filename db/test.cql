// Skapa basic data
CREATE (P1:Person {kortid:'88880001',namn:'Test1 Testorsson', antaldagar:'28', sparadedagar:'0'})
CREATE (P2:Person {kortid:'88880002', namn:'Test2 Testorsson', antaldagar:'28', sparadedagar:'10'})
CREATE (CHEF:Person {kortid:'88880003', namn:'Chef Testorsson', antaldagar:'28', sparadedagar:'5'})
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
   (P1)-[:HAS_CHEF]->(CHEF)
MERGE
   (P2)-[:HAS_CHEF]->(CHEF)


// Skapa ansökan
MATCH (P:Person {kortid:'88880001'})
MATCH (LT:Ledighetstyp {typ:'Flexledighet'})
CREATE (L:Ledighet {id:apoc.create.uuid(), godkänd:false})
MERGE (P)-[:REQUESTS]->(L)
MERGE (L)-[:TYPE]->(LT)


//Test skapa ledighet med from och tom
CREATE (P2:Person {kortid:'88880002', namn:'Test2 Testorsson', antaldagar:'28', sparadedagar:'10'})
CREATE (LS:Ledighetstyp {typ:'Semester'})
CREATE (L:Ledighet {id:apoc.create.uuid(), godkänd:false})
MERGE (P2)-[:REQUESTS]->(L)
MERGE (L)-[:TYPE]->(LS)
WITH L
CALL ga.timetree.events.attach({node: L, time: apoc.date.parse('2015/03/25','ms','yyyy/MM/dd'), relationshipType: "FROM"})
YIELD node as node1
CALL ga.timetree.events.attach({node: L, time: apoc.date.parse('2015/04/25','ms','yyyy/MM/dd'), relationshipType: "TOM"})
YIELD node RETURN node

//Rensa allt
MATCH (n) DETACH DELETE (n)