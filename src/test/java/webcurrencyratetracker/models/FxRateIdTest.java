//package webcurrencyratetracker.models;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.persistence.EntityManager;
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class FxRateIdTest {
//
//    @Autowired
//    EntityManager em;
//
//    @Test
//    void testEquals() {
//        // 2 transient entities need to be NOT equal
//        FxRateId e1 = new FxRateId(Currency.EUR, Currency.USD, LocalDate.parse("2020-07-24"));
//        FxRateId e2 = new FxRateId(Currency.EUR, Currency.GBP, LocalDate.parse("2020-07-24"));
//        assertFalse(e1.equals(e2));
//
//// 2 managed entities that represent different records need to be NOT equal
//        e1 = em.find(FxRateId.class, id1);
//        e2 = em.find(FxRateId.class, id2);
//        assertFalse(e1.equals(e2));
//
//// 2 managed entities that represent the same record need to be equal
//        e1 = em.find(FxRateId.class, id1);
//        e2 = em.find(FxRateId.class, id1);
//        assertTrue(e1.equals(e2));
//
//// a detached and a managed entity object that represent the same record need to be equal
//        em.detach(e1);
//        e2 = em.find(FxRateId.class, id1);
//        assertTrue(e1.equals(e2));
//
//// a re-attached and a managed entity object that represent the same record need to be equal
//        e1 = em.merge(e1);
//        assertTrue(e1.equals(e2));
//    }
//
//    @Test
//    void testHashCode() {
//    }
//}
