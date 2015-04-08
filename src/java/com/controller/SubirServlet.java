/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author luisa.suarezz
 */

@MultipartConfig(maxFileSize=16167215)
public class SubirServlet extends HttpServlet {
    private String dbURL="jdbc:mysql://localhost:3306/Archivo";
    private String dbUser="root";
    private String dbPass="";
    
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        
        String firstName=request.getParameter("firstName");
        String lastName=request.getParameter("lastName");
        InputStream inputStream=null;
        
        
        //Obtener el archivo en partes a traves de una peticion Multipart
        Part filePart=request.getPart("photo");
        if(filePart!=null){
            //Informacion para debug 
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
            
            //Obtener el InputStream de el archivo subido
            inputStream=filePart.getInputStream();
            
        }
        
        Connection conn=null;
        String message=null;
        
        try{
            //conectar a la BD
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn=DriverManager.getConnection(dbURL, dbUser, dbPass);
            
            
            //construir Estamento en SQL
            String sql="INSERT  INTO contacts(first_name, last_name, photo) values (?,?,?)";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
           
            if(inputStream !=null){
            statement.setBlob(3, inputStream);
            
            }
            
            //Enviar el Estamento al servidor de BD
            
            int row =statement.executeUpdate();
            if(row>0){
             message="Archivo actualizado en la BD";   
            }    
            
            //si se va a hacer un select --> Query
            
        }catch(SQLException ex){
        
        message="ERROR: "+ ex.getMessage();
         
        }finally{
            if(conn!=null){
                //cerramos la conexion a la BD
                try{
                conn.close();
                
                }catch(SQLException ex){
                ex.printStackTrace();
                }
                
            }
          
        }
          
            //Stear el mensaje en el ambito del Request 
            request.setAttribute("Message", message);
            
            //Forward a la apgina del mensaje
            getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
            
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SubirServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SubirServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
