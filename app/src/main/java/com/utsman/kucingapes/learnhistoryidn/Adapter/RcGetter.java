package com.utsman.kucingapes.learnhistoryidn.Adapter;

public class RcGetter {
    private String title;
    private String desc;
    private String img;
    private String url;
    private String cat;
    private String nameCat;
    private String idCard;
    private String titleCat;
    private String pin;
    private String imgCat;
    private int prog;
    private int id;

    public RcGetter(String title, String desc, String img, String url, String cat, String nameCat, String idCard, String titleCat, String pin, String imgCat, int prog, int id) {
        this.title = title;
        this.desc = desc;
        this.img = img;
        this.url = url;
        this.cat = cat;
        this.nameCat = nameCat;
        this.idCard = idCard;
        this.titleCat = titleCat;
        this.pin = pin;
        this.imgCat = imgCat;
        this.prog = prog;
        this.id = id;
    }

    public RcGetter() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getProg() {
        return prog;
    }

    public void setProg(int prog) {
        this.prog = prog;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getNameCat() {
        return nameCat;
    }

    public void setNameCat(String nameCat) {
        this.nameCat = nameCat;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTitleCat() {
        return titleCat;
    }

    public void setTitleCat(String titleCat) {
        this.titleCat = titleCat;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getImgCat() {
        return imgCat;
    }

    public void setImgCat(String imgCat) {
        this.imgCat = imgCat;
    }
}
