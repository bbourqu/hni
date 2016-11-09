package org.hni.admin.service;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.hni.provider.service.ProviderLocationService;
import org.hni.provider.service.ProviderService;
import org.hni.user.dao.AddressDAO;
import org.hni.user.om.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/providers", description = "Operations on Providers and ProviderLocations")
@Component
@Path("/providers")
public class ProviderController {
	private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);
	
	@Inject private ProviderService providerService;
	@Inject private ProviderLocationService providerLocationService;
	@Inject private AddressDAO addressDao;
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Returns the Provider with the given id"
		, notes = ""
		, response = Provider.class
		, responseContainer = "")
	public Provider getProvider(@PathParam("id") Long id) {
		return providerService.get(id);
	}

	@POST
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Creates a new Provider or saves the Provider with the given id"
		, notes = "An Provider without an ID field will be created"
		, response = Provider.class
		, responseContainer = "")
	public Provider saveProvider(Provider provider) {
		return providerService.save(provider);
	}

	@DELETE
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Deletes the Provider with the given id"
		, notes = ""
		, response = Provider.class
		, responseContainer = "")
	public Provider getDelete(@PathParam("id") Long id) {
		return providerService.delete(new Provider(id));
	}

	@POST
	@Path("/{id}/addresses")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Adds an address to a Provider"
		, notes = "Use the /addresses API to update addresses"
		, response = Provider.class
		, responseContainer = "")
	public Provider addAddressToProvider(@PathParam("id") Long id, Address address) {
		Provider provider = providerService.get(id);
		if (null != provider) {
			provider.getAddresses().add(address);
			providerService.save(provider);
		}
		return provider;
	}

	@DELETE
	@Path("/{id}/addresses/{addressId}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Removes the address from a provider"
		, notes = ""
		, response = Provider.class
		, responseContainer = "")
	public Provider removeAddressFromProvider(@PathParam("id") Long id, @PathParam("addressId") Long addressId) {
		Provider provider = providerService.get(id);
		if (null != provider) {
			Address address = addressDao.get(addressId);
			if ( null != address ) {
				provider.getAddresses().remove(address); // Hibernate will manage the mapping table for us.
				providerService.save(provider);
			}
		}
		return provider;
	}
	
	/** Provider Locations **/

	@GET
	@Path("/{id}/providerLocations")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Returns a collection of ProviderLocations for the given Provider"
	, notes = ""
	, response = ProviderLocation.class
	, responseContainer = "")
	public Collection<ProviderLocation> getProviderLocations(@PathParam("id") Long id) {
		return providerLocationService.with(new Provider(id));
	}
	
	@POST
	@Path("/{id}/providerLocations")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Adds a ProviderLocation for the given Provider"
	, notes = ""
	, response = ProviderLocation.class
	, responseContainer = "")
	public ProviderLocation addProviderLocation(@PathParam("id") Long id, ProviderLocation providerLocation) {
		providerLocation.setProvider(new Provider(id));
		return providerLocationService.save(providerLocation);
	}

	@DELETE
	@Path("/{id}/providerLocations/{plid}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Deletes given ProviderLocation for the given Provider"
	, notes = ""
	, response = ProviderLocation.class
	, responseContainer = "")
	public ProviderLocation addProviderLocation(@PathParam("id") Long id, @PathParam("plid") Long plid) {
		ProviderLocation providerLocation = providerLocationService.get(plid);
		if (providerLocation.getProvider().getId().equals(id)) {
			providerLocationService.delete(new ProviderLocation(plid));
		}
		//TODO: throw error?
		return null;
	}
	
	@POST
	@Path("/{id}/providerLocations/{plid}/addresses")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Adds an address to a ProviderLocation"
		, notes = "Use the /addresses API to update addresses"
		, response = Provider.class
		, responseContainer = "")
	public ProviderLocation addAddressToProviderLocation(@PathParam("id") Long id, @PathParam("plid") Long plid, Address address) {
		ProviderLocation providerLocation = providerLocationService.get(plid);
		if (providerLocation.getProvider().getId().equals(id)) {
			providerLocation.getAddresses().add(address);
			providerLocationService.save(providerLocation);
		}

		return providerLocation;
	}

	@DELETE
	@Path("/{id}/providerLocations/{plid}/addresses/{addressId}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Removes the address from a providerLocation"
		, notes = ""
		, response = Provider.class
		, responseContainer = "")
	public ProviderLocation removeAddressFromProviderLocation(@PathParam("id") Long id, @PathParam("plid") Long plid, @PathParam("addressId") Long addressId) {
		ProviderLocation providerLocation = providerLocationService.get(plid);
		if (providerLocation.getProvider().getId().equals(id)) {
			Address address = addressDao.get(addressId);
			if ( null != address ) {
				providerLocation.getAddresses().remove(address); // Hibernate will manage the mapping table for us.
				providerLocationService.save(providerLocation);
			}			
		}

		return providerLocation;
	}	
}
