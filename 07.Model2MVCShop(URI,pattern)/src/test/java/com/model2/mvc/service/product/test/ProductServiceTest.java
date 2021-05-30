package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;


/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
										"classpath:config/context-aspect.xml",
										"classpath:config/context-mybatis.xml",
										"classpath:config/context-transaction.xml" })
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
	
		product.setProdName("testAI");
		product.setProdDetail("testRobot");
		product.setManuDate("20210519");
		product.setPrice(18000);
		product.setFileName("robot22.jpg");

		
		productService.addProduct(product);
		
		//product = productService.getProduct(10023);

		//==> console 확인
		System.out.println(product);
		
		//==> API 확인
//		Assert.assertEquals(10023, product.getProdNo());
//		Assert.assertEquals("testAI", product.getProdName());
//		Assert.assertEquals("testRobot", product.getProdDetail());
//		Assert.assertEquals("20210513", product.getManuDate());
//		Assert.assertEquals(10000, product.getPrice());
//		Assert.assertEquals("robot.jpg", product.getFileName());
	}
	
	@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();

//		product.setProdName("testProdName");
//		product.setProdDetail("testProdDetail");
//		product.setManuDate("20210511");
//		product.setPrice(12000);
//		product.setFileName("경기도");
		
		productService.getProduct(10040);

		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
//		Assert.assertEquals(10021, product.getProdNo());
//		Assert.assertEquals("testProdName", product.getProdName());
//		Assert.assertEquals("testProdDetail", product.getProdDetail());
//		Assert.assertEquals("20210511", product.getManuDate());
//		Assert.assertEquals(12000, product.getPrice());
//		Assert.assertEquals("서울", product.getFileName());

	//	Assert.assertNotNull(productService.getProduct("user02"));
	//	Assert.assertNotNull(productService.getProduct("user05"));
	}
	
	//@Test
	 public void testUpdateProduct() throws Exception{
		 
		Product product = productService.getProduct(10021);
		
		Assert.assertNotNull(product);
		
		Assert.assertEquals(10021, product.getProdNo());
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20210511", product.getManuDate());
		Assert.assertEquals(12000, product.getPrice());
		Assert.assertEquals("경기도", product.getFileName());

		product.setFileName("서울");
				
		productService.updateProduct(product);
		
		product = productService.getProduct(10021);
		Assert.assertNotNull(product);
		
		//==> console 확인
		System.out.println(product);
			
		//==> API 확인
		Assert.assertEquals(10021, product.getProdNo());
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20210511", product.getManuDate());
		Assert.assertEquals(12000, product.getPrice());
		Assert.assertEquals("서울", product.getFileName());
	 }
	
	 //==>  주석을 풀고 실행하면....
	// @Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");

	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");

	 }
	 
	// @Test
	 public void testGetProductListByProdNo() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10022");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	//Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");

	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 //	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");

	 }
	 
	 //@Test
	 public void testGetProductListByProdName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 //	search.setSearchCondition("1");
	 //	search.setSearchKeyword("testProdName");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Product> list = (List<Product>)map.get("list");
	 	//Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	//System.out.println(list.size());
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
		System.out.println("totalCount : "+totalCount);
		
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("testProdName");

	 	map = productService.getProductList(search);
	 	
	 	list = (List<Product>)map.get("list");
	 	//Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println("totalCount : "+totalCount);
	
	 }
	 
	//@Test
	 public void testGetProductListByPrice() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("2");
	 	search.setSearchKeyword("%10%");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");

	 	
	 	//search.setSearchCondition("2");
	 	//search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");

	 }
	 
		 
}