package goorm.wherebnb;


import goorm.wherebnb.domain.dao.*;
import goorm.wherebnb.domain.dto.request.BookingRequest;
import goorm.wherebnb.domain.dto.request.PaymentRequest;
import goorm.wherebnb.repository.PropertyRepository;
import goorm.wherebnb.service.BookingService;
//import goorm.wherebnb.service.PropertyService;
import goorm.wherebnb.service.PropertyService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() throws IOException {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final PropertyService propertyService;
        private final PropertyRepository propertyRepository;
        private final BookingService bookingService;
        private final BCryptPasswordEncoder passwordEncoder;

        public void dbInit() throws IOException {
            User user = User.builder()
                    .name("John Doe")
                    .email("johndoe@example.com")
                    .picture("profilePic.jpg")
                    .password(passwordEncoder.encode("password123"))
                    .role("USER")
                    .phoneNumber("1234567890")
                    .emergencyNumber("0987654321")
                    .explanation("안녕 나는 조 도")
                    .address(new Address("Country", "State", "City", "Street", "Details", "Zipcode", 10.0, 20.0))
                    .build();

            User sampleUser = User.builder()
                    .name("홍길동")
                    .email("honggildong@example.com")
                    .picture("profilePic.jpg")
                    .password(passwordEncoder.encode("password123"))
                    .phoneNumber("01012345678")
                    .emergencyNumber("01087654321")
                    .explanation("안녕 나는 홍길동")
                    .address(new Address("대한민국", "서울", "강남구", "도곡동", "세부주소", "12345", 37.4979, 127.0276))
                    .build();

            Address propertyAddress = Address.builder()
                    .country("Country")
                    .state("State")
                    .city("City")
                    .street("Street")
                    .details("Apartment 101")
                    .zipcode("123456")
                    .latitude(10.0)
                    .longitude(20.0)
                    .build();

            PropertyDetail propertyDetail = PropertyDetail.builder()
                    .maxPeople(4)
                    .selfCheckIn(true)
                    .petAvailable(false)
                    .smokeAvailable(false)
                    .checkInTime(14)
                    .checkOutTime(11)
                    .bedroom(2)
                    .bed(3)
                    .bathroom(1)
                    .build();

            Property property = Property.builder()
                    .host(user)

                    .propertyName("Lovely Apartment")
                    .propertyType(PropertyType.호텔)
                    .propertyExplanation("A cozy and modern apartment in the city center.")
                    .propertyDetail(propertyDetail)
                    .address(propertyAddress)
                    .price(100)
                    .category(Category.coolPool)
                    .photos(Arrays.asList("photo1.jpg", "photo2.jpg"))
                    .amenities(Arrays.asList(Amenity.TV, Amenity.DRYER))
                    .build();

            Address mountainAddress = Address.builder()
                    .country("Country")
                    .state("Mountain State")
                    .city("Mountain Town")
                    .street("Mountain Road")
                    .details("Cabin 101")
                    .zipcode("123456")
                    .latitude(20.0)
                    .longitude(40.0)
                    .build();

            PropertyDetail mountainDetail = PropertyDetail.builder()
                    .maxPeople(4)
                    .selfCheckIn(true)
                    .petAvailable(true)
                    .smokeAvailable(false)
                    .checkInTime(14)
                    .checkOutTime(11)
                    .bedroom(2)
                    .bed(2)
                    .bathroom(1)
                    .build();

            Property mountainCabin = Property.builder()
                    .host(user)
                    .propertyName("Mountain Cabin")
                    .propertyType(PropertyType.주택)
                    .propertyExplanation("A cozy cabin in the mountains.")
                    .propertyDetail(mountainDetail)
                    .address(mountainAddress)
                    .price(150)
                    .photos(Arrays.asList("cabin1.jpg", "cabin2.jpg"))
                    .amenities(Arrays.asList(Amenity.WIFI, Amenity.WORK_ONLY_SPACE))
                    .build();


            Address propertyAddress2 = Address.builder()
                    .country("대한민국")
                    .state("서울특별시")
                    .city("강남")
                    .street("길거리")
                    .details("비싼 집")
                    .zipcode("234456")
                    .latitude(20.0)
                    .longitude(30.0)
                    .build();

            PropertyDetail propertyDetail2 = PropertyDetail.builder()
                    .maxPeople(4)
                    .selfCheckIn(true)
                    .petAvailable(true)
                    .smokeAvailable(false)
                    .checkInTime(14)
                    .checkOutTime(11)
                    .bedroom(3)
                    .bed(4)
                    .bathroom(2)
                    .build();

            Address villaAddress = Address.builder()
                    .country("Country")
                    .state("State")
                    .city("Beach City")
                    .street("Beach Street")
                    .details("Villa 202")
                    .zipcode("789012")
                    .latitude(15.0)
                    .longitude(30.0)
                    .build();

            PropertyDetail villaDetail = PropertyDetail.builder()
                    .maxPeople(6)
                    .selfCheckIn(false)
                    .petAvailable(true)
                    .smokeAvailable(false)
                    .checkInTime(15)
                    .checkOutTime(10)
                    .bedroom(3)
                    .bed(4)
                    .bathroom(2)
                    .build();

            Property beachVilla = Property.builder()
                    .host(user)
                    .propertyName("Beachfront Villa")
                    .propertyType(PropertyType.주택)
                    .propertyExplanation("A beautiful villa with stunning beach views.")
                    .propertyDetail(villaDetail)
                    .address(villaAddress)
                    .category(Category.tropical)
                    .price(200)
                    .photos(Arrays.asList("villa1.jpg", "villa2.jpg"))
                    .amenities(Arrays.asList(Amenity.WIFI, Amenity.AIR_CONDITIONER))
                    .build();

            Address studioAddress = Address.builder()
                    .country("Country")
                    .state("State")
                    .city("Downtown")
                    .street("Main Street")
                    .details("Studio 303")
                    .zipcode("345678")
                    .latitude(25.0)
                    .longitude(50.0)
                    .build();

            PropertyDetail studioDetail = PropertyDetail.builder()
                    .maxPeople(2)
                    .selfCheckIn(true)
                    .petAvailable(false)
                    .smokeAvailable(true)
                    .checkInTime(12)
                    .checkOutTime(11)
                    .bedroom(1)
                    .bed(1)
                    .bathroom(1)
                    .build();

            Property downtownStudio = Property.builder()
                    .host(user)
                    .propertyName("Downtown Studio")
                    .propertyType(PropertyType.아파트)
                    .propertyExplanation("A compact and convenient studio in the heart of the city.")
                    .propertyDetail(studioDetail)
                    .address(studioAddress)
                    .price(8000)
                    .category(Category.cabin)
                    .photos(Arrays.asList("studio1.jpg", "studio2.jpg"))
                    .amenities(Arrays.asList(Amenity.KITCHEN, Amenity.WASHER, Amenity.WIFI))
                    .build();



            Property property2 = Property.builder()
                    .host(user)
                    .propertyName("비싼 집")
                    .propertyType(PropertyType.주택)
                    .propertyExplanation("외관은 별로지만 내부는 엄청난 비싼 집")
                    .propertyDetail(propertyDetail2)
                    .address(propertyAddress2)
                    .price(200000)
                    .category(Category.container)
                    .photos(Arrays.asList("photo1.jpg", "photo2.jpg"))
                    .amenities(Arrays.asList(Amenity.TV, Amenity.WIFI))
                    .build();

            Address cottageAddress = Address.builder()
                    .country("Country")
                    .state("Rural State")
                    .city("Countryside")
                    .street("Country Lane")
                    .details("Cottage")
                    .zipcode("567890")
                    .latitude(35.0)
                    .longitude(40.0)
                    .build();


            PropertyDetail cottageDetail = PropertyDetail.builder()
                    .maxPeople(5)
                    .selfCheckIn(false)
                    .petAvailable(true)
                    .smokeAvailable(false)
                    .checkInTime(16)
                    .checkOutTime(10)
                    .bedroom(2)
                    .bed(3)
                    .bathroom(2)
                    .build();

            Property countryCottage = Property.builder()
                    .host(user)
                    .propertyName("Country Cottage")
                    .propertyType(PropertyType.별채)
                    .propertyExplanation("A charming cottage in a serene countryside setting.")
                    .propertyDetail(cottageDetail)
                    .address(cottageAddress)
                    .price(150)
                    .category(Category.container)
                    .photos(Arrays.asList("cottage1.jpg", "cottage2.jpg"))
                    .amenities(Arrays.asList(Amenity.IRON, Amenity.HEATING_SYSTEM))
                    .build();

            Address cityAddress = Address.builder()
                    .country("Country")
                    .state("City State")
                    .city("Metropolis")
                    .street("Downtown Avenue")
                    .details("Apt 505")
                    .zipcode("567890")
                    .latitude(25.0)
                    .longitude(35.0)
                    .build();

            PropertyDetail cityDetail = PropertyDetail.builder()
                    .maxPeople(2)
                    .selfCheckIn(true)
                    .petAvailable(false)
                    .smokeAvailable(true)
                    .checkInTime(16)
                    .checkOutTime(12)
                    .bedroom(1)
                    .bed(1)
                    .bathroom(1)
                    .build();

            Property cityApartment = Property.builder()
                    .host(user)
                    .propertyName("City Apartment")
                    .propertyType(PropertyType.아파트)
                    .propertyExplanation("A modern apartment in the heart of the city.")
                    .propertyDetail(cityDetail)
                    .address(cityAddress)
                    .price(100)
                    .photos(Arrays.asList("apartment1.jpg", "apartment2.jpg"))
                    .amenities(Arrays.asList(Amenity.WIFI, Amenity.HEATING_SYSTEM))
                    .build();



            Review review1 = Review.builder()
                    .user(sampleUser)
                    .property(property)
                    .score(Score.builder()
                            .cleanScore(4)
                            .communicationScore(4)
                            .checkInScore(5)
                            .accuracyScore(5)
                            .locationScore(4)
                            .priceScore(3)
                            .build())
                    .content("정말 깨끗하고 아늑한 숙소였습니다. 호스트의 친절함도 좋았어요.")
                    .build();

            Review review2 = Review.builder()
                    .user(sampleUser)
                    .property(property)
                    .score(Score.builder()
                            .cleanScore(3)
                            .communicationScore(2)
                            .checkInScore(4)
                            .accuracyScore(2)
                            .locationScore(4)
                            .priceScore(5)
                            .build())
                    .content("위치가 매우 좋았지만, 체크인 과정이 조금 복잡했어요.")
                    .build();

            Review review3 = Review.builder()
                    .user(sampleUser)
                    .property(property)
                    .score(Score.builder()
                            .cleanScore(4)
                            .communicationScore(3)
                            .checkInScore(2)
                            .accuracyScore(5)
                            .locationScore(4)
                            .priceScore(5)
                            .build())
                    .content("완벽한 숙소! 모든 것이 기대 이상이었습니다.")
                    .build();

            Review review4 = Review.builder()
                    .user(sampleUser)
                    .property(property)
                    .score(Score.builder()
                            .cleanScore(2)
                            .communicationScore(3)
                            .checkInScore(5)
                            .accuracyScore(5)
                            .locationScore(4)
                            .priceScore(5)
                            .build())
                    .content("리뷰 네번째")
                    .build();

            Review review5 = Review.builder()
                    .user(sampleUser)
                    .property(property)
                    .score(Score.builder()
                            .cleanScore(4)
                            .communicationScore(3)
                            .checkInScore(2)
                            .accuracyScore(5)
                            .locationScore(4)
                            .priceScore(5)
                            .build())
                    .content("리뷰 다섯번쨰")
                    .build();

            Review review6 = Review.builder()
                    .user(sampleUser)
                    .property(property)
                    .score(Score.builder()
                            .cleanScore(4)
                            .communicationScore(3)
                            .checkInScore(2)
                            .accuracyScore(5)
                            .locationScore(4)
                            .priceScore(5)
                            .build())
                    .content("리뷰 6번쨰")
                    .build();

            Review review7 = Review.builder()
                    .user(sampleUser)
                    .property(property)
                    .score(Score.builder()
                            .cleanScore(4)
                            .communicationScore(3)
                            .checkInScore(2)
                            .accuracyScore(5)
                            .locationScore(4)
                            .priceScore(5)
                            .build())
                    .content("리뷰 7번쨰")
                    .build();

            Review review8 = Review.builder()
                    .user(sampleUser)
                    .property(property)
                    .score(Score.builder()
                            .cleanScore(4)
                            .communicationScore(3)
                            .checkInScore(2)
                            .accuracyScore(5)
                            .locationScore(4)
                            .priceScore(5)
                            .build())
                    .content("리뷰 8번쨰")
                    .build();

            em.persist(user);
            em.persist(sampleUser);
            em.persist(property);
            em.persist(property2);
            em.persist(beachVilla);
            em.persist(downtownStudio);
            em.persist(countryCottage);
            em.persist(mountainCabin);
            em.persist(cityApartment);
            em.persist(review1);
            em.persist(review2);
            em.persist(review3);
            em.persist(review4);
            em.persist(review5);
            em.persist(review6);
            em.persist(review7);
            em.persist(review8);

            bookingService.createBooking(1L,
                    BookingRequest.builder()
                            .userId(1L)
                            .checkInDate(LocalDate.of(2024, 2, 21))
                            .checkOutDate(LocalDate.of(2024, 2, 24))
                            .numberOfAdults(2)
                            .numberOfChildren(1)
                            .numberOfInfants(1)
                            .paymentRequest(PaymentRequest.builder()
                                    .email(sampleUser.getEmail())
                                    .paymentMethod(PaymentMethod.CARD)
                                    .cardNumber("1234-1234-1234-1234")
                                    .expirationMonth("02")
                                    .expirationYear("28")
                                    .cvc(123)
                                    .totalPrice(1000000)
                                    .nation("KOREA")
                                    .build())
                            .message("hello")
                            .build()
            );

            bookingService.createBooking(1L,
                    BookingRequest.builder()
                            .userId(2L)
                            .checkInDate(LocalDate.of(2024, 2, 25))
                            .checkOutDate(LocalDate.of(2024, 2, 27))
                            .numberOfAdults(2)
                            .numberOfChildren(1)
                            .numberOfInfants(1)
                            .paymentRequest(PaymentRequest.builder()
                                    .email(sampleUser.getEmail())
                                    .paymentMethod(PaymentMethod.CARD)
                                    .cardNumber("1234-1234-1234-1234")
                                    .expirationMonth("02")
                                    .expirationYear("28")
                                    .cvc(123)
                                    .totalPrice(120000)
                                    .nation("KOREA")
                                    .build())
                            .message("hello")
                            .build()
            );

            bookingService.createBooking(1L,
                    BookingRequest.builder()
                            .userId(2L)
                            .checkInDate(LocalDate.of(2024, 2, 2))
                            .checkOutDate(LocalDate.of(2024, 2, 10))
                            .numberOfAdults(1)
                            .numberOfChildren(1)
                            .numberOfInfants(1)
                            .paymentRequest(PaymentRequest.builder()
                                    .email(sampleUser.getEmail())
                                    .paymentMethod(PaymentMethod.CARD)
                                    .cardNumber("1234-1234-1234-1234")
                                    .expirationMonth("02")
                                    .expirationYear("28")
                                    .cvc(123)
                                    .totalPrice(900000)
                                    .nation("KOREA")
                                    .build())
                            .message("hello")
                            .build()
            );

        }


    }
}
