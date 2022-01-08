package moe.kurenai.tdlight.model.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import moe.kurenai.tdlight.model.chat.ChatMember;
import moe.kurenai.tdlight.model.message.User;

public record ChatMemberMember(
        @JsonProperty("status") String status,
        @JsonProperty("user") User user
) implements ChatMember {
}
