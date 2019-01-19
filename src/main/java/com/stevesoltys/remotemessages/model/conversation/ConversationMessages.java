package com.stevesoltys.remotemessages.model.conversation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stevesoltys.remotemessages.mapper.ConversationDeserializer;
import com.stevesoltys.remotemessages.model.message.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Steve Soltys
 */
@JsonDeserialize(using = ConversationDeserializer.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationMessages {

    private String identifier;

    @Builder.Default
    private Set<ConversationParticipant> participants = new HashSet<>();

    @Builder.Default
    private List<? extends Message> messages = new ArrayList<>();

}
