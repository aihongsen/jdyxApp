package com.jdyx.app.appvideo.service.impl;

import com.jdyx.app.appvideo.mapper.MessageMapper;
import com.jdyx.app.bean.Message;
import com.jdyx.app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;


}
