package test.core.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2022/9/21
 */
public class MenuBuild {

    public static List<Menu> createTestData0() {
        List<Menu> list = new ArrayList<>();
        list.add(new Menu("01", "系统管理", ""));
        list.add(new Menu("0101", "角色管理", "01"));
        list.add(new Menu("010101", "新增角色", "0101"));
        list.add(new Menu("010102", "修改角色", "0101"));
        list.add(new Menu("010103", "删除角色", "0101"));
        list.add(new Menu("0102", "用户管理", "01"));
        list.add(new Menu("010201", "新增用户", "0102"));
        list.add(new Menu("010202", "修改用户", "0102"));
        list.add(new Menu("0103", "运行监控", "01"));
        list.add(new Menu("0104", "操作日志", "01"));
        list.add(new Menu("0105", "基础参数管理", "01"));
        list.add(new Menu("010501", "基础参数修改", "0105"));
        list.add(new Menu("02", "库存管理", ""));
        list.add(new Menu("0201", "基本入库", "02"));
        return list;
    }

}
