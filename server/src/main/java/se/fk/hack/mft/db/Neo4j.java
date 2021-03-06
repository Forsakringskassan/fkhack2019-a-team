package se.fk.hack.mft.db;

import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import se.fk.hack.mft.vo.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Neo4j {
    private static final Driver driver = GraphDatabase.driver("bolt://localhost:7687");
    private static final String ID = "kortid";
    private static final int ONE_DAY = 1;

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
                        "MATCH (l)-[:TYPE]->(lt:Ledighetstyp)\n" +
                        "MATCH (l)-[:FROM]->(fday:Day)<-[:CHILD]-(fmonth:Month)<-[:CHILD]-(fyear:Year)\n" +
                        "MATCH (l)-[:TOM]->(tday:Day)<-[:CHILD]-(tmonth:Month)<-[:CHILD]-(tyear:Year)\n" +
                        "WHERE p.%2$s = { %2$s }\n" +
                        "RETURN\n" +
                        "    l.from AS from,\n" +
                        "    l.tom AS tom,\n" +
                        "    l.id AS id,\n" +
                        "    l.godkand AS godkand,\n" +
                        "    lt.id AS ledighetstypid,\n" +
                        "    lt.typ AS ledighetstyp", Labels.Person.name(), ID),
                params);

        List<Ledighet> ledigheter = new ArrayList<>();

        result.list().stream().forEach(record -> ledigheter.add(new Ledighet(
                record.get("id").asString(),
                record.get("from").asString(),
                record.get("tom").asString(),
                new Ledighetstyp(record.get("ledighetstypid").asString(), record.get("ledighetstyp").asString()),
                record.get("godkand").asBoolean())));

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
                "MATCH (user:%1$s) WHERE user.%2$s = { %2$s }\n" +
                        "MATCH (ledighetstyp:Ledighetstyp {typ: { ledighetstyp }})\n" +
                        "CREATE (ledighet:Ledighet {id: apoc.create.uuid(), godkand: false, from: { from }, tom: { tom }})\n" +
                        "MERGE (user)-[:REQUESTS]->(ledighet)\n" +
                        "MERGE (ledighet)-[:TYPE]->(ledighetstyp)\n" +
                        "WITH user, ledighet\n" +
                        "CALL ga.timetree.events.attach({node: ledighet, time: apoc.date.parse({ from },'ms','yyyy-MM-dd'), relationshipType: 'FROM'})\n" +
                        "YIELD node AS n1\n" +
                        "CALL ga.timetree.events.attach({node: ledighet, time: apoc.date.parse({ tom },'ms','yyyy-MM-dd'), relationshipType: 'TOM'})\n" +
                        "YIELD node AS n2\n" +
                        "RETURN user",
                Labels.Person.name(), ID),
                params);

        return result.hasNext();
    }

    public static boolean setStatusLedighet(LedighetStatusRequest request) {
        Session session = driver.session();

        Map<String, Object> params = new HashMap<>();
        params.put("id", request.getId());
        params.put("status", request.getStatus());

        StatementResult result = session.run(
                "MATCH (ledighet:Ledighet) WHERE ledighet.id = { id } SET ledighet.godkand = { status } RETURN ledighet",
                params);

        return result.hasNext();
    }

    public static List<Ledighetstyp> ledighetstyper() {
        Session session = driver.session();

        StatementResult result = session.run(
                "MATCH (ledighetstyp:Ledighetstyp) RETURN ledighetstyp.id AS id, ledighetstyp.typ AS namn");

        return result.list().stream().map(record -> new Ledighetstyp(record.get("id").asString(), record.get("namn").asString())).collect(Collectors.toList());
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

    public static List<UserLedighetRange> userLedighetRange(List<String> kortidn, String from, String tom) throws ParseException {
        Session session = driver.session();

        Map<String, Object> params = new HashMap<>();
        params.put("kortidn", kortidn);
        params.put("from", from);
        params.put("tom", tom);

        StatementResult result = session.run(String.format(
                "WITH { kortidn } AS kortidn\n" +
                        "UNWIND kortidn AS kortid\n" +
                        "MATCH (p:Person)--(l:Ledighet)--(lt:Ledighetstyp)\n" +
                        "WHERE p.kortid = kortid \n" +
                        "AND NOT ({ from } < l.from AND { tom } < l.from)\n" +
                        "AND NOT ({ from } > l.tom AND { tom } > l.tom)\n" +
                        "RETURN p.kortid AS kortid, p.namn AS namn, l AS ledighet, lt.id AS ledighetstypid, lt.typ AS ledighetstypnamn"),
                params);


        Map<String, UserLedighetRange> ledighetMap = new HashMap<>();
        kortidn.stream().forEach(kortid -> {
            UserLedighetRange ulr = new UserLedighetRange();
            ulr.setKortid(kortid);
            ledighetMap.put(kortid, ulr);

        });

        result.list().stream().forEach(record -> {
            String kortid = record.get("kortid").asString();
            String namn = record.get("namn").asString();
            Node ledighet = record.get("ledighet").asNode();
            Ledighetstyp ledighetstyp = new Ledighetstyp(
                    record.get("ledighetstypid").asString(),
                    record.get("ledighetstypnamn").asString());

            UserLedighetRange ledighetRange = ledighetMap.get(kortid);
            ledighetRange.setKortid(kortid);
            ledighetRange.setNamn(namn);
            ledighetRange.addLedighet(
                    new Ledighet(
                            ledighet.get("id").asString(),
                            ledighet.get("from").asString(),
                            ledighet.get("tom").asString(),
                            ledighetstyp,
                            ledighet.get("godkand").asBoolean()));
        });

        for (UserLedighetRange l : ledighetMap.values()) {
            // Fill in missing use info if no ledighet.
            if (l.getNamn() == null) {
                User u = Neo4j.getUser(new UserIdRequest(l.getKortid()));
                l.setNamn(u.getNamn());
            }

            // Use calendar object to traverse calendar range.
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Calendar iterator = Calendar.getInstance();
            iterator.setTime(format.parse(from));

            Calendar end = Calendar.getInstance();
            end.setTime(format.parse(tom));

            // Iterate all dates in range for user to determine daily ledighetstyp.
            while (!iterator.after(end)) {
                String dateString = format.format(iterator.getTime());
                l.handleDate(dateString);
                iterator.add(Calendar.DATE, ONE_DAY);
            }
        }

        // Take values from map and return them in list.
        List<UserLedighetRange> userLedigheter = new ArrayList<>();
        ledighetMap.values().stream().forEach(userLedigheter::add);
        return userLedigheter;
    }
}
