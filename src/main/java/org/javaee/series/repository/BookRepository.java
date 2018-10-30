package org.javaee.series.repository;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.constraints.NotNull;

import org.hibernate.engine.spi.SessionImplementor;
import org.javaee.series.model.Book;


@Transactional
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BookRepository {
	@PersistenceContext(name = "JavaHelps")
	private EntityManager entityManager;
	
	@PostConstruct
	public void m1() throws SQLException {
		System.out.println("hello");		
		SessionImplementor sessionImp =  (SessionImplementor) entityManager.getDelegate();
			DatabaseMetaData metadata = sessionImp.connection().getMetaData();
			Connection connection= sessionImp.connection().getMetaData().getConnection();
			System.out.println(connection.toString());
			//do whatever you need with the metadata object...
			System.out.println(metadata.getDatabaseProductName()); 
			
		
	}

	@Transactional(value=TxType.REQUIRED)
	public void delete(@NotNull Long id) {
		Book book= entityManager.getReference(Book.class, id);
		entityManager.remove(book);
	}

	public Book find(@NotNull Long id) {
		return entityManager.find(Book.class, id);
	}
	@Transactional(value=TxType.REQUIRED)
	public Book create(Book book) {
		entityManager.persist(book);
		return book;
	}
	
	public List<Book> findAll(){
		TypedQuery<Book> query=entityManager.createQuery("Select b frm Book b order by b.title DESC",Book.class);
		return query.getResultList();
	}
	
	public Integer countALl() {
		TypedQuery<Integer> query=entityManager.createQuery("Select count(b) frm Book b ",Integer.class);
		return query.getSingleResult();
	}
}
