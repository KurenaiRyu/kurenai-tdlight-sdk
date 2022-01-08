package moe.kurenai.tdlight.model.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.tobee.telegram.model.message.User;

import java.util.Optional;

public record ChatJoinRequest(
        @JsonProperty("chat") Chat chat,
        @JsonProperty("from") User from,
        @JsonProperty("date") long date,
        @JsonProperty("bio") Optional<String> bio,
        @JsonProperty("invite_link") Optional<dev.tobee.telegram.model.chat.ChatInviteLink> inviteLink
) {
}
