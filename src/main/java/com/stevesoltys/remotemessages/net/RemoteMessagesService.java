package com.stevesoltys.remotemessages.net;

import com.stevesoltys.remotemessages.model.conversation.Conversation;
import com.stevesoltys.remotemessages.model.conversation.ConversationMessages;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

/**
 * The RemoteMessages Retrofit service.
 *
 * @author Steve Soltys
 */
public interface RemoteMessagesService {

    @GET("getInitialContactList.srv")
    Call<List<Conversation>> getConversations();

    @GET("getMessages.srv")
    Call<ConversationMessages> getMessages(@Query("hashid") String identifier);

    @POST("sendMessage.srv")
    Call<Void> sendMessage(@Body MultipartBody body);
}
