package org.roof.node.test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.roof.node.jobs.datasource.DataSourceRegister;
import org.roof.spring.CurrentSpringContext;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring-all.xml" })
public class JdbcReadJobTest extends AbstractJUnit4SpringContextTests {
	private JobOperator jobOperator; // 注入job操作
	private DataSourceRegister jsonJdbcDataSourceRegister; // 注入JsonJdbc数据源注册C
	private String config = "{'name': 'letv' , 'url' : 'jdbc:mysql://127.0.0.1:3306/node', 'username' : 'root', 'password' : '123456zx'}";// 数据源配置
	private String params = null; // sql执行参数

	@Before
	public void setUp() throws Exception {
		
		CurrentSpringContext.setCurrentContext(this.applicationContext);
		jsonJdbcDataSourceRegister.regist(config, null); // 注册数据源
		params = "dataSourceName:letv,sql:select * from s_dictionary order by id desc,startTime:"
				+ new Date().getTime(); // job启动参数
	}

//	@Test
	public void testJdbcReadJob() throws Exception {
		try {
			jobOperator.start("testJdbcReadJob", params); // 启动一个job
		} catch (NoSuchJobException | JobInstanceAlreadyExistsException | JobParametersInvalidException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testJdbcRead2ChanelJob() throws Exception {
		try {
			jobOperator.start("testJdbcRead2ChannelJob", params);
		} catch (NoSuchJobException | JobInstanceAlreadyExistsException | JobParametersInvalidException e) {
			e.printStackTrace();
		}
	}

	@Autowired
	public void setJobOperator(@Qualifier("jobOperator") JobOperator jobOperator) {
		this.jobOperator = jobOperator;
	}

	@Autowired
	public void setJsonJdbcDataSourceRegister(
			@Qualifier("jsonJdbcDataSourceRegister") DataSourceRegister jsonJdbcDataSourceRegister) {
		this.jsonJdbcDataSourceRegister = jsonJdbcDataSourceRegister;
	}

}
