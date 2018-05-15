package pl.com.itti.app.driver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.com.itti.app.driver.model.Attachment;
import pl.com.itti.app.driver.service.AttachmentService;
import pl.com.itti.app.driver.util.FileUtils;

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
