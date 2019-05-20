CREATE (P1:Person {kortid:'88880001',namn:'Tobias Leo', antaldagar:'28', sparadedagar:'0'})
CREATE (P2:Person {kortid:'88880002', namn:'Pontus Ellström', antaldagar:'28', sparadedagar:'10'})
CREATE (P4:Person {kortid:'88880003', namn:'Mikael Ivarsson', antaldagar:'28', sparadedagar:'10'})
CREATE (P5:Person {kortid:'88880004',namn:'Daniel Edvinsson', antaldagar:'28', sparadedagar:'0'})
CREATE (CHEF:Person {kortid:'88880005', namn:'Mr T', antaldagar:'28', sparadedagar:'5'})

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
  (P5)-[:HAS_CHEF]->(CHEF);