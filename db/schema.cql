CREATE CONSTRAINT ON (p:Person) ASSERT p.kortid IS UNIQUE;
CREATE CONSTRAINT ON (r:Roll) ASSERT r.title IS UNIQUE;
CREATE CONSTRAINT ON (l:Ledighetstyp) ASSERT l.id IS UNIQUE;
CREATE CONSTRAINT ON (l:Ledighet) ASSERT l.id IS UNIQUE;