package edu.bbte.idde.kzim2149;

import edu.bbte.idde.kzim2149.dao.AbstractDaoFactory;
import edu.bbte.idde.kzim2149.dao.PCPartsDaoI;
import edu.bbte.idde.kzim2149.model.PCPart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;

@Slf4j
@WebServlet("/index")
public class PageServlet extends HttpServlet {
    private final PCPartsDaoI pcPartsDao = AbstractDaoFactory.getInstance().getPCPartsDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("{} {} {}", req.getMethod(), req.getRequestURI(), req.getProtocol());

        Collection<PCPart> pcParts = pcPartsDao.findAll();
        resp.setHeader("Content-Type", "application/json");
        log.info(pcParts.toString());
        req.setAttribute("pcparts", pcParts);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
