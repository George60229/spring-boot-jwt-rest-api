package com.alibou.security.converter;



import com.alibou.security.dto.request.CertificateEditRequestDto;
import com.alibou.security.dto.request.CertificateRequestDTO;
import com.alibou.security.dto.response.ResponseCertificateDTO;
import com.alibou.security.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CertificateConverter {
    @Autowired

    TagConverter tagConverter;

    public void setTagConverter(TagConverter tagConverter){
        this.tagConverter=tagConverter;
    }

    public Page<ResponseCertificateDTO> convertListToDTO(List<GiftCertificate> certificates) {

        return listToPage(certificates.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()));
        //see spring mapper todo


    }


    public ResponseCertificateDTO convertToDTO(GiftCertificate certificate) {
        ResponseCertificateDTO responseCertificateDTO = new ResponseCertificateDTO();
        responseCertificateDTO.setCertificateId(certificate.getCertificateId());
        responseCertificateDTO.setName(certificate.getName());
        responseCertificateDTO.setDescription(certificate.getDescription());
        responseCertificateDTO.setDuration(certificate.getDuration());
        responseCertificateDTO.setTags(tagConverter.convertWithList(certificate.getTags()));
        if (certificate.getCreateDate() != null) {
            responseCertificateDTO.setCreateDate(certificate.getCreateDate().toString());
        }
        if (certificate.getLastUpdateDate() != null) {
            responseCertificateDTO.setLastUpdateDate(certificate.getLastUpdateDate().toString());
        }
        responseCertificateDTO.setPrice(certificate.getPrice());
        return responseCertificateDTO;
    }

    public GiftCertificate convertDTOtoModel(CertificateRequestDTO certificate) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(certificate.getName());
        giftCertificate.setDescription(certificate.getDescription());
        giftCertificate.setDuration(certificate.getDuration());
        giftCertificate.setPrice(certificate.getPrice());
        giftCertificate.setTags(certificate.getTags());
        giftCertificate.setCreateDate(LocalDateTime.now());
        return giftCertificate;
    }

    public GiftCertificate updateByField(CertificateEditRequestDto certificateEditRequestDto, GiftCertificate giftCertificate) {
        String value = certificateEditRequestDto.getValue();
        switch (certificateEditRequestDto.getParameter()) {
            case NAME -> giftCertificate.setName(value);
            case DESCRIPTION -> giftCertificate.setDescription(value);
            case PRICE -> giftCertificate.setPrice(BigDecimal.valueOf(Integer.parseInt(value)));
            case DURATION -> giftCertificate.setDuration(Integer.parseInt(value));
        }
        return giftCertificate;
    }

    public GiftCertificate updateByRequest(GiftCertificate giftCertificate,
                                           CertificateRequestDTO certificateRequestDTO) {
        if (giftCertificate.getName() != null || certificateRequestDTO.getName() != null) {
            giftCertificate.setName(Objects.requireNonNullElse(certificateRequestDTO.getName(),
                    giftCertificate.getName()));
        }
        if (giftCertificate.getPrice() != null || certificateRequestDTO.getPrice() != null) {
            giftCertificate.setPrice(Objects.requireNonNullElse(certificateRequestDTO.getPrice(),
                    giftCertificate.getPrice()));
        }
        if (giftCertificate.getDescription() != null || certificateRequestDTO.getDescription() != null) {
            giftCertificate.setDescription(Objects.requireNonNullElse(certificateRequestDTO.getDescription(),
                    giftCertificate.getDescription()));
        }
        if (giftCertificate.getDuration() == 0) {
            giftCertificate.setDuration(certificateRequestDTO.getDuration());
        }
        if (!certificateRequestDTO.getTags().isEmpty()) {
            giftCertificate.setTags(certificateRequestDTO.getTags());
        }

        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        return giftCertificate;
    }

    public Page<ResponseCertificateDTO> listToPage(List<ResponseCertificateDTO> certificateResponseDto) {
        return new PageImpl<>(certificateResponseDto);
    }

}
