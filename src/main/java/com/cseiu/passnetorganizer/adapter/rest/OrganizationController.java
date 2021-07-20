package com.cseiu.passnetorganizer.adapter.rest;

import com.cseiu.passnetorganizer.adapter.controller.CommandGateway;
import com.cseiu.passnetorganizer.domain.aggregate.vo.AcademicMonth;
import com.cseiu.passnetorganizer.domain.command.AddDepartmentCommand;
import com.cseiu.passnetorganizer.domain.command.AddSemesterCommand;
import com.cseiu.passnetorganizer.domain.command.AddStudentCommand;
import com.cseiu.passnetorganizer.domain.command.CreateOrganizationCommand;
import com.cseiu.passnetorganizer.domain.request.AddDepartmentRequest;
import com.cseiu.passnetorganizer.domain.request.AddSemesterRequest;
import com.cseiu.passnetorganizer.domain.request.AddStudentRequest;
import com.cseiu.passnetorganizer.domain.request.CreateOrganizationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j(topic = "[OrganizationController]")
@RequestMapping(value = "/api/organizations")
public class OrganizationController extends BaseController {

    private final CommandGateway commandGateway;

    @Autowired
    public OrganizationController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> createOrganization(@Valid @RequestBody CreateOrganizationRequest request) {
        log.info("Receive request: [{}]", request.toString());

        var command = CreateOrganizationCommand.builder()
           .name(request.getName())
           .location(request.getLocation())
           .build();
        commandGateway.send(command);

        return returnCreated();
    }

    @PostMapping(value = "/{id}/add-department")
    public ResponseEntity<Object> addDepartment(@PathVariable("id") String orgId, @Valid @RequestBody AddDepartmentRequest request) {
        log.info("Receive request: [{}]", request.toString());

        var command = AddDepartmentCommand.builder()
           .name(request.getName())
           .code(request.getCode())
           .build();
        command.setAggregateId(orgId);
        commandGateway.send(command);

        return returnCreated();
    }

    @PostMapping(value = "/{id}/add-semester")
    public ResponseEntity<Object> addSemester(@PathVariable("id") String orgId, @Valid @RequestBody AddSemesterRequest request) {
        log.info("Receive request: [{}]", request.toString());

        var command = AddSemesterCommand.builder()
           .name(request.getName())
           .startMonth(AcademicMonth.findMonth(request.getStartMonth()))
           .endMonth(AcademicMonth.findMonth(request.getEndMonth()))
           .build();
        command.setAggregateId(orgId);
        commandGateway.send(command);

        return returnCreated();
    }

    @PostMapping(value = "/{orgId}/department/{depId}/add-student")
    public ResponseEntity<Object> addStudent(@PathVariable("orgId") String orgId, @PathVariable("depId") String depId, @Valid @RequestBody AddStudentRequest request) {
        log.info("Receive request: [{}]", request.toString());

        var command = AddStudentCommand.builder()
           .cardId(request.getCardId())
           .profileId(request.getProfileId())
           .departmentId(depId)
           .build();
        command.setAggregateId(orgId);
        commandGateway.send(command);

        return returnCreated();
    }
}
