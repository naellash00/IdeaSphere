package com.example.ideasphere.Repositiory;

import com.example.ideasphere.Model.MonthlyDraw;
import com.example.ideasphere.Repository.MonthlyDrawRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MonthlyDrawRepositoryTest {

    @Autowired
    private MonthlyDrawRepository monthlyDrawRepository;

    private MonthlyDraw testDraw1, testDraw2;

    @BeforeEach
    void setUp() {
        // Initialize test data
        testDraw1 = new MonthlyDraw();
        testDraw1.setName("Test Draw 1");
        testDraw1.setPrize("Prize A");
        testDraw1.setRequiredPoints(100);
        testDraw1.setStatus("Ongoing");
        testDraw1.setCreatedAt(LocalDate.of(2024, 1, 1));
        testDraw1.setEndDate(LocalDate.of(2024, 1, 31));
        monthlyDrawRepository.save(testDraw1);

        testDraw2 = new MonthlyDraw();
        testDraw2.setName("Test Draw 2");
        testDraw2.setPrize("Prize B");
        testDraw2.setRequiredPoints(200);
        testDraw2.setStatus("Completed");
        testDraw2.setCreatedAt(LocalDate.of(2024, 2, 1));
        testDraw2.setEndDate(LocalDate.of(2024, 2, 28));
        monthlyDrawRepository.save(testDraw2);
    }

    @Test
    void testFindMonthlyDrawById() {
        MonthlyDraw foundDraw = monthlyDrawRepository.findMonthlyDrawById(testDraw1.getId());
        assertNotNull(foundDraw);
        assertEquals("Test Draw 1", foundDraw.getName());
    }

    @Test
    void testFindMonthlyDrawByPrizeContaining() {
        List<MonthlyDraw> draws = monthlyDrawRepository.findMonthlyDrawByPrizeContaining("Prize");
        assertEquals(2, draws.size());
    }

    @Test
    void testExistsByCreatedAtBetween() {
        boolean exists = monthlyDrawRepository.existsByCreatedAtBetween(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 31)
        );
        assertTrue(exists);
    }

    @Test
    void testFindByEndDateBetween() {
        List<MonthlyDraw> draws = monthlyDrawRepository.findByEndDateBetween(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 31)
        );
        assertEquals(1, draws.size());
        assertEquals("Test Draw 1", draws.get(0).getName());
    }

    @Test
    void testFindByPointsLessThanEqual() {
        List<MonthlyDraw> draws = monthlyDrawRepository.findByPointsLessThanEqual(150);
        assertEquals(1, draws.size());
        assertEquals("Test Draw 1", draws.get(0).getName());
    }
}