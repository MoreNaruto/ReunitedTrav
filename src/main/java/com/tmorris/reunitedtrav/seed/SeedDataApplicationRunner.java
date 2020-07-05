package com.tmorris.reunitedtrav.seed;

import com.github.javafaker.Faker;
import com.tmorris.reunitedtrav.models.*;
import com.tmorris.reunitedtrav.models.enums.EventType;
import com.tmorris.reunitedtrav.repositories.*;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
    private final HotelRepository hotelRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public SeedDataApplicationRunner(
            EventRepository eventRepository,
            FamilyRepository familyRepository,
            ItineraryRepository itineraryRepository,
            TravelerRepository travelerRepository,
            HotelRepository hotelRepository,
            AccountRepository accountRepository
    ) {
        this.eventRepository = eventRepository;
        this.familyRepository = familyRepository;
        this.itineraryRepository = itineraryRepository;
        this.travelerRepository = travelerRepository;
        this.hotelRepository = hotelRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Faker faker = Faker.instance();
        int numberOfTravelers = 52;
        int numberOfEvents = 224;
        int numberOfHotels = 40;

        List<Traveler> travelerList = new ArrayList<>();
        List<Family> familyList = new ArrayList<>();
        List<Itinerary> itineraryList = new ArrayList<>();
        List<Event> eventList = new ArrayList<>();
        List<Hotel> hotelList = new ArrayList<>();

        Map<Integer, List<Integer>> eventsPerItinerary = buildEventList(faker, numberOfEvents, eventList);
        eventRepository.saveAll(eventList);

        Map<Integer, List<Integer>> hotelsPerItinerary = buildHotelList(faker, numberOfHotels, hotelList);
        hotelRepository.saveAll(hotelList);

        buildItineraryList(faker, numberOfTravelers, travelerList, itineraryList, eventList, eventsPerItinerary, hotelList, hotelsPerItinerary);
        travelerRepository.saveAll(travelerList);
        itineraryRepository.saveAll(itineraryList);

        buildListOfPeopleInFamily(faker, numberOfTravelers, travelerList, familyList);
        familyRepository.saveAll(familyList);
    }

    private void buildListOfPeopleInFamily(Faker faker, int numberOfTravelers, List<Traveler> travelerList, List<Family> familyList) {
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

        for (int i = 0; i < numberOfTravelers / 4; i++) {
            List<Integer> numberOfFamilyMembers = peopleInFamily.get(i);

            List<Traveler> travelers = travelerList.subList(
                    numberOfFamilyMembers.get(0),
                    numberOfFamilyMembers.get(1));

            familyList.add(Family.builder()
                    .familyAccountOwner(travelers.get(0))
                    .travelers(travelers)
                    .profilePicture(faker.bothify("????##.jpg"))
                    .name(faker.funnyName().name())
                    .build());

        }
    }

    private void buildItineraryList(
            Faker faker,
            int numberOfTravelers,
            List<Traveler> travelerList,
            List<Itinerary> itineraryList,
            List<Event> eventList,
            Map<Integer, List<Integer>> eventsPerItinerary,
            List<Hotel> hotelList,
            Map<Integer, List<Integer>> hotelsPerItinerary
    ) {
        for (int i = 0; i < numberOfTravelers; i++) {
            List<Integer> numberOfEventsPerItinerary = eventsPerItinerary.get(Math.floorDiv(i, 4));
            List<Integer> numberOfHotelsPerItinerary = hotelsPerItinerary.get(Math.floorDiv(i, 4));

            List<Event> events = eventList.subList(
                    numberOfEventsPerItinerary.get(0),
                    numberOfEventsPerItinerary.get(1)
            );

            List<Hotel> hotels = hotelList.subList(
                    numberOfHotelsPerItinerary.get(0),
                    numberOfHotelsPerItinerary.get(1)
            );

            Account account = Account.builder()
                    .username(faker.funnyName().name())
                    .password(BCrypt.hashpw(faker.twinPeaks().quote(), "12"))
                    .build();

            accountRepository.save(account);

            itineraryList.add(Itinerary.builder()
                    .events(events)
                    .hotels(hotels)
                    .build());

            Traveler traveler = Traveler.builder()
                    .email(faker.bothify("????##@gmail.com"))
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .homeCity(faker.address().cityName())
                    .homeState(faker.address().state())
                    .profilePicture(faker.bothify("????##.jpg"))
                    .account(account)
                    .itineraries(itineraryList)
                    .build();

            travelerList.add(traveler);
        }
    }

    private Map<Integer, List<Integer>> buildHotelList(Faker faker, int numberOfHotels, List<Hotel> hotelList) {
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

        Map<Integer, List<Integer>> hotelsPerItinerary = new HashMap<>() {{
            put(0, List.of(0, 2));
            put(1, List.of(3, 5));
            put(2, List.of(6, 9));
            put(3, List.of(10, 11));
            put(4, List.of(12, 15));
            put(5, List.of(16, 21));
            put(6, List.of(22, 25));
            put(7, List.of(26, 28));
            put(8, List.of(29, 31));
            put(9, List.of(32, 33));
            put(10, List.of(34, 35));
            put(11, List.of(36, 37));
            put(12, List.of(38, 39));
        }};

        for (int i = 0; i < numberOfHotels; i++) {
            hotelList.add(Hotel.builder()
                    .name(faker.company().name())
                    .checkIn(LocalDateTime.now()
                            .plusSeconds(randomDataGenerator.nextLong(100, 800))
                            .plusDays(randomDataGenerator.nextLong(1, 10))
                    )
                    .checkOut(LocalDateTime.now()
                            .plusSeconds(randomDataGenerator.nextLong(400, 1200))
                            .plusDays(randomDataGenerator.nextLong(12, 25))
                    )
                    .build());
        }

        return hotelsPerItinerary;
    }

    private Map<Integer, List<Integer>> buildEventList(Faker faker, int numberOfEvents, List<Event> eventList) {
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

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
                    .maximumAmountOfPeople(faker.random().nextInt(2, 100))
                    .minimumAmountOfPeople(1)
                    .name(faker.funnyName().name())
                    .eventType(EventType.values()[faker.random().nextInt(8)])
                    .startTime(LocalDateTime.now()
                            .plusSeconds(randomDataGenerator.nextLong(100, 800))
                            .plusDays(randomDataGenerator.nextLong(1, 10))
                    )
                    .endTime(LocalDateTime.now()
                            .plusSeconds(randomDataGenerator.nextLong(400, 1200))
                            .plusDays(randomDataGenerator.nextLong(12, 25))
                    )
                    .build());
        }
        return eventsPerItinerary;
    }
}
