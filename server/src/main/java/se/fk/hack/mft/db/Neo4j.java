package se.fk.hack.mft.db;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import se.fk.hack.mft.vo.Ledighet;
import se.fk.hack.mft.vo.UserIdRequest;
import se.fk.hack.mft.vo.User;
import se.fk.hack.mft.vo.UserLedighetRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Neo4j {
    private static Driver driver = GraphDatabase.driver("bolt://localhost:7687");
    private static final String ID = "kortid";

    public static User getUser(UserIdRequest request) {
        Session session = driver.session();

        Map<String, Object> params = new HashMap<>();
        params.put(ID, request.getKortid());

        StatementResult result = session.run(String.format(
                "MATCH (user:%1$s) WHERE user.%2$s = { %2$s } RETURN user LIMIT 1", Labels.Person.name(), ID),
                params);

        // Row in result
        Record record = result.single();

        // Field in row
        Node user = record.get("user").asNode();

        return new User(
                user.get("kortid").asString(),
                user.get("namn").asString());
    }

    public static List<User> getMedarbetare(UserIdRequest request) {
        Session session = driver.session();

        Map<String, Object> params = new HashMap<>();
        params.put(ID, request.getKortid());

        StatementResult result = session.run(String.format(
                "MATCH (user:%1$s)<-[:HAS_CHEF]-(collegue:%1$s) WHERE user.%2$s = { %2$s } RETURN collegue", Labels.Person.name(), ID),
                params);

        return getUsersFromResult(result);
    }

    public static List<User> getCollegues(UserIdRequest request) {
        Session session = driver.session();

        Map<String, Object> params = new HashMap<>();
        params.put(ID, request.getKortid());

        StatementResult result = session.run(String.format(
                "MATCH (user:%1$s)-[:HAS_CHEF]->()<-[:HAS_CHEF]-(collegue:%1$s) WHERE user.%2$s = { %2$s } RETURN collegue", Labels.Person.name(), ID),
                params);

        return getUsersFromResult(result);
    }

    public static boolean isManager(UserIdRequest request) {
        Session session = driver.session();

        Map<String, Object> params = new HashMap<>();
        params.put(ID, request.getKortid());

        StatementResult result = session.run(String.format(
                "MATCH (user:%1$s)<-[:HAS_CHEF]-() WHERE user.%2$s = { %2$s } RETURN user LIMIT 1", Labels.Person.name(), ID),
                params);

        // Any match means manager of at least someone.
        return result.hasNext();
    }

    public static List<Ledighet> userGetLedighet(UserIdRequest request) {
        Session session = driver.session();

        Map<String, Object> params = new HashMap<>();
        params.put(ID, request.getKortid());

        StatementResult result = session.run(String.format(
                "MATCH (p:%1$s)-[:REQUESTS]->(l:Ledighet)\n" +
                        "MATCH (l)-[:FROM]->(fday:Day)<-[:CHILD]-(fmonth:Month)<-[:CHILD]-(fyear:Year)\n" +
                        "MATCH (l)-[:TOM]->(tday:Day)<-[:CHILD]-(tmonth:Month)<-[:CHILD]-(tyear:Year)\n" +
                        "WHERE p.%2$s = { %2$s }\n" +
                        "RETURN\n" +
                        "    toString(fyear.value)+'-'+toString(fmonth.value)+'-'+toString(fday.value) AS from,\n" +
                        "    toString(tyear.value)+'-'+toString(tmonth.value)+'-'+toString(tday.value) AS tom,\n" +
                        "    l.godkänd AS godkänd", Labels.Person.name(), ID),
                params);

        List<Ledighet> ledigheter = new ArrayList<>();

        result.list().stream().forEach(record -> {
            ledigheter.add(new Ledighet(record.get("from").asString(), record.get("tom").asString(), record.get("godkänd").asBoolean()));
        });

        return ledigheter;
    }

    public static boolean userCreateLedighet(UserLedighetRequest request) {
        Session session = driver.session();

        Map<String, Object> params = new HashMap<>();
        params.put(ID, request.getKortid());
        params.put("from", request.getFrom());
        params.put("tom", request.getTom());
        params.put("ledighetstyp", request.getLedighetstyp());

        StatementResult result = session.run(String.format(
                "MATCH (user:%1$s) WHERE user.%2$s = { %2$s } \n" +
                        "MATCH (ledighetstyp:Ledighetstyp {typ: { ledighetstyp }}) \n" +
                        "CREATE (ledighet:Ledighet {id: apoc.create.uuid(), godkänd: false}) \n" +
                        "MERGE (user)-[:REQUESTS]->(ledighet) \n" +
                        "MERGE (ledighet)-[:TYPE]->(ledighetstyp) \n" +
                        "WITH user, ledighet \n" +
                        "CALL ga.timetree.events.attach({node: ledighet, time: apoc.date.parse({ from },'ms','yyyy-MM-dd'), relationshipType: 'FROM'}) \n" +
                        "YIELD node AS n1 \n" +
                        "CALL ga.timetree.events.attach({node: ledighet, time: apoc.date.parse({ tom },'ms','yyyy-MM-dd'), relationshipType: 'TOM'}) \n" +
                        "YIELD node AS n2 \n" +
                        "RETURN user",
                Labels.Person.name(), ID),
                params);

        return result.hasNext();
    }

    public static List<String> ledighetstyper() {
        Session session = driver.session();

        StatementResult result = session.run(String.format(
                "MATCH (ledighetstyp:%1$s) RETURN ledighetstyp.typ", Labels.Ledighetstyp.name()));

        return result.list().stream().map(record -> record.get(0).asString()).collect(Collectors.toList());
    }

    /**
     * From Neo4j {@link StatementResult } extract a list of simple users.
     * @param result
     * @return
     */
    private static List<User> getUsersFromResult(StatementResult result) {
        List<Record> records = result.list();

        List<User> users = new ArrayList<>(records.size());

        records.stream().map(record -> record.get(0).asNode()).forEach(node -> users.add(new User(node.get("kortid").asString(), node.get("namn").asString())));

        return users;
    }
}
