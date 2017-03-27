package lucasbaiao.com.br.moviessearch.exception;

public class NoNetworkConnectionException extends RuntimeException {

    public NoNetworkConnectionException() {
        super("Você não está conectado à internet");
    }
}
