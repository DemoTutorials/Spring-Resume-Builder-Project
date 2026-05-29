package com.avisys.cv.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class ImageUtil {

	private ImageUtil() {
	      super();
	  }
	  
	  public static BufferedImage makeRoundedCorner(BufferedImage image) {
	      int w = image.getWidth();
	      int h = image.getHeight();
	      int cornerRadius = w*h/2;
	      
	      BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

	      Graphics2D g2 = output.createGraphics();
	      
	      g2.setComposite(AlphaComposite.Src);
	      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	      g2.setColor(Color.WHITE);
	      g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

	      g2.setComposite(AlphaComposite.SrcIn);
	      g2.drawImage(image, 0, 0, null);        
	      g2.dispose();
	      return output;
	  }
}
