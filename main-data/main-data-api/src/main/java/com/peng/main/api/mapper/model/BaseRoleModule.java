package com.peng.main.api.mapper.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "base_role_module")
public class BaseRoleModule implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ROLE_ID")
    private String roleId;

    @Column(name = "MODULE_ID")
    private String moduleId;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return ROLE_ID
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * @return MODULE_ID
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId
     */
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
}