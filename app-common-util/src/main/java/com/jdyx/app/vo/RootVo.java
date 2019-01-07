package com.jdyx.app.vo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class RootVo implements Serializable {
    private String action;

    private String application;

    private String path;

    private String uri;

    private List<EntitiesVo> entities;

    private int timestamp;

    private int duration;

    private String organization;

    private String applicationName;
}
