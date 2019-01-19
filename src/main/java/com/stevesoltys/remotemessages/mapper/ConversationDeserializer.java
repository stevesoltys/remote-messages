package com.stevesoltys.remotemessages.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.stevesoltys.remotemessages.model.conversation.ConversationMessages;
import com.stevesoltys.remotemessages.model.conversation.ConversationParticipant;
import com.stevesoltys.remotemessages.model.message.Message;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Steve Soltys
 */
@Log4j2
public class ConversationDeserializer extends JsonDeserializer<ConversationMessages> {

    @Override
    public ConversationMessages deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectCodec codec = parser.getCodec();
        ArrayNode node = codec.readTree(parser);

        Map<String, String> participantMap = codec.treeAsTokens(node.get(0)).
                readValueAs(new TypeReference<Map<String, String>>() {

                });

        Set<ConversationParticipant> participants = participantMap.entrySet().stream()
                .map(entry -> new ConversationParticipant(Long.valueOf(entry.getKey()), entry.getValue()))
                .collect(Collectors.toSet());

        List<Message> messages = new ArrayList<>();

        for (int index = 3; index < node.size(); index++) {
            try {
                messages.add(codec.treeToValue(node.get(index), Message.class));

            } catch(Exception ex) {
                log.error(ex);
            }
        }

        return ConversationMessages.builder()
                .participants(participants)
                .messages(messages)
                .build();
    }
}
