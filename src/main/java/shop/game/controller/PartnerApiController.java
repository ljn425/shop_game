package shop.game.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.game.dto.FilePondProcessDto;
import shop.game.enums.ImageType;
import shop.game.service.GameService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/partner")
public class PartnerApiController {

    private final GameService gameService;

    @PostMapping("/game/image")
    public ResponseEntity<FilePondProcessDto> gameImageUpload(@RequestParam("file")MultipartFile file,
                                               @RequestHeader("x-game-number") String gameNumber) throws IOException {
        FilePondProcessDto filePondProcessDto = null;
        if (!file.isEmpty()) {
            filePondProcessDto = gameService.uploadFile(file, Long.valueOf(gameNumber));
        }

//        Map<String, String> response = new HashMap<>();
//        response.put("key", file.getOriginalFilename());

        return ResponseEntity.status(HttpStatus.CREATED).body(filePondProcessDto);
    }

    @DeleteMapping("/game/image")
    public ResponseEntity<String> gameImageDelete(@RequestBody(required = false) String fileName) throws IOException {
        gameService.deleteFile(fileName);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @GetMapping("/game/image/load")
    public Map<String, List<FilePondProcessDto>> gameImageLoad(@RequestParam("gameNumber") Long gameNumber) {
        List<FilePondProcessDto> gameImages = gameService.findGameImages(gameNumber, ImageType.SCREENSHOT);
        Map<String, List<FilePondProcessDto>> response = new HashMap<>();
        response.put("fileNames", gameImages);
        return response;
    }



}
