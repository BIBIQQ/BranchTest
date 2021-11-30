package com.ff.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.yaml.snakeyaml.events.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author FF
 * @date 2021/11/20
 * @TIME:14:17
 */
@Data
@TableName("method_log_info")
public class MethodLogInfo {
    //主键id
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
    //用户id
    private long userId;
    //访问方法
    private String method;
    //方法所需要的时间
    private long  methodTime;
    //开始时间
    private LocalDateTime methodStart;
    //结束时间
    private  LocalDateTime methodEnd;
    // 访问用户ip地址
    private String ip;

}
