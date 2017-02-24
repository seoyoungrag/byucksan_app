package com.sds.acube.app.common.util;

//import java.awt.Container;
//import java.awt.Graphics2D;
//import java.awt.HeadlessException;
//import java.awt.Image;
//import java.awt.MediaTracker;
//import java.awt.RenderingHints;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//
//import javax.imageio.ImageIO;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ThumbNailUtil {
    /*
    static final Log logger = LogFactory.getLog(AppException.class);

    public static boolean createBmpThumbNail(String imgOrg, String imgThumbNail, int tWidth, int tHeight) throws Exception {
	boolean result = false;
	File sourceFile = new File(imgOrg);
	if (sourceFile.exists()) {
	    BufferedImage bi = ImageIO.read(sourceFile);

	    BufferedImage bufferImage = new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D  g2 = bufferImage.createGraphics();//Graphics2D 객체 생성
	    g2.drawImage(bi, 0, 0, tWidth, tHeight, null);//이미지를 가로 ,세로 크기로 그린다.     
	    File file = new File(imgThumbNail);
	    ImageIO.write(bufferImage, "jpg", file);//그려진 이미지를 파일로 채움
	    bi.flush();

	    result = true;
	}
	
	return result;
    }

    public static boolean createThumbNail(String imgOrg, String imgThumbNail, int tWidth, int tHeight, int tQuality) throws Exception {
	boolean result = false;
	BufferedOutputStream out = null;

	try {

	    // load image from INFILE
	    Image image = Toolkit.getDefaultToolkit().getImage(imgOrg);
	    MediaTracker mediaTracker = new MediaTracker(new Container());
	    mediaTracker.addImage(image, 0);
	    mediaTracker.waitForID(0);

	    // determine thumbnail size from WIDTH and HEIGHT
	    int thumbWidth = tWidth;
	    int thumbHeight = tHeight;
	    double thumbRatio = (double) thumbWidth / (double) thumbHeight;
	    int imageWidth = image.getWidth(null);
	    int imageHeight = image.getHeight(null);

	    if(imageWidth < tWidth) {
		thumbWidth = image.getWidth(null);
		thumbHeight = image.getHeight(null);
	    } else {

		double imageRatio = (double) imageWidth / (double) imageHeight;
		if(thumbRatio < imageRatio) {
		    thumbHeight = (int) (thumbWidth / imageRatio);
		} else {
		    thumbWidth = (int) (thumbHeight * imageRatio);
		}
	    }

	    // draw original image to thumbnail image object and
	    // scale it to the new size on-the-fly
	    BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = thumbImage.createGraphics();
	    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
	    graphics2D.dispose();

	    // save thumbnail image to OUTFILE
	    out = new BufferedOutputStream(new FileOutputStream(imgThumbNail));
	    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	    JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
	    int quality = tQuality;
	    quality = Math.max(0, Math.min(quality, 100));
	    param.setQuality((float) quality / 100.0f, false);
	    encoder.setJPEGEncodeParam(param);
	    encoder.encode(thumbImage);
	    result = true;
	} catch(HeadlessException e) {
	    logger.error(e.getMessage());
	} catch(Exception e) {
	    logger.error(e.getMessage());
	} finally {
	    try {
		if(out != null)
		    out.close();
	    } catch(Exception e) {
		logger.error(e.getMessage());
		throw new Exception();
	    }
	}
	
	return result;
    }
*/    
}
