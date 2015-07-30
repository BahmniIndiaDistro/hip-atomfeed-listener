
package org.bahmni.module.pacsintegration.atomfeed.worker;

import org.apache.log4j.Logger;
import org.bahmni.module.pacsintegration.atomfeed.contract.encounter.OpenMRSEncounter;
import org.bahmni.module.pacsintegration.services.OpenMRSService;
import org.bahmni.module.pacsintegration.services.PacsIntegrationService;
import org.ict4h.atomfeed.client.domain.Event;
import org.ict4h.atomfeed.client.service.EventWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EncounterFeedWorker implements EventWorker {

    private static final Logger logger = Logger.getLogger(EncounterFeedWorker.class);

    @Autowired
    private PacsIntegrationService pacsIntegrationService;

    @Autowired
    private OpenMRSService openMRSService;

    public EncounterFeedWorker() {
    }

    @Override
    public void process(Event event) {
        try {
            logger.info("Getting encounter data...");
            String encounterUri = event.getContent();
            OpenMRSEncounter encounter = openMRSService.getEncounter(encounterUri);
            pacsIntegrationService.processEncounter(encounter);
        } catch (Exception e) {
            logger.error("Failed send order to modality", e);
            throw new RuntimeException("Failed send order to modality", e);
        }
    }

    @Override
    public void cleanUp(Event event) {
    }
}