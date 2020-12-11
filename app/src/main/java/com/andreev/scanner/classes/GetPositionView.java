package com.andreev.scanner.classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPositionView implements Serializable {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("createdFrom")
    @Expose
    private Integer createdFrom;
    @SerializedName("mark")
    @Expose
    private String mark;
    @SerializedName("diameter")
    @Expose
    private String diameter;
    @SerializedName("packing")
    @Expose
    private String packing;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("part")
    @Expose
    private String part;
    @SerializedName("plav")
    @Expose
    private String plav;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("mass")
    @Expose
    private Double mass;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("all")
    @Expose
    private List<String> all = null;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetPositionView() {
    }

    /**
     *
     * @param date
     * @param all
     * @param plav
     * @param part
     * @param mass
     * @param packing
     * @param manufacturer
     * @param createdFrom
     * @param diameter
     * @param comment
     * @param id
     * @param mark
     * @param status
     * @param type
     */
    public GetPositionView(Long id, Integer createdFrom, String mark, String diameter, String packing, String date, String comment, String part, String plav, String manufacturer, Double mass, String status, List<String> all, String type) {
        super();
        this.id = id;
        this.createdFrom = createdFrom;
        this.mark = mark;
        this.diameter = diameter;
        this.packing = packing;
        this.date = date;
        this.comment = comment;
        this.part = part;
        this.plav = plav;
        this.manufacturer = manufacturer;
        this.mass = mass;
        this.status = status;
        this.all = all;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(Integer createdFrom) {
        this.createdFrom = createdFrom;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getPlav() {
        return plav;
    }

    public void setPlav(String melting) {
        this.plav = melting;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getAll() {
        return all;
    }

    public void setAll(List<String> all) {
        this.all = all;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "GetPositionView{" +
                "id=" + id +
                ", createdFrom=" + createdFrom +
                ", mark='" + mark + '\'' +
                ", diameter='" + diameter + '\'' +
                ", packing='" + packing + '\'' +
                ", date='" + date + '\'' +
                ", comment='" + comment + '\'' +
                ", part='" + part + '\'' +
                ", plav='" + plav + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", mass=" + mass +
                ", status='" + status + '\'' +
                ", all=" + all +
                ", type='" + type + '\'' +
                '}';
    }
}
