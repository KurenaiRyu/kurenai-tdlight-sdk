package moe.kurenai.tdlight.model.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import moe.kurenai.tdlight.model.chat.ChatMember;
import moe.kurenai.tdlight.model.message.User;

import java.util.Optional;

public record ChatMemberOwner(
        @JsonProperty("status") String status,
        @JsonProperty("user") User user,
        @JsonProperty("is_anonymous") boolean isAnonymous,
        @JsonProperty("custom_title") Optional<String> customTitle
) implements ChatMember {
}
