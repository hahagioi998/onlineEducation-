import com.alibaba.fastjson.JSONObject;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.configuration.FileSystem;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Test {

    @org.junit.Test
    public void generateQRCode() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("company", "http://www.xuecheng.com");
        jsonObject.put("author", "lxj");
        jsonObject.put("address", "湖南省");

        String jsonString = jsonObject.toString();

        Map<EncodeHintType, Object> map = new HashMap<>();

        map.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            BitMatrix encode = new MultiFormatWriter().encode(jsonString,
                    BarcodeFormat.QR_CODE, 200, 200, map);
            Path path= FileSystems.getDefault().getPath("f://QRCode.jpg");
            MatrixToImageWriter.writeToPath(encode,"jpg",path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
