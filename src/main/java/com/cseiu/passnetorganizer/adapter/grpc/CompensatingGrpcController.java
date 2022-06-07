package com.cseiu.passnetorganizer.adapter.grpc;

import com.cseiu.passnet.saga.organizersaga.CompensatingExecutorGrpc;
import com.cseiu.passnet.saga.organizersaga.ConsumeEvents;
import com.cseiu.passnetorganizer.domain.compensating.AddNonStudentCompensating;
import com.cseiu.passnetorganizer.domain.compensating.AddStudentCompensating;
import com.cseiu.passnetorganizer.domain.compensating.BaseCompensating;
import com.cseiu.passnetorganizer.domain.exception.CompensatingHandlerNorFoundException;
import com.cseiu.passnetorganizer.usecase.executor.CompensatingHandler;
import com.cseiu.passnetorganizer.usecase.factory.CompensatingHandlerFactory;
import com.cseiu.passnetorganizer.usecase.service.CompensatingBackupService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
@Slf4j(topic = "[CompensatingGrpcController]")
public class CompensatingGrpcController extends CompensatingExecutorGrpc.CompensatingExecutorImplBase {

    private final CompensatingBackupService compensatingBackupService;
    private final CompensatingHandlerFactory compensatingHandlerFactory;

    @Autowired
    public CompensatingGrpcController(CompensatingBackupService compensatingBackupService, CompensatingHandlerFactory compensatingHandlerFactory) {
        this.compensatingBackupService = compensatingBackupService;
        this.compensatingHandlerFactory = compensatingHandlerFactory;
    }

    @Override
    public void rollback(ConsumeEvents.EventIdProtobuf request, StreamObserver<ConsumeEvents.ServiceResponseProtobuf> responseObserver) {
        var compensating = compensatingBackupService.getFromStore(request.getEventId());
        try {
            var handler = buildHandler(compensating);
            handler.reverse(compensating);

            responseObserver.onNext(ConsumeEvents.ServiceResponseProtobuf.newBuilder().setMessage("SUCCESS").build());
        } catch (CompensatingHandlerNorFoundException exception) {

            log.error("CompensatingHandlerNorFound: {}", exception.getMessage());
        } finally {

            compensatingBackupService.removeFromStore(compensating.getEventId());
            responseObserver.onCompleted();
        }
    }

    private CompensatingHandler buildHandler(BaseCompensating compensating) {
        if (compensating instanceof AddStudentCompensating) {
            return compensatingHandlerFactory.produce((AddStudentCompensating) compensating);
        } else if (compensating instanceof AddNonStudentCompensating) {
            return compensatingHandlerFactory.produce((AddNonStudentCompensating) compensating);
        } else {
            throw new CompensatingHandlerNorFoundException("Compensating type not found");
        }
    }
}
