package com.stevesoltys.remotemessages.model.conversation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Steve Soltys
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConversationParticipant {

    private long id;

    private String name;
}
