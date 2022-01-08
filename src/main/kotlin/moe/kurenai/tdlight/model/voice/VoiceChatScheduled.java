package moe.kurenai.tdlight.model.voice;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VoiceChatScheduled(
        @JsonProperty("start_date") int startDate
) {
}
