package com.chandler.qr.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chandler.qr.QRCodeView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Controller
public class QrController {
	
	private final QRCodeView qRCodeView = new QRCodeView();
	
    @GetMapping("/")
    public String hello() {
    	System.out.println("하하하");
        return "index";
    }

    /**
     * localhost:8080/make?content=https://ondaji.iptime.org 라고 실행하면 
     *  qr코드가 생성되어 browser에 보이게된다.
     * @param request
     * @param response
     * @param content
     */
    @GetMapping("/make")
    public void makeQr(HttpServletRequest request, HttpServletResponse response, @RequestParam String content) {
        Map<String, Object> model = new HashMap<>();
        model.put("content", content);
        try {
        	qRCodeView.renderMergedOutput(model, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * localhost:8080/save?content=https://ondaji.iptime.org 라고 실행하면 
     *  /appdata/qrImage폴더에 yyyyMMddHHmmssqr.png 형태의 qr코드가 생성된다.
     * @param request
     * @param content
     * @return
     */
    @GetMapping("/save")
    public ResponseEntity<?> saveQr(HttpServletRequest request, @RequestParam String content) {
    	String fileName;
        
    	try {
        	fileName = qrImageGenerator(content);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
            return new ResponseEntity<>("fail", null, HttpStatus.CONFLICT);
        }
        
        return new ResponseEntity<>(fileName + ".png", null, HttpStatus.OK);
    }
    
    private String qrImageGenerator(String content) throws WriterException, IOException {
    	
    	String savePath = "/appdata/qrImage";
        File file = new File(savePath);
        //파일 경로가 없으면 파일 생성
        if (!file.exists()) {
            file.mkdirs();
        }
        
        //QR 생성
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 100, 100);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        //yyyyMMddHHmmss 형식의 날짜 및 시간 정보 파일명에 추가
        String datetimeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = datetimeStr + "qr";
        //파일 생성
        File temp = new File(savePath + "/" + fileName + ".png");

        //ImageIO를 사용하여 파일쓰기
        ImageIO.write(bufferedImage, "png", temp);

        return fileName;
    }
}
