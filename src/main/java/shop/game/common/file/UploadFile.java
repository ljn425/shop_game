package shop.game.common.file;

import lombok.Data;

@Data
public class UploadFile {
    private String uploadFileName;
    private String storeFileName;
    private String ext;
    private Long fileSize;
    public UploadFile(String uploadFileName, String storeFileName, String ext, Long fileSize) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.ext = ext;
        this.fileSize = fileSize;
    }
}
