package shop.game.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/partner")
public class PartnerApiController {

    @PostMapping("/game/image")
    public Map<String, String> gameImageUpload(@RequestParam("file")MultipartFile file) {
        if (!file.isEmpty()) {
            log.info("file name: {}", file.getOriginalFilename());
            log.info("file size: {}", file.getSize());
        }

        Map<String, String> response = new HashMap<>();
        response.put("key", file.getOriginalFilename());

        return response;
    }

    @DeleteMapping("/game/image")
    public ResponseEntity<String> gameImageDelete(HttpServletRequest request, @RequestBody(required = false) String id) throws IOException {
        System.out.println("id = " + id);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }


}
