package moe.kurenai.tdlight.request.command

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.command.BotCommand
import moe.kurenai.tdlight.model.command.BotCommandScope
import moe.kurenai.tdlight.request.Request

/**
 * Use this method to get the current list of the bot's commands for the given scope and user language.
 * Returns an Array of BotCommand objects. If commands aren't set, an empty list is returned.
 * @author Kurenai
 * @since 2023/5/11 20:14
 */

class GetMyCommands(
    /**
     * A JSON-serialized object, describing scope of users for which the commands are relevant. Defaults to
     * BotCommandScopeDefault.
     */
    val scope: BotCommandScope? = null,
    /**
     * A two-letter ISO 639-1 language code. If empty, commands will be applied to all users from the given scope,
     * for whose language there are no dedicated commands
     */
    val languageCode: String? = null
) : Request<ResponseWrapper<List<BotCommand>>>() {

    @JsonIgnore
    override val method = "setMyCommands"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<List<BotCommand>>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST
}