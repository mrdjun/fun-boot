package com.fun.framework.web.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Ztree 树结构实体类
 *
 * @author DJun
 */
@Getter
@Setter
@ToString
public class Ztree implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点父ID */
    private Long pId;

    /** 解决 pId 被序列化成 pid 的问题*/
    @JsonProperty("pId")
    public Long getPId(){
        return pId;
    }

    /** 节点名称 */
    private String name;

    /** 节点标题 */
    private String title;

    /** 是否勾选 */
    private boolean checked = false;

    /** 是否展开 */
    private boolean open = false;

    /** 是否能勾选 */
    private boolean nocheck = false;
}
