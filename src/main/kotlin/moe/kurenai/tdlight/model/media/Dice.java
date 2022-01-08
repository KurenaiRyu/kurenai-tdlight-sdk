package moe.kurenai.tdlight.model.media;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Dice(
        @JsonProperty("emoji") String emoji,
        @JsonProperty("value") int value
) {
}
