package org.hutrace.handy.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hutrace.handy.exception.ScanningApplicationException;
import org.hutrace.handy.language.Logger;
import org.hutrace.handy.language.LoggerFactory;
import org.hutrace.handy.language.SystemProperty;
import org.hutrace.handy.utils.scan.ScanningSuffixConduct;

public class MyBatisResourceScanning implements ScanningSuffixConduct {
	
	private static Logger log = LoggerFactory.getLogger(MyBatisResourceScanning.class);

	private final String SUFFIX = "xml";
	private final String[] packages;
	
	public MyBatisResourceScanning(String[] packages) {
		this.packages = packages;
	}
	
	@Override
	public String[] getPackages() {
		return packages;
	}

	@Override
	public boolean supports(String fileName) {
		return fileName.endsWith(SUFFIX);
	}

	@Override
	public void add(String resource) throws ScanningApplicationException {
		if(resource.endsWith(SUFFIX)) {
			xml(resource.substring(0, resource.length() - 4).replace(".", "/") + ".xml");
		}
	}
	
	private void xml(String resource) throws ScanningApplicationException {
		log.debug("mybatis.mapper.load", resource);
		ErrorContext.instance().resource(resource);
		Collection<SqlSessionFactory> lists = PondSqlFactory.sqlFactoryMap.values();
		for(SqlSessionFactory sqlSessionFactory : lists) {
			InputStream inputStream;
			try {
				inputStream = Resources.getResourceAsStream(resource);
			}catch (IOException e) {
				throw new ScanningApplicationException(SystemProperty.get("mybatis.mapper.load.error", resource), e);
			}
			XMLMapperBuilder mapperParser = new XMLMapperBuilder(
					inputStream,
					sqlSessionFactory.getConfiguration(),
					resource,
					sqlSessionFactory.getConfiguration().getSqlFragments());
	        mapperParser.parse();
		}
	}
	
}
