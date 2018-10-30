package org.javaee.series.rest;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.javaee.series.model.Book;
import org.javaee.series.repository.BookRepository;

@Path("/books")
public class BookEndpoint {
	@Inject
	private BookRepository bookRepository;
	@POST
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response createBook(Book book,@Context UriInfo uriInfo) {
		Book book2= bookRepository.create(book);
		URI uri=uriInfo.getBaseUriBuilder().path(book2.getId().toString()).build("{/id}");
		return Response.created(uri).build();
	}
}
