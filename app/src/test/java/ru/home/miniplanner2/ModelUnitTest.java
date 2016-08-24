package ru.home.miniplanner2;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import ru.home.miniplanner2.db.HelperFactory;
import ru.home.miniplanner2.model.Bay;
import ru.home.miniplanner2.model.Party;
import ru.home.miniplanner2.model.Plan;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ModelUnitTest {
    @Test
    public void planTotalCostShare() throws Exception {
        Plan plan = new Plan();
        plan.setParties(new ArrayList<Party>());

        Party party = new Party();
        party.setPlan(plan);
        party.setBays(new ArrayList<Bay>());
        plan.getParties().add(party);

        Bay bay = new Bay();
        bay.setParty(party);
        bay.setCost(new BigDecimal("123"));
        party.getBays().add(bay);

        bay = new Bay();
        bay.setParty(party);
        bay.setCost(new BigDecimal("321"));
        party.getBays().add(bay);

        plan.getTotalCost();

        assertEquals(plan.getTotalCost(), new BigDecimal("144"));
        assertEquals(plan.getShare(), new BigDecimal("244"));
    }
}