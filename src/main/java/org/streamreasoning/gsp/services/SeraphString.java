package org.streamreasoning.gsp.services;

import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.Aggregation;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

import java.util.List;
import java.util.Map;

public class SeraphString implements ContinuousQuery {

    private final String query;

    public SeraphString(String query) {
        this.query = query;
    }


    @Override
    public void addNamedWindow(String streamUri, WindowNode wo) {

    }

    @Override
    public void setIstream() {

    }

    @Override
    public void setRstream() {

    }

    @Override
    public void setDstream() {

    }

    @Override
    public boolean isIstream() {
        return false;
    }

    @Override
    public boolean isRstream() {
        return false;
    }

    @Override
    public boolean isDstream() {
        return false;
    }

    @Override
    public void setSelect() {

    }

    @Override
    public void setConstruct() {

    }

    @Override
    public boolean isSelectType() {
        return false;
    }

    @Override
    public boolean isConstructType() {
        return false;
    }

    @Override
    public DataStream getOutputStream() {
        return null;
    }

    @Override
    public void setOutputStream(String uri) {

    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public Map<? extends WindowNode, DataStream> getWindowMap() {
        return null;
    }

    @Override
    public Time getTime() {
        return null;
    }

    @Override
    public RelationToRelationOperator r2r() {
        return null;
    }

    @Override
    public StreamToRelationOp[] s2r() {
        return new StreamToRelationOp[0];
    }

    @Override
    public RelationToStreamOperator r2s() {
        return null;
    }

    @Override
    public List<Aggregation> getAggregations() {
        return null;
    }

    public String getQuery() {
        return query;
    }
}
