package edu.bbte.idde.kzim2149.dao;

import java.sql.SQLException;

public class RepoException extends RuntimeException {
    public RepoException(SQLException message) {
        super(message);
    }
}
