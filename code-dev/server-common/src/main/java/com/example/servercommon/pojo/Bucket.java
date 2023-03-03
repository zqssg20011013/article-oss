package com.example.servercommon.pojo;

import io.minio.messages.ResponseDate;
import org.simpleframework.xml.Element;

import java.time.ZonedDateTime;

/**
 * @project: blog
 * @ClassName: Bucket
 * @author: smallwei
 * @creat: 2023/1/17 10:05
 * 描述:
 */
public class Bucket {
    @Element(
            name = "Name"
    )
    private String name;
    @Element(
            name = "CreationDate"
    )
    private ResponseDate creationDate;

    public Bucket() {
    }

    public String name() {
        return this.name;
    }

    public ZonedDateTime creationDate() {
        return this.creationDate.zonedDateTime();
    }
}