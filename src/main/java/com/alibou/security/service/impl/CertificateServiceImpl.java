package com.alibou.security.service.impl;



import com.alibou.security.converter.CertificateConverter;
import com.alibou.security.converter.TagConverter;
import com.alibou.security.dto.request.CertificateEditRequestDto;
import com.alibou.security.dto.request.CertificateFindByRequestDTO;
import com.alibou.security.dto.request.CertificateRequestDTO;
import com.alibou.security.dto.response.ResponseCertificateDTO;
import com.alibou.security.dto.response.TagResponseDTO;
import com.alibou.security.exception.AppNotFoundException;
import com.alibou.security.exception.BadRequestException;
import com.alibou.security.exception.ErrorCode;
import com.alibou.security.model.GiftCertificate;
import com.alibou.security.model.Tag;
import com.alibou.security.repository.CertificateRepository;
import com.alibou.security.repository.TagRepository;
import com.alibou.security.service.CertificateService;
import com.alibou.security.utils.FindParameter;
import com.alibou.security.utils.SortParameter;
import com.alibou.security.utils.SortWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;

    private final TagRepository tagRepository;

    public CertificateServiceImpl(CertificateRepository certificateRepository, TagRepository tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

    @Autowired
    private CertificateConverter converter;

    @Autowired
    private TagConverter tagConverter;


    @Override
    public void deleteCertificateById(Integer id) {
        certificateRepository.deleteById(id);
    }


    @Override
    public ResponseCertificateDTO getCertificateById(int id) {

        if (certificateRepository.findById(id).isEmpty()) {
            throw new AppNotFoundException("Certificate with this id " + id + " is not found",
                    ErrorCode.CERTIFICATE_NOT_FOUND);
        }
        GiftCertificate giftCertificate = certificateRepository.findById(id).get();

        return converter.convertToDTO(giftCertificate);
    }

    @Transactional
    @Override
    public ResponseCertificateDTO createCertificate(CertificateRequestDTO certificateDto) {

        create(certificateDto);
        return converter.convertToDTO(certificateRepository.save(converter.convertDTOtoModel(certificateDto)));
    }


    @Override
    public void setConverter(CertificateConverter converter, TagConverter tagConverter) {
        this.converter = converter;
        this.tagConverter = tagConverter;
    }

    @Override
    public Page<ResponseCertificateDTO> listCertificates(CertificateFindByRequestDTO certificateFindByRequestDTO,
                                                         Pageable pageable) {
        if (certificateFindByRequestDTO == null) {
            certificateFindByRequestDTO = new CertificateFindByRequestDTO();
        }
        if (!certificateFindByRequestDTO.getFindParameter().equals(FindParameter.DEFAULT)
                && certificateFindByRequestDTO.getValue().equals("")) {
            throw new BadRequestException("Value must have FindParameter", ErrorCode.BAD_REQUEST_ERROR);
        }
        if (certificateFindByRequestDTO.getFindParameter().equals(FindParameter.DEFAULT)
                && !certificateFindByRequestDTO.getValue().equals("")) {
            throw new BadRequestException("FindParameter must be here", ErrorCode.BAD_REQUEST_ERROR);
        }


        List<GiftCertificate> res;

        if (certificateFindByRequestDTO.getFindParameter().name().equals("DESCRIPTION")) {
            res = certificateRepository.findByDescriptionLike(certificateFindByRequestDTO.getValue());
        } else if (certificateFindByRequestDTO.getFindParameter().name().equals("NAME")) {
            res = certificateRepository.findByNameLike(certificateFindByRequestDTO.getValue());
        } else {
            res = certificateRepository.findAll();
        }


        if (certificateFindByRequestDTO.getSortParameter().equals(SortParameter.DATE)) {
            if (certificateFindByRequestDTO.getSortWay().equals(SortWay.DESC)) {
                res = res.stream().sorted(Comparator.comparing(GiftCertificate::getCreateDate).reversed()).collect(Collectors.toList());
            } else
                res = res.stream().sorted(Comparator.comparing(GiftCertificate::getCreateDate)).collect(Collectors.toList());
        }
        if (certificateFindByRequestDTO.getSortParameter().equals(SortParameter.NAME)) {
            if (certificateFindByRequestDTO.getSortWay().equals(SortWay.DESC)) {
                res = res.stream().sorted(Comparator.comparing(GiftCertificate::getName).reversed()).collect(Collectors.toList());
            } else
                res = res.stream().sorted(Comparator.comparing(GiftCertificate::getName)).collect(Collectors.toList());
        }

        if (res.size() > pageable.getPageSize()) {
            res = res.subList(pageable.getPageNumber() * pageable.getPageSize(), (pageable.getPageNumber() + 1) * pageable.getPageSize());
        }

        return converter.convertListToDTO(res);
    }

    @Override
    public ResponseCertificateDTO editCertificate(CertificateRequestDTO certificateEditDto, int id) {
        Optional<GiftCertificate> giftCertificate = certificateRepository.findById(id);

        if (giftCertificate.isEmpty()) {
            throw new AppNotFoundException("Certificate with this id is not exist" + id, ErrorCode.TAG_NOT_FOUND);
        }

        GiftCertificate certificate = giftCertificate.get();
        create(certificateEditDto);
        return converter.convertToDTO(certificateRepository.save(converter.updateByRequest(certificate, certificateEditDto)));
    }

    @Override
    public Page<ResponseCertificateDTO> findByTagName(String tagName, Pageable pageable) {
        return converter.convertListToDTO(certificateRepository.findByTagsName(tagName));
    }

    @Override
    public ResponseCertificateDTO editOneField(CertificateEditRequestDto certificateEditRequestDto) {

        if (certificateEditRequestDto.getParameter() == null || certificateEditRequestDto.getValue() == null || certificateEditRequestDto.getId() == 0) {
            throw new BadRequestException("Value, id and parameter can't be null", ErrorCode.BAD_REQUEST_ERROR);
        }
        int id = certificateEditRequestDto.getId();

        Optional<GiftCertificate> certificate = certificateRepository.findById(id);
        if (certificate.isEmpty()) {
            throw new AppNotFoundException("Certificate with this id is not found: " + id,
                    ErrorCode.CERTIFICATE_NOT_FOUND);
        }

        GiftCertificate giftCertificate = certificate.get();
        return converter.convertToDTO(certificateRepository.save(converter.updateByField(certificateEditRequestDto, giftCertificate)));
    }

    @Override
    public Page<ResponseCertificateDTO> findByTagsNameList(List<String> tagNames, Pageable pageable) {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        for (String tagName : tagNames) {
            giftCertificates.addAll(certificateRepository.findByTagsName(tagName));
        }
        List<GiftCertificate> certificates = giftCertificates.stream().
                filter(p -> new HashSet<>(p.getTagNames()).containsAll(tagNames)).toList();
        return converter.convertListToDTO(certificates);

    }

    @Override
    @Transactional
    public TagResponseDTO popularTag() {

        List<Tag> tags = certificateRepository.countByTagsName();
        List<Tag> res = tags.stream().sorted(Comparator.comparingInt(Tag::getGiftCertificatesSize).reversed()).toList();
        return tagConverter.convertOneToDTO(res.get(0));
    }

    private void create(CertificateRequestDTO certificateEditDto) {
        List<Tag> result = new ArrayList<>();

        for (int i = 0; i < certificateEditDto.getTags().size(); i++) {
            List<Tag> tags = tagRepository.findByName(certificateEditDto.getTags().get(i).getName());
            if (!tagRepository.findByName(certificateEditDto.getTags().get(i).getName()).isEmpty()) {
                result.addAll(tags);
            } else {
                Tag tag = new Tag();
                tag.setName(certificateEditDto.getTags().get(i).getName());
                result.add(tag);
            }

        }
        certificateEditDto.setTags(result);
    }

}
