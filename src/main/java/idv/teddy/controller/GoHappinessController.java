package idv.teddy.controller;

import idv.teddy.dto.ActivityDto;
import idv.teddy.entity.Activity;
import idv.teddy.repository.ActivityRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@Data
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
}
