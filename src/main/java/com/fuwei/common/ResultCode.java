package com.fuwei.common;

public enum ResultCode {

    SUCCESS("000","执行成功"),
    LOGIN_LOST("001","登陆失效"),
    SYSTEM_ERROR("002","系统异常,请稍后重试"),
    LOGIN_FAIL("003","用户名或密码错误"),
    USERNAME_LOST("004","登陆账号必填"),
    PASSWORD_LOST("005","密码必填"),
    REALNAME_LOST("006","真实姓名必填"),
    USERNAME_EXIST("007","账号已存在"),
    MENU_NAME_LOST("008","菜单名称必填"),
    MENU_LEVEL_LOST("009","菜单等级必传"),
    NOT_ADMIN("010","非管理员用户不能操作"),
    MENU_REPE("011","同层级下菜单不能重复"),
    MENUID_LOST("012","菜单ID必传"),
    MOULDNUM_LOST("013","模具号必填"),
    ROBOTKIND_LOST("014","机种必填"),
    MACHINENUM_LOST("015","注塑机编号必填"),
    DATE_LOST("016","日期必填"),
    TIME_LOST("017","时间必填"),
    CONDITIONID_LOST("018","conditionID必传"),
    CONDITION_LOST("019","您要操作的成型条件记录已不存在"),
    PERMISSION_LIMIT("020","您不是该成型条件记录的创建人，不能操作记录"),
    PAGENO_LIMIT("021","pageNum必传"),
    PAGESIZE_LIMIT("022","pageSize必传"),
    RECORD_DATA_LIMIT("023","记录的数据必传"),
    USERID_LOST("024","用户ID必传");


    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
