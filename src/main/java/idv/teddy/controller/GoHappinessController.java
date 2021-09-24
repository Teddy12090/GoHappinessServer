package idv.teddy.controller;

import idv.teddy.dto.ActivityDto;
import idv.teddy.entity.Activity;
import idv.teddy.repository.ActivityRepository;
import idv.teddy.validation.OnCreate;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@Data
@Validated
public class GoHappinessController {
    private final ActivityRepository activityRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/lastUpdate")
    public String lastUpdate() {
        return "2021-09-22";
    }

    @GetMapping("/info")
    public String total() {
        return String.valueOf(activityRepository.count());
    }

    @GetMapping("/activities")
    public List<ActivityDto> activities() {
        List<Activity> activities = StreamSupport.stream(activityRepository.findAll().spliterator(), false).collect(Collectors.toList());
        return activities.stream()
                .map(activity -> modelMapper.map(activity, ActivityDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/activity")
    public int add(@Validated(OnCreate.class) @NotNull @RequestBody(required = false) ActivityDto activityDto) {
        System.out.println("title: " + activityDto.getTitle());
        return 0;
    }
}
