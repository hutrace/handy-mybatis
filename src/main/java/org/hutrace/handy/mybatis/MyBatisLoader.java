package org.hutrace.handy.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hutrace.handy.exception.AppLoaderException;
import org.hutrace.handy.exception.ConfigurationReadException;
import org.hutrace.handy.language.LanguageLoader;
import org.hutrace.handy.language.Logger;
import org.hutrace.handy.language.LoggerFactory;
import org.hutrace.handy.language.SystemLanguage;
import org.hutrace.handy.language.SystemProperty;
import org.hutrace.handy.loader.Loader;
import org.hutrace.handy.utils.scan.ScanningSuffix;

/**
 * MyBatis加载器
 * <p>加载MyBatis配置信息以及需要扫描的MyBatis的xml资源文件
 * @author hu trace
 *
 */
public class MyBatisLoader implements Loader {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private List<MyBatisConfig> configs;
	
	private String[] mappers;
	
	private Map<SystemLanguage, String> language;
	
	public MyBatisLoader() {
		String className = MyBatisLoader.class.getName().replace("MyBatisLoader", "").replace(".", "/");
		language = new HashMap<>();
		language.put(SystemLanguage.ZH_CN, className + "language/zh-CN.txt");
		language.put(SystemLanguage.EN_US, className + "language/en-US.txt");
	}
	
	public void setConfigs(List<MyBatisConfig> configs) {
		this.configs = configs;
	}

	public void setMappers(String mappers) throws ConfigurationReadException {
		String[] arr = mappers.split(",");
		this.mappers = new String[arr.length];
		for(int i = 0; i < arr.length; i++) {
			if(arr[i].trim().isEmpty()) {
				throw new ConfigurationReadException(SystemProperty.get("mybatis.config.mapper.error"));
			}
			this.mappers[i] = arr[i].trim();
		}
	}

	@Override
	public void execute() throws AppLoaderException {
		extendSystemLanguage();
		loaderMyBatis();
	}
	
	/**
	 * 扩展系统语言包
	 * @throws AppLoaderException
	 */
	private void extendSystemLanguage() throws AppLoaderException {
		SystemLanguage sl = LanguageLoader.instance.getSystemLanguage();
		LanguageLoader.instance.extendSystem(language.get(sl));
	}
	
	/**
	 * 加载MyBatis
	 * <p>1. 加载配置
	 * <p>2. 扫描mapper
	 * @throws AppLoaderException
	 */
	private void loaderMyBatis() throws AppLoaderException {
		log.debug("mybatis.config.init");
		if(configs == null) {
			throw new AppLoaderException("");
		}
		if(mappers == null) {
			throw new AppLoaderException("");
		}
		for(MyBatisConfig config : configs) {
			config.build();
		}
		ScanningSuffix scanner = new ScanningSuffix();
		scanner.setScanning(new MyBatisResourceScanning(mappers));
		scanner.start();
	}
	
}
