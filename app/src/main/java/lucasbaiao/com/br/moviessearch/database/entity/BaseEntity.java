package lucasbaiao.com.br.moviessearch.database.entity;

public class BaseEntity {

    private String _uuid;

    public BaseEntity(String id) {
        _uuid = id;
    }

    public String getUUID() {
        return _uuid;
    }
}
