package com.marraph.iris.dto.converter;

import com.marraph.iris.dto.organisation.OrganisationDTO;
import com.marraph.iris.dto.organisation.ProjectDTO;
import com.marraph.iris.dto.organisation.TeamDTO;
import com.marraph.iris.dto.organisation.UserDTO;
import com.marraph.iris.dto.task.TaskDTO;
import com.marraph.iris.dto.task.TopicDTO;
import com.marraph.iris.model.organisation.Organisation;
import com.marraph.iris.model.organisation.Project;
import com.marraph.iris.model.organisation.Team;
import com.marraph.iris.model.organisation.User;
import com.marraph.iris.model.task.Task;
import com.marraph.iris.model.task.Topic;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class DTOConverter {

    public OrganisationDTO parseOrganisation(Organisation organisation) {
        return new OrganisationDTO(
                organisation.getId(),
                organisation.getName()
        );
    }

    public ProjectDTO parseProject(Project project, Set<TaskDTO> tasks) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getPriority(),
                project.getIsArchived(),
                tasks
        );
    }

    public TeamDTO parseTeam(Team team, OrganisationDTO organisation) {
        return new TeamDTO(
                team.getId(),
                team.getName(),
                organisation
        );
    }

    public UserDTO parseUser(User user, Set<TeamDTO> teams) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getEmail(),
                teams
        );
    }

    public TaskDTO parseTask(Task task, TopicDTO topic) {
        return new TaskDTO(
                task.getId(),
                task.getName(),
                task.getDescription(),
                topic,
                task.getIsArchived(),
                task.getDuration(),
                task.getDeadline(),
                task.getStatus(),
                task.getPriority()
        );
    }

    public TopicDTO parseTopic(Topic topic) {
        return new TopicDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getHexCode()
        );
    }
}
