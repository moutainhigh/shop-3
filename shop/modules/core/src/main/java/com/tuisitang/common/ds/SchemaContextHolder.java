package com.tuisitang.common.ds;

/**
 * 
 * @{#} SchemaContextHolder.java   
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *
 */
public class SchemaContextHolder {

    /**
     * 线程安全
     */
    private static final ThreadLocal<String> schemaHolder = new ThreadLocal<String>();
    
    public static final String SCHEMA_TEST = "test";// 测试环境
    public static final String SCHEMA_PRODUCTION = "production";// 生产环境

    public static void setSchema(String schema) {
        schemaHolder.set(schema);
    }

    public static String getSchema() {
        return schemaHolder.get();
    }

    public static void clearSchema() {
        schemaHolder.remove();
    }

}
