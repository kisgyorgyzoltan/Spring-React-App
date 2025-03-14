package edu.bbte.idde.kzim2149;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.bbte.idde.kzim2149.dao.AbstractDaoFactory;
import edu.bbte.idde.kzim2149.dao.PrebuiltPCDaoI;
import edu.bbte.idde.kzim2149.dao.RepoException;
import edu.bbte.idde.kzim2149.model.PrebuiltPC;
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
@WebServlet("/prebuilt-pcs")
public class PrebuilltPCsServlet extends HttpServlet {
    private final PrebuiltPCDaoI prebuiltPCDao = AbstractDaoFactory.getInstance().getPrebuiltPCDao();
    private ObjectMapper objectMapper;

    private void useCors(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, "
            + "Authorization, X-Requested-With");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("{} {} {}", req.getMethod(), req.getRequestURI(), req.getProtocol());
        ObjectNode responseBody = objectMapper.createObjectNode();
        String idStr = req.getParameter("id");

        useCors(resp);

        resp.setHeader("Content-Type", "application/json");
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

            PrebuiltPC prebuiltPC;
            try {
                prebuiltPC = prebuiltPCDao.findById(id);
            } catch (RepoException e) {
                log.error(e.getMessage());
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                responseBody.put("msg", "Internal server error");
                objectMapper.writeValue(resp.getOutputStream(), responseBody);
                return;
            }

            if (prebuiltPC == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                responseBody.put("msg", "Not found");
                objectMapper.writeValue(resp.getOutputStream(), responseBody);
                return;
            }
            objectMapper.writeValue(resp.getOutputStream(), prebuiltPC);
        }
        log.info("Returning all prebuilt-pcs");

        Collection<PrebuiltPC> prebuiltPCS;
        try {
            prebuiltPCS = prebuiltPCDao.findAll();
        } catch (RepoException e) {
            log.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseBody.put("msg", "Internal server error");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        objectMapper.writeValue(resp.getOutputStream(), prebuiltPCS);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        PrebuiltPC prebuiltPC;
        try {
            prebuiltPC = objectMapper.readValue(requestBody, PrebuiltPC.class);
        } catch (JsonProcessingException e) {
            log.error("Invalid request body: " + requestBody);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBody.put("msg", "Bad request");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }
        try {
            prebuiltPCDao.insert(prebuiltPC);
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        String requestBody = req.getReader().lines().collect(Collectors.joining());
        if (requestBody.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBody.put("msg", "Bad request");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }
        PrebuiltPC prebuiltPC;
        try {
            prebuiltPC = objectMapper.readValue(requestBody, PrebuiltPC.class);
        } catch (JsonProcessingException e) {
            log.error("Invalid request body: " + requestBody);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBody.put("msg", "Bad request");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        PrebuiltPC prebuiltPCid;
        try {
            prebuiltPCid = prebuiltPCDao.findById(id);
        } catch (RepoException e) {
            log.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseBody.put("msg", "Internal server error");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        if (prebuiltPCid == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseBody.put("msg", "Not found");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        try {
            prebuiltPCDao.update(id, prebuiltPC);
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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        PrebuiltPC prebuiltPC;
        try {
            prebuiltPC = prebuiltPCDao.findById(id);
        } catch (RepoException e) {
            log.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseBody.put("msg", "Internal server error");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        if (prebuiltPC == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseBody.put("msg", "Not found");
            objectMapper.writeValue(resp.getOutputStream(), responseBody);
            return;
        }

        try {
            prebuiltPCDao.delete(id);
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
}
