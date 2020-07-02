
package com.sh.courier_mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class Currency implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code_type_id")
    @Expose
    private String codeTypeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sequence")
    @Expose
    private String sequence;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
