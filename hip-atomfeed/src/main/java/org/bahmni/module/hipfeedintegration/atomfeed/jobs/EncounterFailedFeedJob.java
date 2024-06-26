package org.bahmni.module.hipfeedintegration.atomfeed.jobs;

import org.bahmni.module.hipfeedintegration.atomfeed.client.AtomFeedClientFactory;
import org.bahmni.module.hipfeedintegration.atomfeed.worker.EncounterFeedWorker;
import org.ict4h.atomfeed.client.service.FeedClient;
import org.quartz.DisallowConcurrentExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@DisallowConcurrentExecution
@Component("openMRSEncounterFailedFeedJob")
@ConditionalOnExpression("'${enable.scheduling}'=='true'")
public class EncounterFailedFeedJob implements FeedJob {
    private final Logger logger = LoggerFactory.getLogger(EncounterFailedFeedJob.class);
    private  EncounterFeedWorker encounterFeedWorker;
    private  AtomFeedClientFactory atomFeedClientFactory;

    private FeedClient atomFeedClient;

    @Autowired
    public EncounterFailedFeedJob(EncounterFeedWorker encounterFeedWorker, AtomFeedClientFactory atomFeedClientFactory) {
        this.encounterFeedWorker = encounterFeedWorker;
        this.atomFeedClientFactory = atomFeedClientFactory;
    }

    public EncounterFailedFeedJob() {
    }

    @Override
    public void process() {
        if(atomFeedClient == null){
            atomFeedClient = atomFeedClientFactory.get(encounterFeedWorker);
        }
        logger.info("Processing failed event.");
        atomFeedClient.processFailedEvents();
        logger.info("Completed processing failed event.");
    }
}
