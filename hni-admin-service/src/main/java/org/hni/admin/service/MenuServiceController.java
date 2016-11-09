package org.hni.admin.service;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hni.provider.om.Menu;
import org.hni.provider.om.MenuItem;
import org.hni.provider.om.Provider;
import org.hni.provider.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/menus", description = "Operations on Menus and MenuItems")
@Component
@Path("/menus")
public class MenuServiceController {
	private static final Logger logger = LoggerFactory.getLogger(MenuServiceController.class);
	
	@Inject private MenuService menuService;
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Returns the menu with the given id"
		, notes = ""
		, response = Menu.class
		, responseContainer = "")
	public Menu getMenu(@PathParam("id") Long id) {
		return menuService.get(id);
	}

	@POST
	@Path("/providers/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Creates a new Menu or saves the Menu with the given id associated with the given Provider"
		, notes = "An Menu without an ID field will be created"
		, response = Menu.class
		, responseContainer = "")
	public Menu saveOrder(@PathParam("id") Long id, Menu menu) {
		menu.setProvider(new Provider(id));
		return menuService.save(menu);
	}

	@DELETE
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Deletes the Menu with the given id"
		, notes = ""
		, response = Menu.class
		, responseContainer = "")
	public Menu getDelete(@PathParam("id") Long id) {
		return menuService.delete(new Menu(id));
	}

	@GET
	@Path("/providers/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Returns a collection of Menu for the given organization"
	, notes = ""
	, response = Menu.class
	, responseContainer = "")
	public Collection<Menu> getMenus(@PathParam("id") Long id) {		
		return menuService.with(new Provider(id));
	}

	@POST
	@Path("/{id}/menuitems")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Creates a new MenuItem or saves the MenuItem"
		, notes = "An MenuItem without an ID field will be created"
		, response = Menu.class
		, responseContainer = "")
	public Menu saveMenuItem(@PathParam("id") Long id, MenuItem menuItem) {
		Menu menu = menuService.get(id);
		if ( null != menu ) {
			menu.getMenuItems().add(menuItem);
		}
		return menuService.save(menu);
	}

	@DELETE
	@Path("/{id}/menuitems/{miid}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Creates a new MenuItem or saves the MenuItem"
		, notes = "An MenuItem without an ID field will be created"
		, response = Menu.class
		, responseContainer = "")
	public Menu deleteMenuItem(@PathParam("id") Long id, @PathParam("miid") Long miid) {
		Menu menu = menuService.get(id);
		if ( null != menu ) {
			MenuItem menuItem = new MenuItem(miid);
			menu.getMenuItems().remove(menuItem);
		}
		return menuService.save(menu);
	}
}
