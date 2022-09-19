package com.example.SSO_APP_VTP.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModuleData implements Serializable {
    protected String code;
    protected String description;
    protected Long id;
    protected List<ModuleData> lsChildren;
    protected List<ModuleData> lsFunction;
    protected String name;
    protected Long parentId;
    protected Long position;
    protected Long status;
    protected Long type;
    protected String url;
    protected String icon;
}
