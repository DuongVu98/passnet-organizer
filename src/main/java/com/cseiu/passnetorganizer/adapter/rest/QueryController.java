package com.cseiu.passnetorganizer.adapter.rest;

import com.cseiu.passnetorganizer.domain.query.GetAllOrganizationQuery;
import com.cseiu.passnetorganizer.domain.query.GetDepartmentByOrg;
import com.cseiu.passnetorganizer.domain.view.DepartmentView;
import com.cseiu.passnetorganizer.domain.view.OrganizationLiteView;
import com.cseiu.passnetorganizer.usecase.query.ViewProjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/query")
public class QueryController extends BaseController{
    private final ViewProjector projector;

    @Autowired
    public QueryController(ViewProjector projector) {
        this.projector = projector;
    }

    @GetMapping("/organizations")
    public ResponseEntity<List<OrganizationLiteView>> getAllOrganization(){
        return returnOk(projector.query(new GetAllOrganizationQuery()));
    }

    @GetMapping("/organizations/{id}/departments")
    public ResponseEntity<List<DepartmentView>> getDepartmentByOrg(@PathVariable("id") String orgId) {
        return returnOk(projector.query(new GetDepartmentByOrg(orgId)));
    }
}
