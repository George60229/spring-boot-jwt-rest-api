package com.alibou.security.service;



import com.alibou.security.converter.CertificateConverter;
import com.alibou.security.converter.TagConverter;
import com.alibou.security.dto.request.CertificateEditRequestDto;
import com.alibou.security.dto.request.CertificateFindByRequestDTO;
import com.alibou.security.dto.request.CertificateRequestDTO;
import com.alibou.security.dto.response.ResponseCertificateDTO;
import com.alibou.security.dto.response.TagResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CertificateService {

    public void setConverter(CertificateConverter converter, TagConverter tagConverter);


    /**
     * get all entity
     *
     * @return list of certificates.
     */


    Page<ResponseCertificateDTO> listCertificates(CertificateFindByRequestDTO certificateFindByRequestDTO, Pageable pageable);

    /**
     * delete entity by id
     *
     * @param id just id
     */

    void deleteCertificateById(Integer id);


    ResponseCertificateDTO getCertificateById(@RequestParam int id);


    /**
     * get all entity by description
     *
     * @return list of certificates.
     */


    /**
     * get all entity ordered by date asc
     *
     * @return list of certificates.
     */


    /**
     * get all entity ordered by date desc
     *
     * @return list of certificates.
     */


    ResponseCertificateDTO createCertificate(@RequestBody CertificateRequestDTO certificateDTO);


    ResponseCertificateDTO editCertificate(CertificateRequestDTO certificateEditDto, int id);

    Page<ResponseCertificateDTO> findByTagName(String tagName, Pageable pageable);

    ResponseCertificateDTO editOneField(CertificateEditRequestDto certificateEditRequestDto);

    Page<ResponseCertificateDTO> findByTagsNameList(List<String> tagNames,Pageable pageable);

    TagResponseDTO popularTag();
}


