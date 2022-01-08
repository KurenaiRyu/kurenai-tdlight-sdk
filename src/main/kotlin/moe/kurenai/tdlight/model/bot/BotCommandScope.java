package moe.kurenai.tdlight.model.bot;

import java.util.OptionalLong;

public interface BotCommandScope {

    String getType();

    default OptionalLong getChatId() {
        return OptionalLong.empty();
    }

    default OptionalLong getUserId() {
        return OptionalLong.empty();
    }
}
