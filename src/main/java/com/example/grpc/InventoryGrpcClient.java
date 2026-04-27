package com.example.grpc;
import com.shop.inventory.grpc.InventoryServiceGrpc;
import com.shop.inventory.grpc.StockRequest;
import com.shop.inventory.grpc.StockResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.inject.Singleton;
import com.shop.inventory.grpc.ReserveRequest;
import com.shop.inventory.grpc.ReserveResponse;



@Singleton
public class InventoryGrpcClient {

    private final InventoryServiceGrpc.InventoryServiceBlockingStub blockingStub;

    public InventoryGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051) // inventory service
                .usePlaintext()
                .build();

        this.blockingStub = InventoryServiceGrpc.newBlockingStub(channel);
    }

    public StockResponse checkStock(String productId) {
        return blockingStub.checkStock(
                StockRequest.newBuilder()
                        .setProductId(productId)
                        .build()
        );
    }

    public ReserveResponse reserveStock(String productId, int quantity) {
        return blockingStub.reserveStock(
                ReserveRequest.newBuilder()
                        .setProductId(productId)
                        .setAmount(quantity)
                        .build()
        );
    }
}