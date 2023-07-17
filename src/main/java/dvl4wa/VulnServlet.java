package dvl4wa;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.io.Writer;
import java.util.Map;
import java.util.Collections;
import java.util.stream.Collectors;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class VulnServlet extends HttpServlet {
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException {
    Logger logger = LogManager.getLogger();
    try {
      // get x-log header
      String xlog = req.getHeader("x-log");
        
      res.setContentType("text/plain; charset=utf-8");
      Writer writer = res.getWriter();

      if (xlog != null) {
        // Sanitize user input to prevent Log4j LDAP JNDI injection
        String sanitizedXlog = xlog.replaceAll("[^a-zA-Z0-9_\\-\\.]", "_");
        logger.info("x-log: " + sanitizedXlog);
        writer.write("Logging to console using vulnerable log4j2!\n");
      }      
      writer.close();
    } catch(Exception e) {
      throw new ServletException(e.getMessage(), e);
    }
  }
}
