package com.qxiangy.img;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class ImgUtil {
	
	
	//ͼƬ�ϴ�������ͼƬ���ƣ�
	public static String uploadImg(MultipartHttpServletRequest multipartRequest,String dir,String img,String imgName){
			
		String imgFile = ""; 
		MultipartFile file = multipartRequest.getFile(img);// ��ȡ�ϴ��ļ���  
		if(null!=file){
			
			try {
				
				File protoFile = new File(dir);
			 	if(!protoFile.exists()){
			 		
			 		protoFile.mkdir();
			 	}

			 	if(!"".equals(imgName)) {
			 		
			 		imgFile = imgName.substring(imgName.lastIndexOf("/")+1, imgName.length());
			 	} else{
			 		
			 		imgFile = String.format("%d", System.currentTimeMillis());
			 		String protoName = file.getOriginalFilename();
					String extensionName = protoName.substring(protoName.lastIndexOf(".")+1);
					imgFile = imgFile + "." + extensionName;//�ļ���
			 	}
			 	if(file.getSize()>0){
			 		
			 		FileUtils.writeByteArrayToFile(new File(dir+imgFile), file.getBytes());//����ָ���ļ���
			 	}
			} catch (Exception e) {
				
				imgFile = "";
			}
		}
			
		return imgFile;
	}
	
}
