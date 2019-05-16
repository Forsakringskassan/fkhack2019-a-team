// Skapa basic data
CREATE (P1:Person {kortid:'88880001',namn:'Test1 Testorsson', antaldagar:'28', sparadedagar:'0'})
CREATE (P2:Person {kortid:'88880002', namn:'Test2 Testorsson', antaldagar:'28', sparadedagar:'10'})
CREATE (R:Roll {title:'Systemutvecklare'})
CREATE (LS:Ledighet {typ:'Semester'})
CREATE (LF:Ledighet {typ:'Föräldraledighet'})
CREATE (LT:Ledighet {typ:'Tjänsteledighet'})
CREATE (LF:Ledighet {typ:'Flexledighet'})
CREATE (G:GODKAND {godkand:'0'})

MERGE
  (P1)-[:HAS_ROLE {roll:['systemutvecklare']}]->(R)
  (P2)-[:HAS_ROLE {roll:['systemutvecklare']}]->(R)