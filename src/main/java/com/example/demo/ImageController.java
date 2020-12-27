package com.example.demo;

import com.mysql.cj.util.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/image")
public class ImageController {

    Logger logger = LoggerFactory.getLogger(ImageController.class);

    @PostMapping("/upload")
    public String scaleImage(@RequestParam("file") MultipartFile file) throws IOException {
        BufferedImage imBuff = ImageIO.read(file.getInputStream());
        logger.info(" image with %s %s", imBuff.getWidth(), imBuff.getHeight());
        BufferedImage outBuff = Scalr.resize(imBuff, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 384, 216, Scalr.OP_ANTIALIAS);

        // TODO write file to static folder
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = FilenameUtils.getBaseName(file.getOriginalFilename());
        String finalFileName = fileName + "_" + LocalDateTime.now() + "." + extension;
        File outputfile = new File( finalFileName);
        ImageIO.write(outBuff, extension, outputfile);

        // TODO convert file to Multipart
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write( outBuff, extension, byteArrayOutputStream );
        byteArrayOutputStream.flush();

        MultipartFile multipartFile = new MultipartImage(byteArrayOutputStream.toByteArray());

        return "convert image success!";
    }

}
