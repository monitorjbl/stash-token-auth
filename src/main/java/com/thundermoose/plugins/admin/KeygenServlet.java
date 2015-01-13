package com.thundermoose.plugins.admin;

import com.thundermoose.plugins.utils.KeyGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class KeygenServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    try {
      response.setStatus(HttpServletResponse.SC_OK);
      response.getWriter().print('"' + new KeyGenerator().generateKey() + '"');
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
