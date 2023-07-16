package com.harera.hayat.repository.book;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.NeedStatus;
import com.harera.hayat.model.book.BookNeed;

@Repository
public interface BookNeedRepository extends MongoRepository<BookNeed, String> {

    @Query("{ $or: [ {title: {$regex: ?0}}, {description: {$regex: ?0}}], $and:[ {status: {$eq: ?1}}]}")
    List<BookNeed> search(String title, NeedStatus status, PageRequest page);
}
