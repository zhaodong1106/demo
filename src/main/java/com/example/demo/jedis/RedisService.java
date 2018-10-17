package com.example.demo.jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.demo.utils.ScriptUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import javax.annotation.PostConstruct;

@Service
public class RedisService {
	private  String luaScripts;
	@Autowired
	JedisPool jedisPool;
	@Autowired
	private ObjectMapper objectMapper;


	public RedisService(){
		System.out.println("进入默认构造方法");
		StringBuilder sb = new StringBuilder();
		try(InputStream stream = getClass().getClassLoader().getResourceAsStream("luaScripts/xianliu.lua");
			BufferedReader br = new BufferedReader(new InputStreamReader(stream))
		) {

			String str = "";
			while ((str = br.readLine()) != null) {
				sb.append(str).append(System.lineSeparator());
			}
			luaScripts=sb.toString();
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
//	/**
//	 * 获取当个对象
//	 * */
//	@PostConstruct
//	public void init(){
//		System.out.println("进入PostConstruct");
//		StringBuilder sb = new StringBuilder();
//		try(InputStream stream = getClass().getClassLoader().getResourceAsStream("luaScripts/xianliu.lua");
//			BufferedReader br = new BufferedReader(new InputStreamReader(stream))
//		) {
//
//			String str = "";
//			while ((str = br.readLine()) != null) {
//				sb.append(str).append(System.lineSeparator());
//			}
//			luaScripts=sb.toString();
//		} catch (IOException e) {
//			System.err.println(e.getStackTrace());
//		}
//
//	}
		public Long evalId(String userId,String limit){
			Jedis jedis=null;
			try{
				jedis =  jedisPool.getResource();
				System.out.println(luaScripts);
				Long eval = (Long)jedis.eval(luaScripts,Arrays.asList(userId),Arrays.asList(limit));
				return eval;

			}finally {
				returnToPool(jedis);
			}
		}
	public <T> T get(KeyPrefix prefix, String key,  Class<T> clazz) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			 //生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			 String  str = jedis.get(realKey);
			 T t =  objectMapper.readValue(str,clazz);
			 return t;
		 } catch (JsonParseException e) {
			 e.printStackTrace();
		 } catch (JsonMappingException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 } finally {
			  returnToPool(jedis);
		 }
		return null;
	}
	/**
	 * 设置对象
	 * */
	public <T> boolean set(KeyPrefix prefix, String key,  T value) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			 String str = objectMapper.writeValueAsString(value);
			 if(str == null || str.length() <= 0) {
				 return false;
			 }
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			 int seconds =  prefix.expireSeconds();
			 if(seconds <= 0) {
				 jedis.set(realKey, str);
			 }else {
				 jedis.setex(realKey, seconds, str);
			 }
			 return true;
		 } catch (JsonProcessingException e) {
			 e.printStackTrace();
		 } finally {
			  returnToPool(jedis);
		 }
		return false;
	}
	
	public <T> boolean setNXEX(final KeyPrefix prefix, final String key, final T req) {
		if(req == null){
			return false;
		}
		int expireSeconds = prefix.expireSeconds();
		if(expireSeconds <= 0) {
			throw new RuntimeException("[SET EX NX]必须设置超时时间");
		}
		String realKey = prefix.getPrefix() + key;
		Jedis jc = null;
		try {
			String value = objectMapper.writeValueAsString(req);
			jc = jedisPool.getResource();
			String ret =  jc.set(realKey, value, "nx", "ex", expireSeconds);
			return "OK".equals(ret);
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			returnToPool(jc);
		}
	}
	
	/**
	 * 判断key是否存在
	 * */
	public <T> boolean exists(KeyPrefix prefix, String key) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			return  jedis.exists(realKey);
		 }finally {
			  returnToPool(jedis);
		 }
	}
	
	/**
	 * 删除
	 * */
	public boolean delete(KeyPrefix prefix, String key) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			//生成真正的key
			String realKey  = prefix.getPrefix() + key;
			long ret =  jedis.del(realKey);
			return ret > 0;
		 }finally {
			  returnToPool(jedis);
		 }
	}
	
	/**
	 * 增加值
	 * */
	public <T> Long incr(KeyPrefix prefix, String key) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			return  jedis.incr(realKey);
		 }finally {
			  returnToPool(jedis);
		 }
	}
	
	/**
	 * 减少值
	 * */
	public <T> Long decr(KeyPrefix prefix, String key) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			return  jedis.decr(realKey);
		 }finally {
			  returnToPool(jedis);
		 }
	}
	
	public boolean delete(KeyPrefix prefix) {
		if(prefix == null) {
			return false;
		}
		List<String> keys = scanKeys(prefix.getPrefix());
		if(keys==null || keys.size() <= 0) {
			return true;
		}
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(keys.toArray(new String[0]));
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	public List<String> scanKeys(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			List<String> keys = new ArrayList<String>();
			String cursor = "0";
			ScanParams sp = new ScanParams();
			sp.match("*"+key+"*");
			sp.count(100);
			do{
				ScanResult<String> ret = jedis.scan(cursor, sp);
				List<String> result = ret.getResult();
				if(result!=null && result.size() > 0){
					keys.addAll(result);
				}
				//再处理cursor
				cursor = ret.getStringCursor();
			}while(!cursor.equals("0"));
			return keys;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
//	public static <T> String beanToString(T value) {
//		if(value == null) {
//			return null;
//		}
//		Class<?> clazz = value.getClass();
//		if(clazz == int.class || clazz == Integer.class) {
//			 return ""+value;
//		}else if(clazz == String.class) {
//			 return (String)value;
//		}else if(clazz == long.class || clazz == Long.class) {
//			return ""+value;
//		}else {
//			return JSON.toJSONString(value);
//		}
//	}

	@SuppressWarnings("unchecked")
//	public static <T> T stringToBean(String str, Class<T> clazz) {
//		if(str == null || str.length() <= 0 || clazz == null) {
//			 return null;
//		}
//		if(clazz == int.class || clazz == Integer.class) {
//			 return (T)Integer.valueOf(str);
//		}else if(clazz == String.class) {
//			 return (T)str;
//		}else if(clazz == long.class || clazz == Long.class) {
//			return  (T)Long.valueOf(str);
//		}else {
//			return JSON.toJavaObject(JSON.parseObject(str), clazz);
//		}
//	}

	private void returnToPool(Jedis jedis) {
		 if(jedis != null) {
			 jedis.close();
		 }
	}

}
