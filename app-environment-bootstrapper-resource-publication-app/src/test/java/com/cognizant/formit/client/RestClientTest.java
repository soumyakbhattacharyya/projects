package com.cognizant.formit.client;

import com.cognizant.formit.model.Project;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-beans.xml" })
public class RestClientTest {

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;
	
//	@Test
//	public void restJsonClientTest() {
//		
//		Books booksAux = restTemplate.getForObject("http://localhost:8080/spring-rest-example/rest/book/names.json", Books.class);
//		List<Book> books = booksAux.getBooks();
//		
//		Assert.assertNotNull(books);
//		Assert.assertTrue(books.size() > 0);
//	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void restXmlClientTest() {
		
		//Object project =restTemplate.getForObject("http://localhost:8080/AppEnvPubApp/rest/resource/resources.xml", Project.class);
		Project project = (Project) restTemplate.getForObject("http://localhost:8080/AppEnvPubApp/rest/resource/resources.xml", Project.class);
		
		Assert.assertNotNull(project);
		Assert.assertTrue(project.getList().size() > 0);
		System.out.println("project.getList()"+project.getList());
	}
}
