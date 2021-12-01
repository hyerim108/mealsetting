package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;



public class detail extends JFrame{
	public detail() {
		setTitle("메인");
		Container c = getContentPane();
		c.add(new CP(),BorderLayout.CENTER);
		String url = "http://m.naver.com";
        makeQRCode(url, 200, 200, ".", "daum");

		setSize(300,300);
		setVisible(true);
		
	}
	public static void makeQRCode(String url, int width,int height,String filePath,String fileName){
        File file = null;
        JLabel la = new JLabel();
        try {
            // 경로가 존재하지 않으면 경로 생성
            file = new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
            // UTF-8로 인코딩된 문자열을 ISO-8859-1로 생성
            url = new String(url.getBytes("UTF-8"),"ISO-8859-1");
            // QRCodeWriter객체 생성
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(url, BarcodeFormat.QR_CODE, width, height);
            // 배경색과 전경색 지정
            int foregroundColor = 0xFF000000;
            int backgroundColor = 0xFFAA0000;
            MatrixToImageConfig config = new MatrixToImageConfig(foregroundColor,backgroundColor);
            // BufferedImage 객체 생성
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix,config);
            // 이미지 저장
            ImageIO.write(qrImage, "png", new File(filePath+File.separator+fileName+".png"));
            qrImage = ImageIO.read(new File(".\\DataFiles\\QR.png"));
            la.setIcon(new ImageIcon(qrImage));
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	class CP extends JPanel{
		public CP() {
			ImageIcon img = new ImageIcon(".\\DataFiles\\QR.png");
			JLabel la = new JLabel(img);
			
			
			QRCodeWriter q = new QRCodeWriter();

			try {
				String text = "https://www.daum.net";

				text = new String(text.getBytes("UTF-8"), "ISO-8859-1");
				// 바코드 색상 값
				int qrcodeColor =   0xFF2e4e96;
				// 배경 색상 값
				int backgroundColor = 0xFFFFFFFF;

				BitMatrix bitMatrix = q.encode(text, BarcodeFormat.QR_CODE,200,200);
				MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor,backgroundColor);
				BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);
				// ImageIO를 사용한 바코드 파일쓰기
				ImageIO.write(bufferedImage, "png", new File(".\\DataFiles\\QR.png"));

	
				MatrixToImageWriter.writeToStream(bitMatrix, "png",
						new FileOutputStream(new File(".\\DataFiles\\QR.png")));
				add(la);
				
			} catch (Exception e) {

				e.printStackTrace();

			}
		}
	}
	public static void main(String arg[]) {
		new detail();
	}
	
}
