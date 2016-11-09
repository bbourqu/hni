package org.hni.provider.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import javax.inject.Inject;

import org.hni.provider.om.Menu;
import org.hni.provider.om.MenuItem;
import org.hni.provider.om.Provider;
import org.joda.time.DateTime;
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
public class TestMenuService {

	@Inject private ProviderService providerService;
	@Inject private ProviderLocationService providerLocationService;
	@Inject private MenuService menuService;
	
	@Test
	public void testGetMenu() {
		Menu menu = menuService.get(1L);
		assertNotNull(menu);
	}
	
	@Test
	public void testGetMenusForProvider() {
		Provider provider = providerService.get(1L);
		Collection<Menu> menus = menuService.with(provider);
		assertEquals(1, menus.size());
	}
	
	@Test
	public void testAddMenuToProvider() {
		Provider provider = providerService.get(1L);
		assertNotNull(provider);
		
		Menu menu = new Menu();
		menu.setName("test menu 2");
		menu.setProvider(provider);
		
		menuService.save(menu);
		
		Collection<Menu> menus = menuService.with(provider);
		assertEquals(2, menus.size());
	}
	
	@Test
	public void testAddItemToMenu() {
		Provider provider = providerService.get(1L);
		assertNotNull(provider);
		
		Menu menu = new Menu();
		menu.setName("test menu 2");
		menu.setProvider(provider);
		
		for(int i = 1; i < 5; i++) {
			menu.getMenuItems().add(new MenuItem("item "+i, "item "+i+" desc", i+1.23, new DateTime().plusMonths(1).toDate()));
			menu = menuService.save(menu);
		}
		
		Menu check = menuService.get(menu.getId());
		assertNotNull(check);
		assertEquals(4, check.getMenuItems().size());
	}

	@Test
	public void testRemoveItemFromMenu() {
		Menu menu = menuService.get(1L);
		assertNotNull(menu);
		assertEquals(4, menu.getMenuItems().size());
		
		MenuItem mi = new MenuItem(4L);		
		menu.getMenuItems().remove(mi);
		menuService.save(menu);
		
		Menu check = menuService.get(1L);
		assertNotNull(check);
		assertEquals(3, check.getMenuItems().size());
	}
}
