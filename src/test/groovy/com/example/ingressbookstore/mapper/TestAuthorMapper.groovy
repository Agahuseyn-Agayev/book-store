package com.example.ingressbookstore.mapper

import com.example.ingressbookstore.entity.AuthorEntity
import com.example.ingressbookstore.model.enums.Status
import com.example.ingressbookstore.model.request.SaveAuthorRequest
import com.example.ingressbookstore.model.request.UpdateAuthorRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static com.example.ingressbookstore.mapper.AuthorMapper.builderAuthorEntity
import static com.example.ingressbookstore.mapper.AuthorMapper.builderAuthorResponse
import static com.example.ingressbookstore.mapper.AuthorMapper.getAuthors
import static com.example.ingressbookstore.mapper.AuthorMapper.updateAuthor
import static com.example.ingressbookstore.model.enums.Status.ACTIVE

class TestAuthorMapper extends Specification {
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestAuthorBuilderResponse"() {
        given:
        def entity = random.nextObject(AuthorEntity)

        when:
        def variable = builderAuthorResponse(entity)

        then:
        variable.name == entity.name
        variable.surname == entity.surname
        variable.age == entity.age

    }

    def "TestAuthorBuilderEntity"() {
        given:
        def request = random.nextObject(SaveAuthorRequest)

        when:

        def variable = builderAuthorEntity(request)

        then:
        variable.name == request.name
        variable.surname == request.surname
        variable.age == request.age
        variable.status== ACTIVE
    }

    def "TestUpdateAuthor"() {
        given:
        def entity = random.nextObject(AuthorEntity)
        def request = random.nextObject(UpdateAuthorRequest)

        when:
        updateAuthor(entity, request)

        then:
        entity.name == request.name
        entity.surname == request.surname
        entity.age == request.age
    }

    def "TestGetAuthors"() {
        given:
        def entity = random.nextObject(AuthorEntity)

        when:
        def variable = getAuthors(entity)

        then:
        variable.name == entity.name
        variable.surname==entity.surname
        variable.age==entity.age
    }
}
