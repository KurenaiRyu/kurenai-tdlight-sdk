package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Optional

data class User(

    @JsonProperty("id")
    val id: Long,

    @JsonProperty("is_bot")
    val isBot: Boolean,

    @JsonProperty("first_name")
    val firstName: String,

    @JsonProperty("last_name")
    val lastName: String? = null,

    @JsonProperty("username")
    val username: String? = null,

    @JsonProperty("language_code")
    val languageCode: String? = null,

    @JsonProperty("can_join_groups")
    val canJoinGroups: Boolean? = null,

    @JsonProperty("can_read_all_group_messages")
    val canReadAllGroupMessages: Boolean? = null,

    @JsonProperty("supports_inline_queries")
    val supportsInlineQueries: Boolean? = null,
) {
}