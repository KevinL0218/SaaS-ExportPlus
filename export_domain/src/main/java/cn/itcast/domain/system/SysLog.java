package cn.itcast.domain.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysLog {

    private String id;
    private String userName;
    private String ip;
    private Date time;
    private String method;
    private String action;
    private String companyId;
    private String companyName;
}