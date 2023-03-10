package com.chandler.qr;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Component
public class QRCodeView extends AbstractView{
    
    public QRCodeView() {
        //뷰의 컨텐츠 타입 설정
        //이미지로 뿌려야 함.
        setContentType("image/png; charset=UTF-8");
    }
    
    public void renderMergedOutput(Map<String, Object> model, HttpServletRequest req, HttpServletResponse res) throws Exception {
    	renderMergedOutputModel(model, req, res);
    }
 
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest req, HttpServletResponse res) throws Exception{
          
        res.setContentType(getContentType());
        res.setHeader("Content-Transfer-Encoding", "binary");
          
        OutputStream out = res.getOutputStream();
          
        QRCodeWriter qrCodeWriter = new QRCodeWriter();        // QR 코드
        MultiFormatWriter barcode = new MultiFormatWriter();    // 바코드
           
        String text = (String)model.get("content");
        //한글 데이터 처리
        text = new String(text.getBytes("UTF-8"), "ISO-8859-1");
        //QR 코드 생성 및 출력
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 100, 100);
        /*BitMatrix bitMatrix = barcode.encode(text, BarcodeFormat.CODE_128, 100, 100);*/
           
        // zxing에서 스트림에 파일을 뿌릴수있도록 메소드를 지원함.
        MatrixToImageWriter.writeToStream(bitMatrix, "png", out);
           
        out.flush();
    }
}