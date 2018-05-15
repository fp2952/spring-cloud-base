package com.peng.main.api.pojo.response;


import java.util.List;

/**
 * Created by fp295 on 2018/5/15.
 */
public class ModuleAndSystemResponse {

    private String id;

    private String moduleName;

    private List<ModuleAndSystemResponse> subModules;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<ModuleAndSystemResponse> getSubModules() {
        return subModules;
    }

    public void setSubModules(List<ModuleAndSystemResponse> subModules) {
        this.subModules = subModules;
    }

}
