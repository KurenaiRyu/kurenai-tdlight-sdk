package moe.kurenai.tdlight.model.media;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;
import java.util.OptionalLong;

public record File(
        @JsonProperty("file_id") String fileId,
        @JsonProperty("file_unique_id") String fileUniqueId,
        @JsonProperty("file_size") OptionalLong fileSize,
        @JsonProperty("file_path") Optional<String> filePath
) {
}
