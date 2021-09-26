package idv.teddy.dto;

import idv.teddy.Config;
import idv.teddy.entity.Activity;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringJUnitConfig(Config.class)
public class ActivityDtoTest {
    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testMap() {
        Activity activity = Activity.builder()
                .title("舞誥頌")
                .build();
        ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);
        assertEquals("舞誥頌", activityDto.getTitle());
    }

    @Test
    public void testMapForDate() {
        Activity activity = Activity.builder()
                .startDate(Date.valueOf(LocalDate.of(2021, 9, 26)))
                .endDate(Date.valueOf(LocalDate.of(2022, 9, 26)))
                .build();
        ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);
        assertEquals("2021-09-26", activityDto.getStartDate());
        assertEquals("2022-09-26", activityDto.getEndDate());
    }
}