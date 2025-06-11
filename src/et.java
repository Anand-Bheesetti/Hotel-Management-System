import javax.servlet.http.*;
import java.io.*;

public class FileDownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // ✅ Source: user-controlled input from request parameter
        String filename = request.getParameter("file");

        // ❌ Sink: unsanitized user input used to construct file path
        File file = new File("/var/data/" + filename);

        // ❌ Sink: vulnerable to path traversal (e.g., filename = ../../etc/passwd)
        FileInputStream fis = new FileInputStream(file);

        // Just read file into output (simulated)
        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }

        fis.close();
        os.flush();
    }
}
