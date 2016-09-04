package com.adoulfakkar.quizzApp.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.adoulfakkar.quizzApp.service.api.UserService;
import com.adoulfakkar.quizzApp.webapp.utils.Tools;

@Controller
public class UploadController {

	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/image/upload", method=RequestMethod.POST)
	@ResponseBody
	public UploadResponse postImage (@RequestParam MultipartFile image) {
		OutputStream out = null;
		InputStream in = null;
		String path = null;
		
		try {
			String name = UUID.randomUUID().toString();
			int idx = image.getOriginalFilename().lastIndexOf(".");
			if (idx != -1) {
				String extension  = image.getOriginalFilename().substring(idx + 1);
				name += "." + extension;
			}
			
			String filePath = Constants.IMAGE_PATH + name;
			path = Constants.IMAGE_WEB_PATH + name;
			out = new FileOutputStream(filePath);
			
			in = image.getInputStream();
			Tools.copyStream(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return new UploadResponse(path);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/file/upload", method=RequestMethod.POST)
	@ResponseBody
	public UploadResponse postFile (@RequestParam MultipartFile file) {
		OutputStream out = null;
		InputStream in = null;
		String path = null;
		
		try {
			String name = file.getOriginalFilename();
			
			int idx = name.lastIndexOf(".");
			String extension = name.substring(idx);
			String fileName = name.substring(0, idx);

			String filePath = Constants.FILE_PATH + name;
			File f = new File (filePath);
			int count = 0;
			while (f.exists()) {
				name = fileName + "_" + count + extension;
				filePath = Constants.FILE_PATH + name;
				f = new File (filePath);
			}
			path = Constants.FILE_WEB_PATH + name;
			out = new FileOutputStream(f);
			
			in = file.getInputStream();
			Tools.copyStream(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return new UploadResponse(path);
	}
	
	@RequestMapping(value="/get/application.apk", method=RequestMethod.GET)
	@ResponseBody
	public void getApk (@RequestParam String token, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<String> roles = userService.role(token);
		boolean hasRole = false;
		for (String role : roles) {
			if ("USER".equals(role)) {
				hasRole = true;
				break;
			}
		}
		if (!hasRole) {
			throw new AuthenticationServiceException("Authorisation insuffisante");
		}
		String path = Constants.APK_FILEPATH;
		OutputStream out = null;
		InputStream in = null;
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		try {
			out = response.getOutputStream();
			in = new FileInputStream(path);
			response.setContentLength(in.available());
			byte[] bufer = new byte[4096];
			int len = -1;
			while ((len = in.read(bufer, 0, 4096)) != -1) {
				out.write(bufer, 0, len);
				out.flush();
			}
		} catch (IOException e) {
			throw e;
		}
		finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	@RequestMapping(value="/get/video.mp4", method=RequestMethod.GET)
	@ResponseBody
	public void getVideo (@RequestParam String token, @RequestParam String videoName,HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<String> roles = userService.role(token);
		boolean hasRole = false;
		for (String role : roles) {
			if ("USER".equals(role)) {
				hasRole = true;
				break;
			}
		}
		if (!hasRole) {
			throw new AuthenticationServiceException("Authorisation insuffisante");
		}
		String path = Constants.VIDEO_FILEPATH + videoName;
		
		OutputStream out = null;
		InputStream in = null;
		response.setContentType("video/mp4");
		try {
			out = response.getOutputStream();
			in = new FileInputStream(path);
			response.setContentLength(in.available());
			byte[] bufer = new byte[4096];
			int len = -1;
			while ((len = in.read(bufer, 0, 4096)) != -1) {
				out.write(bufer, 0, len);
				out.flush();
			}
		} catch (IOException e) {
			throw e;
		}
		finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
