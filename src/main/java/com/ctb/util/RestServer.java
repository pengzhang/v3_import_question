package com.ctb.util;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * Rest请求处理类
 * 针对Jersey框架提供的RestAPI接口调用的封装
 * @author zp
 * @version 0.0.3 
 * 1.添加处理返回状态不是200的错误
 * 2.完善异常的处理
 *
 */
public class RestServer {
	
	public static Logger log = LoggerFactory.getLogger(RestServer.class);


	/**
	 * 使用本类需要了解的概念
	 *  1. 基础地址变量：
	 *  |http://127.0.0.1/system_context | /getinfo  		| /name/a.json  	|
	 *  |系统部署地址                      | 类Path    		| 方法Path       	|
	 *  |Rest服务器地址					 | RestAPI接口		| RestAPI接口地址	    |
	 *  |server	                         | restURI		    | path 		        |
	 */
	public static Client  c = null;
	public static Client getClient(){
		if(c == null){
			ClientConfig config = new DefaultClientConfig();
			config.getClasses().add(JacksonJsonProvider.class);
			config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
			c = Client.create(config);
		}
		return c;
	}

	/**
	 * 创建JerseyClient实例
	 * @param server Rest服务器地址
	 * @param restURI  RestAPI接口
	 * @return  webResource
	 */
	public static WebResource getWebResource(String restURL){
		WebResource webResource = getClient().resource(restURL);
		return webResource;
	}


	/**
	 * Post请求的处理
	 * @param server	 Rest服务器地址
	 * @param restURI	 RestAPI接口
	 * @param path		 RestAPI接口地址
	 * @param object	  需要post提交的对象（传入实体本类型）
	 * @return 服务器返回内容
	 * @throws MyException 截获此方法的所有异常，并统一throw PrivilegeException
	 *
	 */
	public static String postRest(String restURL, Object object) {


			String str="";
			ClientResponse response = null;
			try {
				log.info("[request url:]" + restURL);
				response = getWebResource(restURL)
						.header("Content-Type", MediaType.APPLICATION_JSON)
						.type(MediaType.APPLICATION_JSON)
						.post(ClientResponse.class, object);
				str = response.getEntity(String.class);
			} catch (Throwable t){
				log.info("[post error:]",t);
				t.printStackTrace();
				return "POSTRequestError";
			}
			
			if(response.getStatus()!=200){
				log.info("[post getEntity Value:]" + str);
				return "POSTRequestStatus";				
			}

			log.info("[post getEntity Value:]" + str);
			return str;

	}

	/**
	 * Put请求的处理
	 * @param server	 Rest服务器地址
	 * @param restURI	 RestAPI接口
	 * @param path		 RestAPI接口地址
	 * @param object	  需要put提交的对象（传入实体本类型）
	 * @return 服务器返回内容
	 * @throws MyException 截获此方法的所有异常，并统一throw PrivilegeException
	 *
	 */
	public static String putRest(String restURL, Object object)  {

		String str = "";
		ClientResponse response = null;
		try {
			log.info("[request url:]" + restURL);
			response = getWebResource(restURL)
					.header("Content-Type", MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class, object);
			str = response.getEntity(String.class);
			log.info("[put getEntity Value:]" + str);

		}  catch (Throwable t){
			log.info("[put error:]",t);
			return "PUTRequestError";
		}
		
		if(response.getStatus()!=200){
			log.info("[put getEntity Value:]" + str);
			return "PUTRequestStatus";					
		}

		return str;
	}

	/**
	 * DELETE请求的处理
	 * @param server	 Rest服务器地址
	 * @param restURI	 RestAPI接口
	 * @param path		 RestAPI接口地址
	 * @return 服务器返回内容
	 * @throws MyException 截获此方法的所有异常，并统一throw PrivilegeException
	 */
	public static String deleteRest(String restURL){

		String str = "";
		ClientResponse response = null;
		try {
			log.info("[request url:]" + restURL);
			response = getWebResource(restURL)
					.header("Content-Type", MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.delete(ClientResponse.class);
			str = response.getEntity(String.class);
			log.info("[delete getEntity Value:]" + str);

		} catch (Throwable t){
			log.info("[delete error:]",t);
			return "DELETERequestError";
		}
		
		if(response.getStatus()!=200){
			log.info("[put getEntity Value:]" + str);
			return "DELETERequestStatus";					
		}

		return str;
	}
	/**
	 * GET请求的处理
	 * @param server	 Rest服务器地址
	 * @param restURI	 RestAPI接口
	 * @param path		 RestAPI接口地址
	 * @return 服务器返回内容
	 * @throws MyException 截获此方法的所有异常，并统一throw PrivilegeException
	 */
	public static String getRest(String restURL){

		String str = null;
		ClientResponse response = null;
		try {
			log.info("[request url:]" + restURL);
			response = getWebResource(restURL)
					.header("Content-Type", MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);
			str = response.getEntity(String.class);
			log.info("[get getEntity Value:]" + str);
			System.out.println("[get getEntity Value:]" + str);

		} catch (Throwable t){
			log.info("[get error:]",t);
			return "GETRequestError";
		}
		
		if(response.getStatus()!=200){
			log.info("[get getEntity Value:]" + str);
			return "GETRequestError";				
		}
		return str;

	}

}