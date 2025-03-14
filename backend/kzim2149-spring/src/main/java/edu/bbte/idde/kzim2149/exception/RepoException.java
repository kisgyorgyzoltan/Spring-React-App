package edu.bbte.idde.kzim2149.exception;

import java.sql.SQLException;

public class RepoException extends RuntimeException {
    public RepoException(SQLException message) {
        super(message);
    }

    public RepoException() {
        super();
    }

    public RepoException(String message) {
        super(message);
    }

    public RepoException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepoException(Throwable cause) {
        super(cause);
    }
}
