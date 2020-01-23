package eu.fp7.driver.ost.driver.web;

import eu.fp7.driver.ost.driver.model.Attachment;
import eu.fp7.driver.ost.driver.service.AttachmentService;
import eu.fp7.driver.ost.driver.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @GetMapping("/{attachment_id:\\d+}")
    public void findFileById(@PathVariable(value = "attachment_id") long attachmentId,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        Attachment attachment = attachmentService.findFileById(attachmentId);
        String resDir = attachmentService.getResourceDirOfAttachment(attachment);
        String filePath = FileUtils.getAbsolutePath(resDir + attachment.getUri());
        FileUtils.setFileResponse(response, request, filePath);
    }
}
