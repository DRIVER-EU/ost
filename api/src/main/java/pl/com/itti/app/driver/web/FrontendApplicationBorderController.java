package pl.com.itti.app.driver.web;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.com.itti.app.driver.util.schema.SchemaCreator;


@RestController
@RequestMapping("admin/border")
public class FrontendApplicationBorderController {

  @ResponseBody
  @GetMapping("/lower")
  public ResponseEntity returnLowerBorderText(){
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Border", "---");

    return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toString());
  }
}


