package com.jdyx.app.vo;
import lombok.Data;

import java.io.Serializable;

@Data
public class HunXinEntities implements Serializable {
        private String uuid;

        private String type;

        private int created;

        private int modified;

        private String username;

        private boolean activated;

        private String nickname;

    }
