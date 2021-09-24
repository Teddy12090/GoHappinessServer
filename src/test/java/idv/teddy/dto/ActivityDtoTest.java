package idv.teddy.dto;

import idv.teddy.entity.Activity;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureTestDatabase
public class ActivityDtoTest {
    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testMap() {
        Activity activity = Activity.builder().title("舞誥頌").build();
        ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);
        assertEquals("舞誥頌", activityDto.getTitle());
    }
}