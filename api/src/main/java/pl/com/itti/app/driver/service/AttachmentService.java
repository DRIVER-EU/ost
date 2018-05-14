package pl.com.itti.app.driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.com.itti.app.driver.dto.AttachmentDTO;
import pl.com.itti.app.driver.model.Answer;
import pl.com.itti.app.driver.model.Attachment;
import pl.com.itti.app.driver.model.enums.AttachmentType;
import pl.com.itti.app.driver.repository.AttachmentRepository;
import pl.com.itti.app.driver.util.FileProperties;
import pl.com.itti.app.driver.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttachmentService {

    private static final String AUDIO = "audio";

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private FileProperties fileProperties;

    public List<Attachment> createAttachments(List<String> descriptions,
                                              List<AttachmentDTO.Coordinates> coordinates,
                                              MultipartFile[] files,
                                              Answer answer) throws IOException {
        List<Attachment> attachments = new ArrayList<>();

        if (descriptions != null) {
            attachments.addAll(createDescriptionAttachments(descriptions, answer));
        }

        if (coordinates != null) {
            attachments.addAll(createLocationAttachments(coordinates, answer));
        }

        attachments.addAll(createFileAttachments(files, answer));

        return attachmentRepository.save(attachments);
    }

    private List<Attachment> createDescriptionAttachments(List<String> descriptions, Answer answer) {
        return descriptions.stream()
                .map(s -> convertDescriptionToAttachment(s, answer))
                .collect(Collectors.toList());
    }

    private List<Attachment> createLocationAttachments(List<AttachmentDTO.Coordinates> coordinates, Answer answer) {
        return coordinates.stream()
                .map(coord -> convertCoordinatesToAttachment(coord, answer))
                .collect(Collectors.toList());
    }

    private List<Attachment> createFileAttachments(MultipartFile[] files, Answer answer) throws IOException {
        List<Attachment> attachments = new ArrayList<>();

        for (MultipartFile file : files) {
            String dir;
            AttachmentType type;

            if (file.getContentType().startsWith(AUDIO)) {
                dir = fileProperties.getSoundDir();
                type = AttachmentType.VOICE;
            } else {
                dir = fileProperties.getImageDir();
                type = AttachmentType.PICTURE;
            }

            String fullName = saveFile(file, dir);
            attachments.add(convertFileToAttachment(file, fullName, type, answer));
        }
        return attachments;
    }


    private Attachment convertDescriptionToAttachment(String description, Answer answer) {
        return Attachment.builder()
                .answer(answer)
                .description(description)
                .type(AttachmentType.DESCRIPTION)
                .build();
    }

    private Attachment convertCoordinatesToAttachment(AttachmentDTO.Coordinates coordinates, Answer answer) {
        return Attachment.builder()
                .answer(answer)
                .longitude(coordinates.longitude)
                .latitude(coordinates.latitude)
                .altitude(coordinates.altitude)
                .type(AttachmentType.LOCATION)
                .build();
    }

    private Attachment convertFileToAttachment(MultipartFile file, String fullName, AttachmentType type, Answer answer) {
        return Attachment.builder()
                .answer(answer)
                .description(file.getOriginalFilename())
                .uri(fullName)
                .type(type)
                .build();
    }

    private String saveFile(MultipartFile file, String dir) throws IOException {
        String randomFileName = FileUtils.generateRandomFileName(new File(dir));
        String extension = FileUtils.getFileExtension(file);
        String fullName = randomFileName + extension;

        FileUtils.save(new File(dir), file, fullName);
        return fullName;
    }
}
