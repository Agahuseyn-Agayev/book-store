package com.example.ingressbookstore.service

import com.example.ingressbookstore.entity.AuthorEntity
import com.example.ingressbookstore.entity.BookEntity
import com.example.ingressbookstore.mapper.BookMapper
import com.example.ingressbookstore.model.request.SaveBookRequest
import com.example.ingressbookstore.model.request.UpdateBookRequest
import com.example.ingressbookstore.repository.AuthorRepository
import com.example.ingressbookstore.repository.BookRepository
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import static com.example.ingressbookstore.model.enums.Status.DELETED

class TestBookService extends Specification {
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    private BookRepository repository
    @Autowired
    private AuthorRepository authorRepository
    private BookService service
    private MailSenderService mailSenderService

    def setup() {
        repository = Mock()
        authorRepository = Mock()
        service = new BookService(repository, authorRepository, mailSenderService)
    }

    def "TestGetBookById success"() {
        given:
        def id = random.nextObject(Long)
        def entity = random.nextObject(BookEntity)

        when:
        def variable = service.getBookById(id)

        then:
        1 * repository.findById(id) >> Optional.of(entity)
        variable.name == entity.name
        variable.description == entity.description
        variable.authorId == entity.author.id
    }

    def "TestGetBookById error"() {
        given:
        def id = random.nextObject(Long)

        when:
        service.getBookById(id)

        then:
        1 * repository.findById(id) >> Optional.empty()
        RuntimeException exception = thrown()
        exception.message == "BOOK_NOT_FOUND"

    }

    def "TestSaveBookRequest"() {
        given:
        def id = random.nextObject(Long)
        def entity = random.nextObject(BookEntity)
        def request = random.nextObject(SaveBookRequest)
        def authorEntity = random.nextObject(AuthorEntity)
        def mapper = BookMapper.builderBookEntity(request, authorEntity)

        when:
        authorRepository.findById(id) >> Optional.of(authorEntity)
        service.saveBook(request)


        then:
        1 * authorRepository.findById(id) >> Optional.of(authorEntity)
        1 * repository.save(BookMapper.builderBookEntity(request, authorEntity)) >> Optional.of(entity)
    }

    def "TestUpdateBookRequest success"() {
        given:
        def id = random.nextObject(Long)
        def entity = random.nextObject(BookEntity)
        def request = random.nextObject(UpdateBookRequest)

        when:
        service.updateBook(id, request)

        then:
        1 * repository.findByAuthorId(id) >> Optional.of(entity)
        BookMapper.updateBook(entity, request)
    }

    def "TestUpdateBookRequest error"() {
        given:
        def id = random.nextObject(Long)
        def request = random.nextObject(UpdateBookRequest)

        when:
        service.updateBook(id, request)

        then:
        1 * repository.findByAuthorId(id) >> Optional.empty()
        0 * repository.save(id)
        RuntimeException exception = thrown()
        exception.message == "BOOK_NOT_FOUND"
    }

    def "TestDeleteBookRequest success"() {
        given:
        def id = random.nextObject(Long);
        def entity = random.nextObject(BookEntity)

        when:
        service.deleteBook(id)

        then:
        1 * repository.findByAuthorId(id) >> Optional.of(entity)
        1 * repository.save(entity)
        entity.status == DELETED
    }

    def "TestDeleteBookRequest error"() {
        given:
        def id = random.nextObject(Long);

        when:
        service.deleteBook(id)

        then:
        1 * repository.findByAuthorId(id) >> Optional.empty()
        0 * repository.save()
        RuntimeException exception = thrown()
        exception.message == "BOOK_NOT_FOUND"
    }

    def "TestGetAuthors"() {
        given:
        def entity = random.nextObject(BookEntity)
        def bookList = [entity]
        repository.findAll() >> bookList

        when:
        service.getBooks()

        then:
        1 * repository.findAll() >> bookList
    }


}