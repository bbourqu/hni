package org.hni.provider.service;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.hni.provider.om.Provider;
import org.hni.user.om.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * The schema for testing is located in the hni-schema project which gets shared across
 * all test cases.
 * 
 * @author j2parke
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-applicationContext.xml"} )
@Transactional
public class TestProviderService {

	@Inject private ProviderService providerService;
	
	@Test
	public void testGetProvider() {
		Provider provider = providerService.get(1L);
		assertNotNull(provider);
	}
	
	@Test
	public void testAddAddress() {
		Provider provider = providerService.get(1L);
		assertNotNull(provider);
		assertEquals(0, provider.getAddresses().size());
		
		provider.getAddresses().add( new Address("address1", "address2", "city", "AR", "zip") );
		providerService.save(provider);
		
		Provider providerCheck = providerService.get(1L);
		assertNotNull(providerCheck);
		assertEquals(1, provider.getAddresses().size());
		
	}
}
