package com.steer.data.common.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

//import org.springframework.data.redis.support.collections.RedisProperties;

@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig{


	@Value("${spring.redis.cluster.nodes}")
	private String nodes;
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private String port;
	@Value("${spring.redis.timeout}")
	private String timeoutCon;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.lettuce.pool.max-idle}")
	private Integer maxIdle;
	@Value("${spring.redis.lettuce.pool.min-idle}")
	private Integer minIdle;
	@Value("${spring.redis.lettuce.pool.max-active}")
	private Integer maxTotal;
	@Value("${spring.redis.lettuce.shutdown-timeout}")
	private String shutdownTimeOut;
	@Value("${spring.redis.lettuce.pool.max-wait}")
	private Long maxWaitMillis;

	@Bean
	LettuceConnectionFactory lettuceConnectionFactory() {
		// 连接池配置
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxIdle(maxIdle == null ? 8 : maxIdle);
		poolConfig.setMinIdle(minIdle == null ? 1 : minIdle);
		poolConfig.setMaxTotal(maxTotal == null ? 8 : maxTotal);
		poolConfig.setMaxWaitMillis(maxWaitMillis == null ? 5000L : maxWaitMillis);
		LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
				.poolConfig(poolConfig)
				.commandTimeout(Duration.ofMillis(Long.valueOf(timeoutCon)))
				.shutdownTimeout(Duration.ofMillis(Long.valueOf(shutdownTimeOut)))
				.build();
		// 单机redis
		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
		redisConfig.setHostName(host==null||"".equals(host)?"localhost":host.split(":")[0]);
		redisConfig.setPort(Integer.valueOf(port==null||"".equals(port)?"6379":port));
		if (password != null && !"".equals(password)) {
			//redisConfig.setPassword(password);
			redisConfig.setPassword(RedisPassword.of(password));
		}

		// 哨兵redis
		// RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();

		// 集群redis
//		RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
//		Set<RedisNode> nodeses = new HashSet<>();
//		String[] hostses = nodes.split("-");
//		for (String h : hostses) {
//			h = h.replaceAll("\\s", "").replaceAll("\n", "");
//			if (!"".equals(h)) {
//				String host = h.split(":")[0];
//				int port = Integer.valueOf(h.split(":")[1]);
//				nodeses.add(new RedisNode(host, port));
//			}
//		}
//		redisConfig.setClusterNodes(nodeses);
//		// 跨集群执行命令时要遵循的最大重定向数量
//		redisConfig.setMaxRedirects(3);
//		//redisConfig.setPassword(password);
//		redisConfig.setPassword(RedisPassword.of(password));

		return new LettuceConnectionFactory(redisConfig, lettucePoolingClientConfiguration);
	}



	/**
	 * https://www.jianshu.com/p/c168e2b825cb
	 * https://www.cnblogs.com/zeng1994/p/03303c805731afc9aa9c60dbbd32a323.html
	 * https://www.cnblogs.com/superfj/p/9232482.html
	 */
	public static final Logger log = LoggerFactory.getLogger(RedisConfig.class);

	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
//	public RedisTemplate<Object, Object> redisTemplate(
//			RedisConnectionFactory redisConnectionFactory) {
	public RedisTemplate<Object, Object> redisTemplate(
			LettuceConnectionFactory redisConnectionFactory) {
		//redisConnectionFactory=lettuceConnectionFactory();
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		//使用fastjson序列化
		FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
		// value值的序列化采用fastJsonRedisSerializer
		template.setValueSerializer(fastJsonRedisSerializer);
		template.setHashValueSerializer(fastJsonRedisSerializer);
		// key的序列化采用StringRedisSerializer
		template.setKeySerializer(new StringRedisSerializer());
		// hash的key也采用String的序列化方式
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setConnectionFactory(redisConnectionFactory);
		//template.setConnectionFactory(lettuceConnectionFactory());
		return template;
	}

	@Bean
	@ConditionalOnMissingBean(StringRedisTemplate.class)
//	public StringRedisTemplate stringRedisTemplate(
//			RedisConnectionFactory redisConnectionFactory) {
	public StringRedisTemplate stringRedisTemplate(
				LettuceConnectionFactory redisConnectionFactory) {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}


//	/**
//	 * 管理缓存
//	 */
//	@Bean
//	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//		RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory).build();
//		return redisCacheManager;
//	}
//
//	/**
//	 * redis数据操作异常处理 这里的处理：在日志中打印出错误信息，但是放行
//	 * 保证redis服务器出现连接等问题的时候不影响程序的正常运行，使得能够出问题时不用缓存
//	 *
//	 * @return
//	 */
//	@Override
//	@Bean
//	public CacheErrorHandler errorHandler() {
//		// 异常处理策略，异常处理，当Redis发生异常时，打印日志，但是程序正常走
//		log.info("基于注解缓存使用，Redis缓存故障操作缓存时异常处理策略，初始化 -> [{}]", "Redis CacheErrorHandler");
//		CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
//			@Override
//			public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
//				//log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
//				doHandlerRedisErrorException(e,key);
//			}
//
//			@Override
//			public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
//				//log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
//				doHandlerRedisErrorException(e,key);
//			}
//
//			@Override
//			public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
//				//log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
//				doHandlerRedisErrorException(e,key);
//			}
//
//			@Override
//			public void handleCacheClearError(RuntimeException e, Cache cache) {
//				//log.error("Redis occur handleCacheClearError：", e);
//				doHandlerRedisErrorException(e,null);
//			}
//		};
//		return cacheErrorHandler;
//		//原文链接：https://blog.csdn.net/u014401141/article/details/79024483
//	}
//
//	protected void doHandlerRedisErrorException(Exception e,Object key){
//		log.error(String.format("Redis异常，key=[%s]",key),e);
//	}

}