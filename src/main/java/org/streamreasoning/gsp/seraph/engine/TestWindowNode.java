package org.streamreasoning.gsp.seraph.engine;


import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowType;

import java.time.Duration;

public class TestWindowNode implements WindowNode {
    @Override
    public String iri() {
        return "w1";
    }

    @Override
    public boolean named() {
        return true;
    }

    @Override
    public WindowType getType() {
        return WindowType.TIME_BASED;
    }

    @Override
    public long getT0() {
        return 0L;
    }

    @Override
    public long getRange() {
        return Duration.ofSeconds(10).toMillis();
    }

    @Override
    public long getStep() {
        return Duration.ofSeconds(10).toMillis();
    }

    @Override
    public String getUnitRange() {
        return "millis";
    }

    @Override
    public String getUnitStep() {
        return "millis";
    }
}
