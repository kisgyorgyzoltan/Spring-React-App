package edu.bbte.idde.kzim2149;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.bbte.idde.kzim2149.dao.AbstractDaoFactory;
import edu.bbte.idde.kzim2149.dao.PCPartsDaoI;
import edu.bbte.idde.kzim2149.dao.RepoException;
import edu.bbte.idde.kzim2149.model.PCPart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@WebServlet("/pcparts")
public class PCPartsServlet extends HttpServlet {
    private final PCPartsDaoI pcPartsDao = AbstractDaoFactory.getInstance().getPCPartsDao();
    private ObjectMapper objectMapper;

    private void useCors(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers,"
            + " Authorization, X-Requested-With");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("{} {} {}", req.getMethod(), req.getRequestURI(), req.getProtocol());
        ObjectNode responseBody = objectMapper.createObjectNode();
        String idStr = req.getParameter("id");

        useCors(resp);

        if (idStr != null) {
            long id;
            try {
                id = Long.parseLong(idStr);
            } catch (NumberFormatException e) {
                log.error("Invalid id: " + idStr);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseBody.put("msg", "Bad request");
                objectMapper.writeValue(resp.getOutputStream(), responseBody);
                return;
            }

            PCPart pcPart;
            try {
                pcPart = pcPartsDao.findById(id);
            } catch (RepoException e) {
                log.error(e.getMessage());
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                responseBody.put("msg", "Internal server error");
                objectMapper.writeValue(resp.getOutputStream(), responseBody);
                return;
            }

            if (pcPart == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                responseBody.put("msg", "Not found");
                objectMapper.writeValue(resp.getOutputStream(), responseBody);
                return;
            }
            objectMapper.writeValue(resp.getOutputStream(), pcPart);
            return;
        }
        log.info("Returning all pcparts");
        Collection<PCPart> pcParts;
        try {
            pcParts = pcPartsDao.findAll();
        } catch (RepoException e) {
            log.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseBody.put("msg", "Internal server error");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }
        objectMapper.writeValue(resp.getOutputStream(), pcParts);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("{} {} {}", req.getMethod(), req.getRequestURI(), req.getProtocol());
        ObjectNode responseBody = objectMapper.createObjectNode();
        String requestBody = req.getReader().lines().collect(Collectors.joining());

        useCors(resp);

        resp.setHeader("Content-Type", "application/json");
        if (requestBody.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBody.put("msg", "Bad request");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        PCPart pcPart;
        try {
            pcPart = objectMapper.readValue(requestBody, PCPart.class);
        } catch (JsonProcessingException e) {
            log.error("Error while parsing JSON", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBody.put("msg", "Bad request");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }
        try {
            pcPartsDao.insert(pcPart);
        } catch (RepoException e) {
            log.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseBody.put("msg", "Internal server error");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_CREATED);
        responseBody.put("msg", "Created");
        objectMapper.writeValue(resp.getOutputStream(), responseBody);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("{} {} {}", req.getMethod(), req.getRequestURI(), req.getProtocol());
        ObjectNode responseBody = objectMapper.createObjectNode();
        String idStr = req.getParameter("id");

        useCors(resp);

        resp.setHeader("Content-Type", "application/json");
        if (idStr == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBody.put("msg", "Bad request");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        long longId;
        try {
            longId = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            log.error("Invalid id: " + idStr);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBody.put("msg", "Bad request");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        PCPart pcPart;
        try {
            pcPart = pcPartsDao.findById(longId);
        } catch (RepoException e) {
            log.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseBody.put("msg", "Internal server error");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        if (pcPart == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseBody.put("msg", "Not found");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        try {
            pcPartsDao.delete(longId);
        } catch (RepoException e) {
            log.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseBody.put("msg", "Internal server error");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        responseBody.put("msg", "Deleted");
        objectMapper.writeValue(resp.getOutputStream(), responseBody);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("{} {} {}", req.getMethod(), req.getRequestURI(), req.getProtocol());
        ObjectNode responseBody = objectMapper.createObjectNode();
        String idStr = req.getParameter("id");

        useCors(resp);

        resp.setHeader("Content-Type", "application/json");
        if (idStr == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBody.put("msg", "Bad request");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        long longId;
        try {
            longId = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            log.error("Invalid id: " + idStr);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBody.put("msg", "Bad request");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        String requestBody = req.getReader().lines().collect(Collectors.joining());
        if (requestBody.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBody.put("msg", "Bad request");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        PCPart pcPart;
        try {
            pcPart = objectMapper.readValue(requestBody, PCPart.class);
        } catch (JsonProcessingException e) {
            log.error("Error while parsing JSON", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBody.put("msg", "Bad request");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        PCPart pcPartid;
        try {
            pcPartid = pcPartsDao.findById(longId);
        } catch (RepoException e) {
            log.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseBody.put("msg", "Internal server error");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        if (pcPartid == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseBody.put("msg", "Not found");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        try {
            pcPartsDao.update(longId, pcPart);
        } catch (RepoException e) {
            log.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseBody.put("msg", "Internal server error");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        responseBody.put("msg", "Updated");
        objectMapper.writeValue(resp.getOutputStream(), responseBody);
    }
}
