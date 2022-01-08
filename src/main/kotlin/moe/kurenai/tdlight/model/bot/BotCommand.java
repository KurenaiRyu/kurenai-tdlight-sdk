package moe.kurenai.tdlight.model.bot;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BotCommand(
        @JsonProperty("command") String command,
        @JsonProperty("description") String description
) {
}
