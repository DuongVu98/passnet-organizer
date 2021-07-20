package com.cseiu.passnetorganizer.adapter.grpc;

import com.cseiu.passnet.saga.organizersaga.ConsumeEvents;
import com.cseiu.passnet.saga.organizersaga.EventConsumerGrpc;
import com.cseiu.passnetorganizer.adapter.controller.CommandGateway;
import com.cseiu.passnetorganizer.domain.command.AddStudentCommand;
import com.cseiu.passnetorganizer.usecase.factory.CommandExecutorFactory;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
@Slf4j(topic = "[EventConsumingGrpcController]")
public class EventConsumingGrpcController extends EventConsumerGrpc.EventConsumerImplBase {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAILURE = "FAILURE";
    private final CommandGateway commandGateway;

    @Autowired
    public EventConsumingGrpcController(CommandExecutorFactory executorFactory, CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void consumeUserRegisteredEvent(ConsumeEvents.UserRegisteredEvent request, StreamObserver<ConsumeEvents.ServiceResponse> responseObserver) {
        log.info("Receive request: [{}]", request.toString());

        var command = AddStudentCommand.builder()
           .cardId(request.getCardId())
           .userId(request.getUid())
           .departmentId(request.getDepartmentId())
           .build();
        command.setAggregateId(request.getOrganizationId());

        try {
            commandGateway.send(command);
            responseObserver.onNext(ConsumeEvents.ServiceResponse.newBuilder().setMessage(SUCCESS).build());
        } catch (Exception exception) {
            log.error("Exception while consuming UserRegisteredEvent: {}", exception.getMessage());
            responseObserver.onNext(ConsumeEvents.ServiceResponse.newBuilder().setMessage(FAILURE).build());
        } finally {
            responseObserver.onCompleted();
        }
    }
}
