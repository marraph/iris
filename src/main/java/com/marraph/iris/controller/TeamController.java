package com.marraph.iris.controller;

import com.marraph.iris.model.organisation.Team;
import com.marraph.iris.service.plain.organisation.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/v1/team")
@RestController
public final class TeamController extends AbstractController<Team> {

    @Autowired
    public TeamController(TeamService teamService) {
       super(teamService);
    }

}