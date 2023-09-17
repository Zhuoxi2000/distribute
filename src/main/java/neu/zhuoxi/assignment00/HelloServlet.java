package neu.zhuoxi.assignment00;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 从请求中读取JSON数据
        Gson gson = new Gson();

        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            Status status = new Status();
            //测试用例
            if (isValidJson(requestBody.toString())) {
                // 返回一个固定的专辑密钥作为响应
                // 生成唯一的albumID（可以使用UUID等方式）
                String albumKey = generateAlbumKey();

                response.getWriter().write(gson.toJson(albumKey));
                response.getOutputStream().print(gson.toJson(status));
                response.getOutputStream().flush();

                // 获取上传的图片数据
                String imagePart = request.getParameter("image");
                // 保存图片到文件系统或数据库，根据需要进行相应的处理

                //创建album对象
                Album album = (Album) gson.fromJson(requestBody.toString(), Album.class);

            } else {
                response.getWriter().write("Validation failed.");
                response.getOutputStream().print(gson.toJson(status));
                response.getOutputStream().flush();
            }
            // 获取表单数据

        } catch (Exception ex) {
            ex.printStackTrace();
            Status status = new Status();
            status.setSuccess(false);
            status.setDescription(ex.getMessage());
            response.getOutputStream().print(gson.toJson(status));
            response.getOutputStream().flush();
        }
    }
    private String generateAlbumKey() {
        // 在这里实现生成albumID的逻辑，可以使用UUID等方式生成唯一的ID
        String albumKey = "myAlbum123";
        return albumKey;
    }
    private boolean isValidJson(String json) {
        try {
            // 使用JsonParser来验证JSON语法是否有效
            JsonParser parser = new JsonParser();
            parser.parse(json);
            return true;
        } catch (JsonSyntaxException e) {
            // JSON数据无效，捕获JsonSyntaxException异常
            return false;
        }
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 返回一个固定的响应（常量数据）
        String albumID = request.getParameter("albumID");
        System.out.println(albumID);

         //检查albumID是否为空或无效
        if (albumID == null || albumID.isEmpty() || !isValidAlbumID(albumID)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid asd  albumID");
            return;
        }

        // 根据albumID从数据库或其他数据源获取专辑信息，这里使用示例数据
        Album album = getAlbumInfo(albumID);

        // 检查是否找到专辑信息
        if (album == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Album not found");
            return;
        }

        // 将专辑信息转换为JSON格式并发送响应
        String albumJsonString = new Gson().toJson(album);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(albumJsonString);
        out.flush();


    }
    private boolean isValidAlbumID(String albumID) {
        // 在这里添加检查albumID是否有效的逻辑
        return true; // 假定所有albumID都是有效的
    }

    // 获取专辑信息的方法，根据实际需求实现
    private Album getAlbumInfo(String albumID) {
        // 在这里添加从数据库或其他数据源获取专辑信息的逻辑
        // 示例中返回一个固定的AlbumInfo对象
        return new Album("Eason ","White Rose","1996");
    }

    private boolean validateJson(String json) {
        // 在此处执行JSON验证逻辑
        // 返回true表示验证成功，否则返回false
        // 可以根据您的要求进行验证
        return true; // 这里示例中简单返回true
    }

}