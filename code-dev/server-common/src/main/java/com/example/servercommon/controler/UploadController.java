package com.example.servercommon.controler;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.servercommon.dto.*;

import com.example.servercommon.service.UpdateService;

import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @project: blog
 * @ClassName: UPloadController
 * @author: smallwei
 * @creat: 2022/8/27 17:30
 * 描述:
 */

@CrossOrigin
@RestController
@Slf4j
public class UploadController {
    /**
     * 上传图片
     *
     * @param multipartFile 文件对象
     * @return
     * @throws IOException
     */
    @Autowired
    UpdateService updateService;
    //  minio upload
    @ResponseBody
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public String uploadImg(@RequestParam("bytes") byte[] bytes, @RequestParam("username") String username, @RequestParam("filename")String filename,
                            @RequestParam("contenttype")String contenttype)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException {
            System.out.print(filename);
            System.out.print(contenttype);
        return updateService.UpdateImg(bytes, username,filename,contenttype);


    }
    @SentinelResource(value="upload")
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadImg( @RequestParam String username, @RequestParam("file") MultipartFile file,@RequestParam("prefix") String prefix)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException {
         updateService.add(file.getInputStream(),username,file.getOriginalFilename(),prefix);
        return "添加成功";
    }

    //minio donload
    @SentinelResource(value="Download")
    @ResponseBody
    @RequestMapping(value = "/Download")
    public String downLoad(@RequestBody String username, @RequestBody String objectname ,@RequestBody String path) throws
            ServerException, InsufficientDataException, ErrorResponseException,
            IOException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException {
         updateService.donload( username, objectname,path);
         return "下载成功";
    }
    @RequestMapping(value="/mkdir",method = RequestMethod.POST)
    public String Mkdir(@RequestBody updatemindto dto) throws
            ServerException, InsufficientDataException, ErrorResponseException, IOException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException {

              if(dto.getLocalpath()==null){
                  dto.setLocalpath("");
              }
        return updateService.Mkdir(dto.getUsername(),dto.getObjectname(),dto.getLocalpath());
    }
    @ResponseBody
    @RequestMapping(value="/url")
    public String  Url(@RequestBody urlDto dto) throws Exception {
        return updateService.getPresignedObjectUrl(dto.getUsername(),dto.getObjectname());
    }
     @ResponseBody
     @RequestMapping(value="/list")
    public List<minDto> List(@RequestBody listDto dto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

         System.out.print(dto.getObjectname());
          System.out.print(dto.getPrefix());
        return updateService.List(dto.getObjectname(),dto.getPrefix());


     }
     @ResponseBody
     @RequestMapping("/delete")
     public  void Delete(@RequestBody Dldto dto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        updateService.delete(dto.getUsername(),dto.getObjectname());
     }







}


