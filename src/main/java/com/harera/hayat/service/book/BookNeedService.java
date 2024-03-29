package com.harera.hayat.service.book;

import static com.harera.hayat.util.FileUtils.convertMultiPartToFile;
import static com.harera.hayat.util.ObjectMapperUtils.mapAll;
import static org.springframework.data.domain.PageRequest.of;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.harera.hayat.exception.DocumentNotFoundException;
import com.harera.hayat.model.NeedCategory;
import com.harera.hayat.model.NeedStatus;
import com.harera.hayat.model.ai.Prediction;
import com.harera.hayat.model.book.BookNeed;
import com.harera.hayat.model.book.BookNeedRequest;
import com.harera.hayat.model.book.BookNeedResponse;
import com.harera.hayat.model.user.UserDto;
import com.harera.hayat.repository.book.BookNeedRepository;
import com.harera.hayat.service.NeedNotificationsService;
import com.harera.hayat.service.ai.PredictionService;
import com.harera.hayat.service.city.CityService;
import com.harera.hayat.service.file.CloudFileService;
import com.harera.hayat.service.user.UserService;

@Service
public class BookNeedService {

    private final CityService cityService;
    private final BookNeedValidation bookNeedValidation;
    private final ModelMapper modelMapper;
    private final BookNeedRepository bookNeedRepository;
    private final UserService userService;
    private final CloudFileService cloudFileService;
    private final NeedNotificationsService needNotificationsService;
    private final PredictionService predictionService;

    public BookNeedService(CityService cityService, BookNeedValidation bookNeedValidation,
                    ModelMapper modelMapper, BookNeedRepository bookNeedRepository,
                    UserService userService, CloudFileService cloudFileService,
                    NeedNotificationsService needNotificationsService,
                    PredictionService predictionService) {
        this.cityService = cityService;
        this.bookNeedValidation = bookNeedValidation;
        this.modelMapper = modelMapper;
        this.bookNeedRepository = bookNeedRepository;
        this.userService = userService;
        this.cloudFileService = cloudFileService;
        this.needNotificationsService = needNotificationsService;
        this.predictionService = predictionService;
    }

    public BookNeedResponse create(BookNeedRequest bookNeedRequest,
                    String authorization) {
        bookNeedValidation.validate(bookNeedRequest);

        BookNeed bookNeed = modelMapper.map(bookNeedRequest, BookNeed.class);
        bookNeed.setNeedDate(LocalDateTime.now());
        bookNeed.setNeedExpirationDate(LocalDateTime.now().plusDays(45));
        bookNeed.setCategory(NeedCategory.BOOKS);
        bookNeed.setCity(cityService.get(bookNeedRequest.getCityId()));
        bookNeed.setStatus(NeedStatus.PENDING);
        bookNeed.setUser(modelMapper.map(userService.getUser(authorization),
                        UserDto.class));

        var bookNeedResponse = bookNeedRepository.save(bookNeed);

        needNotificationsService.notifyProcessingNeed(bookNeed);
//        reviewNeed(bookNeed);

        return modelMapper.map(bookNeedResponse, BookNeedResponse.class);
    }

    private void reviewNeed(BookNeed bookNeed) {
        Prediction prediction = predictionService
                        .predict(bookNeed.getTitle() + " " + bookNeed.getDescription());
        if (Objects.equals(prediction.getLabel(), "BOOK")
                        && prediction.getCertainty() > 0.5) {
            bookNeed.setStatus(NeedStatus.ACTIVE);
            needNotificationsService.notifyDonationAccepted(bookNeed);
        } else {
            needNotificationsService.notifyDonationRejected(bookNeed);
            bookNeed.setStatus(NeedStatus.REJECTED);
        }
        bookNeedRepository.save(bookNeed);
    }

    public List<BookNeedResponse> list() {
        List<BookNeed> all = bookNeedRepository.findAll();
        return mapAll(all, BookNeedResponse.class);
    }

    public void upvote(String id, String bearerToken) {
        BookNeed bookNeed = bookNeedRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Book Need not found"));
        bookNeed.upvote(userService.getUser(bearerToken).getId());
        bookNeedRepository.save(bookNeed);
    }

    public void downvote(String id, String bearerToken) {
        BookNeed bookNeed = bookNeedRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Book Need not found"));
        bookNeed.downvote(userService.getUser(bearerToken).getId());
        bookNeedRepository.save(bookNeed);
    }

    public List<BookNeedResponse> search(String query, int page) {
        return search(query, page, 16);
    }

    public BookNeedResponse get(String id) {
        BookNeed bookNeed = bookNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException("Book Need not found"));
        return modelMapper.map(bookNeed, BookNeedResponse.class);
    }

    public BookNeedResponse updateImage(String id, MultipartFile file) {
        BookNeed bookNeed = bookNeedRepository.findById(id).orElseThrow(
                        () -> new DocumentNotFoundException(BookNeed.class, id));

        String imageUrl = cloudFileService.uploadFile(convertMultiPartToFile(file));
        if (bookNeed.getImageUrl() == null) {
            bookNeed.setImageUrl(imageUrl);
        } else {
            cloudFileService.deleteFile(bookNeed.getImageUrl());
            bookNeed.setImageUrl(imageUrl);
        }

        bookNeedRepository.save(bookNeed);
        return modelMapper.map(bookNeed, BookNeedResponse.class);
    }

    public List<BookNeedResponse> search(String query, int page, int pageSize) {
        page = Integer.max(page, 1) - 1;
        List<BookNeed> bookNeeds = bookNeedRepository.search(query, NeedStatus.ACTIVE,
                        of(page, pageSize));
        return mapAll(bookNeeds, BookNeedResponse.class);
    }
}
