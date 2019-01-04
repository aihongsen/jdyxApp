package com.jdyx.app.vo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class HunXinRoot implements Serializable {
    private String action;

    private String application;

    private String path;

    private String uri;

    private List<HunXinEntities> entities;

    private int timestamp;

    private int duration;

    private String organization;

    private String applicationName;
}
