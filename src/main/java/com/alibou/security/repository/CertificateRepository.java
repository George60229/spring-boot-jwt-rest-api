package com.alibou.security.repository;


import com.alibou.security.model.GiftCertificate;
import com.alibou.security.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<GiftCertificate, Integer> {
    @Query("select t from GiftCertificate g right join Tag t on t.tag_id = g.certificateId")
    List<Tag> countByTagsName();
    List<GiftCertificate> findByName(String name);
    List<GiftCertificate> findByTagsName(String name);
    List<GiftCertificate> findByDescriptionLike(String description);
    List<GiftCertificate> findByNameLike(String name);


}
