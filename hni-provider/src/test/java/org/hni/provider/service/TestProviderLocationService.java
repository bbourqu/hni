package org.hni.provider.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
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
public class TestProviderLocationService {

	@Inject private ProviderService providerService;
	@Inject private ProviderLocationService providerLocationService;
	
	@Test
	public void testGetProviderLocation() {
		ProviderLocation providerLocation = providerLocationService.get(1L);
		assertNotNull(providerLocation);
	}
	
	@Test
	public void testAddAddress() {
		ProviderLocation providerLocation = providerLocationService.get(1L);
		assertNotNull(providerLocation);
		assertEquals(0, providerLocation.getAddresses().size());
		
		providerLocation.getAddresses().add( new Address("address1", "address2", "city", "AR", "zip") );
		providerLocationService.save(providerLocation);
		
		ProviderLocation plCheck = providerLocationService.get(1L);
		assertNotNull(plCheck);
		assertEquals(1, plCheck.getAddresses().size());
		
	}
	
	@Test
	public void testAddLocationToProvider() {
		Provider provider = providerService.get(1L);
		assertNotNull(provider);
		
		ProviderLocation pl = new ProviderLocation();
		pl.setCreated(new Date());
		pl.setCreatedById(1L);
		pl.setName("test new location");
		pl.setProvider(provider);
		
		pl = providerLocationService.save(pl);
		
		Collection<ProviderLocation> list = providerLocationService.with(provider);
		assertNotNull(list);
		assertEquals(2, list.size());

	}
}
