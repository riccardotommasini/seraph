package org.streamreasoning.gsp.services;

import org.springframework.stereotype.Service;
import org.streamreasoning.gsp.seraph.data.PGStream;
import org.streamreasoning.gsp.seraph.data.PGraph;
import org.streamreasoning.gsp.seraph.data.PGraphImpl;
import org.streamreasoning.gsp.seraph.data.Source;
import org.streamreasoning.gsp.seraph.engine.ContentPGraph;
import org.streamreasoning.gsp.seraph.engine.Neo4jContinuousQueryExecutionImpl;
import org.streamreasoning.gsp.seraph.engine.QueryFactory;
import org.streamreasoning.gsp.seraph.engine.Seraph;
import org.streamreasoning.gsp.seraph.engine.windowing.SeraphStreamToRelationOp;
import org.streamreasoning.gsp.seraph.syntax.SeraphQL;
import org.streamreasoning.rsp4j.api.engine.features.QueryRegistrationFeature;
import org.streamreasoning.rsp4j.api.engine.features.StreamRegistrationFeature;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SeraphService {

    private final Seraph seraph;
    private final AtomicInteger eventCounter = new AtomicInteger(1);
    private ContinuousQueryExecution<PGraph, PGraph, Map<String, Object>, Map<String, Object>> cqe;
    private ContentPGraph content;
    private SeraphStreamToRelationOp s2r;

    public SeraphService(Seraph seraph) {
        this.seraph = seraph;
    }

    public String print() {
        return seraph.name;
    }

    public ContinuousQueryExecution<PGraph, PGraph, Map<String, Object>, Map<String, Object>> register(SeraphString seraphQL, String stream) {
        SeraphQL q = parse(seraphQL);
        q.setInputStream(stream);
        q.setOutputStream("http://" + seraphQL.getID());
        cqe = seraph.register(q);

        //register the parsed seraph query as Neo4jContinuousQueryExecution
        Neo4jContinuousQueryExecutionImpl cqe2 = (Neo4jContinuousQueryExecutionImpl) cqe;

        //With this i can see the snapshot graph
        this.s2r = (SeraphStreamToRelationOp) cqe2.s2rs()[0];

        return cqe;
    }

    public ContentPGraph getContent(long ts) {
        return (ContentPGraph) s2r.content(ts);
    }

    public void unregisterQuery(SeraphString s) {

    }

    public PGraph nextEvent(String event, String stream) {
        System.out.println(event);
        URL url = Source.class.getClassLoader().getResource(event + ".json");
        try (FileReader fileReader = new FileReader(url.getPath())) {
            PGraph pGraph2 = PGraphImpl.fromJson(fileReader);
            seraph.get(stream).ifPresent(s -> s.put(pGraph2, System.currentTimeMillis()));
            return pGraph2;
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public PGraph nextEvent(String event) {
        eventCounter.compareAndSet(10, 1);
        String fileName = "testGraph" + (eventCounter.getAndIncrement());
        return nextEvent(fileName, event);
    }

    public void send(String stream, PGraph e) {
        seraph.get(stream).ifPresent(s -> s.put(e, System.currentTimeMillis()));
    }

    public PGStream register(PGStream s) {
        return seraph.register(s);
    }


    public SeraphQL parse(SeraphString value) {
        try {
            return QueryFactory.parse(value.getQuery());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
