package com.example.ingressbookstore.service

import com.example.ingressbookstore.entity.AuthorEntity
import com.example.ingressbookstore.mapper.AuthorMapper
import com.example.ingressbookstore.model.request.SaveAuthorRequest
import com.example.ingressbookstore.model.request.UpdateAuthorRequest
import com.example.ingressbookstore.repository.AuthorRepository
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static com.example.ingressbookstore.model.enums.Status.DELETED

class estAuthorService extends Specification {
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    private AuthorRepository authorRepository
    private AuthorService authorService

    def setup() {
        authorRepository = Mock()
        authorService = new AuthorService(authorRepository)
    }

    def "TestAuthorGetById success"() {
        given:
        def id = random.nextObject(Long)
        def entity = random.nextObject(AuthorEntity)

        when:
        def variable = authorService.getAuthorById(id)

        then:
        1 * authorRepository.findById(id) >> Optional.of(entity)
        variable.name == entity.name
        variable.surname == entity.surname
        variable.age == entity.age
    }

    def "TestAuthorGetById error"() {
        given:
        def id = random.nextObject(Long)

        when:
        authorService.getAuthorById(id)

        then:
        1 * authorRepository.findById(id) >> Optional.empty()
        RuntimeException exception = thrown()
        exception.message == "AUTHOR_NOT_FOUND"

    }

    def "TestSaveAuthorRequest"() {
        given:
        def entity = random.nextObject(AuthorEntity)
        def request = random.nextObject(SaveAuthorRequest)
        def mapper = random.nextObject(AuthorMapper)

        when:
        authorService.saveAuthor(request)

        then:
        1 * authorRepository.save(AuthorMapper.builderAuthorEntity(request)) >> Optional.of(entity)
    }

    def "TestUpdateAuthorRequest success"() {
        given:
        def id = random.nextObject(Long)
        def entity = random.nextObject(AuthorEntity)
        def request = random.nextObject(UpdateAuthorRequest)

        when:
        authorService.updateAuthor(id, request)

        then:
        1 * authorRepository.findById(id) >> Optional.of(entity)
        AuthorMapper.updateAuthor(entity, request)
    }

    def "TestUpdateAuthorRequest error"() {
        given:
        def id = random.nextObject(Long)
        def request = random.nextObject(UpdateAuthorRequest)

        when:
        authorService.updateAuthor(id, request)

        then:
        1 * authorRepository.findById(id) >> Optional.empty()
        0 * authorRepository.save(id)
        RuntimeException exception = thrown()
        exception.message == "AUTHOR_NOT_FOUND"

    }

    def "TestDeleteAuthor success"() {
        given:
        def id = random.nextObject(Long)
        def entity = random.nextObject(AuthorEntity)

        when:
        authorService.deleteAuthor(id)

        then:
        1 * authorRepository.findById(id) >> Optional.of(entity)
        1 * authorRepository.save(entity)
        entity.status == DELETED
    }

    def "TestDeleteAuthor error"() {
        given:
        def id = random.nextObject(Long)

        when:
        authorService.deleteAuthor(id)

        then:
        1 * authorRepository.findById(id) >> Optional.empty()
        0 * authorRepository.save()
        RuntimeException exception = thrown()
        exception.message == "AUTHOR_NOT_FOUND"
    }

    def "TestAuthorGetAuthorBooksById"() {
        given:
        def id = random.nextObject(Long)
        def string=random.nextObject(String)
        def authorList = [string ]
        authorRepository.findBookNameByAuthorId(id) >> authorList
        when:

        authorService.getAuthorBooksById(id)

        then:
        1 * authorRepository.findBookNameByAuthorId(id) >> authorList
    }

    def "TestGetAuthors"() {
        given:
        def entity = random.nextObject(AuthorEntity)
        def authorList = [entity]
        authorRepository.findAll() >> authorList

        when:
        authorService.getAuthors()

        then:
        1 * authorRepository.findAll() >> authorList
    }
}