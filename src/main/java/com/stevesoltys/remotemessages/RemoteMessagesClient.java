package com.stevesoltys.remotemessages;

import com.burgstaller.okhttp.AuthenticationCacheInterceptor;
import com.burgstaller.okhttp.CachingAuthenticatorDecorator;
import com.burgstaller.okhttp.digest.CachingAuthenticator;
import com.burgstaller.okhttp.digest.Credentials;
import com.burgstaller.okhttp.digest.DigestAuthenticator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stevesoltys.remotemessages.model.conversation.Conversation;
import com.stevesoltys.remotemessages.model.conversation.ConversationMessages;
import com.stevesoltys.remotemessages.net.OutgoingMessage;
import com.stevesoltys.remotemessages.net.RemoteMessagesService;
import com.sun.istack.internal.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okio.BufferedSink;
import org.apache.logging.log4j.util.Strings;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The RemoteMessages client.
 *
 * @author Steve Soltys
 */
public class RemoteMessagesClient {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Getter
    private final String baseUrl;

    private final RemoteMessagesService remoteMessagesService;

    @Builder
    public RemoteMessagesClient(@NonNull String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        if(Strings.isNotEmpty(username) && Strings.isNotEmpty(password)) {
            DigestAuthenticator authenticator = new DigestAuthenticator(new Credentials(username, password));
            Map<String, CachingAuthenticator> authCache = new ConcurrentHashMap<>();

            clientBuilder.authenticator(new CachingAuthenticatorDecorator(authenticator, authCache));
            clientBuilder.addInterceptor(new AuthenticationCacheInterceptor(authCache));
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(clientBuilder.build())
                .addConverterFactory(JacksonConverterFactory.create(OBJECT_MAPPER))
                .build();

        remoteMessagesService = retrofit.create(RemoteMessagesService.class);
    }

    public List<Conversation> getConversations() throws IOException {
        Response<List<Conversation>> response = remoteMessagesService.getConversations().execute();

        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException(response.message());
        }

        return response.body();
    }


    public ConversationMessages getMessages(String conversationId) throws IOException {
        Response<ConversationMessages> response = remoteMessagesService.getMessages(conversationId).execute();

        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException(response.message());
        }

        ConversationMessages conversationMessages = response.body();
        conversationMessages.setIdentifier(conversationId);
        return conversationMessages;
    }

    public void sendMessage(OutgoingMessage message) throws IOException {

        MultipartBody multipartBody = new MultipartBody.Builder()
                .addPart(createPart("hashid", message.getConversationId()))
                .addPart(createPart("reqUID", message.getRequestId().toString().substring(0, 4)))
                .addPart(createPart("recipients", String.join(",", message.getRecipients())))
                .addPart(createPart("file-name", ""))
                .addPart(createPart("text", message.getMessage()))
                .setType(MediaType.get("multipart/form-data"))
                .build();

        Response<Void> response = remoteMessagesService.sendMessage(multipartBody).execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.message());
        }
    }

    private MultipartBody.Part createPart(String name, String content) {
        RequestBody requestBody = new RequestBody() {

            @Override
            public @Nullable
            MediaType contentType() {
                return null;
            }

            @Override
            public long contentLength() {
                return -1;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(content.getBytes(), 0, content.length());
            }
        };

        return MultipartBody.Part.createFormData(name, null, requestBody);
    }
}
