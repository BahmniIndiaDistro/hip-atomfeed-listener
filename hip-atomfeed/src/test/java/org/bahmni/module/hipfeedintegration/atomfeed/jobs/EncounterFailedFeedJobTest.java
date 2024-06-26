package org.bahmni.module.hipfeedintegration.atomfeed.jobs;

import org.bahmni.module.hipfeedintegration.atomfeed.client.AtomFeedClientFactory;
import org.bahmni.module.hipfeedintegration.atomfeed.worker.EncounterFeedWorker;
import org.ict4h.atomfeed.client.service.AtomFeedClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class EncounterFailedFeedJobTest {

    @Mock
    EncounterFeedWorker encounterFeedWorker;

    @Mock
    AtomFeedClientFactory atomFeedClientFactory;

    @Mock
    AtomFeedClient atomFeedClient;

    @InjectMocks
    private EncounterFailedFeedJob encounterFeedJob = new EncounterFailedFeedJob();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldProcessTheEvent() throws Exception {
        when(atomFeedClientFactory.get(encounterFeedWorker)).thenReturn(atomFeedClient);

        encounterFeedJob.process();

        verify(atomFeedClient, times(1)).processFailedEvents();
    }
}