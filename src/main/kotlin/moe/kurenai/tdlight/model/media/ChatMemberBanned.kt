package moe.kurenai.tdlight.model.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import moe.kurenai.tdlight.model.chat.ChatMember;
import moe.kurenai.tdlight.model.message.User;

public record ChatMemberBanned(
        @JsonProperty("status") String status,
        @JsonProperty("user") User user,
        @JsonProperty("until_date") long untilDate
) implements ChatMember {
}
