package com.cseiu.passnetorganizer.adapter.grpc;

import com.cseiu.passnet.saga.organizersaga.ConsumeEvents;
import com.cseiu.passnet.saga.organizersaga.EventConsumerGrpc;
import com.cseiu.passnetorganizer.adapter.controller.CommandGateway;
import com.cseiu.passnetorganizer.domain.command.AddNonStudentCommand;
import com.cseiu.passnetorganizer.domain.command.AddStudentCommand;
import com.cseiu.passnetorganizer.usecase.factory.CommandExecutorFactory;
import com.cseiu.passnetorganizer.usecase.service.RequestContextEventStore;
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
    private final RequestContextEventStore requestContextEventStore;

    @Autowired
    public EventConsumingGrpcController(CommandExecutorFactory executorFactory, CommandGateway commandGateway, RequestContextEventStore requestContextEventStore) {
        this.commandGateway = commandGateway;
        this.requestContextEventStore = requestContextEventStore;
    }

    @Override
    public void consumeUserRegisteredEvent(ConsumeEvents.UserRegisteredEventProtobuf request, StreamObserver<ConsumeEvents.ServiceResponseProtobuf> responseObserver) {
        log.info("Receive request: [{}]", request.toString());

        try {
            this.requestContextEventStore.setValue(request.getEventId());
            if(request.getProfileRole().equals("STUDENT")) {
                commandGateway.send(buildAddStudentCommand(request));
            } else {
                commandGateway.send(buildAddNonStudentCommand());
            }
            responseObserver.onNext(ConsumeEvents.ServiceResponseProtobuf.newBuilder().setMessage(SUCCESS).build());
        } catch (Exception exception) {
            log.error("Exception while consuming UserRegisteredEvent: {}", exception.getMessage());
            responseObserver.onNext(ConsumeEvents.ServiceResponseProtobuf.newBuilder().setMessage(FAILURE).build());
        } finally {
            requestContextEventStore.release();
            responseObserver.onCompleted();
        }
    }

    private AddStudentCommand buildAddStudentCommand(ConsumeEvents.UserRegisteredEventProtobuf request) {
        return AddStudentCommand.builder()
           .aggregateId(request.getOrganizationId())
           .cardId(request.getCardId())
           .userId(request.getUid())
           .departmentId(request.getDepartmentId())
           .build();
    }
    private AddNonStudentCommand buildAddNonStudentCommand() {
        return new AddNonStudentCommand();
    }
}
