package idv.teddy.dto;

import idv.teddy.entity.Activity;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ActivityDtoTest {
    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testMap() {
        Activity activity = Activity.builder().title("夠爽").build();
        ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);
        assertEquals("夠爽", activityDto.getTitle());
        assertEquals("2021-09-24", activityDto.getStartDate());
    }
}