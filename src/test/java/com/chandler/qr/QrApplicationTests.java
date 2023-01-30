package com.chandler.qr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@SpringBootTest
class QrApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	void testQr() throws WriterException, IOException {
		String qr = getQRCodeImage("https://ondaji.iptime.org", 200, 200);
		
		System.out.println("@~~:" + qr);
	}
	
	// QR코드 이미지 생성
	public static String getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

		return Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
	}
	
	public static String createQr(String text, int width, int height) throws WriterException, IOException {
		BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		MatrixToImageWriter.writeToStream(matrix, "PNG", out);
			
		return Base64.getEncoder().encodeToString(out.toByteArray());
  }
}
