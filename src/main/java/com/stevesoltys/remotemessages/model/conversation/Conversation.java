package com.stevesoltys.remotemessages.model.conversation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Steve Soltys
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {

    @JsonProperty("isGroupConv")
    private boolean group;

    @JsonProperty("failedMsgCount")
    private int failedMessages;

    @JsonProperty("unreadMsgCount")
    private int unreadMessages;

    private String name;

    private int prefserv;

    @JsonProperty("hash")
    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "s")
    private Date date;
}
