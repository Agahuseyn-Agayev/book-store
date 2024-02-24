package com.example.ingressbookstore.mapper

import com.example.ingressbookstore.entity.AuthorEntity
import com.example.ingressbookstore.entity.BookEntity
import com.example.ingressbookstore.model.enums.Status
import com.example.ingressbookstore.model.request.SaveBookRequest
import com.example.ingressbookstore.model.request.UpdateBookRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static com.example.ingressbookstore.mapper.BookMapper.*
import static com.example.ingressbookstore.mapper.BookMapper.builderBookEntity
import static com.example.ingressbookstore.mapper.BookMapper.builderBookResponse
import static com.example.ingressbookstore.model.enums.Status.*

class TestBookMapper extends Specification{
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestBookResponseBuild"(){
        given:
        def entity=random.nextObject(BookEntity)

        when:
        def variable= builderBookResponse(entity)

        then:
        variable.name==entity.name
        variable.description==entity.description
    }

    def "TestBookEntityBuild"(){
        given:
        def request=random.nextObject(SaveBookRequest)
        def author=random.nextObject(AuthorEntity)

        when:
        def variable= builderBookEntity(request,author)

        then:
        variable.name==request.name
        variable.description==request.description
        variable.author==author
        variable.status== ACTIVE

    }

    def "TestUpdateBook"(){
        given:
        def entity=random.nextObject(BookEntity)
        def request=random.nextObject(UpdateBookRequest)

        when:
        updateBook(entity,request)

        then:
        request.name==entity.name
        request.description==entity.description
    }

    def "TestGetStudents"(){
        given:
        def entity=random.nextObject(BookEntity)

        when:
        def variable=getBooks(entity)

        then:
        variable.name==entity.name
        variable.description==entity.description
        variable.authorId==entity.author.id
    }

}
