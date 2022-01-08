package moe.kurenai.tdlight.model.poll;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PollOption(
        @JsonProperty("text") String text,
        @JsonProperty("voter_count") int voterCount
) {
}
