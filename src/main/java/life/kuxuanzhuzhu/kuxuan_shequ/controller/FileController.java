package life.kuxuanzhuzhu.kuxuan_shequ.controller;

import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomErrorCode;
import life.kuxuanzhuzhu.kuxuan_shequ.Exception.CustomException;
import life.kuxuanzhuzhu.kuxuan_shequ.dto.FileDTO;
import life.kuxuanzhuzhu.kuxuan_shequ.provider.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 邓鑫鑫
 * @date 2019年08月23日 21:30:26
 * @Description
 */
@Controller
public class FileController {

    @Autowired
    private UCloudProvider ucloudProvider;

    @ResponseBody
    @RequestMapping("file/upload")
    public FileDTO upload(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html");
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file =  multipartHttpServletRequest.getFile("editormd-image-file");
        try {
            String fileName = ucloudProvider.upload(file.getInputStream(),file.getContentType(),file.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(fileName);
            return fileDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(CustomErrorCode.FILE_UPLOAD_FAIL);
        }
    }
}
