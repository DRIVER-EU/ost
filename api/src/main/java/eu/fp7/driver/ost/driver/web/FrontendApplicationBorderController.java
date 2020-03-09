package eu.fp7.driver.ost.driver.web;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/admin/border")
public class FrontendApplicationBorderController {

  @ResponseBody
  @GetMapping("/lower")
  public ResponseEntity returnLowerBorderText(){
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Border", "---");

    return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toString());
  }
}


