package com.samodeika.servlet;

import com.samodeika.common.Dog;
import com.samodeika.common.Processor;
import com.samodeika.constants.Constants;
import com.samodeika.dao.DogDao;
import com.samodeika.dao.DogDaoImpl;
import com.samodeika.json.JsonProcessorImpl;
import com.samodeika.xls.XlsProcessorImpl;
import com.samodeika.xml.XmlProcessorImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UploadServlet", urlPatterns = {"/UploadServlet"})
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Do get to UploadServlet!");
        getServletContext().getRequestDispatcher("/WEB-INF/pages/uploadPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Request method: " + req.getMethod());
        String fileType = req.getParameter("fileType");
        Part part = req.getPart("data");

        if(fileType == null || fileType.isEmpty()) {
            req.setAttribute("errorUploadMessage", "Not selected or incorrect file type!");
        }
        else {

            List<Dog> dogs = new ArrayList<>();
            Processor processor;
            switch (fileType) {
                case Constants.C_JSON:
                    processor = new JsonProcessorImpl();
                    dogs = processor.processFile(part.getInputStream());
                    break;
                case Constants.C_XLS:
                    processor = new XlsProcessorImpl();
                    break;
                case Constants.C_XML:
                    processor = new XmlProcessorImpl();
                    break;
                default:
                    processor = new JsonProcessorImpl();
                    req.setAttribute("errorUploadMessage", "Not selected or incorrect file type!");
                    break;
            }

            dogs = processor.processFile(part.getInputStream());
            DogDao dao = new DogDaoImpl();
            if(dao.saveDogs(dogs)) {
                req.setAttribute("successUploadMessage", "File was uploaded successfully!");
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/pages/uploadPage.jsp").forward(req, resp);

    }
}
