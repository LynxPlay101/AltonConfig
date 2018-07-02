package me.lynxplay.altonconfig;

import me.lynxplay.altonconfig.annotation.ConfigAutoCreate;
import me.lynxplay.altonconfig.annotation.method.Builder;
import me.lynxplay.altonconfig.annotation.method.DefaultValue;
import me.lynxplay.altonconfig.annotation.method.Placeholder;
import me.lynxplay.altonconfig.annotation.method.Value;

import java.util.Arrays;

@ConfigAutoCreate
public interface TestConfig extends AltonConfig {

    @Value("spell.notFound")
    @DefaultValue("The spell %spell% could not be found")
    String spellNotFound(@Placeholder("%spell%") String spell);

    @Value("server.ports")
    int[] availablePorts();

    @Builder("server.ports")
    default int[] _buildPorts(String input) {
        if (input == null || input.isEmpty()) return new int[0];

        return Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
    }

}