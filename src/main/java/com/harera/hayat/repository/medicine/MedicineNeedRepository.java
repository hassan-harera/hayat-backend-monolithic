package com.harera.hayat.repository.medicine;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.harera.hayat.model.NeedStatus;
import com.harera.hayat.model.medicine.MedicineNeed;

@Repository
public interface MedicineNeedRepository extends MongoRepository<MedicineNeed, String> {

    int countById(String id);

    @Query("{ $or: [ {title: {$regex: ?0}}, {description: {$regex: ?0}}], $and:[ {status: {$eq: ?1}}]}")
    List<MedicineNeed> search(String title, NeedStatus status, PageRequest page);
}
