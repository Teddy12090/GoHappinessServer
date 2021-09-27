package idv.teddy.repository;

import idv.teddy.entity.Activity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ActivityRepositoryTest {
    @Autowired
    private ActivityRepository activityRepository;

    @Test
    public void testSave() {
        assertEquals(0, activityRepository.count());
        activityRepository.save(Activity.builder()
                .title("舞誥頌")
                .startDate(Date.valueOf(LocalDate.of(2021, 9, 27)))
                .url("http://localhost")
                .source("idv.teddy")
                .build());
        assertEquals(1, activityRepository.count());
    }
}