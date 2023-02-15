package com.alibou.security.controller;


import com.alibou.security.dto.request.*;
import com.alibou.security.dto.response.ResponseCertificateDTO;
import com.alibou.security.dto.response.TagResponseDTO;
import com.alibou.security.exception.BadRequestException;
import com.alibou.security.exception.ErrorCode;
import com.alibou.security.service.CertificateService;
import com.alibou.security.urlCreator.CertificateUrlCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/certificate")
public class CertificateController {
    @Autowired
    private CertificateService certificateServiceBean;

    @Autowired
    CertificateUrlCreator certificateUrlCreator;


    @GetMapping("/{id}")
    public CollectionModel<ResponseCertificateDTO> getCertificateById(@PathVariable(value = "id") int id) {
        List<ResponseCertificateDTO> list = new ArrayList<>();
        list.add(certificateServiceBean.getCertificateById(id));
        List<Link> links = new ArrayList<>();
        links.add(certificateUrlCreator.findPopularTag());
        return CollectionModel.of(list, links);

    }


    @PostMapping("/addCertificate")
    public CollectionModel<ResponseCertificateDTO> addCertificate(@RequestBody CertificateRequestDTO giftCertificate) {
        List<ResponseCertificateDTO> list = new ArrayList<>();
        list.add(certificateServiceBean.createCertificate(giftCertificate));

        List<Link> links = new ArrayList<>();
        links.add(certificateUrlCreator.findPopularTag());

        return CollectionModel.of(list, links);

    }


    @GetMapping("/getAllCertificates")
    public CollectionModel<ResponseCertificateDTO> getAllCertificatesWithPage(@RequestBody CertificateFindByRequestDTO
                                                                                      certificateFindByRequestDTO) {
        Pageable pageable = PageRequest.of(certificateFindByRequestDTO.getPage(), 10);
        Page<ResponseCertificateDTO> list = certificateServiceBean.listCertificates(certificateFindByRequestDTO,
                pageable);


        List<Link> links = new ArrayList<>();
        links.add(certificateUrlCreator.findPopularTag());
        return CollectionModel.of(list, links);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCertificateById(@PathVariable(value = "id") Integer id) {
        certificateServiceBean.deleteCertificateById(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ResponseCertificateDTO> editById(@RequestBody CertificateRequestDTO certificateRequestDTO, @PathVariable(value = "id") int id) {
        List<ResponseCertificateDTO> list = new ArrayList<>();
        list.add(certificateServiceBean.editCertificate(certificateRequestDTO, id));

        List<Link> links = new ArrayList<>();
        links.add(certificateUrlCreator.findPopularTag());

        return CollectionModel.of(list, links);
    }


    @GetMapping("/findByTag/{name}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ResponseCertificateDTO> findByTag(@PathVariable(value = "name") String name,
                                                             @RequestBody RequestPage requestPage) {

        Pageable pageable = PageRequest.of(defaultPagination(requestPage) - 1, 10);
        Page<ResponseCertificateDTO> list = (certificateServiceBean.findByTagName(
                name, pageable));


        List<Link> links = new ArrayList<>();
        links.add(certificateUrlCreator.findPopularTag());

        return CollectionModel.of(list, links);
    }


    @PutMapping("/editOneField")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ResponseCertificateDTO> editCertificateByParameter(@RequestBody CertificateEditRequestDto certificateEditRequestDto) {
        List<ResponseCertificateDTO> list = new ArrayList<>();
        list.add(certificateServiceBean.editOneField(certificateEditRequestDto));
        List<Link> links = new ArrayList<>();
        links.add(certificateUrlCreator.findPopularTag());
        return CollectionModel.of(list, links);

    }


    @GetMapping("/findByTags")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ResponseCertificateDTO> findByTagList(@RequestBody FindByTagRequestDto tagNames) {
        Pageable pageable = PageRequest.of(tagNames.getPage(), 10);
        Page<ResponseCertificateDTO> list = (certificateServiceBean.findByTagsNameList(
                tagNames.getTagNames(), pageable));

        List<Link> links = new ArrayList<>();
        links.add(certificateUrlCreator.findPopularTag());

        return CollectionModel.of(list, links);
    }


    @GetMapping("/findPopularTag")
    public CollectionModel<TagResponseDTO> popularTag() {
        List<TagResponseDTO> list = new ArrayList<>();
        list.add(certificateServiceBean.popularTag());

        List<Link> links = new ArrayList<>();


        return CollectionModel.of(list, links);
    }

    private int defaultPagination(RequestPage requestPage) {
        int number = requestPage.getPage();
        if (requestPage.getPage() < 0) {
            throw new BadRequestException("Page must be positive", ErrorCode.BAD_REQUEST_ERROR);
        }
        if (number == 0) {
            number = 1;
        }
        return number;
    }



}
