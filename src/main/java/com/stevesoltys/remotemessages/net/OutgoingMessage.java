package com.stevesoltys.remotemessages.net;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

import java.util.Set;
import java.util.UUID;

/**
 * @author Steve Soltys
 */
@Builder
@Data
public class OutgoingMessage {

    @NonNull
    private String conversationId;

    @NonNull
    private String message;

    @Singular
    @NonNull
    private Set<String> recipients;

    @NonNull
    @Builder.Default
    private UUID requestId = UUID.randomUUID();
}
