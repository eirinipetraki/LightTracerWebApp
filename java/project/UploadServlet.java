/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project;


import HandleData.GalleryPicture;
import database.DatabaseInsert;
import java.io.File;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UploadServlet extends HttpServlet {
     private HttpSession session;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        doPost(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        String url = "/Controller?";
        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();

                    if (!item.isFormField()) {
                        String fileName = item.getName();

                        String root = getServletContext().getRealPath("/");
                        File path = new File(root + "/uploads");
                        if (!path.exists()) {
                            boolean status = path.mkdirs();
                        }

                        File uploadedFile = new File(path + "/" + fileName);
                        System.out.println("Path"+uploadedFile.getAbsolutePath());
                        item.write(uploadedFile);
                       if(request.getParameter(Commands.paramUploadGraph) != null){
                            String userName = request.getParameter(Commands.paramUploadGraph);
                            System.out.println("Name"+userName);
                            GalleryPicture pic = new GalleryPicture(userName, null, uploadedFile, 7);
                            DatabaseInsert connector = new DatabaseInsert();
                            connector.insertPictureIntoGallery(pic);
                           
                            url += Commands.buttonUpload + "=true";
                       }
                       
                        else{
                            url += Commands.buttonHome + "=true";
                        }
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         request.setAttribute(Commands.attrError, "File upload succsess!");
        forward(url,request,response);
    }
    
    private void forward(String addr, HttpServletRequest request, HttpServletResponse response)
    {
        RequestDispatcher rd = getServletContext().getRequestDispatcher(addr);
        try {
            rd.forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
