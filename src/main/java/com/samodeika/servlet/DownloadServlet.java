package com.samodeika.servlet;

import com.samodeika.common.Dog;
import com.samodeika.common.Processor;
import com.samodeika.constants.Constants;
import com.samodeika.dao.DogDao;
import com.samodeika.dao.DogDaoImpl;
import com.samodeika.json.JsonProcessorImpl;
import com.samodeika.utils.FileUtils;
import com.samodeika.xls.XlsProcessor;
import com.samodeika.xls.XlsProcessorImpl;
import com.samodeika.xml.XmlProcessorImpl;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet(name = "DownloadServlet", urlPatterns = {"/DownloadServlet"})
public class DownloadServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Do get to DownloadServlet!");
        getServletContext().getRequestDispatcher("/WEB-INF/pages/downloadPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Request method: " + req.getMethod());
        String fileType = req.getParameter("fileType");
        Part part = req.getPart("data");

        if(fileType == null || fileType.isEmpty()) {
            req.setAttribute("errorDownloadMessage", "Not selected or incorrect file type!");
        }
        else {

            System.out.println("Download file type is: " + fileType);
            DogDao dao = new DogDaoImpl();
            List<Dog> dogs = dao.getDogs();
            File file = new File("default");
            OutputStream outStream = resp.getOutputStream();
            String headerKey;
            String headerValue;
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            FileInputStream inStream;
            String filename = "dogs";
            String data;

            switch (fileType) {
                case Constants.C_JSON:
                    Processor jsonProcessor = new JsonProcessorImpl();
                    data = jsonProcessor.downloadFile(dogs);
                    file = FileUtils.writeToFile(filename + ".json", data);
                    inStream = new FileInputStream(file);

                    headerKey = "Content-Disposition";
                    headerValue = String.format("attachment; filename=\"%s\"", file.getName());

                    resp.setHeader(headerKey, headerValue);
                    resp.setContentType("application/json");
                    resp.setContentLength(data.length());

                    // obtains response's output stream
                    while ((bytesRead = inStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }

                    inStream.close();
                    break;
                case Constants.C_XLS:
                    XlsProcessor xlsProcessor = new XlsProcessorImpl();
                    XSSFWorkbook workbook = xlsProcessor.getXls(dogs);
                    data = workbook.toString();
                    file = FileUtils.writeToFile(filename + ".xls", data);
                    headerKey = "Content-Disposition";
                    headerValue = String.format("attachment; filename=\"%s\"", file.getName());

                    resp.setHeader(headerKey, headerValue);
                    resp.setContentType("application/xls");
                    workbook.write(outStream);
                    break;
                case Constants.C_XML:
                    Processor xmlProcessor = new XmlProcessorImpl();
                    data = xmlProcessor.downloadFile(dogs);
                    file = FileUtils.writeToFile(filename + ".xml", data);
                    inStream = new FileInputStream(file);
                    headerKey = "Content-Disposition";
                    headerValue = String.format("attachment; filename=\"%s\"", file.getName());

                    resp.setHeader(headerKey, headerValue);
                    resp.setContentType("application/xml");
                    resp.setContentLength(data.length());

                    // obtains response's output stream
                    while ((bytesRead = inStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }

                    inStream.close();
                    break;
            }

            outStream.flush();
            outStream.close();
            req.setAttribute("successDownloadMessage", "File was uploaded successfully!");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/pages/downloadPage.jsp").forward(req, resp);
    }
}
