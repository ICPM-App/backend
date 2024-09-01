package icpmapp;

import icpmapp.entities.*;
import icpmapp.repository.PageRepository;
import icpmapp.repository.SessionContentRepository;
import icpmapp.repository.SessionHeaderRepository;
import icpmapp.repository.UserRepository;
import icpmapp.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@SpringBootApplication
public class SecurityApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private SessionHeaderRepository sessionHeaderRepository;
    @Autowired
    private SessionContentRepository sessionContentRepository;
    public static void main(String[] args) {

        SpringApplication.run(SecurityApplication.class, args);
    }

    public void run(String... args){
        User adminAccount = userRepository.findByRole(Role.ADMIN);
        if (null == adminAccount){
            User user = new User();
            user.setEmail("noreply.ICPM24@gmail.com");
            user.setFirstname("admin");
            user.setLastname("admin");
            user.setRole(Role.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);

            pageRepository.save(new Page("Messages from Organizers", 2, "Message from the organizers content goes here."));
            pageRepository.save(new Page("Information about Excursion", 1, "<p>Join us for an exciting excursion! Details to be announced.</p>"));
            pageRepository.save(new Page("Travelling Information", 1, "<h5>Travelling</h5><p>Below you can find a map where the main points are highlighted: the airport, the city center main stop and DTU’s main bus stop. To travel between the airport and the city center there is a single metro line. To travel between the city center and DTU the easiest way is using the bus line 150S (which runs every 10 minutes in both directions).</p><p><strong>How to get to Copenhagen City Center from Copenhagen Airport</strong><br>Take the Metro Line M2 destination <em>Vanløse</em> to <em>Nørreport Station</em>. We encourage you to travel on public transportation to lower your carbon footprint.</p><p><strong>How to get to DTU from Copenhagen City Center</strong><br>From the area around Kongens Nytorv: Take the Metro or walk to <em>Nørreport Station</em> and take either bus line 150S destination <em>Kokkedal St.</em>, departing from above ground. Get off at <em>Rævehøjvej, DTU</em>, and walk to the Campus.</p><p>From the area around Copenhagen City Hall and Copenhagen Central Station (Vesterbro): Walk to Copenhagen Central Station and take the S-train line A destination <em>Hillerød St</em>., get off at <em>Lyngby Station </em>and change to bus line 40E destination <em>Rævehøjvej, DTU, </em>get off at the terminus and walk to the Campus.</p><p>Walking direction from bus stop <em>Rævehøjvej, DTU</em> to building 116 (6 minutes):</p><div class=\"wp-block-image\"><figure class=\"aligncenter size-full is-resized\"><a href=\"https://maps.app.goo.gl/oStjnqokKJHViiX77\" target=\"_blank\" rel=\"noopener\"><img src=\"https://icpmconference.org/2024/wp-content/uploads/sites/9/2023/09/map.jpg\" alt=\"\"  srcset=\"https://icpmconference.org/2024/wp-content/uploads/sites/9/2023/09/map.jpg 827w, https://icpmconference.org/2024/wp-content/uploads/sites/9/2023/09/map-300x182.jpg 300w, https://icpmconference.org/2024/wp-content/uploads/sites/9/2023/09/map-768x467.jpg 768w\" /></a></figure></div><p><strong>Travel Card (Rejsekort)</strong><br>Rejsekort (Travel Card) is a prepaid card for taking public transportations in Denmark. You can obtain an anonymous card in convenient stores or at Rejsekort machines. We suggest you buy your Rejsekort at the airport, since you might find Rejsekort machines outside airport running out of new anonymous cards prepared to be purchased very often.</p>"));
            pageRepository.save(new Page("Conference Venue", 1, "<p>The conference will take place in the heart of the city, offering easy access to various amenities and cultural landmarks. Stay tuned for more details.</p>"));
            pageRepository.save(new Page("Food Information", 1, "<p>Our event will cater to a variety of dietary needs, including vegetarian, vegan, and gluten-free options. More details on meal times and options will be provided closer to the event date.</p>"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            SessionHeader sh1 = new SessionHeader(LocalDateTime.parse("2024-06-10 09:00:00", formatter), LocalDateTime.parse("2024-06-10 10:00:00", formatter), "Alice", "Main Hall", "Opening Keynote", SessionType.KEYNOTE);
            SessionHeader sh2 = new SessionHeader(LocalDateTime.parse("2024-06-10 10:30:00", formatter), LocalDateTime.parse("2024-06-10 11:00:00", formatter), "Bob", "Room 101", "Morning Coffee Break", SessionType.COFFEE);
            SessionHeader sh3 = new SessionHeader(LocalDateTime.parse("2024-06-10 12:00:00", formatter), LocalDateTime.parse("2024-06-10 13:00:00", formatter), "Conference Catering", "Dining Area", "Lunch Buffet", null);
            SessionHeader sh4 = new SessionHeader(LocalDateTime.parse("2024-06-10 15:00:00", formatter), LocalDateTime.parse("2024-06-10 15:30:00", formatter), "Conference Team", "Room 102 & Room 103", "Parallel Q&A Sessions", SessionType.QnA);
            SessionHeader sh5 = new SessionHeader(LocalDateTime.parse("2024-06-10 09:00:00", formatter), LocalDateTime.parse("2024-06-10 10:00:00", formatter), "Cindy", "Main Hall", "Day 2 Kickoff", SessionType.KEYNOTE);
            SessionHeader sh6 = new SessionHeader(LocalDateTime.parse("2024-06-10 10:30:00", formatter), LocalDateTime.parse("2024-06-10 11:00:00", formatter), "Bob", "Room 101", "Morning Coffee Break", SessionType.COFFEE);
            SessionHeader sh7 = new SessionHeader(LocalDateTime.parse("2024-06-11 14:00:00", formatter), LocalDateTime.parse("2024-06-11 15:00:00", formatter), "Dan", "Workshop Room", "Interactive Workshop", SessionType.PRACTICAL);
            SessionHeader sh8 = new SessionHeader(LocalDateTime.parse("2024-06-11 14:00:00", formatter), LocalDateTime.parse("2024-06-11 15:00:00", formatter), "Eve", "Auditorium", "Industry Panel Discussion", SessionType.KEYNOTE);
            SessionHeader sh9 = new SessionHeader(LocalDateTime.parse("2024-06-13 09:00:00", formatter), LocalDateTime.parse("2024-06-13 10:00:00", formatter), "Frank", "Main Hall", "Closing Keynote", SessionType.KEYNOTE);
            SessionHeader sh10 = new SessionHeader(LocalDateTime.parse("2024-06-17 09:30:00", formatter), LocalDateTime.parse("2024-06-17 10:30:00", formatter), "Gina", "Conference Room A", "Sustainable Practices in Industry", SessionType.KEYNOTE);
            SessionHeader sh11 = new SessionHeader(LocalDateTime.parse("2024-06-17 11:00:00", formatter), LocalDateTime.parse("2024-06-17 12:00:00", formatter), "Henry & Team", "Outdoor Area", "Networking Brunch", SessionType.FOOD);
            SessionHeader sh12 = new SessionHeader(LocalDateTime.parse("2024-06-18 14:00:00", formatter), LocalDateTime.parse("2024-06-18 16:00:00", formatter), "Ian", "Workshop Room A", "Hands-on Data Analysis Workshop", null);
            SessionHeader sh13 = new SessionHeader(LocalDateTime.parse("2024-06-18 10:00:00", formatter), LocalDateTime.parse("2024-06-18 10:30:00", formatter), "Julia", "Lobby", "Mid-morning Coffee Break", SessionType.COFFEE);
            SessionHeader sh14 = new SessionHeader(LocalDateTime.parse("2024-06-18 13:00:00", formatter), LocalDateTime.parse("2024-06-18 14:00:00", formatter), "Conference Catering", "Main Dining Hall", "International Buffet Lunch", SessionType.FOOD);
            SessionHeader sh15 = new SessionHeader(LocalDateTime.parse("2024-06-19 15:30:00", formatter), LocalDateTime.parse("2024-06-19 16:30:00", formatter), "Kevin", "Auditorium B", "Panel: The Future of Technology", SessionType.QnA);
            SessionHeader sh16 = new SessionHeader(LocalDateTime.parse("2024-06-20 09:00:00", formatter), LocalDateTime.parse("2024-06-20 09:45:00", formatter), "Linda", "Conference Room B", "Breakfast and Learn: AI Innovations", null);
            SessionHeader sh17 = new SessionHeader(LocalDateTime.parse("2024-06-20 11:15:00", formatter), LocalDateTime.parse("2024-06-20 12:00:00", formatter), "Mike", "Outdoor Terrace", "Yoga and Mindfulness Session", SessionType.PRACTICAL);
            SessionHeader sh18 = new SessionHeader(LocalDateTime.parse("2024-06-21 16:00:00", formatter), LocalDateTime.parse("2024-06-21 17:00:00", formatter), "Nina", "Main Hall", "Closing Ceremony: Reflecting on Our Journey", SessionType.KEYNOTE);

            sessionHeaderRepository.save(sh1);
            sessionHeaderRepository.save(sh2);
            sessionHeaderRepository.save(sh3);
            sessionHeaderRepository.save(sh4);
            sessionHeaderRepository.save(sh5);
            sessionHeaderRepository.save(sh6);
            sessionHeaderRepository.save(sh7);
            sessionHeaderRepository.save(sh8);
            sessionHeaderRepository.save(sh9);
            sessionHeaderRepository.save(sh10);
            sessionHeaderRepository.save(sh11);
            sessionHeaderRepository.save(sh12);
            sessionHeaderRepository.save(sh13);
            sessionHeaderRepository.save(sh14);
            sessionHeaderRepository.save(sh15);
            sessionHeaderRepository.save(sh16);
            sessionHeaderRepository.save(sh17);
            sessionHeaderRepository.save(sh18);



            userRepository.save(new User("USA", "TechGiant", "john.doe@techgiant.com", "John", "Doe", true));
            userRepository.save(new User("Canada", "MapleTech", "alice.smith@mapletech.com", "Alice", "Smith", false));
            userRepository.save(new User("UK", "LondonBiz", "bob.brown@londonbiz.com", "Bob", "Brown", true));
            userRepository.save(new User("USA", "TechGiant", "carol.jones@techgiant.com", "Carol", "Jones", true));
            userRepository.save(new User("Germany", "AutoBuild", "david.lee@autobuild.com", "David", "Lee", false));
            userRepository.save(new User("France", "ParisInc", "eva.white@parisinc.com", "Eva", "White", true));
            userRepository.save(new User("USA", "HealthPlus", "frank.black@healthplus.com", "Frank", "Black", false));
            userRepository.save(new User("Canada", "MapleTech", "grace.green@mapletech.com", "Grace", "Green", true));
            userRepository.save(new User("UK", "TechGiant", "harry.taylor@techgiant.co.uk", "Harry", "Taylor", false));
            userRepository.save(new User("Germany", "AutoBuild", "isabella.wilson@autobuild.com", "Isabella", "Wilson", true));
            userRepository.save(new User("France", "Foodies", "jason.martin@foodies.fr", "Jason", "Martin", false));
            userRepository.save(new User("USA", "BeveragesCo", "karen.hall@beveragesco.com", "Karen", "Hall", true));
            userRepository.save(new User("Canada", "HealthPlus", "louis.clark@healthplus.ca", "Louis", "Clark", false));
            userRepository.save(new User("UK", "LondonBiz", "maria.lewis@londonbiz.com", "Maria", "Lewis", true));
            userRepository.save(new User("USA", "TechGiant", "noah.allen@techgiant.com", "Noah", "Allen", false));
            userRepository.save(new User("Germany", "BerlinTech", "olivia.scott@berlintech.de", "Olivia", "Scott", true));
            userRepository.save(new User("France", "ParisInc", "peter.young@parisinc.com", "Peter", "Young", false));
            userRepository.save(new User("Canada", "MapleTech", "quinn.edwards@mapletech.com", "Quinn", "Edwards", true));
            userRepository.save(new User("UK", "BakerGoods", "rachel.moore@bakergoods.co.uk", "Rachel", "Moore", false));
            userRepository.save(new User("USA", "BeveragesCo", "samuel.walker@beveragesco.com", "Samuel", "Walker", true));

        }
    }
}
