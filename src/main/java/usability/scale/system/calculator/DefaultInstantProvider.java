package usability.scale.system.calculator;

import java.time.Instant;

public class DefaultInstantProvider implements InstantProvider {
    @Override
    public Instant now() {
        return Instant.now();
    }
}
