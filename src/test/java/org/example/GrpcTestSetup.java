package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.grpc.GreetingServiceGrpc;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

public abstract class GrpcTestSetup {

    protected static Server server;
    protected static ManagedChannel channel;
    protected static GreetingServiceGrpc.GreetingServiceBlockingStub blockingStub;

    @BeforeAll
    public static void setup() throws IOException {
        server = ServerBuilder.forPort(8080)
                .addService(new GreetingServiceImpl())
                .build()
                .start();

        channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        blockingStub = GreetingServiceGrpc.newBlockingStub(channel);

        System.out.println("Server started on port 8080");
    }

    @AfterAll
    public static void tearDown() {
        if (channel != null) {
            channel.shutdown();
        }
        if (server != null) {
            server.shutdown();
        }
    }
}

