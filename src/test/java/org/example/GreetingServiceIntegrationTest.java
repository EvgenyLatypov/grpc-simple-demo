package org.example;

import org.example.grpc.GreetingServiceOuterClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreetingServiceIntegrationTest extends GrpcTestSetup {

    @Test
    void testGreetingResponse() {
        GreetingServiceOuterClass.HelloRequest request = GreetingServiceOuterClass.HelloRequest.newBuilder()
                .setName("Eugene")
                .addHobbies("Reading")
                .addHobbies("Coding")
                .build();

        GreetingServiceOuterClass.HelloResponse response = blockingStub.greeting(request);

        assertEquals("Hello from Server: Eugene", response.getGreeting());
    }
}
