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

    public static List<User> getCollegues(UserIdRequest request) {
        Session session = driver.session();

        Map<String, Object> params = new HashMap<>();
        params.put(ID, request.getKortid());

        StatementResult result = session.run(String.format(
                "MATCH (user:%1$s)-[:HAS_CHEF]->()<-[:HAS_CHEF]-(collegue:%1$s) WHERE user.%2$s = { %2$s } RETURN collegue", Labels.Person.name(), ID),
                params);

        // Row in result
        List<Record> records = result.list();

        List<User> collegues = new ArrayList<>(records.size());

        System.out.println(records.size());

        records.stream().map(record -> record.get("collegue").asNode()).forEach(node -> collegues.add(new User(node.get("kortid").asString(), node.get("namn").asString())));

        return collegues;
    }

    public static boolean isManager(UserIdRequest request) {
        Session session = driver.session();

        Map<String, Object> params = new HashMap<>();
        params.put(ID, request.getKortid());

        StatementResult result = session.run(String.format(
                "MATCH (user:%1$s)<-[:HAS_CHEF]-() WHERE user.%2$s = { %2$s } RETURN user LIMIT 1", Labels.Person.name(), ID),
                params);

        // Any match means manager of at least someone.
        return result.list().size() > 0;
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
                        "    toString(fyear.value)+\"-\"+toString(fmonth.value)+\"-\"+toString(fday.value) AS from,\n" +
                        "    toString(tyear.value)+\"-\"+toString(tmonth.value)+\"-\"+toString(tday.value) AS tom,\n" +
                        "    l.godkänd AS godkänd", Labels.Person.name(), ID),
                params);

        List<Record> records = result.list();

        List<Ledighet> ledigheter = new ArrayList<>(records.size());

        records.stream().forEach(record -> {
            ledigheter.add(new Ledighet(record.get("from").asString(), record.get("tom").asString(), record.get("godkänd").asBoolean()));
        });

        return ledigheter;
    }

    public static boolean userCreateLedighet(UserLedighetRequest request) {
        return false;
    }
}
