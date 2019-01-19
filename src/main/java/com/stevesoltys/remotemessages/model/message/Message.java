package com.stevesoltys.remotemessages.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stevesoltys.remotemessages.mapper.NumericBooleanDeserializer;
import com.stevesoltys.remotemessages.mapper.ReverseNumericBooleanDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Steve Soltys
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Message {

    @JsonProperty("isRead")
    @JsonDeserialize(using = NumericBooleanDeserializer.class)
    private boolean read;

    @JsonProperty("guid")
    private String id;

    @JsonProperty("error_status")
    @JsonDeserialize(using = NumericBooleanDeserializer.class)
    private boolean failure;

    private List<MessagePart> messageParts = new LinkedList<>();

    @JsonProperty("isSent")
    private boolean sent;

    @JsonProperty("iMsIM")
    @JsonDeserialize(using = ReverseNumericBooleanDeserializer.class)
    private boolean sms;

    @JsonProperty("date")
    private long timestamp;

    private String timeRead;

    @JsonProperty("isDelivered")
    @JsonDeserialize(using = NumericBooleanDeserializer.class)
    private boolean delivered;

    @JsonProperty("particID")
    private long participantId;
}
