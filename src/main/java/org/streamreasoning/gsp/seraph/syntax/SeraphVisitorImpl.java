package org.streamreasoning.gsp.seraph.syntax;

import org.antlr.v4.runtime.tree.ParseTree;
import org.streamreasoning.gsp.seraph.syntax.parser.SeraphBaseVisitor;
import org.streamreasoning.gsp.seraph.syntax.parser.SeraphParser;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;

import java.time.Duration;
import java.util.*;

public class SeraphVisitorImpl extends SeraphBaseVisitor<ContinuousQuery> {

    private Map<String, S2R> inputs = new HashMap<>();
    private Map<String, R2S> outputs = new HashMap<>();
    private Map<String, String> inputParameters = new HashMap<>();
    private Map<String, Object> outputParameters = new HashMap<>();
    private List<String> projections = new ArrayList<>();
    private Map<String, List<StringBuilder>> relationParameters = new HashMap<>();

    public SeraphVisitorImpl() {

        relationParameters.put("range", new ArrayList<>());
        relationParameters.put("r2r", new ArrayList<>(Collections.singletonList(new StringBuilder())));

    }


    @Override
    public ContinuousQuery visitOC_Seraph(SeraphParser.OC_SeraphContext ctx) {

        // *S2R Part
        inputParameters.put("identifier", ctx.id.getText().replace("<", "").replace(">", ""));

        String defaultInputStream = null;

        inputParameters.put("input", defaultInputStream);
        inputParameters.put("starting", ctx.starting_time.getText());


        // *R2S Part
        Duration period = Duration.parse(ctx.range.getText().trim());
        String streamOperator = ctx.stream_op.getText();

        String defaultOutputStream = null;

        outputParameters.put("output", defaultOutputStream);
        outputParameters.put("period", period.toString());
        outputParameters.put("streamOperator", streamOperator);
        outputParameters.put("projections", projections);

        return super.visitOC_Seraph(ctx);
    }

    @Override
    public ContinuousQuery visitOC_ProjectionBody(SeraphParser.OC_ProjectionBodyContext ctx) {

        return super.visitOC_ProjectionBody(ctx);
    }

    @Override
    public ContinuousQuery visitOC_ProjectionItem(SeraphParser.OC_ProjectionItemContext ctx) {
        String text = "";
        if (ctx.children.size() > 1) {
            text = ctx.children.get(ctx.children.size() - 1).getText();
        } else
            text = ctx.children.get(0).getText();
        projections.add(text);
        return super.visitOC_ProjectionItem(ctx);
    }

    @Override
    public ContinuousQuery visitOC_ProjectionItems(SeraphParser.OC_ProjectionItemsContext ctx) {

        return super.visitOC_ProjectionItems(ctx);
    }

    @Override
    public ContinuousQuery visitOC_Return(SeraphParser.OC_ReturnContext ctx) {

        outputParameters.put("returnStatement", ctx.children.get(0).getText());
        String returnStatement = ctx.children.get(1).getText();


        for (StringBuilder r2r : relationParameters.get("r2r")) {
            r2r.append(" RETURN").append(returnStatement);
        }

        relationParameters.get("r2r").remove(relationParameters.get("r2r").size() - 1);

        return super.visitOC_Return(ctx);
    }


    @Override
    public ContinuousQuery visitOC_Match(SeraphParser.OC_MatchContext ctx) {

        StringBuilder cypherMatch = new StringBuilder();

        for (ParseTree subTree : ctx.children) {
            if (subTree.getClass() == SeraphParser.OC_WithinContext.class) {
                String range = ((SeraphParser.OC_WithinContext) subTree).ISO8601_DURATION().getText().trim();

                relationParameters.get("range").add(new StringBuilder(range));
            } else {
                cypherMatch.append(subTree.getText());

            }
        }


        relationParameters.get("r2r").get(relationParameters.get("r2r").size() - 1).append(cypherMatch);
        relationParameters.get("r2r").add(new StringBuilder());

        return super.visitOC_Match(ctx);
    }


    @Override
    public ContinuousQuery visitOC_With(SeraphParser.OC_WithContext ctx) {
        relationParameters.get("r2r").get(relationParameters.get("r2r").size() - 1).append(ctx.getText());

        return super.visitOC_With(ctx);
    }

    public ContinuousQuery visitOC_Unwind(SeraphParser.OC_UnwindContext ctx) {
        relationParameters.get("r2r").get(relationParameters.get("r2r").size() - 1).append(ctx.getText());

        return super.visitOC_Unwind(ctx);
    }

    //returns the parsed seraph query
    public SeraphQL getQuery() {

        System.out.println("-----------------PARSING-----------------");

        System.out.println("input:    " + inputParameters);
        System.out.println("relation: " + relationParameters);
        System.out.println("output:   " + outputParameters);

        String query = relationParameters.get("r2r").get(0).toString();

        String inputStream = inputParameters.get("input");
        String startingTime = inputParameters.get("starting");
        Duration range = Duration.parse(relationParameters.get("range").get(0));

        inputs.put(inputStream, new S2R(startingTime, range));

        String outputStream = (String) outputParameters.get("output");
        String streamOperator = (String) outputParameters.get("streamOperator");
        Duration period = Duration.parse((CharSequence) outputParameters.get("period"));

        outputs.put(outputStream, new R2S(streamOperator, period, null));

        System.out.println();

        return new SeraphQL(new R2R(query), inputs, outputs, projections);
    }


    /*
    @Override
    //Gets the input stream and window details from the query
    public ContinuousQuery visitOS_S2R(SeraphParser.OS_S2RContext ctx) {
        String input = ctx.input.getText().replace("<", "").replace(">", "");
        String starting = ctx.starting.getText();
        Duration range = Duration.parse(ctx.range.getText());

        inputs.put(input, new S2R(starting, range));

        return super.visitOS_S2R(ctx);
    }


    //gets the Cypher.g4 query from the seraph query
    @Override
    public ContinuousQuery visitOS_R2R(SeraphParser.OS_R2RContext ctx) {
        //Cypher.g4 query
        this.cypher = ctx.getText();
        return super.visitOS_R2R(ctx);
    }


    */


    /*
    @Override
    public ContinuousQuery visitOS_R2S(SeraphParser.OS_R2SContext ctx) {

        Duration periodD = null;
        Integer periodE = null;

        if (ctx.period.oS_Duration() != null) {
            periodD = Duration.parse(ctx.period.oS_Duration().getText());
        } else
            periodE = Integer.parseInt(ctx.period.oS_EventRange().oC_IntegerLiteral().getText());

        String op = ctx.op.getText().replace("<", "").replace(">", "");
        String output = ctx.output.getText();

        outputs.put(output, new R2S(op, periodD, periodE));

        return super.visitOS_R2S(ctx);
    }
    */


}
