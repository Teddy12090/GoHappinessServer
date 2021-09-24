package idv.teddy.repository;

import idv.teddy.entity.Activity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ActivityRepositoryTest {
    @Autowired
    private ActivityRepository activityRepository;

    @Test
    public void testSave() {
        assertEquals(0, activityRepository.count());
        activityRepository.save(Activity.builder().title("舞誥頌").build());
        assertEquals(1, activityRepository.count());
    }
}