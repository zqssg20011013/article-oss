package com.example.servercommon.service;
import com.example.servercommon.dto.minDto;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @project: blog
 * @ClassName: UpdateService
 * @author: smallwei
 * @creat: 2023/1/17 17:05
 * 描述:
 */
@Service
public class UpdateService {
    String endpoint = "http://127.0.0.1:9000";
    String aceeeskey = "minioadmin";
    String secretkey = "minioadmin";
    MinioClient minioclient = MinioClient.builder().endpoint(endpoint).credentials(aceeeskey, secretkey).build();

    public String UpdateImg(byte[] bytes, String username, String filename, String contenttype) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        InputStream file = new ByteArrayInputStream(bytes);

        String bucket="image"+username;
        boolean exist=minioclient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());

        if(exist!=true){
            String pub = "{\"Version\":\"2012-10-17\"," +
                    "\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":" +
                    "{\"AWS\":[\"*\"]},\"Action\":[\"s3:ListBucket\",\"s3:ListBucketMultipartUploads\"," +
                    "\"s3:GetBucketLocation\"],\"Resource\":[\"arn:aws:s3:::" + bucket +
                    "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:PutObject\",\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:GetObject\",\"s3:ListMultipartUploadParts\"],\"Resource\":[\"arn:aws:s3:::" +
                    bucket +
                    "/*\"]}]}";
            minioclient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            minioclient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket).config(pub).build());

       }

        minioclient.putObject(PutObjectArgs.builder().contentType(contenttype).
                bucket(bucket).object(filename).stream(file, file.available(), -1).build());
        return "localhost:9000:image"+username;
    }
    public GetObjectResponse get(String objectname, String username) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

       GetObjectResponse file =  minioclient.getObject(GetObjectArgs.builder().bucket(username).object(objectname).build());

       return file;
    }
    public void donload(String username,String filename,String path) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioclient.downloadObject(DownloadObjectArgs.builder().bucket(username).object(filename).filename(path).build());
    }
    public String Mkdir(String username,String objectname,String localpath)
            throws IOException, ServerException, InsufficientDataException, ErrorResponseException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException,
            InternalException {
        String txt="！！!->文件夹内容清空后文件夹自动删除";
        InputStream file = new ByteArrayInputStream(txt.getBytes());
         if( localpath.equals("")){
          minioclient.putObject(PutObjectArgs.builder().bucket(username).object(objectname+"/"+"注意事项")
                  .stream(file, file.available(), -1).build());}
         else{
             minioclient.putObject(PutObjectArgs.builder().bucket(username).object(localpath+"/"+objectname+"/"+"注意事项")
                     .stream(file, file.available(), -1).build());}

         return "创建文件夹成功";
    }
    public List<minDto> List(String username , String prefix) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Iterable <io.minio.Result<io.minio.messages.Item>> results=minioclient.listObjects
                (ListObjectsArgs.builder().bucket(username).prefix(prefix).build());
           List <minDto> name=new ArrayList<minDto>();

        for (Result<Item> result : results) {
            minDto dto=    new minDto(result.get().objectName(),result.get().isDir(),result.get().size());
              name.add(dto);
        }
          return name;
    }
    //
    public String getPresignedObjectUrl(String bucketName, String objectName) throws Exception {
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .method(Method.GET).build();
        return minioclient.getPresignedObjectUrl(args);
    }
    public void delete(String username, String objectname) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        minioclient.removeObject(RemoveObjectArgs.builder().bucket(username).object(objectname).build());

    }
    public void  add(InputStream input,String username,String objectname,String localpath) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if( localpath.equals("")){
            minioclient.putObject(PutObjectArgs.builder().bucket(username).object(objectname)
                    .stream(input,input.available(),-1).build());}
        else{
            minioclient.putObject(PutObjectArgs.builder().bucket(username).object(localpath+objectname)
                    .stream(input,input.available(),-1).build());}
    }

}
