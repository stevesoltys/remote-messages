package com.stevesoltys.remotemessages.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Steve Soltys
 */
@JsonTypeInfo(property = "type", use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "text", value = TextMessagePart.class),
        @JsonSubTypes.Type(name = "media", value = MediaMessagePart.class)
})
public class MessagePart {

    @JsonProperty("partid")
    @Getter
    @Setter
    private String id;
}
