package lucasbaiao.com.br.moviessearch.exception;

public class InvalidParameterException extends Throwable {
    public InvalidParameterException(String paramterName) {
        super(String.format("Parâmetro inválido ou nao encontrado: \"%s\"",  paramterName));
    }
}
