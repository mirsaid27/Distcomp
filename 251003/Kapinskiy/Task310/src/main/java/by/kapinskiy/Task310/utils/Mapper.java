package by.kapinskiy.Task310.utils;

import by.kapinskiy.Task310.DTOs.Requests.IssueRequestDTO;
import by.kapinskiy.Task310.DTOs.Requests.UserRequestDTO;
import by.kapinskiy.Task310.DTOs.Responses.IssueResponseDTO;
import by.kapinskiy.Task310.DTOs.Responses.UserResponseDTO;
import by.kapinskiy.Task310.models.Issue;
import by.kapinskiy.Task310.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {
    private final ModelMapper modelMapper;

    @Autowired
    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User userRequestToUser(UserRequestDTO userRequestDTO) {
        return modelMapper.map(userRequestDTO, User.class);
    }

    public UserResponseDTO userToUserResponse(User user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public List<UserResponseDTO> usersToUserResponses(List<User> users) {
        return users.stream().map(this::userToUserResponse).collect(Collectors.toList());
    }

    public Issue issueRequestToIssue(IssueRequestDTO issueRequestDTO) {
        return modelMapper.map(issueRequestDTO, Issue.class);
    }

    public IssueResponseDTO issueToIssueResponse(Issue issue) {
        return modelMapper.map(issue, IssueResponseDTO.class);
    }

    public List<IssueResponseDTO> issuesToIssueResponses(List<Issue> issues) {
        return issues.stream().map(this::issueToIssueResponse).collect(Collectors.toList());
    }
}
