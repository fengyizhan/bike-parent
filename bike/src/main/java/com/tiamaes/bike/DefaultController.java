package com.tiamaes.bike;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.octo.captcha.module.CaptchaModuleException;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

@Controller
public class DefaultController {

	@Autowired
	private DefaultManageableImageCaptchaService imageCaptchaService;
	
	@RequestMapping(value = {"/{module:(?!module|resources).+}/**/*.html", "/build.html"}, method = RequestMethod.GET)
	public void module(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("/index.html").forward(request, response);
	}
	
	@RequestMapping(value="/jcaptcha",method=RequestMethod.GET)
	public void jcaptcha(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		byte[] captchaChallengeAsJpeg = null;
        // the output stream to render the captcha image as jpeg into 
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // get the session id that will identify the generated captcha.  
            // the same id must be used to validate the response, the session id  
            // is a good candidate!
            String captchaId = req.getSession().getId();
            // call the ImageCaptchaService getChallenge method
            BufferedImage challenge = imageCaptchaService.getImageChallengeForID(captchaId, req.getLocale());
            // a jpeg encoder
            ImageIO.write(challenge, "JPEG", jpegOutputStream);
        } catch (IllegalArgumentException e) {
        	resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (CaptchaModuleException e) {
        	resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  
            return;
        }  
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        // flush it in the response
        resp.setHeader("Cache-Control", "no-store");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        ServletOutputStream output = resp.getOutputStream();
        output.write(captchaChallengeAsJpeg);
        
        output.flush();  
        output.close();
	}
}
