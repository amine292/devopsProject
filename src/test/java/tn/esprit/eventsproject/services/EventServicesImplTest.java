package tn.esprit.eventsproject.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.entities.Tache;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;
import tn.esprit.eventsproject.services.EventServicesImpl;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
 class EventServicesImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private LogisticsRepository logisticsRepository;

    @InjectMocks
    private EventServicesImpl eventServices;

    @Test
    void addParticipant() {
        Participant participant = new Participant();
        when(participantRepository.save(participant)).thenReturn(participant);

        Participant result = eventServices.addParticipant(participant);

        verify(participantRepository, times(1)).save(participant);
        assertNotNull(result);
        // Ajoutez d'autres assertions selon vos besoins.
    }

    @Test
    void addAffectEvenParticipant() {
        Event event = new Event();
        int idParticipant = 1;
        Participant participant = new Participant();
        participant.setIdPart(idParticipant);

        when(participantRepository.findById(idParticipant)).thenReturn(Optional.of(participant));
        when(eventRepository.save(event)).thenReturn(event);

        Event result = eventServices.addAffectEvenParticipant(event, idParticipant);

        verify(participantRepository, times(1)).findById(idParticipant);
        verify(eventRepository, times(1)).save(event);
        assertNotNull(result);
        // Ajoutez d'autres assertions selon vos besoins.
    }

    @Test
    void addAffectEvenParticipantWithSet() {
        Event event = new Event();
        Set<Participant> participants = new HashSet<>();
        participants.add(new Participant());
        event.setParticipants(participants);

        when(eventRepository.save(event)).thenReturn(event);

        Event result = eventServices.addAffectEvenParticipant(event);

        verify(eventRepository, times(1)).save(event);
        assertNotNull(result);
        // Ajoutez d'autres assertions selon vos besoins.
    }

    @Test
    void addAffectLog() {
        Logistics logistics = new Logistics();
        Event event = new Event();
        String descriptionEvent = "test event";

        when(eventRepository.findByDescription(descriptionEvent)).thenReturn(event);
        when(logisticsRepository.save(logistics)).thenReturn(logistics);

        Logistics result = eventServices.addAffectLog(logistics, descriptionEvent);

        verify(eventRepository, times(1)).findByDescription(descriptionEvent);
        verify(logisticsRepository, times(1)).save(logistics);
        assertNotNull(result);
        // Ajoutez d'autres assertions selon vos besoins.
    }

    @Test
    void getLogisticsDates() {
        // Créez des données de test
        LocalDate dateDebut = LocalDate.now();
        LocalDate dateFin = dateDebut.plusDays(7);
        Event event = new Event();
        Logistics logistics1 = new Logistics();
        logistics1.setReserve(true);
        Logistics logistics2 = new Logistics();
        logistics2.setReserve(false);

        Set<Logistics> logisticsSet = new HashSet<>(Arrays.asList(logistics1, logistics2));
        event.setLogistics(logisticsSet);

        // Simulez le comportement du repository
        when(eventRepository.findByDateDebutBetween(dateDebut, dateFin)).thenReturn(Collections.singletonList(event));

        // Exécutez la méthode à tester
        List<Logistics> result = eventServices.getLogisticsDates(dateDebut, dateFin);

        // Vérifiez le résultat
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).isReserve());
    }

    @Test
    void calculCout() {
        // Créez des données de test
        Event event = new Event();
        event.setDescription("Test Event");
        Logistics logistics1 = new Logistics();
        logistics1.setPrixUnit(10.0F);
        logistics1.setQuantite(5);
        logistics1.setReserve(true);
        Logistics logistics2 = new Logistics();
        logistics2.setPrixUnit(15.0F);
        logistics2.setQuantite(3);
        logistics2.setReserve(true);

        Set<Logistics> logisticsSet = new HashSet<>(Arrays.asList(logistics1, logistics2));
        event.setLogistics(logisticsSet);

        List<Event> events = Collections.singletonList(event);

        // Simulez le comportement du repository
        when(eventRepository.findByParticipants_NomAndParticipants_PrenomAndParticipants_Tache("Tounsi", "Ahmed", Tache.ORGANISATEUR)).thenReturn(events);

        // Exécutez la méthode à tester
        eventServices.calculCout();

        // Vérifiez que les opérations de sauvegarde ont été appelées avec les bons arguments
        verify(eventRepository, times(1)).save(event);
    }
}
