package com.fun.project.app.business.entity;

import com.fun.framework.web.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author DJun
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Business extends BaseEntity {
    private Long id;
}
