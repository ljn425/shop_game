package shop.game.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class FilePondProcessDto {
    private String filename;
    @Builder
    public FilePondProcessDto(String filename) {
        this.filename = filename;
    }
}
