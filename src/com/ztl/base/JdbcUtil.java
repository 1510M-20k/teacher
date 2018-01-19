package com.ztl.base;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtil {
	/**
	 * 获取数据库链接
	 * 
	 * @Title: getConn
	 * @param @return 设定文件
	 * @return Connection 返回类型
	 * @throws
	 */
	public static Connection getConn() {
		Connection conn = null;// 数据库链接
		String drivername = "com.mysql.jdbc.Driver";// 驱动类
		String url = "jdbc:mysql://localhost:3306/ztl";// 数据库路径
		String username = "root";// 用户名
		String password = "root";// 密码
		// 1、加载驱动类
		try {
			Class.forName(drivername);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭数据库流
	 * 
	 * @Title: closeConn
	 * @param @param res
	 * @param @param ps
	 * @param @param conn 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void closeConn(ResultSet res, PreparedStatement ps,
			Connection conn) {
		try {
			if (res != null) {
				res.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * INSERT, UPDATE, DELETE 操作都可以包含在其中
	 * 
	 * @Title: update
	 * @param @param sql
	 * @param @param args 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static int update(String sql, Object... args) {
		Connection conn = getConn();// 获取链接
		PreparedStatement ps = null;
		int m = 0;
		try {
			ps = conn.prepareStatement(sql);// 获取预编译对象
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			m = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(null, ps, conn);
		}
		return m;
	}

	/**
	 * 传入 SQL 语句和 Class 对象, 返回 SQL 语句查询到的记录对应的 Class 类的对象的集合
	 * 
	 * @param clazz
	 *            : 对象的类型
	 * @param sql
	 *            : SQL 语句
	 * @param args
	 *            : 填充 SQL 语句的占位符的可变参数.
	 * @return
	 */
	public static <T> List<T> getList(Class<T> clazz, String sql,
			Object... args) {
		List<T> list = new ArrayList<T>();
		Connection conn = getConn();// 获取链接
		PreparedStatement ps = null;
		ResultSet res = null;
		try {
			ps = conn.prepareStatement(sql);// 获取预编译对象
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			Field[] fields = clazz.getDeclaredFields(); // 反射
			res = ps.executeQuery();
			while (res.next()) {
				T t = clazz.newInstance();
				for (int i = 0; i < fields.length; i++) {
					String propertyName = fields[i].getName();
					try {
						String value = res.getString(propertyName);
						// 构建一个属性描述器 把对应属性 propertyName 的 get 和 set 方法保存到属性描述器中
						PropertyDescriptor pd = new PropertyDescriptor(
								propertyName, clazz);
						pd.getWriteMethod().invoke(t, value); // Write对应set()方法

						// Method getMethod = pd.getReadMethod();//从属性描述器中获取 get
						// 方法
						// Object value = getMethod.invoke(clazz);//调用方法获取方法的返回值
					} catch (Exception e) {
						System.out.println("errorinfo:" + e.getMessage());
					}
				}
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(res, ps, conn);
		}
		return list;
	}

	// 查询一条记录, 返回对应的对象
	public static <T> T getOne(Class<T> clazz, String sql, Object... args) {
		List<T> result = getList(clazz, sql, args);
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	/**
	 * 返回某条记录的某一个字段的值 或 一个统计的值(一共有多少条记录等.)
	 * 
	 * @Title: getForValue
	 * @param @param sql
	 * @param @param args
	 * @param @return 设定文件
	 * @return E 返回类型
	 * @throws
	 */
	public <E> E getValue(String sql, Object... args) {
		Connection conn = getConn();// 获取链接
		PreparedStatement ps = null;
		ResultSet res = null;
		try {
			ps = conn.prepareStatement(sql);// 获取预编译对象
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			res = ps.executeQuery();
			if (res.next()) {
				return (E) res.getObject(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeConn(res, ps, conn);
		}
		return null;
	}
	/**
	 * jdbc查询，返回map类型
	 * @param sql
	 * @param args
	 * @return
	 */
	public static List<Map<String, Object>> getList(String sql, Object... args) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = getConn();// 获取链接
		PreparedStatement ps = null;
		ResultSet res = null;
		try {
			ps = conn.prepareStatement(sql);// 获取预编译对象
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			res = ps.executeQuery();
			ResultSetMetaData rsmd = res.getMetaData();
			int fieldCount = rsmd.getColumnCount();
			while (res.next()) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= fieldCount; i++) {
					String fieldName = rsmd.getColumnName(i);
					map.put(fieldName, res.getObject(fieldName));
					System.out.println(fieldName+"=========="+res.getObject(fieldName).toString());
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(res, ps, conn);
		}
		return list;
	}
	/**
	 * main方法测试
	 * @param args
	 */
	public static void main(String[] args) {
		List<Map<String, Object>> list = getList("select * from ztl_menu");
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			System.out.println(map.toString());
		}
	}
}
