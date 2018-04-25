package pl.com.itti.app.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.dto.AttachmentDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.model.Attachment;
import pl.com.itti.app.driver.model.enums.AttachmentType;
import pl.com.itti.app.driver.util.FileProperties;
import pl.com.itti.app.driver.util.FileUtils;
import pl.com.itti.app.driver.util.InvalidDataException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttachmentService {

    @Autowired
    private FileProperties fileProperties;

    public List<Attachment> createDescriptionAttachments(List<String> descriptions, Answer answer) {
        return descriptions.stream()
                .map(s -> Attachment.builder()
                        .answer(answer)
                        .description(s)
                        .type(AttachmentType.DESCRIPTION)
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<Attachment> createLocationAttachments(List<AttachmentDTO.Coordinates> coordinates, Answer answer) {
        return coordinates.stream()
                .map(coord -> Attachment.builder()
                        .answer(answer)
                        .longitude(coord.longitude)
                        .latitude(coord.latitude)
                        .altitude(coord.altitude)
                        .type(AttachmentType.LOCATION)
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<Attachment> createFileAttachments(MultipartFile[] files, Answer answer) throws IOException {
        List<Attachment> attachments = new ArrayList<>();

        for (MultipartFile file : files) {
            String dir;
            AttachmentType type;

            if (file.getContentType().startsWith("audio")) {
                dir = fileProperties.getSoundDir();
                type = AttachmentType.VOICE;
            } else if (file.getContentType().startsWith("image")) {
                dir = fileProperties.getImageDir();
                type = AttachmentType.PICTURE;
            } else {
                throw new InvalidDataException("Bad type of attachment");
            }

            String fullName = saveFile(file, dir);
            attachments.add(
                    Attachment.builder()
                            .answer(answer)
                            .description(file.getOriginalFilename())
                            .uri(fullName)
                            .type(type)
                            .build()
            );
        }

        return attachments;
    }

    private String saveFile(MultipartFile file, String dir) throws IOException {
        String randomFileName = FileUtils.generateRandomFileName(new File(dir));
        String extension = "." + file.getOriginalFilename().split("\\.")[1];
        String fullName = randomFileName + extension;

        FileUtils.save(new File(dir), file, fullName);
        return fullName;
    }
}
