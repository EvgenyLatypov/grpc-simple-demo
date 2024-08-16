package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.grpc.GreetingServiceGrpc;
import org.example.grpc.GreetingServiceOuterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreetingServiceIntegrationTest {

    private static ManagedChannel channel;
    private static GreetingServiceGrpc.GreetingServiceBlockingStub blockingStub;

    @BeforeAll
    static void setup() {
        channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        blockingStub = GreetingServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    static void tearDown() {
        if (channel != null) {
            channel.shutdown();
        }
    }

    @Test
    void testGreetingResponse() {
        // Создание запроса
        GreetingServiceOuterClass.HelloRequest request = GreetingServiceOuterClass.HelloRequest.newBuilder()
                .setName("TestName")
                .addHobbies("Reading")
                .addHobbies("Coding")
                .build();

        // Отправка запроса и получение ответа
        Iterator<GreetingServiceOuterClass.HelloResponse> responseIterator = blockingStub.greeting(request);

        // Проверка того, что получено 3 ответов и каждый ответ корректен
        for (int i = 0; i < 3; i++) {
            GreetingServiceOuterClass.HelloResponse response = responseIterator.next();
            assertEquals("Hello from Server: TestName", response.getGreeting());
        }
    }
}
