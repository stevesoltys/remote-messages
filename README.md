# Remote Messages
[![Build Status](https://travis-ci.org/stevesoltys/remote-messages.svg?branch=master)](https://travis-ci.org/stevesoltys/remote-messages)

A [Remote Messages](http://remotemessages.com/) client library.

This allows for sending and receiving iMessages programmatically with an iOS device that has Remote Messages installed.

## Installation
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compile 'com.github.stevesoltys:remote-messages:0.1.0'
}
```

## Usage
Create an `RemoteMessagesClient` instance with the base URL, optional credentials, and make a query.

Here's an example:
```java
RemoteMessagesClient client = RemoteMessagesClient.builder()
    .baseUrl("http://192.168.1.5:333")
    .username("admin").password("password")
    .build();

List<Conversation> conversations = client.getConversations();
Conversation conversation = conversations.get(0);

ConversationMessages messages = client.getMessages(conversation.getId());
messages.getMessages().forEach(System.out::println);

client.sendMessage(OutgoingMessage.builder()
    .conversationId(conversation.getId())
    .message("Hello!")
    .build());
```

## Notes
Please note that this has only been tested on the legacy version of Remote Messages (v2).
Testing for other versions would be appreciated.

## License
This application is available as open source under the terms of the [MIT License](http://opensource.org/licenses/MIT).
