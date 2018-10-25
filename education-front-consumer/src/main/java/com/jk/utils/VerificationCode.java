package com.jk.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ImgServlet
 */
@WebServlet(name="VerificationCode",urlPatterns="/verificationCode")
public class VerificationCode extends HttpServlet{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int width = 80;  
        int height = 40;  
        int lines = 10;  
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
  
        Graphics g = img.getGraphics();  
  
        // 璁剧疆鑳屾櫙鑹�  
        g.setColor(Color.WHITE);  
        g.fillRect(0, 0, width, height);  
  
        // 璁剧疆瀛椾綋  
        g.setFont(new Font("瀹嬩綋", Font.BOLD, 20));  
  
        // 闅忔満鏁板瓧  
        Random r = new Random(new Date().getTime());  
        String code = "";
        for (int i = 0; i < 4; i++) { 
            int a = r.nextInt(10); 
            code += a;
            int y = 10 + r.nextInt(20);// 10~30鑼冨洿鍐呯殑涓�涓暣鏁帮紝浣滀负y鍧愭爣  
  
            Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));  
            g.setColor(c);  
  
            g.drawString("" + a, 5 + i * width / 4, y);  
        }  
        HttpSession session = request.getSession(true);
        session.setAttribute("imgcode", code);
        // 骞叉壈绾�  
        for (int i = 0; i < lines; i++) {  
            Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));  
            g.setColor(c);  
            g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));  
        }  
  
        g.dispose();// 绫讳技浜庢祦涓殑close()甯﹀姩flush()---鎶婃暟鎹埛鍒癷mg瀵硅薄褰撲腑  
        // 鍛婅瘔瀹㈡埛绔紝杈撳嚭鐨勬牸寮�  
        response.setContentType("image/jpeg");  
        response.reset();
        // 鍛婅瘔瀹㈡埛绔紝杈撳嚭鐨勬牸寮�  
        response.setContentType("image/jpeg");  
        ImageIO.write(img, "jpg", response.getOutputStream());  
    }  

}
