package se.fk.hack.mft.db;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import se.fk.hack.mft.vo.UserIdRequest;
import se.fk.hack.mft.vo.User;

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
}
