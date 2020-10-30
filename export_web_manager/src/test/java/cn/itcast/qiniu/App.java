package cn.itcast.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/*
空间名称：saas143
临时域名：qij9jcfkv.hd-bkt.clouddn.com   （上传时候不用，访问时候用）
AK： 4GHsXCE6Mr0jBEQPsqNv24Hg5KnrqtMG9lo98ZpR
SK： e0rYJNsgCIFafNmn7Fn7Qze4pq-kPVNvudCFFBT0
*/
public class App {
    public static void main(String[] args) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "4GHsXCE6Mr0jBEQPsqNv24Hg5KnrqtMG9lo98ZpR";
        String secretKey = "e0rYJNsgCIFafNmn7Fn7Qze4pq-kPVNvudCFFBT0";
        String bucket = "saas143";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "E:\\love.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }
}
