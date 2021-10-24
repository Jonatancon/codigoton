package reto.models;

import java.math.BigDecimal;

public class Client {

    private Integer id;
    private String code;
    private byte male;
    private Integer type;
    private String location;
    private String company;
    private byte encrypt;
    private BigDecimal total;

    public Client(){}

    public Client(Integer id, String code, byte male, Integer type, String location,
                  String company, byte encrypt, BigDecimal total) {
        this.id = id;
        this.code = code;
        this.male = male;
        this.type = type;
        this.location = location;
        this.company = company;
        this.encrypt = encrypt;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte getMale() {
        return male;
    }

    public void setMale(byte male) {
        this.male = male;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public byte getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(byte encrypt) {
        this.encrypt = encrypt;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
