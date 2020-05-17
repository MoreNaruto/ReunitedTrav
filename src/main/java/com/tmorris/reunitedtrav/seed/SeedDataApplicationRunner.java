package com.tmorris.reunitedtrav.seed;

import com.github.javafaker.Faker;
import com.tmorris.reunitedtrav.models.Event;
import com.tmorris.reunitedtrav.models.Family;
import com.tmorris.reunitedtrav.models.Itinerary;
import com.tmorris.reunitedtrav.models.Traveler;
import com.tmorris.reunitedtrav.models.enums.Type;
import com.tmorris.reunitedtrav.repositories.EventRepository;
import com.tmorris.reunitedtrav.repositories.FamilyRepository;
import com.tmorris.reunitedtrav.repositories.ItineraryRepository;
import com.tmorris.reunitedtrav.repositories.TravelerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile("testing")
public class SeedDataApplicationRunner implements ApplicationRunner {
    private final EventRepository eventRepository;
    private final FamilyRepository familyRepository;
    private final ItineraryRepository itineraryRepository;
    private final TravelerRepository travelerRepository;

    @Autowired
    public SeedDataApplicationRunner(
            EventRepository eventRepository,
            FamilyRepository familyRepository,
            ItineraryRepository itineraryRepository,
            TravelerRepository travelerRepository
    ) {
        this.eventRepository = eventRepository;
        this.familyRepository = familyRepository;
        this.itineraryRepository = itineraryRepository;
        this.travelerRepository = travelerRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Faker faker = Faker.instance();
        int numberOfTravelers = 52;
        int numberOfEvents = 224;

        List<Traveler> travelerList = new ArrayList<>();
        List<Family> familyList = new ArrayList<>();
        List<Itinerary> itineraryList = new ArrayList<>();
        List<Event> eventList = new ArrayList<>();

        Map<Integer, List<Integer>> eventsPerItinerary = new HashMap<>() {{
            put(0, List.of(0, 11));
            put(1, List.of(12, 24));
            put(2, List.of(25, 33));
            put(3, List.of(34, 41));
            put(4, List.of(42, 61));
            put(5, List.of(62, 70));
            put(6, List.of(71, 73));
            put(7, List.of(74, 89));
            put(8, List.of(90, 123));
            put(9, List.of(124, 143));
            put(10, List.of(144, 169));
            put(11, List.of(170, 200));
            put(12, List.of(201, 223));
        }};

        Map<Integer, List<Integer>> peopleInFamily = new HashMap<>() {{
            put(0, List.of(0, 3));
            put(1, List.of(4, 7));
            put(2, List.of(8, 11));
            put(3, List.of(12, 15));
            put(4, List.of(16, 19));
            put(5, List.of(20, 23));
            put(6, List.of(24, 27));
            put(7, List.of(28, 31));
            put(8, List.of(32, 35));
            put(9, List.of(36, 40));
            put(10, List.of(40, 43));
            put(11, List.of(44, 47));
            put(12, List.of(48, 51));
        }};

        for (int i = 0; i < numberOfEvents; i++) {
            String descriptions[] = {
                    faker.matz().quote(),
                    faker.gameOfThrones().quote(),
                    faker.buffy().quotes(),
                    faker.chuckNorris().fact(),
                    faker.elderScrolls().quote(),
                    faker.harryPotter().quote()
            };

            eventList.add(Event.builder()
                    .description(descriptions[faker.random().nextInt(6)])
                    .images(List.of(faker.bothify("????##.jpg"), faker.bothify("????##.jpg")))
                    .maximumAmountOfPeople(faker.random().nextInt(2,100))
                    .minimumAmountOfPeople(1)
                    .name(faker.funnyName().name())
                    .type(Type.values()[faker.random().nextInt(8)])
                    .startTime(LocalDateTime.now().minusSeconds(faker.random().nextLong(600)).minusDays(faker.random().nextLong(7)))
                    .endTime(LocalDateTime.now().plusSeconds(faker.random().nextLong(600)).plusDays(faker.random().nextLong(7)))
                    .build());
        }

        eventRepository.saveAll(eventList);

        for (int i = 0; i < numberOfTravelers; i++) {
            List<Integer> numberOfEventsPerItinerary = eventsPerItinerary.get(Math.floorDiv(i, 4));

            List<Event> events = eventList.subList(
                    numberOfEventsPerItinerary.get(0),
                    numberOfEventsPerItinerary.get(1)
            );

            Traveler traveler = Traveler.builder()
                    .email(faker.bothify("????##@gmail.com"))
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .homeCity(faker.address().cityName())
                    .homeState(faker.address().state())
                    .profilePicture(faker.bothify("????##.jpg"))
                    .build();

            travelerList.add(traveler);

            itineraryList.add(Itinerary.builder()
                    .events(events)
                    .traveler(traveler)
                    .build());
        }
        travelerRepository.saveAll(travelerList);
        itineraryRepository.saveAll(itineraryList);

        for (int i = 0; i < numberOfTravelers / 4; i++) {
            List<Integer> numberOfFamilyMembers = peopleInFamily.get(i);

            List<Traveler> travelers = travelerList.subList(
                    numberOfFamilyMembers.get(0),
                    numberOfFamilyMembers.get(1));

            familyList.add(Family.builder()
                    .travelers(travelers)
                    .profilePicture(faker.bothify("????##.jpg"))
                    .name(faker.funnyName().name())
                    .build());

        }

        familyRepository.saveAll(familyList);
    }
}
