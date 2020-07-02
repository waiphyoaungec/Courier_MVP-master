
package com.sh.courier_mvp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Delivery {

    @SerializedName("exist")
    @Expose
    private List<Exist> exist = null;
    @SerializedName("notexist")
    @Expose
    private List<Notexist> notexist = null;

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    @SerializedName("file_path")
    @Expose
    private String file_path = null;

    public List<Exist> getExist() {
        return exist;
    }

    public void setExist(List<Exist> exist) {
        this.exist = exist;
    }

    public List<Notexist> getNotexist() {
        return notexist;
    }

    public void setNotexist(List<Notexist> notexist) {
        this.notexist = notexist;
    }

}
